package models.abstracts;

import models.interfaces.ISimulationConfig;

public abstract class Entity implements ISimulationConfig {
    // Fields
    // Static Values
    private static int entityUID = 0; // entityUniqueID
    private static final double AGE_TICK_RATE = 0.2;
    private static final double BASE_INIT_VALUE = 0.0;

    // Class Data
    private String entityName;
    private final int entityID;
    protected boolean isAlive;
    protected double posX, posY;
    protected double age, maxAge;

    // Base Constructor
    public Entity() {
        this.entityID = ++entityUID;
        this.isAlive = true;
        this.age = BASE_INIT_VALUE;
        this.maxAge = BASE_INIT_VALUE;
        this.posX = BASE_INIT_VALUE;
        this.posY = BASE_INIT_VALUE;
    }
    
    // Setters and Getters
    // Name
    public void setEntityName(String nameValue) {
        this.entityName = nameValue;
    }
    public String getEntityName() { return this.entityName; }

    // Soul Related
    public void die() {
        this.isAlive = false;
    }
    public boolean isAlive() { return this.isAlive; }

    // IDs
    public int getEntityID() { return this.entityID; }

    // Movement
    public void setPosX(double newPosX) {
        this.posX = Math.max(MIN_CANVAS_WIDTH, Math.min(newPosX, MAX_CANVAS_WIDTH));
    }
    public double getPosX() { return this.posX; }

    public void setPosY(double newPosY) {
        this.posY = Math.max(MIN_CANVAS_HEIGHT, Math.min(newPosY, MAX_CANVAS_HEIGHT));
    }
    public double getPosY() { return this.posY; }

    // Age
    public void growTickRate() {
        this.age += AGE_TICK_RATE;
    }

    public void setMaxAge(double ageValue) {
        this.age = ageValue;
    }

    // Methods
    // Non Abstracts
    public void checkEntityAging() {
        this.isAlive = this.age < this.maxAge;
    }

    // Abstract Blueprint
    public abstract void selfInjectData();
}