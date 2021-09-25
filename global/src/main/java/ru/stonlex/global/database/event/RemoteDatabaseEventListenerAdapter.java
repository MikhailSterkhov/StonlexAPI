package ru.stonlex.global.database.event;

import lombok.NonNull;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;

public abstract class RemoteDatabaseEventListenerAdapter implements RemoteDatabaseEventListener {

    public void onDatabaseConnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {}
    public void onDatabaseReconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {}
    public void onDatabaseDisconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {}

    public void onQueryExecuted(@NonNull RemoteDatabaseConnectionHandler connectionHandler, @NonNull String query) {}

}
