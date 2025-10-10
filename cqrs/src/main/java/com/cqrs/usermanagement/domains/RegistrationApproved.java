package com.cqrs.usermanagement.domains;

import java.time.Instant;

public record RegistrationApproved(
        String aggregateId,
        long aggregateVersion,
        Instant occurredAt,
        Instant recordedAt,
        String commandId,
        String actor
) implements DomainEvent {

    @Override
    public String eventType() {
        return "RegistrationApproved";
    }
}
