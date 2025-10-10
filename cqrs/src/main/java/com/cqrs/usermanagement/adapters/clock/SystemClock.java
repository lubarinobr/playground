package com.cqrs.usermanagement.adapters.clock;

import com.cqrs.usermanagement.application.registration.SubmitRegistrationHandler;

import java.time.Instant;

public class SystemClock implements SubmitRegistrationHandler.Clock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
