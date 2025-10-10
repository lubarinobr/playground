package com.matheus.playground.valueobject.observables;

import java.util.HashSet;
import java.util.Set;


public class FraudPrevention {

    private final Set<CustomListener<Object>> listeners = new HashSet<>();

    public void addNewListener(CustomListener<Object> listener) {
        listeners.add(listener);
    }

    public void removeListener(CustomListener<Object> listener) {
        listeners.remove(listener);
    }

    public void publishEvent(Object t) {
        listeners
                .stream()
                .filter(listener -> {
                    return listener.getType().equals(t.getClass());
                })
                .forEach(customListener -> customListener.run(t));
    }
}
