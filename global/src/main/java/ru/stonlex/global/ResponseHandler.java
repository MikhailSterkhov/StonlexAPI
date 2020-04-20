package ru.stonlex.global;

public interface ResponseHandler<R, O, T extends Throwable> {

    R handleResponse(O o) throws T;
}
