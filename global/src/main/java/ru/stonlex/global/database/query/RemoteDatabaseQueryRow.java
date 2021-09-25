package ru.stonlex.global.database.query;

import lombok.NonNull;

public interface RemoteDatabaseQueryRow {

    @NonNull String getName();
    Object value();
}
