package ru.stonlex.global.database.query.row;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.query.RemoteDatabaseQueryRow;

@RequiredArgsConstructor
@Getter
public class ValueQueryRow implements RemoteDatabaseQueryRow {

    private final String name;
    private final Object value;

    @Override
    public Object toQueryValue() {
        return getValue();
    }
}
