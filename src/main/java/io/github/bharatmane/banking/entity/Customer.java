package io.github.bharatmane.banking.entity;

public class Customer {

    private final String name;
    private final String password;

    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }
}
