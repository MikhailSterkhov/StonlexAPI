package ru.stonlex.global.database.query;

import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.query.type.*;

@RequiredArgsConstructor
public final class RemoteDatabaseQueryInitializer {

    private final String databaseTable;

    public CreateTableQuery createTableQuery() {
        return new CreateTableQuery(databaseTable);
    }

    public DropTableQuery dropTableQuery() {
        return new DropTableQuery(databaseTable);
    }

    public InsertQuery insertQuery() {
        return new InsertQuery(databaseTable);
    }

    public SelectQuery selectQuery() {
        return new SelectQuery(databaseTable);
    }

    public DeleteQuery deleteQuery() {
        return new DeleteQuery(databaseTable);
    }

}
