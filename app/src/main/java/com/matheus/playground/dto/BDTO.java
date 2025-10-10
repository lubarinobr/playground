package com.matheus.playground.dto;

public record BDTO(
        String identifier,
        String surname
) implements BaseDTO<String> {

    @Override
    public String getIdentifier() {
        return this.identifier;
    }
}
