package models.concretes;

import models.abstracts.Animal;
import models.abstracts.Entity;
import models.descriptor.AnimalDescriptor;
import models.utils.Randomizer;

public class Herbivore extends Animal {
    // Fields
    // Statics Values
    // Energy Thresholds
    private static final double FIRST_STAGE_THRESHOLD = 75.5;
    private static final double CRIT_STAGE_THRESHOLD = 15.0;
    // Energy Taxes
    private static final double BIRTH_ENERGY_DRAIN = -40;
    private static final double WALKING_ENERGY_DRAIN = -1.8;
    private static final double MIN_ENERGY_GAINED = 31.0;
    private static final double MAX_ENERGY_GAINED = 40.0;
    // Ranges
    private static final double MIN_ACTION_RANGE = 15.0;
    private static final double MAX_ACTION_RANGE = 35.0;
    private static final double MIN_SEARCH_RANGE = 1.5;
    private static final double MAX_SEARCH_RANGE = 3.2;
    // Rolling Chances
    private static final int MIN_ACTION_ROLL = 1;
    private static final int MAX_ACTION_ROLL = 100;
    private static final int BASE_AMOUNT_CHILDREN = 2;
    private static final int MAX_AMOUNT_CHILDREN = 5;

    // Constructors
    // Default Constructor
    public Herbivore(AnimalDescriptor loaderRef) {
        super(loaderRef);
        this.setActionRange(Randomizer.getRandomDouble(MIN_ACTION_RANGE, MAX_ACTION_RANGE));
        this.setSearchRange(Randomizer.getRandomDouble(MIN_SEARCH_RANGE, MAX_SEARCH_RANGE));
    }

    // Overloaded Constructors-esque Functions
    public static Herbivore birth(AnimalDescriptor loaderRef) {
        Herbivore herb = new Herbivore(loaderRef);

        return herb;
    }

    public static Herbivore birth(AnimalDescriptor loaderRef, double customPosX, double customPosY) {
        Herbivore herb = birth(loaderRef);
        herb.setPosX(customPosX);
        herb.setPosY(customPosY);

        return herb;
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
            if (this.getMatingStatus() && actionRoll <= 65) {
                this.attemptForChild(targetAction2);
            } else {
                this.moveRandomly(this.getBaseSpeed());
                this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
            }
        } else  if (energyAnimal > CRIT_STAGE_THRESHOLD && energyAnimal <= FIRST_STAGE_THRESHOLD) {
            if (actionRoll <= 50) {
                this.huntFood(targetAction1);
            } else if (this.getMatingStatus() && actionRoll <= 80) {
                this.attemptForChild(targetAction2);
            } else {
                this.moveRandomly(this.getBaseSpeed());
                this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
            }
        } else if (energyAnimal <= CRIT_STAGE_THRESHOLD) {
            this.huntFood(targetAction1);
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
            Entity newTarget = targetFood;

            if (newTarget instanceof Plant && newTarget.isAlive()) {
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
            Plant downcastPlant = (Plant) this.getFoodLock();
            if (downcastPlant.getPoisonValue()) {
                this.die();
            } else {
                this.modifyCurrEnergy(Randomizer.getRandomDouble(MIN_ENERGY_GAINED, MAX_ENERGY_GAINED));
            }
            this.getFoodLock().die();
            this.setFoodLock(null);
        } else if (mainDistance <= this.getSearchRange()) {
            this.moveAnimal(this.getFoodLock().getPosX(), this.getFoodLock().getPosY(), this.getBaseSpeed() * this.getChaseMultiplier());
            this.modifyCurrEnergy(WALKING_ENERGY_DRAIN);
        } else {
            this.setFoodLock(null);
        }
    }

    @Override
    public void attemptForChild(Animal targetMate) {
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
            if (newPartner != null && newPartner instanceof Herbivore && newPartner.getClass() == this.getClass() && newPartner.isAlive()) {
                Herbivore newDowncast = (Herbivore) newPartner;
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
                    Herbivore newBorn = birth(this.getAnimalDescriptor(), this.getPosX(), this.getPosY());
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