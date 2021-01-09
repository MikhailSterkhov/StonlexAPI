package ru.stonlex.global.mysql;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MysqlConnectionBuilder {

    private int mysqlPort;

    private String mysqlHost, mysqlUsername, mysqlPassword;

    private final List<String> databaseConnectionList = new LinkedList<>();


// ================================================================================================================== //

    public static MysqlConnectionBuilder newMysqlBuilder() {
        return new MysqlConnectionBuilder();
    }

// ================================================================================================================== //


    /**
     * Установить имя сервера для соединения
     * с базой данных
     *
     * @param mysqlHost - имя сервера
     */
    public MysqlConnectionBuilder mysqlHost(String mysqlHost) {
        this.mysqlHost = mysqlHost;

        return this;
    }

    /**
     * Установить имя пользователя для соединения
     * с базой данных
     *
     * @param mysqlUsername - имя пользователя
     */
    public MysqlConnectionBuilder mysqlUsername(String mysqlUsername) {
        this.mysqlUsername = mysqlUsername;

        return this;
    }

    /**
     * Установить пароль пользователя для соединения
     * с базой данных
     *
     * @param mysqlPassword - пароль пользователя
     */
    public MysqlConnectionBuilder mysqlPassword(String mysqlPassword) {
        this.mysqlPassword = mysqlPassword;

        return this;
    }

    /**
     * Установить порт сервера для соединения
     * с базой данных
     *
     * @param mysqlPort - порт сервера
     */
    public MysqlConnectionBuilder mysqlPort(int mysqlPort) {
        this.mysqlPort = mysqlPort;

        return this;
    }

    /**
     * Добавить подключение к схеме из базы
     * данных в общее подключение к mysql
     *
     * @param databaseName - имя сервера
     */
    public MysqlConnectionBuilder databaseConnect(String databaseName) {
        databaseConnectionList.add(databaseName);

        return this;
    }


    /**
     * Преобразовать все собранные данные
     * в единое соединение с базой данных mysql
     */
    public MysqlConnection buildConnection() {
        MysqlConnection mysqlConnection
                = MysqlConnection.createMysqlConnection(mysqlHost, mysqlUsername, mysqlPassword, mysqlPort);

        for (String databaseName : databaseConnectionList) {
            mysqlConnection.createConnectionToDatabase(databaseName);
        }

        return mysqlConnection;
    }

}
