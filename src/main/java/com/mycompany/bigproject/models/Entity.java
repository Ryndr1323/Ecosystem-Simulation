package com.mycompany.bigproject.models;

import com.mycompany.bigproject.enums.EntityType;

public abstract class Entity {
    private static int idCounter = 0;
    private final int id;
    private EntityType entityCategory;
    protected boolean isAlive;

    // Constructors
    public Entity() {
        idCounter++;
        this.id = idCounter;
        this.isAlive = true;
        this.entityCategory = EntityType.DEF;
    }

    // Setters and Getters
    public void SetEntityType(EntityType value) {
        this.entityCategory = value;
    }
    public EntityType GetEntityType() {
        return this.entityCategory;
    }

    // Method
    public void SetAliveStatus(boolean status) {
        this.isAlive = status;
    }

    public int getID() {
        return this.id;
    }
}
