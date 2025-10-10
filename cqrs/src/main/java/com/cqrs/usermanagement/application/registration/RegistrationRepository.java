package com.cqrs.usermanagement.application.registration;

import com.cqrs.usermanagement.domains.DomainEvent;
import com.cqrs.usermanagement.domains.models.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository {
    Optional<List<DomainEvent>> load(String aggregateId);
    void append(String aggregateId, List<DomainEvent> domainEvent);
    default Registration rehydrate(String id, List<DomainEvent> events) {
        var registration = new Registration(id);
        events.forEach(registration::apply);
        return registration;
    }
}
