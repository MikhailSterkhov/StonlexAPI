package ru.stonlex.global.mysql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MysqlStatement implements Closeable {

    private ResultSet rs;
    private final PreparedStatement statement;

    /**
     * Инициализация статемента
     */
    public MysqlStatement(Connection connection, String sql, Object... elements) throws SQLException {
        this.statement = connection.prepareStatement(sql);

        if (elements != null && elements.length != 0) {
            for (int i = 0; i < elements.length; ++i) {
                this.statement.setObject(i + 1, elements[i]);
            }
        }
    }

    /**
     * Выполнение SQL запроса
     */
    public void execute() throws SQLException {
        statement.execute();
    }

    /**
     * Получение ResultSet при помощи выполнения SQL запроса
     */
    public ResultSet getResultSet() throws SQLException {
        return (this.rs = statement.executeQuery());
    }

    /**
     * Закрытие статемента.
     *
     * Нужно после выполнения запроса.
     */
    @Override
    public void close() {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }

            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
