package com.cqrs.usermanagement.adapters.policies;

import com.cqrs.usermanagement.domains.policies.BlocklistPolicy;

import java.util.Set;

public class StaticBlocklistPolicy implements BlocklistPolicy {

    private final Set<String> blocked;

    public StaticBlocklistPolicy(Set<String> blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean isBlocked(String domain) {
        return blocked.contains(domain);
    }
}
