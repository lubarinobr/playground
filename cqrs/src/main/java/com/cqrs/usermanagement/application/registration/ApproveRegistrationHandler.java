package com.cqrs.usermanagement.application.registration;

import com.cqrs.usermanagement.domains.ApproveRegistration;
import com.cqrs.usermanagement.domains.DomainEvent;

import java.util.List;

public class ApproveRegistrationHandler {

    private final RegistrationRepository registrationRepository;
    private final SubmitRegistrationHandler.Clock clock;

    public ApproveRegistrationHandler(RegistrationRepository registrationRepository, SubmitRegistrationHandler.Clock clock) {
        this.registrationRepository = registrationRepository;
        this.clock = clock;
    }

    public List<DomainEvent> handle(ApproveRegistration approveRegistration) {
        var history = registrationRepository.load(approveRegistration.registrationId());
        var registration = history.isPresent() ? registrationRepository.rehydrate(approveRegistration.registrationId(), history.get()) : null;
        if (registration == null) {
            throw new RuntimeException("Registration not found");
        }
        var events = registration.approveRegistration(approveRegistration, clock.now());
        if (!events.isEmpty()) {
            registrationRepository.append(approveRegistration.registrationId(), events);
        }
        return events;
    }
}
