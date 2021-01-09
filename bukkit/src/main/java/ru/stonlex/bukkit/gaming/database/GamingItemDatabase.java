package ru.stonlex.bukkit.gaming.database;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.gaming.GameAPI;
import ru.stonlex.bukkit.gaming.GameProcess;
import ru.stonlex.bukkit.gaming.item.GamingItem;
import ru.stonlex.bukkit.gaming.item.GamingItemCategory;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;
import ru.stonlex.global.mysql.MysqlConnection;
import ru.stonlex.global.mysql.MysqlDatabaseConnection;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GamingItemDatabase {

    protected @NonNull MysqlDatabaseConnection databaseConnection;
    protected @NonNull String gamingDatabaseName, gamingTableName;


    public static GamingItemDatabase CURRENT;

    /**
     * Создать базу данных для игровых предметов
     *
     * @param mysqlConnection    - основное подключение к Mysql
     * @param gamingDatabaseName - имя игровой базы данных
     * @param gamingTableName    - имя игровой таблицы, в которой будут хранится предметы игроков
     */
    public static GamingItemDatabase newDatabase(@NonNull MysqlConnection mysqlConnection,
                                                 @NonNull String gamingDatabaseName,
                                                 @NonNull String gamingTableName) {

        MysqlDatabaseConnection mysqlDatabaseConnection
                = mysqlConnection.createDatabaseScheme(gamingDatabaseName, true);

        return (CURRENT = new GamingItemDatabase(mysqlDatabaseConnection, gamingDatabaseName, gamingTableName));
    }

    /**
     * Инициализация игровой базы данных
     * для самого процесса игры
     *
     * @param gameProcess - процесс игры
     */
    public void build(@NonNull GameProcess gameProcess) {
        String gameName = GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_NAME).asString();

        databaseConnection.createTable(gameName + "_ItemSelection", "`Name` VARCHAR(16) NOT NULL, `Category` INT NOT NULL, `Item` INT NOT NULL, PRIMARY KEY (`Name`, `Category`) USING BTREE");
        databaseConnection.createTable(gameName + "_ItemPurchased", "`Name` VARCHAR(16) NOT NULL, `Category` INT NOT NULL, `Item` INT NOT NULL");

        gameProcess.setItemDatabase(this);
    }

    /**
     * Занести купленный игровой предмет
     * в базу данных игрока
     *
     * @param gamingPlayer - игрок, который купил
     * @param gamingItem   - купленный игровой предмет
     */
    public void purchase(@NonNull GamingPlayer gamingPlayer,
                         @NonNull GamingItem gamingItem) {

        String gameName = GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_NAME).asString();

        databaseConnection.execute(true, String.format("INSERT INTO `%s_ItemPurchased` VALUES (?,?,?)", gameName),
                gamingPlayer.getPlayerName().toLowerCase(), gamingItem.getItemCategory().getId(), gamingItem.getId());
    }

    /**
     * Занести выбранный игровой предмет
     * в базу данных игрока
     *
     * @param gamingPlayer - игрок, который выбрал
     * @param gamingItem   - выбранный игровой предмет
     */
    public void select(@NonNull GamingPlayer gamingPlayer,
                       @NonNull GamingItem gamingItem) {

        String gameName = GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_NAME).asString();

        databaseConnection.execute(true, String.format("INSERT INTO `%s_ItemSelection` VALUES (?,?,?) ON DUPLICATE KEY UPDATE `Item`=?", gameName),
                gamingPlayer.getPlayerName().toLowerCase(), gamingItem.getItemCategory().getId(), gamingItem.getId());
    }

    /**
     * Получить выбранный игровой предмет
     * игрока из указанной категории
     *
     * @param gamingPlayer       - игрок
     * @param gamingItemCategory - категория
     */
    public GamingItem getItemSelection(@NonNull GamingPlayer gamingPlayer,
                                       @NonNull GamingItemCategory gamingItemCategory) {

        String gameName = GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_NAME).asString();

        return databaseConnection.executeQuery(true, String.format("SELECT * FROM `%s_ItemSelection` WHERE `Name`=? AND `Category`=?", gameName), resultSet -> {

            if (!resultSet.next()) {
                return null;
            }

            int itemId = resultSet.getInt("Item");
            return gamingItemCategory.getGamingItem(itemId);

        }, gamingPlayer.getPlayerName().toLowerCase(), gamingItemCategory.getId());
    }

    /**
     * Получить список купленных игровых
     * предметов игрока
     *
     * @param gamingPlayer - игровой пользователь
     */
    public Multimap<GamingItemCategory, GamingItem> getItemPurchased(@NonNull GamingPlayer gamingPlayer) {
        String gameName = GameAPI.SETTINGS.getSetting(GamingSettingType.GAME_NAME).asString();

        return databaseConnection.executeQuery(true, String.format("SELECT * FROM `%s_ItemPurchased` WHERE `Name`=?", gameName), resultSet -> {
            Multimap<GamingItemCategory, GamingItem> purchasedItemMap = HashMultimap.create();

            while (resultSet.next()) {
                GamingItemCategory itemCategory = GamingItemCategory.of(resultSet.getInt("Category"));

                if (itemCategory == null) {
                    continue;
                }

                purchasedItemMap.put(itemCategory, itemCategory.getGamingItem(resultSet.getInt("Item")));
            }

            return purchasedItemMap;
        }, gamingPlayer.getPlayerName().toLowerCase());
    }

}
