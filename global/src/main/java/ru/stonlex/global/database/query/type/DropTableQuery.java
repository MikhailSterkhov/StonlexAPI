package ru.stonlex.global.database.query.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.query.RemoteDatabaseQuery;
import ru.stonlex.global.database.query.row.TypedQueryRow;

import java.util.LinkedList;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public final class DropTableQuery
        extends RemoteDatabaseQuery<TypedQueryRow> {

    private final String queryFunction = "DROP TABLE";

    private final String databaseTable;

    @Override
    protected void buildQuery(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<TypedQueryRow> queryRows) {
        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("`");
    }

}
