package ru.stonlex.global.database.query.row;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.query.RemoteDatabaseQueryRow;
import ru.stonlex.global.database.query.RemoteDatabaseRowType;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class TypedQueryRow implements RemoteDatabaseQueryRow {

    private final RemoteDatabaseRowType type;
    private final String name;

    private final Collection<IndexType> indexTypes = new ArrayList<>();

    public TypedQueryRow index(@NonNull IndexType index) {
        indexTypes.add(index);

        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("`%s`", name));

        stringBuilder.append(" ");
        stringBuilder.append(type.getQueryFormat());

        for (IndexType indexType : IndexType.values()) {
            if (!indexTypes.contains(indexType))
                continue;

            stringBuilder.append(" ");
            stringBuilder.append(indexType.queryFormat);
        }

        return stringBuilder.toString();
    }

    @Override
    public Object toQueryValue() {
        return null;
    }

    @RequiredArgsConstructor
    public enum IndexType {

        NOT_NULL("NOT NULL"),
        PRIMARY("PRIMARY KEY"),
        KEY("KEY"),
        UNIQUE("UNIQUE"),
        FULLTEXT("FULLTEXT"),
        SPATIAL("SPATIAL"),
        AUTO_INCREMENT("AUTO_INCREMENT"),
        ;

        private final String queryFormat;
    }
}
