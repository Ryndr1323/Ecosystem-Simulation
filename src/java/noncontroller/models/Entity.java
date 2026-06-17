package noncontroller.models;

import java.util.Map;
import noncontroller.interfaces.ISimulationConfig;
import noncontroller.interfaces.IEntityDescriptor;
import noncontroller.interfaces.IInteract;

public abstract class Entity implements ISimulationConfig, IInteract {
    // Fields
    private static int entityCID = 0;
    private static final double AGE_TICK_RATE = 0.2;
    private static final double INVALID_AGE_VALUE = 999;
    private final int entityID;
    protected boolean isAlive;
    protected double posX, posY;
    protected double age, maxAge;
    protected IEntityDescriptor descriptor;

    // Constructors
    public Entity() {
        this.entityID = ++entityCID;
        this.isAlive = true;
        this.age = 0;
        this.maxAge = 999;
        this.posX = 0.0;
        this.posY = 0.0;
    }

    // Overloaded Constructors
    public Entity(double posX, double posY) {
        this();
        this.posX = posX;
        this.posY = posY;
    }
    
    // Setters and Getters
    // Soul Related
    public void die() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    // IDs
    public int getEntityID() {
        return this.entityID;
    }

    // Movement
    public void setPosX(double newPosX) {
        this.posX = Math.max(MIN_CANVAS_WIDTH, Math.min(newPosX, MAX_CANVAS_WIDTH));
    }

    public double getPosX() {
        return this.posX;
    }

    public void setPosY(double newPosY) {
        this.posY = Math.max(MIN_CANVAS_HEIGHT, Math.min(newPosY, MAX_CANVAS_HEIGHT));
    }

    public double getPosY() {
        return this.posY;
    }

    // Age
    public void growTickRate() {
        this.age += AGE_TICK_RATE;
    }

    public void setMaxAge(int ageValue) {
        if (ageValue > age) {
            this.maxAge = ageValue;
        } else {
            this.maxAge = INVALID_AGE_VALUE;
        }
    }

    // Methods
    // Non Abstracts
    public abstract Map<String, Object> dataFetch ();
}