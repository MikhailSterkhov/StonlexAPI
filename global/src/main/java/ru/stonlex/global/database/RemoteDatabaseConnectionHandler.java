package ru.stonlex.global.database;

import lombok.NonNull;
import ru.stonlex.global.database.event.RemoteDatabaseEventHandler;
import ru.stonlex.global.database.query.RemoteDatabaseQueryInitializer;

import java.sql.Connection;
import java.util.Map;

public interface RemoteDatabaseConnectionHandler {

    RemoteDatabaseExecuteHandler getExecuteHandler();

    RemoteDatabaseConnectionFields getConnectionFields();

    Connection getConnection();

    RemoteDatabaseEventHandler getEventHandler();

    Map<String, RemoteDatabaseTable> getDatabaseTables();

    RemoteDatabaseTable getTable(@NonNull String tableName);

    void setEventHandler(@NonNull RemoteDatabaseEventHandler eventHandler);

    void handleConnection();

    void handleDisconnect();

    void reconnect();


    default RemoteDatabaseQueryInitializer newDatabaseQuery(@NonNull String databaseTable) {
        return RemoteDatabasesApi.getInstance().newDatabaseQuery(databaseTable);
    }

}
