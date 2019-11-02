package com.example.investigacion_filtros.models;

public class Allergen {

    private String name;
    private String product;

    public Allergen(String name, String product) {
        this.name = name;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Allergen{" +
                "name='" + name + '\'' +
                '}';
    }
}
