package ru.stonlex.global.database.exception;

public class RemoteDatabaseApiException
        extends RuntimeException {

    public RemoteDatabaseApiException() {
        super();
    }

    public RemoteDatabaseApiException(String message) {
        super(message);
    }

    public RemoteDatabaseApiException(String message, Object... replacement) {
        super(String.format(message, replacement));
    }

}
