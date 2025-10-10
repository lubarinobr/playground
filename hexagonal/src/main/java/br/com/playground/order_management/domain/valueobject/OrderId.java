package br.com.playground.order_management.domain.valueobject;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public record OrderId(UUID id) {
    public static OrderId newId() {
        return new OrderId(UUID.randomUUID());
    }

    public OrderId {
        Objects.requireNonNull(id, "OrderId value cannot be null");
    }
}
