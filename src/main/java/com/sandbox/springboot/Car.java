package com.sandbox.springboot;

public class Car {
    private String engine;
    private String color;

    // Private constructor to enforce object creation via Builder
    private Car(Builder builder) {
        this.engine = builder.engine;
        this.color = builder.color;
    }

    // Nested static Builder class
    public static class Builder {
        private String engine;
        private String color;

        public Builder() {
        }

        public Builder setEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Car build() {
            return new Car(this); 
        }
    }

    public String getEngine() {
        return engine;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Car Details: {" +
            "Engine: '" + engine + '\'' +
            ", Color: '" + color + '\'' +
            '}';
    }
}