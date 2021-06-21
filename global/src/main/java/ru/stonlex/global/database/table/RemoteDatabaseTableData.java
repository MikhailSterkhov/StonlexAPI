package ru.stonlex.global.database.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.RemoteDatabaseTable;

@RequiredArgsConstructor
@Getter
public class RemoteDatabaseTableData
        implements RemoteDatabaseTable {

    private final RemoteDatabaseConnectionHandler connectionHandler;
    private final String name;
}
