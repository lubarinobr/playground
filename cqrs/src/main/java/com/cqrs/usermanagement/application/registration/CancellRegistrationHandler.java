package com.cqrs.usermanagement.application.registration;

import com.cqrs.usermanagement.domains.ApproveRegistration;
import com.cqrs.usermanagement.domains.CancelRegistration;
import com.cqrs.usermanagement.domains.DomainEvent;

import java.util.List;

public class CancellRegistrationHandler {

    private final RegistrationRepository registrationRepository;
    private final SubmitRegistrationHandler.Clock clock;

    public CancellRegistrationHandler(RegistrationRepository registrationRepository, SubmitRegistrationHandler.Clock clock) {
        this.registrationRepository = registrationRepository;
        this.clock = clock;
    }

    public List<DomainEvent> handle(CancelRegistration cancelRegistration) {
        var history = registrationRepository.load(cancelRegistration.registrationId());
        var registration = history.isPresent() ? registrationRepository.rehydrate(cancelRegistration.registrationId(), history.get()) : null;
        if (registration == null) {
            throw new RuntimeException("Registration not found");
        }
        var events = registration.cancellRegistration(cancelRegistration, clock.now());
        if (!events.isEmpty()) {
            registrationRepository.append(cancelRegistration.registrationId(), events);
        }
        return events;
    }
}
