package ru.stonlex.global.database.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RemoteDatabaseApiException
        extends RuntimeException {

    public RemoteDatabaseApiException(String message) {
        super(message);
    }

    public RemoteDatabaseApiException(String message, Object... replacement) {
        super(String.format(message, replacement));
    }

}
