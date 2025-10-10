package com.matheus.playground.valueobject.entities;

import jakarta.validation.constraints.NotNull;

public record FullName(
        @NotNull String name,
        @NotNull String middleName,
        @NotNull String lastName
) {

    public String fullName() {
        return name + " " + middleName + " " + lastName;
    }

    public String abbreviatedName() {
        return name + " " + middleName.charAt(0) + ". " + lastName;
    }

}
