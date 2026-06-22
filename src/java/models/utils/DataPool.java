package models.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import models.abstracts.Animal;
import models.abstracts.Entity;
import models.concretes.Carnivore;
import models.concretes.Herbivore;
import models.concretes.Plant;
import models.descriptor.AnimalDescriptor;
import models.descriptor.PlantDescriptor;
import models.interfaces.ISimulationConfig;

public class DataPool {
    // Fields
    // Cached Data
    private static final List<AnimalDescriptor> cachedHerbivoreData = new ArrayList<>();
    private static final List<AnimalDescriptor> cachedCarnivoreData = new ArrayList<>();
    private static final List<PlantDescriptor> cachedPlantData = new ArrayList<>();
    // Simulator Container
    private static final List<Entity> activeAnimalEntities = new ArrayList<>();
    private static final List<Entity> activeBotanicEntities = new ArrayList<>();
    // Queue
    private static final Queue<Entity> spawnQueue = new LinkedList<>();
    // Top Capp
    private static final int HARD_CAP = ISimulationConfig.HARD_ENTITY_CAP;
    // Setter and Getter
    public synchronized static List<AnimalDescriptor> getCachedHerbivoreData() { return DataPool.cachedHerbivoreData; }
    public synchronized static List<AnimalDescriptor> getCachedCarnivoreData() { return DataPool.cachedCarnivoreData; }
    public synchronized static List<PlantDescriptor> getCachedPlantData() { return DataPool.cachedPlantData; }
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
    // Huge Injector
    public static synchronized void retrieveAnimalCache(List<AnimalDescriptor> animalList) {
        cachedHerbivoreData.clear();
        cachedCarnivoreData.clear();

        for (AnimalDescriptor desc : animalList) {
            if (desc == null) continue;
            String dietType = desc.getAnimalType();

            if ("herbivore".equalsIgnoreCase(dietType)) {
                cachedHerbivoreData.add(desc);
            } else if ("carnivore".equalsIgnoreCase(dietType)) {
                cachedCarnivoreData.add(desc);
            }
        }
    }

    public static synchronized void retrievePlantCache(List<PlantDescriptor> botanyList) {
        cachedPlantData.clear();

        for (PlantDescriptor desc : botanyList) {
            if (desc == null) continue;
            cachedPlantData.add(desc);
        }
    }

    // Spawning Queue
    public static synchronized void requestSpawn(List<? extends Entity> newEntities) {
        if (newEntities == null || newEntities.isEmpty()) {
            return;
        }

        for (Entity queueEntity : newEntities) {
            if (queueEntity != null) {
                spawnQueue.offer(queueEntity);
            }
        }
    }

    public synchronized static void beginAddingQueue() {
        if (spawnQueue.isEmpty()) {
            return;
        }

        int sizeCounter = activeAnimalEntities.size() + activeBotanicEntities.size();
        while (!spawnQueue.isEmpty() && sizeCounter < HARD_CAP) {
            Entity toBeAdded = spawnQueue.poll();
            if (toBeAdded != null) {
                if (toBeAdded instanceof Animal) {
                    activeAnimalEntities.add(toBeAdded);
                    toBeAdded.selfInjectData();
                } else if (toBeAdded instanceof Plant) {
                    activeBotanicEntities.add(toBeAdded);
                    toBeAdded.selfInjectData();
                }
                sizeCounter++;
            }
        }

        if (sizeCounter >= HARD_CAP && !spawnQueue.isEmpty()) {
            spawnQueue.clear();
        }
    }

    // Randomizer
    public static synchronized Entity getAnimalDataRand() {
        if (activeAnimalEntities.isEmpty()) {
            return null;
        }

        int chanceRolled = Randomizer.getRandomInt(0, activeAnimalEntities.size() - 1);
        return activeAnimalEntities.get(chanceRolled);
    }

    public static synchronized Entity getRandomGlobalEntity() {
        int animalSize = activeAnimalEntities.size();
        int botanySize = activeBotanicEntities.size();
        int totalSize = animalSize + botanySize;
        if (totalSize == 0) {
            return null;
        }

        int chanceRolled = Randomizer.getRandomInt(0, totalSize - 1);
        
        if (chanceRolled < animalSize) {
            if (activeAnimalEntities.isEmpty()) {
                return null;
            }
            return activeAnimalEntities.get(chanceRolled);
        } else {
            int botanyIndex = chanceRolled - animalSize;

            if (activeBotanicEntities.isEmpty() || botanyIndex >= activeBotanicEntities.size()) {
                return null;
            }
            return activeBotanicEntities.get(botanyIndex);
        }
    }

    // Flushing
    public static synchronized void flushDeadEntities() {
        if (!activeAnimalEntities.isEmpty()) {
            activeAnimalEntities.removeIf(Animal -> Animal == null || !Animal.isAlive());
        }
        
        if (!activeBotanicEntities.isEmpty()) {
            activeBotanicEntities.removeIf(Plant -> Plant == null || !Plant.isAlive());
        }
    }
}