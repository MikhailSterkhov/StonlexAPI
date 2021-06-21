package ru.stonlex.global.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class RemoteDatabaseConnectionFields {

    @SuppressWarnings("all")
    private int port = 3306;

    private final String host;

    private final String username;

    private final String password;

    private final String scheme;
}
