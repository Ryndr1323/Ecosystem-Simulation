package models.concretes;

import models.abstracts.Animal;
import models.abstracts.Entity;
import models.descriptor.AnimalDescriptor;
import models.utils.Randomizer;

public class Carnivore extends Animal {
    // Fields
    // Statics Values
    // Energy Thresholds
    private static final double FIRST_STAGE_THRESHOLD = 59.5;
    private static final double CRIT_STAGE_THRESHOLD = 35.0;
    // Energy Taxes
    private static final double BIRTH_ENERGY_DRAIN = -45;
    private static final double WALKING_ENERGY_DRAIN = -2;
    private static final double HUNTING_ENERGY_MULT = 2.2;
    private static final double MIN_ENERGY_GAIN = 60.0;
    private static final double MAX_ENERGY_GAIN = 79.8;
    // Ranges
    private static final double MIN_ACTION_RANGE = 15.0;
    private static final double MAX_ACTION_RANGE = 25.0;
    private static final double MIN_SEARCH_RANGE = 250.0;
    private static final double MAX_SEARCH_RANGE = 400.0;
    // Rolling Chances
    private static final int MIN_ACTION_ROLL = 1;
    private static final int MAX_ACTION_ROLL = 100;
    private static final int BASE_AMOUNT_CHILDREN = 1;
    private static final int MAX_AMOUNT_CHILDREN = 2;

    // Constructors
    // Default Constructor
    public Carnivore(AnimalDescriptor loaderRef) {
        super(loaderRef);
        this.setActionRange(Randomizer.getRandomDouble(MIN_ACTION_RANGE, MAX_ACTION_RANGE));
        this.setSearchRange(Randomizer.getRandomDouble(MIN_SEARCH_RANGE, MAX_SEARCH_RANGE));
    }

    // Overloaded Constructors-esque Functions
    public static Carnivore spawn(AnimalDescriptor loaderRef) {
        Carnivore carn = new Carnivore(loaderRef);

        return carn;
    }

    public static Carnivore spawn(AnimalDescriptor loaderRef, double customPosX, double customPosY) {
        Carnivore carn = spawn(loaderRef);
        carn.setPosX(customPosX);
        carn.setPosY(customPosY);

        return carn;
    }

    // Methods
    // Override Abstract Implementations
    // Core
    @Override
	public void doInteract(Entity targetAction1, Animal targetAction2) {
        // Updater Caller 1
        this.updateSoulStatus();

        // Quick Fallback
        if (!this.isAlive) {
            return;
        }

        // Updater Caller 2
        this.updateMatingReadiness();

		// Action Based Threshold
        double energyAnimal = this.getCurrEnergy();
        int actionRoll = Randomizer.getRandomInt(MIN_ACTION_ROLL, MAX_ACTION_ROLL);

        /**
         * 1st Stage - Random
         * 2nd Stage - Baseline Hunger
         * 3nd Stage - Critical Hunger
         * Else - Wander
         */
        if (energyAnimal > FIRST_STAGE_THRESHOLD) {
            if (this.getMatingStatus() && actionRoll <= 40) {
                this.attemptForChild(targetAction2);
            } else {
                this.moveRandomly(this.getBaseSpeed());
                this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
            }
        } else  if (energyAnimal > CRIT_STAGE_THRESHOLD && energyAnimal <= FIRST_STAGE_THRESHOLD) {
            if (actionRoll <= 60) {
                this.huntFood(targetAction1);
            } else if (this.getMatingStatus() && actionRoll <= 85) {
                this.attemptForChild(targetAction2);
            } else {
                this.moveRandomly(this.getBaseSpeed());
                this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
            }
        } else if (energyAnimal <= CRIT_STAGE_THRESHOLD) {
            if (actionRoll <= 96) {
                this.huntFood(targetAction1);
            } else {
                this.moveRandomly(this.getBaseSpeed());
                this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
            }
        } else {
            this.moveRandomly(this.getBaseSpeed());
            this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
        }        
	}

    // Sub-Core
    @Override
    public void huntFood(Entity targetFood) {
        // First Safeguard
        if (this.getFoodLock() == null || !this.getFoodLock().isAlive()) {
            Entity newTarget = null;

            if (newTarget instanceof Herbivore && newTarget.isAlive()) {
                this.setFoodLock(newTarget);
            } else {
                return;
            }
        } 

        // Get Distance Between this and target
        double mainDistance = euclideanCalculation(this.getPosX(), this.getPosY(), this.getFoodLock().getPosX(), this.getFoodLock().getPosY());
        
        /**
         * Act 1 - If Food within Search Range
         * Act 2 - If Food within Action Range
         * Act 3 | Else - Release
         */
        if (mainDistance <= this.getActionRange()) {
            this.getFoodLock().die();
            this.modifyCurrEnergy(Randomizer.getRandomDouble(MIN_ENERGY_GAIN, MAX_ENERGY_GAIN));
            this.setFoodLock(null);
        } else if (mainDistance <= this.getSearchRange()) {
            this.moveAnimal(this.getFoodLock().getPosX(), this.getFoodLock().getPosY(), this.getBaseSpeed() * this.getChaseMultiplier());
            this.modifyCurrEnergy(WALKING_ENERGY_DRAIN * HUNTING_ENERGY_MULT);
        } else {
            this.setFoodLock(null);
        }
    }

	@Override
	public void attemptForChild(Animal targetMate) {
        /**
         * Check Status
         * 1st - Havent Affectionate
         * 2nd - Soulmate is dead
         * 3rd - Soulmate Readiness
         */
        if (this.getMatingLock() == null || !this.getMatingLock().isAlive() || !this.getMatingLock().getMatingStatus()) {
            Entity newPartner = targetMate;

            /**
             * Check Status
             * 1st - Roll Availability
             * 2nd - Soulmate Class Match
             * 3rd - Picked Soulmate is Alive
             * 4th - Same Species
             * 5th - Different Gender
             */
            if (newPartner != null && newPartner instanceof Carnivore && newPartner.getClass() == this.getClass() && newPartner.isAlive()) {
                Carnivore newDowncast = (Carnivore) newPartner;
                if ((newDowncast.getEntityName().equals(this.getEntityName())) && (newDowncast.getGenderTable() != this.getGenderTable())) {
                    this.setMatingLock(newDowncast);
                    newDowncast.setMatingLock(this);
                }
            } else {
                this.setMatingLock(null);
                return;
            }

            // Count Distance
            double mainDistance = euclideanCalculation(this.getPosX(), this.getPosY(), this.getMatingLock().getPosX(), this.getMatingLock().getPosY());

            if (mainDistance <= this.getActionRange()) {
                // New DataPool
                int amountChildren = Randomizer.getRandomInt(BASE_AMOUNT_CHILDREN, MAX_AMOUNT_CHILDREN);
                for (int i = 0; i < amountChildren; i++) {
                    Carnivore newBorn = spawn(this.getAnimalDescriptor(), this.getPosX(), this.getPosY());
                    this.addSpawnQueue(newBorn);
                }

                // Energy Taxes
                this.modifyCurrEnergy(BIRTH_ENERGY_DRAIN);
                this.getMatingLock().modifyCurrEnergy(BIRTH_ENERGY_DRAIN);
                // Increase Amount of Mating
                this.incrementMatingCounter();
                this.getMatingLock().incrementMatingCounter();
                // Release the Lock
                this.getMatingLock().setMatingLock(null);
                this.setMatingLock(null);
                // Invalidate from Breeding
                this.invalidateMatingStatus();
                this.getMatingLock().invalidateMatingStatus();
            } else if (mainDistance <= this.getSearchRange()) {
                moveAnimal(this.getMatingLock().getPosX(), this.getMatingLock().getPosY(), this.getBaseSpeed());
                this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
            } else {
                this.setMatingLock(null);
            }
        }
	}
}

