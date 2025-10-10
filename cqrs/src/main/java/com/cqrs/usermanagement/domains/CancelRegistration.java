package com.cqrs.usermanagement.domains;

import java.time.Instant;

public record CancelRegistration(
        String registrationId,
        String commandId,
        Instant occurredAt,
        String actor
) {
}
