package com.mycompany.bigproject.models;

// Importer
import com.mycompany.bigproject.enums.EntityType;
import com.mycompany.bigproject.interfaces.Nutrition;
import com.mycompany.bigproject.utils.Randomizer;

public class Plant extends Entity implements Nutrition {
    // Field
    private String name;
    private double grownStat;
    private double growIncrementStat;
    private double maxGrownStat;
    private double nutritionVal;
    @SuppressWarnings("Shut Up")
    private boolean isPoisonous;

    // Constructors
    public Plant(String name, double grownStat, double growIncrementStat, double maxGrownStat, double nutritionVal, boolean isPoisonous) {
        super();
        this.SetEntityType(EntityType.PLANT);
        this.name = name;
        this.grownStat = Math.max(0, Math.min(1, grownStat));
        this.maxGrownStat = Math.max(1, Math.min(1.5, maxGrownStat));
        this.nutritionVal = Math.max(0, Math.min(1, nutritionVal));
        this.isPoisonous = isPoisonous;
    }

    public Plant(String name) {
        this(name, Randomizer.getRandomDouble(0, 1), Randomizer.getRandomDouble(0.05, 0.13), Randomizer.getRandomDouble(1.2, 1.4), Randomizer.getRandomDouble(0.5, 1), false);
    }

    // Setters and Getters
    public String GetPlantName() {
        return this.name;
    }

    public void ModifyPoisonousValue(boolean value) {
        this.isPoisonous = value;
    }

    // Methods
    // Interface-Implementation
    @Override
    public double GetNutritionValue() {
        return this.nutritionVal;
    }

    @Override
    public void SetNutritionValue(double value) {
        this.nutritionVal = value;
    }

    // Normal Implementation
    public void GrowPlant(double increment) {
        this.grownStat += this.growIncrementStat;
        if (grownStat > maxGrownStat) {
            this.SetAliveStatus(false);
            System.out.printf("%s {%d} Died due to Overgrown\n", this.name, this.getID());
        }
    }
}
