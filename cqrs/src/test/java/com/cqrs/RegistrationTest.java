package com.cqrs;

import com.cqrs.usermanagement.adapters.clock.SystemClock;
import com.cqrs.usermanagement.adapters.policies.StaticBlocklistPolicy;
import com.cqrs.usermanagement.adapters.repository.InMemoryRegistrationRepository;
import com.cqrs.usermanagement.application.registration.ApproveRegistrationHandler;
import com.cqrs.usermanagement.application.registration.CancellRegistrationHandler;
import com.cqrs.usermanagement.application.registration.RegistrationRepository;
import com.cqrs.usermanagement.application.registration.SubmitRegistrationHandler;
import com.cqrs.usermanagement.domains.*;
import com.cqrs.usermanagement.domains.policies.BlocklistPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RegistrationTest {

    private RegistrationRepository registrationRepository;
    private BlocklistPolicy blocklistPolicy;
    private SubmitRegistrationHandler.Clock clock;

    @BeforeEach
    void setup() {
        this.registrationRepository = new InMemoryRegistrationRepository();
        this.blocklistPolicy = new StaticBlocklistPolicy(Set.of("tempmail.com", "spam.com"));
        this.clock = new SystemClock();
    }

    @Test
    @DisplayName("Submit Registration with a valid email")
    public void submitValidRegistration() {
        var handler = new SubmitRegistrationHandler(registrationRepository, blocklistPolicy, clock);
        List<DomainEvent> handle = handler.handle(new SubmitRegistration("reg-001", "ana@tempmail.com", "cmd-aaa", Instant.parse("2025-10-07T10:30:00Z"), "public-api"));
        List<DomainEvent> handle1 = handler.handle(new SubmitRegistration("reg-002", "ana@gmail.com", "cmd-bbb", Instant.parse("2025-10-07T10:31:00Z"), "public-api"));
        List<DomainEvent> handle2 = handler.handle(new SubmitRegistration("reg-002", "ana@gmail.com", "cmd-bbb", Instant.parse("2025-10-07T11:30:00Z"), "public-api"));

        assertThat(handle.size(), equalTo(2));
        assertThat(handle1.size(), equalTo(2));
        assertThat(handle2.size(), equalTo(0));

        //assert if in the list the object contains instanceOf RegistrationRejectedByPolicy
        assertThat(handle.stream().anyMatch(e -> e instanceof com.cqrs.usermanagement.domains.RegistrationRejectedByPolicy), equalTo(true));
    }

    @Test
    @DisplayName("Submit approve to the registration")
    public void submitApproveRegistration() {

        var submitHandler = new SubmitRegistrationHandler(registrationRepository, blocklistPolicy, clock);
        submitHandler.handle(new SubmitRegistration("reg-001", "ana@tempmail.com", "cmd-aaa", Instant.parse("2025-10-07T10:30:00Z"), "public-api"));
        submitHandler.handle(new SubmitRegistration("reg-002", "ana@gmail.com", "cmd-bbb", Instant.parse("2025-10-07T10:31:00Z"), "public-api"));
        submitHandler.handle(new SubmitRegistration("reg-002", "ana@gmail.com", "cmd-bbb", Instant.parse("2025-10-07T11:30:00Z"), "public-api"));


        var handler = new ApproveRegistrationHandler(registrationRepository, clock);
        List<DomainEvent> handle = handler.handle(new ApproveRegistration("reg-002", clock.now(), "cmd-bbb", "User-1"));
        assertThat(handle, notNullValue());
        Optional<List<DomainEvent>> load = registrationRepository.load("reg-002");
        assertThat(load.isPresent(), is(true));
        assertThat(load.get().size(), equalTo(3));
        assertThat(load.get().stream().anyMatch(e -> e instanceof RegistrationApproved), equalTo(true));

    }

    @Test
    @DisplayName("Submit to cancell to the registration")
    public void submitCancellRegistration() {

        var submitHandler = new SubmitRegistrationHandler(registrationRepository, blocklistPolicy, clock);
        submitHandler.handle(new SubmitRegistration("reg-001", "ana@tempmail.com", "cmd-aaa", Instant.parse("2025-10-07T10:30:00Z"), "public-api"));
        submitHandler.handle(new SubmitRegistration("reg-002", "ana@gmail.com", "cmd-bbb", Instant.parse("2025-10-07T10:31:00Z"), "public-api"));
        submitHandler.handle(new SubmitRegistration("reg-002", "ana@gmail.com", "cmd-bbb", Instant.parse("2025-10-07T11:30:00Z"), "public-api"));


        var handler = new CancellRegistrationHandler(registrationRepository, clock);
        List<DomainEvent> handle = handler.handle(new CancelRegistration("reg-002", "cmd-bbb",  clock.now(), "User-1"));
        assertThat(handle, notNullValue());
        Optional<List<DomainEvent>> load = registrationRepository.load("reg-002");
        assertThat(load.isPresent(), is(true));
        assertThat(load.get().size(), equalTo(3));
        assertThat(load.get().stream().anyMatch(e -> e instanceof RegistrationCancelled), equalTo(true));
        assertThat(load.get().stream().anyMatch(e -> e instanceof RegistrationApproved), equalTo(false));
        assertThat(load.get().stream().anyMatch(e -> e instanceof RegistrationRejectedByPolicy), equalTo(false));

    }

}
