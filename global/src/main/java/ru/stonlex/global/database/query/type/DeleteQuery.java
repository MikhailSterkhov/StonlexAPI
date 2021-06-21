package ru.stonlex.global.database.query.type;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.query.RemoteDatabaseQuery;
import ru.stonlex.global.database.query.row.ValueQueryRow;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public final class DeleteQuery
        extends RemoteDatabaseQuery<ValueQueryRow> {

    private final String queryFunction = "DELETE FROM";

    private final String databaseTable;

    @Override
    protected void buildQuery(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<ValueQueryRow> queryRows) {

        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("`");

        if (!queryRows.isEmpty()) {

            queryBuilder.append(" WHERE ");
            queryBuilder.append(Joiner.on(" AND ").join(queryRows.stream().map(row -> "`" + row.getName() + "`=?").collect(Collectors.toSet())));
        }
    }
}
