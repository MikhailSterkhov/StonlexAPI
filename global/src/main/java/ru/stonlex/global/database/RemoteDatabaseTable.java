package ru.stonlex.global.database;

import ru.stonlex.global.database.query.RemoteDatabaseQueryInitializer;

public interface RemoteDatabaseTable {

    RemoteDatabaseConnectionHandler getConnectionHandler();

    String getName();

    default RemoteDatabaseQueryInitializer newDatabaseQuery() {
        return new RemoteDatabaseQueryInitializer(getName());
    }
}
