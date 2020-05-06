package ru.stonlex.global.mysql;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import lombok.Getter;
import ru.stonlex.global.mysql.execution.MysqlExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MysqlConnection {

    /**
     * Опять же, этот код старый, и переписывать его мне было
     * попросту лень, да и тем более, он прекрасно работает.
     *
     * Если кому-то он неудобен, то система как бы не особо сложная,
     * поэтому можно и самому ее написать
     */

    private final MysqlDataSource dataSource = new MysqlDataSource();

    @Getter
    private final MysqlExecutor executor = new MysqlExecutor(this);


    @Getter
    private Connection connection;

    /**
     * Инициализация и создание MySQL-Соединения
     *
     * @param host - Хост.
     * @param port - Порт.
     * @param username - Имя пользователя.
     * @param password - Пароль.
     * @param database - База данных, схема.
     * @param tableMap - Таблицы с их аргументами, которые нужно создать
     *               при условии, что их не существует.
     */
    private MysqlConnection(String host, int port, String username, String password, String database,
                            Map<String, String> tableMap) {

        dataSource.setServerName(host);
        dataSource.setPort(port);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(database);

        dataSource.setEncoding("UTF-8");

        try {
            this.dataSource.setAutoReconnect(true);
            this.connection = dataSource.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        getExecutor().execute(true, String.format("CREATE DATABASE IF NOT EXISTS `%s`", database));

        tableMap.forEach((table, value) -> {
            String query = String.format("CREATE TABLE IF NOT EXISTS `%s` (%s)", table, value);

            getExecutor().execute(true, query);
        });
    }

    /**
     * Обновление и восстановление соединения
     * при его закрытии
     */
    public void refreshConnection() {
        try {
            if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
                return;
            }
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Все нахуй обосралось с MySql");
        }
    }


    /**
     * Вызов нового билдера соединения с MySQL
     */
    public static SQLBuilder newBuilder() {
        return new SQLBuilder();
    }


    @Getter
    public static class SQLBuilder {

        private String host = "localhost",
                password = "",
                username = "root",
                database = "mysql";

        private int port = 3306;


        private final Map<String, String> tableMap = new HashMap<>();


        /**
         * Инициализация базы данных, схемы
         *
         * @param database - База данных, схема
         */
        public SQLBuilder setDatabase(String database) {
            this.database = database;

            return this;
        }

        /**
         * Инициализация пароля
         *
         * @param password - Пароль
         */
        public SQLBuilder setPassword(String password) {
            this.password = password;

            return this;
        }

        /**
         * Инициализация порта
         *
         * @param port - Порт
         */
        public SQLBuilder setPort(int port) {
            this.port = port;

            return this;
        }

        /**
         * Инициализация хоста
         *
         * @param host - Хост
         */
        public SQLBuilder setHost(String host) {
            this.host = host;

            return this;
        }

        /**
         * Инициализация имени пользователя
         *
         * @param username - Имя пользователя
         */
        public SQLBuilder setUsername(String username) {
            this.username = username;

            return this;
        }

        /**
         * Создание таблицы
         *
         * @param table - Имя таблицы
         * @param value - Аргументы табллицы
         */
        public SQLBuilder createTable(String table, String value) {
            this.tableMap.put(table, value);

            return this;
        }

        /**
         * Построить SQL соединение
         */
        public MysqlConnection build() {
            return new MysqlConnection(host, port, username, password, database, tableMap);
        }

    }

}
