package com.sandbox.springboot.model;


public class Product {
    private Long id;
    private String name;
    private float price;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float value) {
        this.price = value;
    }
}
