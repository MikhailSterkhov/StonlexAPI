package ru.stonlex.global.database;

import lombok.NonNull;
import ru.stonlex.global.database.query.RemoteDatabaseQueryResult;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

public interface RemoteDatabaseExecuteHandler {

    Connection getConnection();

    void refreshConnection();


    void executeUpdate(boolean sync, @NonNull String query, Object... values);

    CompletableFuture<RemoteDatabaseQueryResult> executeQuery(boolean sync, @NonNull String query, Object... values);

}
