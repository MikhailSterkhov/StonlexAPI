package ru.stonlex.global.database;

import lombok.NonNull;
import ru.stonlex.global.database.event.RemoteDatabaseEventListener;
import ru.stonlex.global.database.query.RemoteDatabaseQueryFactory;
import ru.stonlex.global.database.query.type.CreateTableQuery;

import java.sql.Connection;
import java.util.Map;
import java.util.function.Consumer;

public interface RemoteDatabaseConnectionHandler {

    @NonNull RemoteDatabaseExecuteHandler getExecuteHandler();

    @NonNull Connection getConnection();

    @NonNull RemoteDatabaseEventListener getEventHandler();

    @NonNull Map<String, RemoteDatabaseTable> getDatabaseTables();

    @NonNull RemoteDatabaseTable getTable(@NonNull String tableName);


    void setEventHandler(@NonNull RemoteDatabaseEventListener eventListener);

    void handleConnection();

    void handleDisconnect();

    void reconnect();


    default @NonNull RemoteDatabaseQueryFactory newDatabaseQuery(@NonNull String databaseTable) {
        return RemoteDatabasesApi.getInstance().createQueryInit(databaseTable);
    }

    /**
     * Создание таблицы и ее получение из базы SQL
     * при помощи функции CREATE TABLE.
     * <p>
     * P.S.: Выполнять запрос в обработке НЕ НУЖНО!
     * так как он выполняется уже в процессе
     * этого метода
     *
     * @param tableName    - Название таблицы
     * @param queryHandler - Обработчик запроса
     */
    default @NonNull RemoteDatabaseTable createOrGetTable(@NonNull String tableName, @NonNull Consumer<CreateTableQuery> queryHandler) {
        RemoteDatabaseTable remoteDatabaseTable = getTable(tableName);

        if (remoteDatabaseTable != null) {
            return remoteDatabaseTable;
        }

        CreateTableQuery createTableQuery = newDatabaseQuery(tableName).createTableQuery();
        createTableQuery.setCanCheckExists(true);

        queryHandler.accept(createTableQuery);

        createTableQuery.executeSync(this);

        return getTable(tableName);
    }

}
