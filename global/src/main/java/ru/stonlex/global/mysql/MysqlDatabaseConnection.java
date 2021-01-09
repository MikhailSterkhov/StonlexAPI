package ru.stonlex.global.mysql;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import lombok.*;
import org.apache.commons.lang3.SerializationUtils;
import ru.stonlex.global.utility.query.AsyncUtil;
import ru.stonlex.global.utility.query.ThrowableResponseHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MysqlDatabaseConnection {

    private final MysqlDataSource mysqlDataSource;

    @Getter
    private Connection connection;


// ================================================================================================================== //

    /**
     * Создать подключение к схеме базы данных
     *
     * @param databaseName - имя схемы базы данных
     * @param mysqlDataSource - хранилище и обработчик данных базы
     */
    public static MysqlDatabaseConnection createDatabaseConnection(String databaseName,
                                                                   @NonNull MysqlDataSource mysqlDataSource) {
        try {
            mysqlDataSource = SerializationUtils.clone(mysqlDataSource);

            if (databaseName != null) {
                mysqlDataSource.setDatabaseName(databaseName);
            }

            return new MysqlDatabaseConnection(mysqlDataSource, mysqlDataSource.getConnection());
        }

        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Создать подключение к схеме базы данных
     *
     * @param databaseName - имя схемы базы данных
     * @param mysqlConnection - основное соединение с базой данных
     */
    public static MysqlDatabaseConnection createDatabaseConnection(@NonNull String databaseName,
                                                                   @NonNull MysqlConnection mysqlConnection) {

        return createDatabaseConnection(databaseName, mysqlConnection.getMysqlDataSource());
    }

// ================================================================================================================== //



    /**
     * Обновление и восстановление соединения
     * при его закрытии
     */
    public void refreshConnection() {
        try {

            if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
                return;
            }

            this.connection = mysqlDataSource.getConnection();
        }

        catch (SQLException exception) {
            throw new RuntimeException("Все, вешайся, капут, завяли помидоры с Mysql");
        }
    }

    /**
     * Выолнение SQL запроса для обновления или создания
     * данных в таблице
     *
     * @param asyncQuery - разрешение на асинхронный запрос
     * @param mysqlQuery - запрос в базу данных
     * @param queryElements - элементы для определения переменных в запросе
     */
    public void execute(boolean asyncQuery,

                        @NonNull String mysqlQuery,
                        @NonNull Object... queryElements) {

        Runnable command = () -> {
            refreshConnection();

            try (MysqlStatement mysqlStatement = new MysqlStatement(connection, mysqlQuery, queryElements)) {

                mysqlStatement.execute();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        };

        if (asyncQuery) {
            AsyncUtil.submitAsync(command);
            return;
        }

        command.run();
    }

    /**
     * Выолнение SQL запроса с получением {@link ResultSet}
     *
     * @param asyncQuery - разрешение на асинхронный запрос
     * @param mysqlQuery - запрос в базу данных
     * @param responseHandler - обработчик ответа запроса
     * @param queryElements - элементы для определения переменных в запросе
     */
    @SneakyThrows
    public <T> T executeQuery(boolean asyncQuery,

                              @NonNull String mysqlQuery,
                              @NonNull ThrowableResponseHandler<T, ResultSet, SQLException> responseHandler,
                              @NonNull Object... queryElements) {

        Supplier<T> supplier = () -> {
            refreshConnection();

            try (MysqlStatement mysqlStatement = new MysqlStatement(connection, mysqlQuery, queryElements)) {

                return responseHandler.handleResponse(mysqlStatement.getResultSet());
            }

            catch (SQLException exception) {
                exception.printStackTrace();

                return null;
            }
        };

        if (asyncQuery) {
            return AsyncUtil.supplyAsyncFuture(supplier);
        }

        return supplier.get();
    }

    /**
     * Создать таблицу в схеме базы данных
     *
     * @param asyncQuery - разрешение на асинхронный запрос
     * @param mysqlTable - имя таблицы
     * @param tableStructure - структура таблицы
     */
    public MysqlDatabaseConnection createTable(boolean asyncQuery,

                                               @NonNull String mysqlTable,
                                               @NonNull String tableStructure) {

        execute(asyncQuery, String.format("CREATE TABLE IF NOT EXISTS `%s` (%s)", mysqlTable, tableStructure));
        return this;
    }

    /**
     * Асинхронно создать таблицу в схеме базы данных
     *
     * @param mysqlTable - имя таблицы
     * @param tableStructure - структура таблицы
     */
    public MysqlDatabaseConnection createTable(@NonNull String mysqlTable,
                                               @NonNull String tableStructure) {

        execute(true, String.format("CREATE TABLE IF NOT EXISTS `%s` (%s) COLLATE='utf8mb4_unicode_ci'", mysqlTable, tableStructure));
        return this;
    }

}
