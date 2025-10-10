package com.cqrs.usermanagement.adapters.repository;

import com.cqrs.usermanagement.application.registration.RegistrationRepository;
import com.cqrs.usermanagement.domains.DomainEvent;

import java.util.*;

public class InMemoryRegistrationRepository implements RegistrationRepository {

    private final Map<String, List<DomainEvent>> store = new HashMap<>();

    @Override
    public Optional<List<DomainEvent>> load(String aggregateId) {
        return Optional.ofNullable(store.get(aggregateId)).map(List::copyOf);
    }

    @Override
    public void append(String aggregateId, List<DomainEvent> domainEvent) {
        store.computeIfAbsent(aggregateId, k -> new ArrayList<>()).addAll(domainEvent);
    }
}
