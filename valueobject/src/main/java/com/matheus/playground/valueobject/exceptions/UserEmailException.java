package com.matheus.playground.valueobject.exceptions;

public class UserEmailException extends IllegalArgumentException {

    public UserEmailException() {
    }

    public UserEmailException(String s) {
        super(s);
    }

    public UserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
