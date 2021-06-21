package ru.stonlex.global.database.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RemoteDatabaseRowType {

    TINY_INT("TINYINT"),
    SMALL_INT("SMALLINT"),
    MEDIUM_INT("MEDIUMINT"),
    INT("INT"),
    BIG_INT("BIGINT"),
    BIT("BIT"),

    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    DECIMAL("DECIMAL"),

    CHAR("CHAR"),
    VAR_CHAR("VARCHAR"),
    TINY_TEXT("TINYTEXT"),
    TEXT("TEXT"),
    MEDIUM_TEXT("MEDIUMTEXT"),
    LONG_TEXT("LONGTEXT"),
    JSON("JSON"),

    BINARY("BINARY"),
    VAR_BINARY("VARBINARY"),
    TINY_BLOB("TINYBLOB"),
    BLOB("BLOB"),
    MEDIUM_BLOB("MEDIUMBLOB"),
    LONG_BLOB("LONGBLOB"),

    DATE("DATE"),
    TIME("TIME"),
    YEAR("YEAR"),
    DATETIME("DATETIME"),
    TIMESTAMP("TIMESTAMP"),

    UNKNOWN("UNKNOWN"),
    ;

    private final String queryFormat;
}
