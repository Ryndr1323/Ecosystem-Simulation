package models.abstracts;

import java.util.ArrayList;
import java.util.List;
import models.descriptor.AnimalDescriptor;
import models.enums.AnimalType;
import models.enums.GenderTable;
import models.interfaces.IAnimalInteract;
import models.utils.Randomizer;

public abstract class Animal extends Entity implements IAnimalInteract {
    // Fields
    // Static Values
    private static final String KEY_DEF_INIT = "unknown";
    private static final double ENERGY_INIT_VALUE = 100;
    private static final double ENERGY_BREED_MIN = 70.0;
    private static final double BASE_INIT_VALUE = 0.0;

    // Breeds
    private String breedKey = KEY_DEF_INIT;
    private final GenderTable genderType = Randomizer.getRandomGender();
    private AnimalType animalType;

    // Energy
    private double energyAnimal = ENERGY_INIT_VALUE;

    // Actions Related
    private double actionRange = BASE_INIT_VALUE;
    private double searchRange = BASE_INIT_VALUE;
    private double baseSpeed = BASE_INIT_VALUE;
    private double chaseMult = BASE_INIT_VALUE;
    protected double headingAngle = Randomizer.getRandomDouble(0, 360);

    // Mating Related
    private double ageToMate = BASE_INIT_VALUE;
    private Animal mateLock = null;
    private double amountMated = 0;
    private final double MATING_CAP = 1;
    private final List<Animal> spawnQueue = new ArrayList<>();

    // Food Related
    private Entity targetLock = null;

    // Status
    private boolean readyToMate = false;

    // Bridge
    private final AnimalDescriptor loaderRef;

    // Base Constructor
    public Animal(AnimalDescriptor loaderValue) {
        super();
        this.loaderRef = loaderValue;
    }

    // Setter and Getter
    // Bridge
    @Override
    public void selfInjectData() {
        this.loaderRef.injectSimulatedData(this);
    }
    public AnimalDescriptor getAnimalDescriptor() { return this.loaderRef; }

    // Breeds
    public void setBreedKey(String newName) {
        this.breedKey = newName;
    }
    public String getBreedKey() { return this.breedKey; }

    public GenderTable getGenderTable() {
        return this.genderType;
    }

    public void setAnimalType(AnimalType animalValue) {
        this.animalType = animalValue;
    }
    public AnimalType getAnimalType() { return this.animalType; }

    // Energy
    public void modifyCurrEnergy(double energyModify) {
        this.energyAnimal = Math.min(100, this.energyAnimal + energyModify);
    }
    public double getCurrEnergy(){ return this.energyAnimal; }

    // Action Related
    public void setActionRange(double actionValue) {
        this.actionRange = actionValue;
    }
    public double getActionRange() { return this.actionRange; }

    public void setSearchRange(double searchValue) {
        this.searchRange = searchValue;
    }
    public double getSearchRange() { return this.searchRange; }

    public void setBaseSpeed(double speedValue) {
        this.baseSpeed = speedValue;
    }
    public double getBaseSpeed() { return this.baseSpeed; }

    public void setChaseMultiplier(double chaseValue) {
        this.chaseMult = chaseValue;
    }
    public double getChaseMultiplier() { return this.chaseMult; }

    // public double getHeadingAngle() { return this.headingAngle; }

    // Mating Related
    public void setMatingAge(double ageValue) {
        this.ageToMate = ageValue;
    }
    // public double getMatingAge() { return this.ageToMate; }

    public void setMatingLock(Animal targetSoulmate) {
        this.mateLock = targetSoulmate;
    }
    public Animal getMatingLock() { return this.mateLock; }

    public void incrementMatingCounter() {
        this.amountMated += 1;
    }

    // Food Related
    public void setFoodLock(Entity targetValue) {
        this.targetLock = targetValue;
    }
    public Entity getFoodLock() { return this.targetLock; }

    // Status
    public boolean getMatingStatus() { return this.readyToMate; }

    // Methods
    // Non Abstracts
    // Status
    public void updateMatingReadiness() {
        this.readyToMate = (this.age >= ageToMate) && (this.energyAnimal >= ENERGY_BREED_MIN) && (this.amountMated < this.MATING_CAP);
    }

    public void invalidateMatingStatus() {
        this.readyToMate = false;
    }

    public void updateSoulStatus() {
        if (this.energyAnimal <= 0.0) {
            this.die();
        }
    }

    // Helper
    public double euclideanCalculation(double currentX, double currentY, double targetX, double targetY) {
        double deltaX = targetX - currentX;
        double deltaY = targetY - currentY;
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    // Movement
    public void moveRandomly(double speedParam) {
        // Angle Change
        double angleChange = Randomizer.getRandomDouble(-15.0, 15.0);
        this.headingAngle = (this.headingAngle + angleChange) % 360;

        // Angle Helper
        double radians = Math.toRadians(this.headingAngle);

        // Main Movement
        this.setPosX(this.getPosX() + (Math.cos(radians) * speedParam));
        this.setPosY(this.getPosY() + (Math.sin(radians) * speedParam));
    }

    public void moveAnimal(double targetX, double targetY, double speedParam) {
        // Deltas Helper
        double deltaX = targetX - this.getPosX();
        double deltaY = targetY - this.getPosY();

        double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

        // Safeguard to Avoid Jittering
        if (distance > 1.0) {
            double nextX = this.getPosX() + ((deltaX / distance) * speedParam);
            double nextY = this.getPosY() + ((deltaY / distance) * speedParam);
            
            this.headingAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
            
            this.setPosX(nextX);
            this.setPosY(nextY);
        }
    }

    // Mating
    public List<Animal> getCopyQueue() {
        if (this.spawnQueue.isEmpty()) {
            return List.of();
        }

        return List.copyOf(this.spawnQueue);
    }

    public boolean isQueueEmpty() {
        return this.spawnQueue.isEmpty();
    }

    public void addSpawnQueue(Animal newborn) {
        this.spawnQueue.add(newborn);
    }

    public void cleanupSpawnQueue() {
        this.spawnQueue.clear();
    }

    // Abstract Blueprints
    public abstract void huntFood(Entity targetFood);
    public abstract void attemptForChild(Animal targetMate);
}