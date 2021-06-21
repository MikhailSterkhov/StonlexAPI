package ru.stonlex.global.database.query;

public interface RemoteDatabaseQueryRow {

    String getName();

    Object toQueryValue();
}
