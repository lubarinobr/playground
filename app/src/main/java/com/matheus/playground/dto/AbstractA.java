package com.matheus.playground.dto;

public class AbstractA extends BaseAbstract{

    public AbstractA(Long id, String name) {
        super(id, name);
    }

    @Override
    public String getIdentifier() {
        return super.getId().toString();
    }

    @Override
    public String toString() {
        return "AbstractA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
