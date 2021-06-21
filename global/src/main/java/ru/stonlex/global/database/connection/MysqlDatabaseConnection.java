package ru.stonlex.global.database.connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import lombok.*;
import ru.stonlex.global.database.RemoteDatabaseConnectionFields;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.RemoteDatabaseExecuteHandler;
import ru.stonlex.global.database.RemoteDatabaseTable;
import ru.stonlex.global.database.event.RemoteDatabaseEventHandler;
import ru.stonlex.global.database.execute.DataSourceExecuteHandler;
import ru.stonlex.global.database.table.RemoteDatabaseTableData;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public final class MysqlDatabaseConnection implements RemoteDatabaseConnectionHandler {

    private final RemoteDatabaseConnectionFields connectionFields;
    private final Map<String, RemoteDatabaseTable> databaseTables = new HashMap<>();

    private Connection connection;

    private RemoteDatabaseExecuteHandler executeHandler;

    @Setter
    private RemoteDatabaseEventHandler eventHandler;

    @Override
    public RemoteDatabaseTable getTable(@NonNull String tableName) {
        return databaseTables.get(tableName.toLowerCase());
    }

    @Override
    @SneakyThrows
    public void handleConnection() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setServerName(connectionFields.getHost());
        mysqlDataSource.setPort(connectionFields.getPort());
        mysqlDataSource.setUser(connectionFields.getUsername());
        mysqlDataSource.setPassword(connectionFields.getPassword());
        mysqlDataSource.setDatabaseName(connectionFields.getScheme());

        mysqlDataSource.setAutoReconnect(true);
        mysqlDataSource.setEncoding("UTF-8");

        connection = mysqlDataSource.getConnection();
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
        connection.close();

        if (eventHandler != null) {
            eventHandler.onDatabaseDisconnected(this);
        }
    }

    @Override
    @SneakyThrows
    public void reconnect() {
        if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
            return;
        }

        if (eventHandler != null) {
            eventHandler.onDatabaseReconnected(this);
        }

        connection = null;
        handleConnection();
    }

}
