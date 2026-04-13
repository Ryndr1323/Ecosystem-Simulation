package com.mycompany.bigproject.children;

import com.mycompany.bigproject.enums.EntityType;
import com.mycompany.bigproject.enums.GenderTable;
import com.mycompany.bigproject.interfaces.Nutrition;
import com.mycompany.bigproject.interfaces.PlantFeeding;
import com.mycompany.bigproject.models.Animal;
import com.mycompany.bigproject.models.Plant;

public class Herbivore extends Animal implements PlantFeeding, Nutrition {
    // Fields
    private Plant[] favoriteField;
    private double nutritionValue;

    // Constructors
    public Herbivore(String nameHerb, GenderTable genderHerb, double initEnergyHerb) {
        super(nameHerb, genderHerb, initEnergyHerb);
        this.SetEntityType(EntityType.A_HERB);
    }

    public Herbivore(String nameHerb) {
        super(nameHerb);
        this.SetEntityType(EntityType.A_HERB);
    }

    // Methods
    // Interface-Implements
    @Override
    public void DoFeeding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Plant[] GetFavoriteField() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void SetFavoriteField(Plant[] arrPlantField) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double GetNutritionValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void SetNutritionValue(double value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
