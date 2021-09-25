package ru.stonlex.global.database.query.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.query.RemoteDatabaseQuery;
import ru.stonlex.global.database.query.row.TypedQueryRow;

import java.util.LinkedList;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class DropTableQuery
        extends RemoteDatabaseQuery<TypedQueryRow> {

    @NonNull String queryFunction = "DROP TABLE";
    @NonNull String databaseTable;

    @Override
    protected void handle(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<TypedQueryRow> queryRows) {
        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("`");
    }

}
