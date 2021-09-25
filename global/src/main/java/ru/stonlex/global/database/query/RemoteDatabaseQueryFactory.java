package ru.stonlex.global.database.query;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.query.type.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class RemoteDatabaseQueryFactory {

    @NonNull String databaseTable;

    public @NonNull CreateTableQuery createTableQuery() {
        return new CreateTableQuery(databaseTable);
    }

    public @NonNull DropTableQuery dropTableQuery() {
        return new DropTableQuery(databaseTable);
    }

    public @NonNull InsertQuery insertQuery() {
        return new InsertQuery(databaseTable);
    }

    public @NonNull SelectQuery selectQuery() {
        return new SelectQuery(databaseTable);
    }

    public @NonNull DeleteQuery deleteQuery() {
        return new DeleteQuery(databaseTable);
    }

}
