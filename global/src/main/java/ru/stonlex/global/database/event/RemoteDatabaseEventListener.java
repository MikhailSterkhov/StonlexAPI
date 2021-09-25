package ru.stonlex.global.database.event;

import lombok.NonNull;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;

public interface RemoteDatabaseEventListener {

    void onDatabaseConnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler);
    void onDatabaseReconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler);
    void onDatabaseDisconnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler);

    void onQueryExecuted(@NonNull RemoteDatabaseConnectionHandler connectionHandler, @NonNull String query);

}
