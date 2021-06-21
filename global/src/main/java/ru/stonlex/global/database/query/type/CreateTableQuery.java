package ru.stonlex.global.database.query.type;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.query.RemoteDatabaseQuery;
import ru.stonlex.global.database.query.RemoteDatabaseQueryResult;
import ru.stonlex.global.database.query.row.TypedQueryRow;
import ru.stonlex.global.database.table.RemoteDatabaseTableData;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public final class CreateTableQuery
        extends RemoteDatabaseQuery<TypedQueryRow> {

    private final String queryFunction = "CREATE TABLE";
    private final String databaseTable;

    private boolean canCheckExists;

    public CreateTableQuery setCanCheckExists(boolean canCheckExists) {
        this.canCheckExists = canCheckExists;

        return this;
    }

    @Override
    protected void buildQuery(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<TypedQueryRow> queryRows) {
        if (canCheckExists) {
            queryBuilder.append("IF NOT EXISTS ");
        }

        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("` (");

        queryBuilder.append(Joiner.on(", ").join(queryRows.stream()
                .map(TypedQueryRow::toString)
                .collect(Collectors.toCollection(LinkedList::new))
                .iterator()));

        queryBuilder.append(")");
    }

    @Override
    public void executeSync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        addConnectionTable(connectionHandler);

        super.executeSync(connectionHandler);
    }

    @Override
    public void executeAsync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        addConnectionTable(connectionHandler);

        super.executeAsync(connectionHandler);
    }

    @Override
    public CompletableFuture<RemoteDatabaseQueryResult> executeQuerySync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        addConnectionTable(connectionHandler);

        return super.executeQuerySync(connectionHandler);
    }

    @Override
    public CompletableFuture<RemoteDatabaseQueryResult> executeQueryAsync(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        addConnectionTable(connectionHandler);

        return super.executeQueryAsync(connectionHandler);
    }

    private void addConnectionTable(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        connectionHandler.getDatabaseTables().put(databaseTable.toLowerCase(), new RemoteDatabaseTableData(connectionHandler, databaseTable));
    }

}
