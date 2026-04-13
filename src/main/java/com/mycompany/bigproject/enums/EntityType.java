package com.mycompany.bigproject.enums;

public enum EntityType {
    DEF("Default"),
    PLANT("Plants"),
    A_HERB("Herbivore"),
    A_CARN("Carnivore"),
    A_OMNI("Omnivore");

    private final String label;

    // Constructor
    EntityType(String label) {
        this.label = label;
    }

    // Method
    public String getLabel() {
        return this.label;
    }
}
