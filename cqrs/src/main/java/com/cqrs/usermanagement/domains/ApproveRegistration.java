package com.cqrs.usermanagement.domains;

import java.time.Instant;

public record ApproveRegistration(
        String registrationId,
        Instant occurredAt,
        String commandId,
        String actor
) { }
