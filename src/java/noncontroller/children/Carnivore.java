package noncontroller.children;

import java.util.Map;

import noncontroller.descriptor.AnimalDescriptor;
import noncontroller.enums.AnimalType;
import noncontroller.models.Animal;
import noncontroller.models.Entity;
import noncontroller.utils.Randomizer;
import noncontroller.utils.DataPool;

public class Carnivore extends Animal {
    // Fields
    // Statics
    private static final double MIN_ACT_RANGE = 15.0;
    private static final double MAX_ACT_RANGE = 25.0;
    private static final double MIN_SEARCH_RANGE = 170.0;
    private static final double MAX_SEARCH_RANGE = 195.0;
    private static final double MIN_BASE_WALK = 1.8;
    private static final double MAX_BASE_WALK = 2.2;
    private static final double MIN_CHASE_MULT = 1.5;
    private static final double MAX_CHASE_MULT = 1.8;

    // Non Static
    private Entity targetLock;

    // Constructors
    // Default Case
    public Carnivore(String breedName) {
        super(breedName);
        this.setAnimalType(AnimalType.CARNIVORE);
        this.setActionRange(Randomizer.getRandomDouble(MIN_ACT_RANGE, MAX_ACT_RANGE));
        this.setSearchRange(Randomizer.getRandomDouble(MIN_SEARCH_RANGE, MAX_SEARCH_RANGE));
        this.setBaseSpeed(Randomizer.getRandomDouble(MIN_BASE_WALK, MAX_BASE_WALK));
        this.setChaseMult(Randomizer.getRandomDouble(MIN_CHASE_MULT, MAX_CHASE_MULT));
    }

    // Overloaded Constructors
    public Carnivore(String breedName, String desc1, String desc2) {
        this(breedName);
        this.descriptor = new AnimalDescriptor(desc1, desc2);
    }

    public Carnivore(String breedName, int maxAgeValue, String desc1, String desc2) {
        this(breedName, desc1, desc2);
        this.maxAge = maxAgeValue * Randomizer.getRandomDouble(1, 1.3);
    }

    // Setters and Getters
    // Lock Mechanism
    public void setTargetLock(Entity targetValue) {
        this.targetLock = targetValue;
    }

    public boolean hasLockedEntity() {
        if (this.targetLock == null) {
            return false;
        }

        if(!this.targetLock.isAlive()) {
            this.targetLock = null;
            return false;
        }

        return true;
    }


    // Methods
    // Non Abstract
    private void doWander() {
        this.wanderAround();
        this.decrementCurrEnergy(Randomizer.getRandomDouble(0.5, 0.7));

        if (this.getCurrEnergy() <= 0.0) {
            this.die();
            this.setTargetLock(null);
        }
    }

    private void doHunting(Entity targetHerbivore) {

    }

    // Abstract Implementation
    @Override
    public Map<String, Object> dataFetch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doInteract() {
        if (!this.isAlive()) {
            return;
        }

        if (!hasLockedEntity()) {
            this.doHunting(targetLock);
            return;
        }

        if (this.getCurrEnergy() < this.getAnimalEnergy()) {
            Entity potentialFood = DataPool.getRandomEntityFromCache();

            if (potentialFood instanceof Herbivore && potentialFood.isAlive()) {
                double distance = Math.hypot(
                    potentialFood.getPosX() - this.getPosX(),
                    potentialFood.getPosY() - this.getPosY()
                );

                if (distance <= this.getSearchRange()) {
                    this.setTargetLock(potentialFood);
                    this.doHunting(potentialFood);
                }
            }
        }  else {
            this.doWander();
        }
    }
}

