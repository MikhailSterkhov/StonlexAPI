package ru.stonlex.global.database.query.row;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.query.RemoteDatabaseQueryRow;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValueQueryRow implements RemoteDatabaseQueryRow {

    public static @NonNull ValueQueryRow create(@NonNull String name, Object value) {
        return new ValueQueryRow(name, value);
    }

    public static @NonNull ValueQueryRow create(@NonNull String name) {
        return new ValueQueryRow(name);
    }


    @NonNull String name;
    Object value;

    @Override
    public Object value() {
        return getValue();
    }
}
