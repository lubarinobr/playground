package com.matheus.playground.valueobject.observables;

public interface CustomListener<T> {

    void run(T t);
    Class<T> getType();
}
