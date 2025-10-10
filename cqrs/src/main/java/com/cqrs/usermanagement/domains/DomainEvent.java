package com.cqrs.usermanagement.domains;

import java.time.Instant;

public sealed interface DomainEvent permits RegistrationApproved, RegistrationCancelled, RegistrationRejectedByPolicy, RegistrationSubmitted, RegistrationWaitingApproval {
    String aggregateId();
    long aggregateVersion();
    Instant occurredAt();
    Instant recordedAt();
    String actor();
    String commandId();
    String eventType();
}
