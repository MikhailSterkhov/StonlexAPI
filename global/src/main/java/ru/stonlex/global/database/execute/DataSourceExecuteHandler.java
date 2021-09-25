package ru.stonlex.global.database.execute;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.RemoteDatabaseExecuteHandler;
import ru.stonlex.global.database.query.RemoteDatabaseQueryResult;
import ru.stonlex.global.database.query.RemoteDatabaseQueryStatement;
import ru.stonlex.global.utility.ThrowableSupplier;
import ru.stonlex.global.utility.query.AsyncUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class DataSourceExecuteHandler implements RemoteDatabaseExecuteHandler {

    @NonNull RemoteDatabaseConnectionHandler databaseConnection;

    @Override
    @SneakyThrows
    public Connection getConnection() {
        return databaseConnection.getConnection();
    }

    @Override
    public void refreshConnection() {
        databaseConnection.reconnect();
    }


    @Override
    public void executeUpdate(boolean sync, @NonNull String query, Object... values) {
        Runnable command = () -> {
            refreshConnection();

            try (RemoteDatabaseQueryStatement queryStatement = new RemoteDatabaseQueryStatement(sync, getConnection(), query, values)) {
                queryStatement.executeUpdate();

                if (databaseConnection.getEventHandler() != null) {
                    databaseConnection.getEventHandler().onQueryExecuted(databaseConnection, query);
                }
            }

            catch (SQLException exception) {
                exception.printStackTrace();
            }
        };

        if (!sync) {
            AsyncUtil.submitAsync(command);
            return;
        }

        command.run();
    }

    @Override
    public CompletableFuture<RemoteDatabaseQueryResult> executeQuery(boolean sync, @NonNull String query, Object... values) {
        refreshConnection();

        try (RemoteDatabaseQueryStatement queryStatement = new RemoteDatabaseQueryStatement(false, getConnection(), query, values)) {
            if (databaseConnection.getEventHandler() != null) {
                databaseConnection.getEventHandler().onQueryExecuted(databaseConnection, query);
            }

            return sync
                    ? CompletableFuture.completedFuture(queryStatement.executeQuery())
                    : CompletableFuture.supplyAsync((ThrowableSupplier<RemoteDatabaseQueryResult, SQLException>) queryStatement::executeQuery);
        }

        catch (SQLException exception) {
            exception.printStackTrace();

            return null;
        }
    }

}
