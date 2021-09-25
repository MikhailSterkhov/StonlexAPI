package ru.stonlex.global.database.query.type;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.query.RemoteDatabaseQuery;
import ru.stonlex.global.database.query.row.ValueQueryRow;

import java.util.LinkedList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class InsertQuery
        extends RemoteDatabaseQuery<ValueQueryRow> {

    boolean ignore;

    public InsertQuery setIgnore(boolean ignore) {
        this.ignore = ignore;

        return this;
    }

    private final String queryFunction = "INSERT";
    private final String databaseTable;

    @Override
    protected void handle(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<ValueQueryRow> queryRows) {
        if (isIgnore()) {
            queryBuilder.append("IGNORE ");
        }

        queryBuilder.append("INTO ");

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
