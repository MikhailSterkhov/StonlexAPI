package ru.stonlex.global.database.connection;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.RemoteDatabaseExecuteHandler;
import ru.stonlex.global.database.RemoteDatabaseTable;
import ru.stonlex.global.database.event.RemoteDatabaseEventListener;
import ru.stonlex.global.database.execute.DataSourceExecuteHandler;
import ru.stonlex.global.database.table.RemoteDatabaseTableData;
import ru.stonlex.global.utility.query.AsyncUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class SqliteDatabaseConnection implements RemoteDatabaseConnectionHandler {

    @NonNull File file;
    @NonNull Map<String, RemoteDatabaseTable> databaseTables = new HashMap<>();

    Connection connection;

    RemoteDatabaseExecuteHandler executeHandler;

    @Setter
    RemoteDatabaseEventListener eventHandler;


    @Override
    public RemoteDatabaseTable getTable(@NonNull String tableName) {
        return databaseTables.get(tableName.toLowerCase());
    }

    @Override
    @SneakyThrows
    public void handleConnection() {
        connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        executeHandler = new DataSourceExecuteHandler(this);

        // Load database tables.
        Objects.requireNonNull(executeHandler.executeQuery(true, "SHOW TABLES;"))
                .thenAccept(result -> {

                    while (result.next()) {

                        String table = result.getString(1);
                        databaseTables.put(table.toLowerCase(), new RemoteDatabaseTableData(this, table));
                    }
                });

        // Handle event.
        if (eventHandler != null) {
            eventHandler.onDatabaseConnected(this);
        }
    }

    @Override
    @SneakyThrows
    public void handleDisconnect() {
        if (eventHandler != null) {
            eventHandler.onDatabaseDisconnected(this);
        }

        AsyncUtil.submitThrowsAsync(connection::close);
    }

    @Override
    @SneakyThrows
    public void reconnect() {
        if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
            return;
        }

        connection = null;
        handleConnection();

        if (eventHandler != null) {
            eventHandler.onDatabaseReconnected(this);
        }
    }

}
