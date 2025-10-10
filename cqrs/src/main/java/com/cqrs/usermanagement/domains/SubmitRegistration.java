package com.cqrs.usermanagement.domains;

import java.time.Instant;

public record SubmitRegistration(
        String registrationId,
        String email,
        String commandId,
        Instant occurredAt,
        String actor
) {}
