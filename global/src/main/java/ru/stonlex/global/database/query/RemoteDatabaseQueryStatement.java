package ru.stonlex.global.database.query;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

@Getter
public class RemoteDatabaseQueryStatement
        implements Closeable {

    private final boolean sync;
    private final PreparedStatement preparedStatement;

    private RemoteDatabaseQueryResult queryResult;


    /**
     * Инициализация статемента
     */
    public RemoteDatabaseQueryStatement(boolean sync,
                                        @NonNull Connection connection,
                                        @NonNull String mysqlQuery,
                                        @NonNull Object... values)
            throws SQLException {

        this.sync = sync;
        this.preparedStatement = connection.prepareStatement(mysqlQuery);

        if (values != null && values.length > 0 && Arrays.stream(values).anyMatch(Objects::nonNull)) {
            for (int i = 0; i < values.length; i++) {

                preparedStatement.setObject(i + 1, values[i]);
            }
        }
    }

    public void executeUpdate() throws SQLException {
        preparedStatement.execute();
    }

    @SneakyThrows
    public RemoteDatabaseQueryResult executeQuery() {
        return (this.queryResult = new RemoteDatabaseQueryResult(preparedStatement.executeQuery()));
    }

    @Override
    public void close() {
        Runnable command = () -> {

            try {
                if (queryResult != null && !queryResult.isClosed()) {
                    queryResult.close();
                }

                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            }

            catch (Exception exception) {
                exception.printStackTrace();
            }
        };

        if (sync) {
            command.run();
            return;
        }

        new Thread(() -> {

            try {
                Thread.sleep(1500);

            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            command.run();
            Thread.currentThread().stop();

        }).start();
    }

}
