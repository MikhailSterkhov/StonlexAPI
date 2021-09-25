package ru.stonlex.global.database.query.row;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.query.RemoteDatabaseQueryRow;
import ru.stonlex.global.database.query.RemoteDatabaseRowType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypedQueryRow implements RemoteDatabaseQueryRow {

    public static @NonNull TypedQueryRow create(@NonNull RemoteDatabaseRowType type, @NonNull String name) {
        return new TypedQueryRow(type, name);
    }

    public static @NonNull TypedQueryRow createUnique(@NonNull RemoteDatabaseRowType type, @NonNull String name) {
        return new TypedQueryRow(type, name).putIndex(IndexType.UNIQUE);
    }

    public static @NonNull TypedQueryRow createNotNull(@NonNull RemoteDatabaseRowType type, @NonNull String name) {
        return new TypedQueryRow(type, name).putIndex(IndexType.NOT_NULL);
    }

    public static @NonNull TypedQueryRow createPrimary(@NonNull RemoteDatabaseRowType type, @NonNull String name) {
        return new TypedQueryRow(type, name).putIndex(IndexType.PRIMARY);
    }

    public static @NonNull TypedQueryRow createPrimaryNotNull(@NonNull RemoteDatabaseRowType type, @NonNull String name) {
        return new TypedQueryRow(type, name).putIndexes(IndexType.PRIMARY, IndexType.NOT_NULL);
    }

    @NonNull RemoteDatabaseRowType type;
    @NonNull String name;

    @NonNull Collection<IndexType> indexTypes = new ArrayList<>();

    public @NonNull TypedQueryRow putIndex(@NonNull IndexType index) {
        indexTypes.add(index);
        return this;
    }

    public @NonNull TypedQueryRow putIndexes(@NonNull IndexType... indexes) {
        indexTypes.addAll(Arrays.asList(indexes));
        return this;
    }

    @Override
    public @NonNull String toString() {
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
    public Object value() {
        return null;
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum IndexType {

        NOT_NULL("NOT NULL"),
        PRIMARY("PRIMARY KEY"),
        KEY("KEY"),
        UNIQUE("UNIQUE"),
        FULLTEXT("FULLTEXT"),
        SPATIAL("SPATIAL"),
        AUTO_INCREMENT("AUTO_INCREMENT"),
        ;

        @NonNull String queryFormat;
    }
}
