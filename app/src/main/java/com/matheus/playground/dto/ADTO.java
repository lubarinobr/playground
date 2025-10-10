package com.matheus.playground.dto;

public record ADTO<T>(
        Long id,
        String name
) implements BaseDTO<Long> {

    @Override
    public Long getIdentifier() {
        return this.id;
    }
}
