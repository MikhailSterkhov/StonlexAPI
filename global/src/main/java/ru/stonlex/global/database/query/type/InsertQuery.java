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
public final class InsertQuery
        extends RemoteDatabaseQuery<ValueQueryRow> {

    private final String queryFunction = "INSERT INTO";
    private final String databaseTable;

    @Override
    protected void buildQuery(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<ValueQueryRow> queryRows) {
        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("` ");

        queryBuilder.append("(");

        // Build rows.
        queryBuilder.append(Joiner.on(", ").join(queryRows.stream()
                .map(valueQueryRow -> "`" + valueQueryRow.getName() + "`").collect(Collectors.toList())));

        // Build rows values.
        queryBuilder.append(") VALUES (");

        queryBuilder.append(Joiner.on(", ").join(queryRows.stream()
                .map(valueQueryRow -> "?").collect(Collectors.toList())));

        queryBuilder.append(")");
    }

}
