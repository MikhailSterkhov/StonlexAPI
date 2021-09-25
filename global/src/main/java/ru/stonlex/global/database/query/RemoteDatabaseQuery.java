package ru.stonlex.global.database.query;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class RemoteDatabaseQuery<Q extends RemoteDatabaseQueryRow> {

    @NonNull LinkedList<Q> queryRowsList = new LinkedList<>();

    /**
     * Добавить значение к выполняющемуся
     * SQL запросу.
     *
     * @param queryRow - значение запроса.
     */
    public RemoteDatabaseQuery<Q> push(@NonNull Q queryRow) {
        queryRowsList.add(queryRow);
        return this;
    }

    protected abstract String getQueryFunction();

    protected abstract void handle(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<Q> queryRows);


    protected @NonNull String buildToString() {
        StringBuilder stringBuilder = new StringBuilder(getQueryFunction());
        stringBuilder.append(" ");

        handle(stringBuilder, queryRowsList);

        return stringBuilder.toString();
    }

    public void executeSync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        connectionHandler.getExecuteHandler().executeUpdate(true, buildToString(), queryRowsList.stream().map(Q::value).toArray());
    }

    public void executeAsync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        connectionHandler.getExecuteHandler().executeUpdate(false, buildToString(), queryRowsList.stream().map(Q::value).toArray());
    }

    public @NonNull CompletableFuture<RemoteDatabaseQueryResult> executeQuerySync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        return connectionHandler.getExecuteHandler().executeQuery(false, buildToString(), queryRowsList.stream().map(Q::value).toArray());
    }

    public @NonNull CompletableFuture<RemoteDatabaseQueryResult> executeQueryAsync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        return connectionHandler.getExecuteHandler().executeQuery(true, buildToString(), queryRowsList.stream().map(Q::value).toArray());
    }

}
