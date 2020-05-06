package ru.stonlex.global.mysql.statement;

import lombok.Getter;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlStatement implements Closeable {

    private ResultSet resultSet;

    @Getter
    private final PreparedStatement preparedStatement;


    /**
     * Инициализация статемента
     */
    public MysqlStatement(Connection connection, String sql, Object... elements) throws SQLException {
        this.preparedStatement = connection.prepareStatement(sql);

        if (elements != null && elements.length != 0) {
            for (int i = 0; i < elements.length; ++i) {
                this.preparedStatement.setObject(i + 1, elements[i]);
            }
        }
    }

    /**
     * Выполнение SQL запроса
     */
    public void execute() throws SQLException {
        preparedStatement.execute();
    }

    /**
     * Получение ResultSet при помощи выполнения SQL запроса
     */
    public ResultSet getResultSet() throws SQLException {
        return (this.resultSet = preparedStatement.executeQuery());
    }

    /**
     * Закрытие статемента.
     *
     * Нужно после выполнения запроса.
     */
    @Override
    public void close() {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }

            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
