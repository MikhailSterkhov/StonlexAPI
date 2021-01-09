package ru.stonlex.bukkit.gaming.database;

import com.google.common.base.Joiner;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import ru.stonlex.bukkit.gaming.GameProcess;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.global.mysql.MysqlConnection;
import ru.stonlex.global.mysql.MysqlDatabaseConnection;
import ru.stonlex.global.utility.query.ResponseHandler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GamingDatabase {

    protected final MysqlDatabaseConnection databaseConnection;
    protected final String gamingDatabaseName, gamingTableName;

    @Getter
    protected Map<String, ResponseHandler<Object, GamingPlayer>> playerDataMap = new LinkedHashMap<>();


    public static GamingDatabase CURRENT;

    /**
     * Создать игровую базу данных
     *
     * @param mysqlConnection    - основное подключение к Mysql
     * @param gamingDatabaseName - имя игровой базы данных
     * @param gamingTableName    - имя игровой таблицы, в которой будут хранится данные игроков
     */
    public static GamingDatabase newDatabase(@NonNull MysqlConnection mysqlConnection,
                                             @NonNull String gamingDatabaseName,
                                             @NonNull String gamingTableName) {

        MysqlDatabaseConnection mysqlDatabaseConnection
                = mysqlConnection.createDatabaseScheme(gamingDatabaseName, true);

        GamingDatabase gamingDatabase = new GamingDatabase(mysqlDatabaseConnection, gamingDatabaseName, gamingTableName);
        gamingDatabase.addPlayerDataKey("Name", gamingPlayer -> gamingPlayer.getPlayerName().toLowerCase());

        return (CURRENT = gamingDatabase);
    }

    /**
     * Добавить секцию хранения данных игрока
     *
     * @param playerDataKey          - ключ секции в дате игрока
     * @param playerDataValueHandler - обработчик даты для хранения даты
     */
    public GamingDatabase addPlayerDataKey(@NonNull String playerDataKey,
                                           @NonNull ResponseHandler<Object, GamingPlayer> playerDataValueHandler) {

        playerDataMap.put(playerDataKey, playerDataValueHandler);
        return this;
    }

    /**
     * Инициализация игровой базы данных
     * для самого процесса игры
     *
     * @param gameProcess - процесс игры
     */
    public void build(@NonNull GameProcess gameProcess) {
        StringBuilder tableStructureBuilder = new StringBuilder();
        {
            playerDataMap.forEach((columnName, responseHandler) -> {
                Class<?> columnValueType = null;

                try {
                    columnValueType = responseHandler.getClass().getMethod("handleResponse", Object.class).getReturnType();
                }

                catch (NoSuchMethodException exception) {
                    exception.printStackTrace();
                }

                if (!columnValueType.isAssignableFrom(Serializable.class)) {
                    return;
                }

                tableStructureBuilder.append("`").append(columnName).append("`")
                        .append(" ").append(columnValueType.getSimpleName().toUpperCase()).append(" NOT NULL");

                if (columnName.equalsIgnoreCase("Name")) {
                    tableStructureBuilder.append(" PRIMARY KEY");
                }

                tableStructureBuilder.append(", ");
            });
        }

        databaseConnection.createTable(gamingTableName, tableStructureBuilder.toString().substring(0, tableStructureBuilder.length() - 2));
        gameProcess.setGamingDatabase(this);
    }


    /**
     * Сохранить данные игрока в игровую
     * базу данных
     *
     * @param gamingPlayer - игровой пользователь, которого сохранить
     */
    public void savePlayer(@NonNull GamingPlayer gamingPlayer) {
        String insertQueryKeys;
        {
            insertQueryKeys = ("(`").concat(Joiner.on("`, `").join(playerDataMap.keySet()).concat("`)")).concat(" VALUES (")
                    .concat(StringUtils.repeat("?, ", playerDataMap.size()));

            insertQueryKeys = insertQueryKeys.substring(0, insertQueryKeys.length() - 2).concat(")");
        }


        String duplicateKeyQuery;
        {
            StringBuilder stringBuilder = new StringBuilder();

            for (String playerDataKey : playerDataMap.keySet().stream().skip(1).collect(Collectors.toList())) {
                stringBuilder.append("`").append(playerDataKey).append("`=?, ");
            }

            duplicateKeyQuery = stringBuilder.substring(0, stringBuilder.length() - 2);
        }


        Object[] queryArguments = new Object[playerDataMap.size() * 2 - 1];
        {
            AtomicInteger indexCounter = new AtomicInteger(0);
            playerDataMap.forEach((playerDataKey, responseHandler) -> {

                int index = indexCounter.getAndIncrement();
                Object value = responseHandler.handleResponse(gamingPlayer);

                queryArguments[index] = value;

                if (index > 0) {
                    queryArguments[index + playerDataMap.size() - 1] = value;
                }
            });
        }

        databaseConnection.execute(true, "INSERT INTO `" + gamingTableName + "` " + insertQueryKeys
                + " ON DUPLICATE KEY UPDATE " + duplicateKeyQuery, queryArguments);
    }

    /**
     * Выгрузить и инициализировать данные игрока
     * из игровой базу данных
     *
     * @param gamingPlayer - игровой пользователь, которого сохранить
     */
    public void loadPlayer(@NonNull GamingPlayer gamingPlayer) {
        databaseConnection.executeQuery(true, "SELECT * FROM `" + gamingTableName + "` WHERE `Name`=?",
                resultSet -> {

                    if (!resultSet.next()) {
                        return null;
                    }

                    playerDataMap.forEach((playerDataKey, responseHandler) -> {

                        try {
                            gamingPlayer.setPlayerData(playerDataKey, resultSet.getObject(playerDataKey));
                        }

                        catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                    });

                    return null;
                }, gamingPlayer.getPlayerName().toLowerCase());
    }

}
