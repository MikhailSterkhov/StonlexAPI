package ru.stonlex.global.database;

import lombok.*;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoteDatabaseConnectionFields {

    int port = 3306;

    @NonNull String host;

    @NonNull String username;

    @NonNull String password;

    @NonNull String scheme;
}
