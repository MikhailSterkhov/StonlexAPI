package ru.stonlex.global;

public interface ResponseHandler<R, O> {

    R handleResponse(O o);
}
