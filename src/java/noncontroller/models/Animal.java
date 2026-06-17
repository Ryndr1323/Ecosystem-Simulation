package noncontroller.models;

import noncontroller.enums.AnimalType;
import noncontroller.enums.GenderTable;
import noncontroller.utils.Randomizer;

public abstract class Animal extends Entity {
    // Fields
    // Breeds
    private String breedType;
    private String breedKeyword;
    private GenderTable genderType;
    private AnimalType animalType;

    // Energy
    private double energyAnimal;
    private double minEnergyReq;

    // Range Actions
    private double actionRange;
    private double searchRange;

    // Speed Actions
    private double stepWalk;
    private double chaseMult;

    // Angling Wander
    protected double headingAngle;

    // Static
    private static final double MIN_ENERGY_CEIL = 87.5;
    private static final double MAX_ENERGY_CEIL = 100;
    private static final double MIN_REQUIREMENT_CEIL = 47.5;
    private static final double MAX_REQUIREMENT_CEIL = 59.5;

    // Constructors
    // Default Case
    public Animal() {
        super();
        breedType = "Unknown";
        breedKeyword = "unknown";
        genderType = Randomizer.getRandomGender();
        animalType = AnimalType.INVALID;
        energyAnimal = Randomizer.getRandomDouble(MIN_ENERGY_CEIL, MAX_ENERGY_CEIL);
        minEnergyReq = Randomizer.getRandomDouble(MIN_REQUIREMENT_CEIL, MAX_REQUIREMENT_CEIL);
        actionRange = 0.0;
        searchRange = 0.0;
        stepWalk = 0.0;
        chaseMult = 0.0;
        headingAngle = Randomizer.getRandomInt(0, 360);
    }

    public Animal(String breedInput) {
        this();
        this.breedType = breedInput;
        this.breedKeyword = breedType.toLowerCase();
    }

    // Setters and Getters
    // Name
    public String getBreedName() {
        return this.breedType;
    }

    public String getBreedKey() {
        return this.breedKeyword;
    }

    // Genders and Animal Base
    public GenderTable getGenderType() {
        return this.genderType;
    }

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    public void setAnimalType(AnimalType typeValue) {
        this.animalType = typeValue;
    }

    // Energy
    public void decrementCurrEnergy (double energyValue) {
        this.energyAnimal -= energyValue;
    }

    public double getCurrEnergy() {
        return this.energyAnimal;
    }

    public double getAnimalEnergy() {
        return this.minEnergyReq;
    }

    // Ranges
    public void setActionRange(double rangeValue) {
        this.actionRange = rangeValue;
    }

    public void setSearchRange(double rangeValue) {
        this.searchRange = rangeValue;
    }

    public double getActionRange() {
        return this.actionRange;
    }

    public double getSearchRange() {
        return this.searchRange;
    }

    // Speed
    public void setBaseSpeed(double walkValue) {
        this.stepWalk = walkValue;
    }

    public double getBaseSpeed() {
        return this.stepWalk;
    }

    public void setChaseMult(double chaseValue) {
        this.chaseMult = chaseValue;
    }

    public double getChaseMult() {
        return this.chaseMult;
    }

    // Methods
    public void moveTowards(double targetX, double targetY, double currentSpeed) {
        double distance = Math.hypot(targetX - this.getPosX(), targetY - this.getPosY());
        
        if (distance > 0) {
            double ratio = currentSpeed / distance;
            
            double nextX = this.getPosX() + (targetX - this.getPosX()) * ratio;
            double nextY = this.getPosY() + (targetY - this.getPosY()) * ratio;
            
            this.setPosX(nextX);
            this.setPosY(nextY);
            
            this.headingAngle = Math.toDegrees(Math.atan2(targetY - this.getPosY(), targetX - this.getPosX()));
        }
    }

    public void wanderAround() {
        int change = Randomizer.getRandomInt(-15, 15);
        this.headingAngle = (this.headingAngle + change) % 360;

        double radians = Math.toRadians(this.headingAngle);

        double nextX = this.getPosX() + (Math.cos(radians) * this.getBaseSpeed());
        double nextY = this.getPosY() + (Math.sin(radians) * this.getBaseSpeed());

        this.setPosX(nextX);
        this.setPosY(nextY);
    }
}