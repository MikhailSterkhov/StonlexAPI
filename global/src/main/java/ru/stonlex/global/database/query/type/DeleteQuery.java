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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class DeleteQuery
        extends RemoteDatabaseQuery<ValueQueryRow> {

    @NonNull String queryFunction = "DELETE FROM";
    @NonNull String databaseTable;

    @Override
    protected void handle(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<ValueQueryRow> queryRows) {

        queryBuilder.append("`");
        queryBuilder.append(databaseTable);
        queryBuilder.append("`");

        if (!queryRows.isEmpty()) {

            queryBuilder.append(" WHERE ");
            queryBuilder.append(Joiner.on(" AND ").join(queryRows.stream().map(row -> "`" + row.getName() + "`=?").collect(Collectors.toSet())));
        }
    }

}
