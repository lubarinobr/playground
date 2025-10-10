package com.matheus.playground.dto;

public class AbstractB extends BaseAbstract {

    private final String newIdentifier;

    public AbstractB(Long id, String name, String newIdentifier) {
        super(id, name);
        this.newIdentifier = newIdentifier;
    }


    public String getNewIdentifier() {
        return newIdentifier;
    }

    @Override
    public String getIdentifier() {
        return this.newIdentifier;
    }

    @Override
    public String toString() {
        return "AbstractB{" +
                "newIdentifier='" + newIdentifier + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
