import lombok.NonNull;
import lombok.SneakyThrows;
import ru.stonlex.global.database.RemoteDatabaseConnectionHandler;
import ru.stonlex.global.database.event.RemoteDatabaseEventListenerAdapter;

import java.sql.Connection;

public class TestDatabaseEventHandler extends RemoteDatabaseEventListenerAdapter {

    @Override
    @SneakyThrows
    public void onDatabaseConnected(@NonNull RemoteDatabaseConnectionHandler connectionHandler) {
        connectionHandler.reconnect();

        Connection connection = connectionHandler.getConnection();
        System.out.println("Database " + connection.getSchema() + " has been connected!");
    }

    @Override
    public void onQueryExecuted(@NonNull RemoteDatabaseConnectionHandler connectionHandler, @NonNull String query) {
        System.out.println("output_query=[" + query + "]");
    }

}
