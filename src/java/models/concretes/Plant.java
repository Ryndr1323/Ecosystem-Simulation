package models.concretes;

import java.util.ArrayList;
import java.util.List;
import models.abstracts.Entity;
import models.descriptor.PlantDescriptor;
import models.enums.SeedType;
import models.utils.Randomizer;

public class Plant extends Entity {
    // Fields
    // Static Values
    private static final int ASEXUAL_LIMIT = 3;
    private static final double BASE_ROLLING = 0.0;
    private static final double MAX_ROLLING = 1;
    private static final double BASE_CHANCE_BREED = 30.0;
    private static final int SCARCE_THRESHOLD = 30;
    private static final int MAX_SPAWN_CAP = HARD_ENTITY_CAP;

    // Core Value
    private double minMatureAge;
    private SeedType seedlingType = SeedType.INV;
    private boolean isPoisonous = false;

    // Asexual Cloning Related
    private int asexualAttempt = 0;
    private boolean canReproduce = false;
    private final List<Plant> sproutQueue = new ArrayList<>();
    
    // Bridge
    private final PlantDescriptor loaderRef;
    
    // Constructors
    public Plant(PlantDescriptor loaderValue) {
        super();
        this.isPoisonous = false;
        this.loaderRef = loaderValue;
    }

    public static Plant seedling(PlantDescriptor loaderValue) {
        Plant newSeedling = new Plant(loaderValue);

        return newSeedling;
    }

    public static Plant seedling(PlantDescriptor loaderValue, double customX, double customY) {
        Plant newSeedling = seedling(loaderValue);
        newSeedling.setPosX(customX);
        newSeedling.setPosY(customY);

        return newSeedling;
    }

    // Setter and Getter
    // Bridge
    @Override
    public void selfInjectData() {
        this.loaderRef.injectSimulatedData(this);
    }
    public PlantDescriptor getPlantDescriptor() { return this.loaderRef; }

    // Core (Breed) Value
    public void setMaturingAge(double ageValue) {
        this.minMatureAge = ageValue;
    }
    public double getMaturingAge() { return this.minMatureAge; }

    public double getMaxAge() { return this.maxAge; }

    public void setPoisonValue(boolean poisonVal) {
        this.isPoisonous = poisonVal;
    }
    public boolean getPoisonValue() { return this.isPoisonous; }

    public void setSeedlingType(SeedType seedValue) {
        this.seedlingType = seedValue;
    }
    public SeedType getSeedlingType() { return this.seedlingType; }

    // Methods
    // Non Abstract
    // Mating Related
    public void UpdateReproduceReadiness() {
        this.canReproduce = (this.age >= minMatureAge) && (this.asexualAttempt <= ASEXUAL_LIMIT);
    }
    
    public void incrementAsexualAttempt() {
        this.asexualAttempt += 1;
    }

    public List<Plant> getCopyQueue() {
        if (this.sproutQueue.isEmpty()) {
            return List.of();
        }

        return List.copyOf(this.sproutQueue);
    }

    public boolean isQueueEmpty() {
        return this.sproutQueue.isEmpty();
    }

    public void addSproutQueue(Plant newSeed) {
        this.sproutQueue.add(newSeed);
    }

    public void cleanupSproutQueue() {
        this.sproutQueue.clear();
    }


    // Abstract Implementation
	public void doInteract(int currentPopulation) {
        // Quick Fallback
        if (!this.isAlive && !canReproduce) { 
            return;
        }

        double spawnMultiplier;
        if (currentPopulation >= MAX_SPAWN_CAP) {
            spawnMultiplier = 0.0;
        } else if (currentPopulation <= SCARCE_THRESHOLD) {
            spawnMultiplier = 3.0;
        } else {
            double populationRange = MAX_SPAWN_CAP - SCARCE_THRESHOLD;
            double currentProgress = currentPopulation - SCARCE_THRESHOLD;
            
            spawnMultiplier = 3.0 * (1.0 - (currentProgress / populationRange));
        }

        double finalSpawnChance = BASE_CHANCE_BREED * spawnMultiplier;
        double roll = Randomizer.getRandomDouble(BASE_ROLLING, MAX_ROLLING);
        
        if (roll <= finalSpawnChance && finalSpawnChance > 0) {
            Plant newSprout = seedling(this.getPlantDescriptor(), this.getPosX(), this.getPosY());
            
            this.addSproutQueue(newSprout);
        }
	}
}