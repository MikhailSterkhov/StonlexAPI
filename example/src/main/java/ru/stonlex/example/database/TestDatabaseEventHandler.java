package ru.stonlex.example.database;

import lombok.NonNull;
import ru.stonlex.global.database.RemoteDatabaseConnectionFields;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.event.RemoteDatabaseEventHandler;

public class TestDatabaseEventHandler
        implements RemoteDatabaseEventHandler {

    @Override
    public void onDatabaseConnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        RemoteDatabaseConnectionFields connectionFields = connectionHandler.getConnectionFields();

        System.out.println("Database " + connectionFields.getScheme() + " has been connected!");
    }

    @Override
    public void onDatabaseReconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
    }

    @Override
    public void onDatabaseDisconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
    }

    @Override
    public void onQueryExecuted(@NonNull String query) {
        System.out.println("Query execute: " + query + "");
    }

}
