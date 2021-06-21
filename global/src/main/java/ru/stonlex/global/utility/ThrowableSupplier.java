package ru.stonlex.global.utility;

import lombok.SneakyThrows;

import java.util.function.Supplier;

public interface ThrowableSupplier<R, T extends Throwable>
        extends Supplier<R> {

    R implGet() throws T;

    @SneakyThrows
    @Override
    default R get() {
        return implGet();
    }
}
