package models.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import models.abstracts.Entity;
import models.concretes.Carnivore;
import models.concretes.Herbivore;
import models.concretes.Plant;
import models.interfaces.ISimulationConfig;

public class DataPool {
    // Fields
    // Cached Data
    private static final List<Entity> cachedHerbivoreData = new ArrayList<>();
    private static final List<Entity> cachedCarnivoreData = new ArrayList<>();
    private static final List<Entity> cachedPlantData = new ArrayList<>();
    // Simulator Container
    private static final List<Entity> activeAnimalEntities = new ArrayList<>();
    private static final List<Entity> activeBotanicEntities = new ArrayList<>();
    // Queue
    private static final Queue<Entity> spawnQueue = new LinkedList<>();

    // Setter and Getter
    public synchronized static int getSizeAnimal() { return activeAnimalEntities.size(); }
    public synchronized static int getSizeSpecificCarn() {
        if (activeAnimalEntities.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (Entity e : activeAnimalEntities) {
            if (e instanceof Carnivore) {
                count++;
            }
        }
        return count;
    }
        public synchronized static int getSizeSpecificHerb() {
        if (activeAnimalEntities.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (Entity e : activeAnimalEntities) {
            if (e instanceof Herbivore) {
                count++;
            }
        }
        return count;
    }
    public synchronized static int getSizeBotany() { return activeBotanicEntities.size(); }
    public synchronized static List<Entity> getActiveAnimalPool() { return DataPool.activeAnimalEntities; }
    public synchronized static List<Entity> getActiveBotanyPool() { return DataPool.activeBotanicEntities; }
    public synchronized static Queue<Entity> getToBeSpawned() { return DataPool.spawnQueue; }

    // Methods
    // Spawning Queue
    public static synchronized void requestSpawn(List<? extends Entity> newEntities) {
        if (newEntities == null || newEntities.isEmpty()) {
            return;
        }

        spawnQueue.addAll(newEntities);
    }

    // Randomizer
    public static synchronized Entity getAnimalDataRand() {
        if (activeAnimalEntities.isEmpty()) {
            return null;
        }

        int chanceRolled = Randomizer.getRandomInt(0, activeAnimalEntities.size());
        return activeAnimalEntities.get(chanceRolled);
    }

    public static synchronized Entity getRandomGlobalEntity() {
        int totalSize = activeAnimalEntities.size() + activeBotanicEntities.size();
        if (totalSize == 0) {
            return null;
        }

        int chanceRolled = Randomizer.getRandomInt(0, totalSize -1);
        
        if (chanceRolled < activeAnimalEntities.size()) {
            return activeAnimalEntities.get(chanceRolled);
        } else {
            return activeBotanicEntities.get(chanceRolled - activeAnimalEntities.size());
        }
    }

    // Flushing
    public static synchronized void flushSpawnQueue() {
        if (spawnQueue.isEmpty()) {
            return;
        }

        int queueSizing = activeAnimalEntities.size() + activeBotanicEntities.size();
        while (!spawnQueue.isEmpty()) {
            // Safe Peek
            Entity entitySpawn = spawnQueue.peek();

            // Check Size
            if (queueSizing < ISimulationConfig.HARD_ENTITY_CAP) {
                // Pull Data
                spawnQueue.poll();

                if (entitySpawn instanceof Carnivore || entitySpawn instanceof Herbivore) {
                    activeAnimalEntities.add(entitySpawn);
                    queueSizing++;
                } else if (entitySpawn instanceof Plant) {
                    activeBotanicEntities.add(entitySpawn);
                    queueSizing++;
                } else {
                    System.err.println("Unidentified Unflushabe Entity Detected");
                }
            } else {
                // Purge
                System.out.printf("Data within instance of spawnQueue will be purged, %d remaining", spawnQueue.size());
                spawnQueue.clear();
            }
        }
    }

    public static synchronized void flushDeadEntities() {
        if (!activeAnimalEntities.isEmpty()) {
            activeAnimalEntities.removeIf(Animal -> Animal == null || !Animal.isAlive());
        }
        
        if (!activeBotanicEntities.isEmpty()) {
            activeBotanicEntities.removeIf(Plant -> Plant == null || !Plant.isAlive());
        }
    }
}