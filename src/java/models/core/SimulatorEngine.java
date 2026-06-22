package models.core;

import models.abstracts.Animal;
import models.abstracts.Entity;
import models.concretes.Plant;
import models.interfaces.ISimulationConfig;
import models.utils.DataPool;


public class SimulatorEngine {
    // Fields
    // Static Values
    private static final int INIT_BASE_VALUE = 0;
    private static final int HARDCAP_POPULATION = ISimulationConfig.HARD_ENTITY_CAP;

    // Canvas Limit
    // Population
    private int herbivoreCounter = INIT_BASE_VALUE;
    private int carnivoreCounter = INIT_BASE_VALUE;
    private int plantCounter = INIT_BASE_VALUE;

    // Tick Counter
    private int tickCounter = INIT_BASE_VALUE;

    // Runner
    private boolean isRunning = false;
    
    // Constructors
    // Base Constructors
    public SimulatorEngine(int populationValue) {

    }

    // Overloaded Constructors
    public SimulatorEngine(int populationValue, int initPlant, int initHerbivore, int initCarnivore) {
        this(populationValue);
        this.plantCounter = Math.min(initPlant, HARDCAP_POPULATION);
        this.carnivoreCounter = Math.min(initCarnivore, HARDCAP_POPULATION - initPlant);
        this.herbivoreCounter = Math.min(initHerbivore, HARDCAP_POPULATION - (initPlant + initCarnivore));
    }

    // Setter and Getter
    public int getOverallTick() { return this.tickCounter; }

    // Methods
    // Helpers
    private boolean checkEligibilityRun() {
        // Scenario 1 - All Died
        if ((herbivoreCounter == 0) && (carnivoreCounter == 0) && (plantCounter == 0)) {
            return false;
        }

        // Scenario 1 - Only Herbs Exists
        if (plantCounter == HARDCAP_POPULATION) {
            return false;
        }

        // Default
        return true;
    }

    private void initData() {

    }

    // Public Methods
    public void mainEngine() {
        initData();
        this.isRunning =  true;
    }

    public void tickRun() {
        // Quick Fallback
        if (!this.isRunning) {
            return;
        }

        // Update Counter
        plantCounter = DataPool.getSizeBotany();
        herbivoreCounter = DataPool.getSizeSpecificHerb();
        carnivoreCounter = DataPool.getSizeSpecificCarn();

        // Runner Start
        if (checkEligibilityRun()) {
            this.tickCounter++;
            for (Entity entity : DataPool.getActiveAnimalPool()) {
                if (!entity.isAlive()) {
                    continue;
                }

                entity.checkEntityAging();
                if (!entity.isAlive()) {
                    continue;
                }

                if (entity instanceof Animal animal) {
                    Entity cachedDataPool = DataPool.getRandomGlobalEntity();
                    Animal cachedDataPool2 = (Animal) DataPool.getAnimalDataRand();

                    animal.doInteract(cachedDataPool, cachedDataPool2);
                    if (!animal.isQueueEmpty()) {
                        DataPool.requestSpawn(animal.getCopyQueue());
                        animal.cleanupSpawnQueue();
                    }
                    
                }
                entity.growTickRate();
            }

            for (Entity entity : DataPool.getActiveBotanyPool()) {
                if (!entity.isAlive()) {
                    continue;
                }
                
                entity.checkEntityAging();
                if (!entity.isAlive()) {
                    continue;
                }
                
                if (entity instanceof Plant plant) {
                    plant.doInteract(plantCounter);
                    if (!plant.isQueueEmpty()) {
                        DataPool.requestSpawn(plant.getCopyQueue());
                        plant.cleanupSproutQueue();
                    }
                }
                entity.growTickRate();
            }

            // TODO: Get This Proper Way to Run
            DataPool.flushDeadEntities();
            DataPool.beginAddingQueue();
        } else {
            this.isRunning = false;
        }
    }
}