package com.matheus.playground.valueobject.valueobjects;

import com.matheus.playground.valueobject.exceptions.UserEmailException;

public record UserEmail(String email) {

    public UserEmail(String email) {
        this.email = email;
        if (!validate()) {
            throw new UserEmailException("Invalid email");
        }
    }

    public boolean validate() {
        if (this.email == null || this.email.isEmpty()) {
            return false;
        }

        var emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return this.email.matches(emailRegex);
    }

    public String domain() {
        return this.email.split("@")[1];
    }
}
