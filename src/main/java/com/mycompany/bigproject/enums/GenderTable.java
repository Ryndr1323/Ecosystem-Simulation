package com.mycompany.bigproject.enums;

public enum GenderTable {
    INV("Invalid"),
    MALE("Male"),
    FEMALE("Female");

    private final String label;

    // Constructor
    GenderTable(String label) {
        this.label = label;
    }

    // Method
    public String getLabel() {
        return this.label;
    }
}