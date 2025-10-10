package com.cqrs.usermanagement.application.registration;

import com.cqrs.usermanagement.domains.DomainEvent;
import com.cqrs.usermanagement.domains.SubmitRegistration;
import com.cqrs.usermanagement.domains.models.Registration;
import com.cqrs.usermanagement.domains.policies.BlocklistPolicy;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class SubmitRegistrationHandler {

    private final RegistrationRepository registrationRepository;
    private final BlocklistPolicy policy;
    private final Clock clock;

    public SubmitRegistrationHandler(RegistrationRepository registrationRepository, BlocklistPolicy policy, Clock clock) {
        this.registrationRepository = registrationRepository;
        this.policy = policy;
        this.clock = clock;
    }

    public List<DomainEvent> handle(SubmitRegistration submitRegistration) {
        var history = registrationRepository.load(submitRegistration.registrationId());
        var registration = history.isPresent() ? registrationRepository.rehydrate(submitRegistration.registrationId(), history.get()) : new Registration(submitRegistration.registrationId());
        List<DomainEvent> events = registration.decide(submitRegistration, policy, clock.now());
        if (!events.isEmpty()) {
            registrationRepository.append(submitRegistration.registrationId(), events);
        }
        return events;
    }

    public interface Clock { Instant now(); }
}
