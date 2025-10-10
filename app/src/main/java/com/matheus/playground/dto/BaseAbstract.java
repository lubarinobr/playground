package com.matheus.playground.dto;

public abstract class BaseAbstract {

    protected Long id;
    protected String name;

    public BaseAbstract(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getIdentifier();
}
