package ru.stonlex.global.database.query.type;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.query.RemoteDatabaseQuery;
import ru.stonlex.global.database.query.row.ValueQueryRow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public final class SelectQuery
        extends RemoteDatabaseQuery<ValueQueryRow> {

    private final String queryFunction = "SELECT";

    private final String databaseTable;


    private String[] selectedRows = {"*"};

    public SelectQuery setSelectedRows(String... selectedRows) {
        this.selectedRows = selectedRows;

        return this;
    }

    @Override
    protected void buildQuery(@NonNull StringBuilder queryBuilder, @NonNull LinkedList<ValueQueryRow> queryRows) {

        if (Arrays.asList(selectedRows).contains("*")) {
            queryBuilder.append("*");

        } else {

            queryBuilder.append(Joiner.on(",").join(Arrays.stream(selectedRows).map(row -> "`" + row + "`").collect(Collectors.toSet())));
        }

        queryBuilder.append(" FROM `");
        queryBuilder.append(databaseTable);
        queryBuilder.append("`");

        if (!queryRows.isEmpty()) {

            queryBuilder.append(" WHERE ");
            queryBuilder.append(Joiner.on(" AND ").join(queryRows.stream().map(row -> "`" + row.getName() + "`=?").collect(Collectors.toSet())));
        }
    }
}
