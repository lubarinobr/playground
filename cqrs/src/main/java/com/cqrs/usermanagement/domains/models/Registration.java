package com.cqrs.usermanagement.domains.models;

import com.cqrs.usermanagement.domains.*;
import com.cqrs.usermanagement.domains.policies.BlocklistPolicy;

import java.time.Instant;
import java.util.*;

public class Registration {

    public enum State { NEW, SUBMITTED, AWAITING_APPROVAL, REJECTED_APPROVAL, REJECTED_BY_POLICY, APPROVED, CANCELLED }

    private final String id;
    private String email;
    private String domain;
    private State state;
    private long version;
    private final Set<String> processedCommandIds;

    public Registration(String id) {
        this.id = id;
        this.state = State.NEW;
        this.version = 0;
        this.processedCommandIds = new HashSet<>();
    }

    public List<DomainEvent> decide(SubmitRegistration cmd, BlocklistPolicy policy, Instant recordedAt) {
        if (!processedCommandIds.add(cmd.commandId())) return List.of();
        if (state != State.NEW) throw new IllegalStateException("AlreadySubmitted");
        String domainCommand = extractDomain(cmd.email());
        Instant occurredAtCommand = cmd.occurredAt();
        List<DomainEvent> events = new ArrayList<>();
        events.add(new RegistrationSubmitted(id, nextVersion(), occurredAtCommand, recordedAt, cmd.commandId(), cmd.actor(), cmd.email(), domainCommand));
        if (policy.isBlocked(domainCommand)) {
            events.add(new RegistrationRejectedByPolicy(id, nextVersion(), occurredAtCommand, recordedAt, cmd.commandId(), domainCommand, "DOMAIN_BLOCKED", cmd.actor()));
        } else {
            events.add(new RegistrationWaitingApproval(id, nextVersion(), occurredAtCommand, recordedAt, cmd.commandId(), cmd.actor()));
        }
        return events;
    }

    public List<DomainEvent> approveRegistration(ApproveRegistration cmd, Instant recordedAt) {
        if (state != State.AWAITING_APPROVAL) throw new IllegalStateException("NotAwaitingApproval");
        return List.of(new RegistrationApproved(cmd.registrationId(), nextVersion(), cmd.occurredAt(), recordedAt, cmd.commandId(), cmd.actor()));
    }

    public List<DomainEvent> cancellRegistration(CancelRegistration cmd, Instant recordedAt) {
        if (state != State.AWAITING_APPROVAL) throw new IllegalStateException("NotAwaitingApproval");
        return List.of(new RegistrationCancelled(cmd.registrationId(), nextVersion(), cmd.occurredAt(), recordedAt, cmd.commandId(), cmd.actor()));

    }

    //Method to apply all the events created to this aggregate.
    //This will be used in case of reprocessing all the events.
    public void apply(DomainEvent event) {
        if (event instanceof RegistrationSubmitted ev) {
            this.email = ev.email();
            this.domain = ev.domain();
            this.state = State.SUBMITTED;
            this.version = event.aggregateVersion();
            this.processedCommandIds.add(ev.commandId());
        } else if (event instanceof RegistrationRejectedByPolicy ev) {
            this.state = State.REJECTED_BY_POLICY;
            this.version = event.aggregateVersion();
            this.processedCommandIds.add(ev.commandId());
        } else if (event instanceof RegistrationWaitingApproval ev) {
            this.state = State.AWAITING_APPROVAL;
            this.version = event.aggregateVersion();
            this.processedCommandIds.add(ev.commandId());
        } else if (event instanceof RegistrationApproved ev) {
            this.state = State.APPROVED;
            this.version = event.aggregateVersion();
            this.processedCommandIds.add(ev.commandId());
        } else if (event instanceof RegistrationCancelled ev) {
            this.state = State.CANCELLED;
            this.version = event.aggregateVersion();
            this.processedCommandIds.add(ev.commandId());
        }
    }

    private Long nextVersion() {
        return version + 1;
    }

    private static String extractDomain(String email) {
        int at = email.indexOf('@');
        if (at < 0 || at == email.length() - 1) throw new IllegalArgumentException("InvalidEmail");
        return email.substring(at + 1).toLowerCase(Locale.ROOT).trim();
    }

    public String id() {return id;}
    public String email() {return email;}
    public String domain() {return domain;}
    public State state() {return state;}
    public long version() {return version;}
}
