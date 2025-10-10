package br.com.playground.order_management.domain.valueobject;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case CREATED -> next == CONFIRMED || next == CANCELLED;
            case CONFIRMED -> next == CREATED;
            case CANCELLED -> false;
        };
    }
}
