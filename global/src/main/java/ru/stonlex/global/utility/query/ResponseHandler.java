package ru.stonlex.global.utility.query;

public interface ResponseHandler<R, O> {

    R handleResponse(O o);
}
