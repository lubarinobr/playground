package com.cqrs.usermanagement.domains;

import java.time.Instant;

public record RegistrationSubmitted(
        String aggregateId,
        long aggregateVersion,
        Instant occurredAt,
        Instant recordedAt,
        String commandId,
        String actor,
        String email,
        String domain
) implements DomainEvent {

    public String eventType() {
        return "RegistrationSubmitted";
    }
}
