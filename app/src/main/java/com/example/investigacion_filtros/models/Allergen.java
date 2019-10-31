package com.example.investigacion_filtros.models;

public class Allergen {

    private String name;

    public Allergen(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Allergen{" +
                "name='" + name + '\'' +
                '}';
    }
}
