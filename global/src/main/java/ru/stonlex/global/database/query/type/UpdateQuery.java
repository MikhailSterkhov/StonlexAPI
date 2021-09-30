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
public final class UpdateQuery
        extends RemoteDatabaseQuery<ValueQueryRow> {

    @NonNull String queryFunction = "UPDATE";
    @NonNull String databaseTable;

    @NonNull LinkedList<ValueQueryRow> whereRowsList = new LinkedList<>();

    public UpdateQuery where(ValueQueryRow valueQueryRow) {
        whereRowsList.add(valueQueryRow);

        return this;
    }

    @Override
    protected void handle(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<ValueQueryRow> queryRows) {
        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("`");

        if (!queryRows.isEmpty()) {

            queryBuilder.append(" SET ");
            queryBuilder.append(Joiner.on(" AND ").join(queryRows.stream().map(row -> "`" + row.getName() + "`=?").collect(Collectors.toSet())));
        }

        if (!whereRowsList.isEmpty()) {

            queryBuilder.append(" WHERE ");
            queryBuilder.append(Joiner.on(" AND ").join(queryRows.stream().map(row -> "`" + row.getName() + "`=?").collect(Collectors.toSet())));
        }
    }
}
