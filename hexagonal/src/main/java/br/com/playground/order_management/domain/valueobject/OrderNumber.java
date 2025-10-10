package br.com.playground.order_management.domain.valueobject;

import java.util.Objects;

public record OrderNumber(String number) {

    public OrderNumber {
        Objects.requireNonNull(number, "The number cannot be null");
        if (number.length() < 5) {
            throw new IllegalArgumentException("The number must be at least 5 characters");
        }
    }
}
