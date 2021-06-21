package ru.stonlex.global.database;

import lombok.NonNull;
import ru.stonlex.global.database.connection.HikariDatabaseConnection;
import ru.stonlex.global.database.connection.MysqlDatabaseConnection;
import ru.stonlex.global.database.exception.RemoteDatabaseApiException;
import ru.stonlex.global.database.query.RemoteDatabaseQueryInitializer;
import ru.stonlex.global.utility.Instances;

import java.sql.SQLException;

public final class RemoteDatabasesApi {

    public static RemoteDatabasesApi getInstance() {
        RemoteDatabasesApi remoteDatabasesApi = Instances.getInstance(RemoteDatabasesApi.class);

        if (remoteDatabasesApi == null) {
            Instances.addInstance(remoteDatabasesApi = new RemoteDatabasesApi());
        }

        return remoteDatabasesApi;
    }


    public RemoteDatabaseConnectionFields createConnectionFields(@NonNull String host, @NonNull String username, @NonNull String password, @NonNull String scheme) {
        return new RemoteDatabaseConnectionFields(host, username, password, scheme);
    }

    public RemoteDatabaseConnectionFields createConnectionFields(int port, @NonNull String host, @NonNull String username, @NonNull String password, @NonNull String scheme) {
        return new RemoteDatabaseConnectionFields(port, host, username, password, scheme);
    }

    public MysqlDatabaseConnection createMysqlConnection(@NonNull RemoteDatabaseConnectionFields connectionFields) {
        MysqlDatabaseConnection connectionHandler = new MysqlDatabaseConnection(connectionFields);
        connectionHandler.handleConnection();

        return connectionHandler;
    }

    public HikariDatabaseConnection createHikariConnection(@NonNull RemoteDatabaseConnectionFields connectionFields) {
        HikariDatabaseConnection connectionHandler = new HikariDatabaseConnection(connectionFields);
        connectionHandler.handleConnection();

        return connectionHandler;
    }


    public RemoteDatabaseQueryInitializer newDatabaseQuery(@NonNull String databaseTable) {
        return new RemoteDatabaseQueryInitializer(databaseTable);
    }


    public void checkArgument(boolean expression) {
        if (!expression) {
            throw new RemoteDatabaseApiException();
        }
    }

    public void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new RemoteDatabaseApiException(errorMessage);
        }
    }

    public void checkArgument(boolean expression, String errorMessage, Object... replacement) {
        if (!expression) {
            throw new RemoteDatabaseApiException(errorMessage, replacement);
        }
    }


    public void submitSqlExceptions(@NonNull RemoteDatabaseSqlHandler sqlHandler) {
        try {
            sqlHandler.handle();
        }

        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
