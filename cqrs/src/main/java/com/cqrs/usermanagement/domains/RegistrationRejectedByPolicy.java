package com.cqrs.usermanagement.domains;

import java.time.Instant;

public record RegistrationRejectedByPolicy(
        String aggregateId,
        long aggregateVersion,
        Instant occurredAt,
        Instant recordedAt,
        String commandId,
        String domainCommand,
        String domainBlocked,
        String actor
) implements DomainEvent {

    @Override
    public String eventType() {
        return "RegistrationRejectedByPolicy";
    }
}
