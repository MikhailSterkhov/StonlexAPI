package ru.stonlex.global.database;

import lombok.NonNull;
import ru.stonlex.global.database.query.RemoteDatabaseQueryFactory;
import ru.stonlex.global.database.query.RemoteDatabaseQueryResult;
import ru.stonlex.global.database.query.type.InsertQuery;

import java.util.function.Consumer;

public interface RemoteDatabaseTable {

    @NonNull RemoteDatabaseConnectionHandler getConnectionHandler();
    @NonNull String getName();

    /**
     * Выполняет запрос в SQL базу функцией INSERT.
     * <p>
     * P.S.: Выполнять запрос в обработке НЕ НУЖНО!
     * так как он выполняется уже в процессе
     * этого метода
     *
     * @param queryHandler - Обработчик запроса
     */
    default void insert(@NonNull Consumer<InsertQuery> queryHandler) {
        InsertQuery insertQuery = newDatabaseQuery().insertQuery();
        queryHandler.accept(insertQuery);

        insertQuery.executeSync(getConnectionHandler());
    }

    /**
     * Выполняет запрос в SQL базу функцией SELECT,
     * получая все данные из таблицы .
     *
     * @param resultHandler - Обработчик запроса
     */
    default void selectAll(@NonNull Consumer<RemoteDatabaseQueryResult> resultHandler) {
        newDatabaseQuery().selectQuery().executeQuerySync(getConnectionHandler())
                .thenAccept(resultHandler);
    }

    /**
     * Полная очистка таблицы от всех
     * данных в ней
     */
    default void clear() {
        newDatabaseQuery().deleteQuery().executeSync(getConnectionHandler());
    }

    /**
     * Удаление таблицы из базы
     */
    default void drop() {
        newDatabaseQuery().dropTableQuery().executeSync(getConnectionHandler());

        getConnectionHandler().getDatabaseTables().remove(getName().toLowerCase());
    }

    default @NonNull RemoteDatabaseQueryFactory newDatabaseQuery() {
        return new RemoteDatabaseQueryFactory(getName());
    }
}
