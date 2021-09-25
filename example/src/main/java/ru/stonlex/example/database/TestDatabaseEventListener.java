package ru.stonlex.example.database;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.event.RemoteDatabaseEventListener;

import java.sql.Connection;

public class TestDatabaseEventListener implements RemoteDatabaseEventListener {

    @Override
    @SneakyThrows
    public void onDatabaseConnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        Connection connection = connectionHandler.getConnection();

        System.out.println("Database " + connection.getSchema() + " has been connected!");
    }

    @Override
    @SneakyThrows
    public void onDatabaseReconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        Connection connection = connectionHandler.getConnection();

        System.out.println("Connection with " + connection.getSchema() + " has been reconnected!");
    }

    @Override
    @SneakyThrows
    public void onDatabaseDisconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        Connection connection = connectionHandler.getConnection();

        System.out.println("Connection with " + connection.getSchema() + " has been disconnected!");
    }

    @Override
    public void onQueryExecuted(@NonNull RemoteDatabaseConnectionHandler connectionHandler, @NonNull String query) {
        System.out.println("output_query=[" + query + "]");
    }

}
