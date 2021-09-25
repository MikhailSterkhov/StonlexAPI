package ru.stonlex.global.database.table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.RemoteDatabaseTable;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoteDatabaseTableData implements RemoteDatabaseTable {

    @NonNull RemoteDatabaseConnectionHandler connectionHandler;
    @NonNull String name;
}
