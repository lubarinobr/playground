package com.matheus.playground.valueobject.valueobjects;

import jakarta.validation.constraints.NotNull;

public record SocialNumber(String value) {

    public SocialNumber(String value) {
        this.value = value;
        if (!validate()) {
            throw new IllegalArgumentException("Invalid social number");
        }
    }

    public boolean validate() {
        return value != null && value.length() == 11;
    }
}
