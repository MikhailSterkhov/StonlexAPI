package ru.stonlex.global.database;

import java.sql.SQLException;

public interface RemoteDatabaseSqlHandler {

    void handle() throws SQLException;
}
