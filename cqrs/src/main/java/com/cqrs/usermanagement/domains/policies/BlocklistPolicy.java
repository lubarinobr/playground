package com.cqrs.usermanagement.domains.policies;

public interface BlocklistPolicy {
    boolean isBlocked(String domain);
}
