package ru.stonlex.global.database.query;

import lombok.NonNull;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public abstract class RemoteDatabaseQuery<Q extends RemoteDatabaseQueryRow> {

    private final LinkedList<Q> queryRowsList = new LinkedList<>();

    /**
     * Добавить значение к выполняющемуся
     * SQL запросу.
     *
     * @param queryRow - значение запроса.
     */
    public RemoteDatabaseQuery<Q> queryRow(@NonNull Q queryRow) {
        queryRowsList.add(queryRow);
        return this;
    }

    protected abstract String getQueryFunction();

    protected abstract void buildQuery(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<Q> queryRows);


    protected String queryString() {
        StringBuilder stringBuilder = new StringBuilder(getQueryFunction());
        stringBuilder.append(" ");

        buildQuery(stringBuilder, queryRowsList);

        return stringBuilder.toString();
    }

    public void executeSync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        connectionHandler.getExecuteHandler().executeUpdate(true, queryString(), queryRowsList.stream().map(Q::toQueryValue).toArray());
    }

    public void executeAsync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        connectionHandler.getExecuteHandler().executeUpdate(false, queryString(), queryRowsList.stream().map(Q::toQueryValue).toArray());
    }

    public CompletableFuture<RemoteDatabaseQueryResult> executeQuerySync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        return connectionHandler.getExecuteHandler().executeQuery(false, queryString(), queryRowsList.stream().map(Q::toQueryValue).toArray());
    }

    public CompletableFuture<RemoteDatabaseQueryResult> executeQueryAsync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        return connectionHandler.getExecuteHandler().executeQuery(true, queryString(), queryRowsList.stream().map(Q::toQueryValue).toArray());

    }

}
