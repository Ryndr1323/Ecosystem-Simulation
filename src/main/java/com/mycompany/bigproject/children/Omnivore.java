package com.mycompany.bigproject.children;

import com.mycompany.bigproject.enums.EntityType;
import com.mycompany.bigproject.enums.GenderTable;
import com.mycompany.bigproject.interfaces.AnimalFeeding;
import com.mycompany.bigproject.interfaces.Nutrition;
import com.mycompany.bigproject.interfaces.PlantFeeding;
import com.mycompany.bigproject.models.Animal;
import com.mycompany.bigproject.models.Plant;

public class Omnivore extends Animal implements AnimalFeeding, PlantFeeding, Nutrition {
    // Fields
    private Animal[] targetField;
    private Plant[] favoriteField;
    private double nutritionValue;
    
    // Constructors
    public Omnivore(String nameOmni, GenderTable genderOmni, double initEnergyOmni) {
        super(nameOmni, genderOmni, initEnergyOmni);
        this.SetEntityType(EntityType.A_OMNI);
    }

    public Omnivore(String nameOmni) {
        super(nameOmni);
        this.SetEntityType(EntityType.A_OMNI);
    }

    // Methods
    // Interface-Implements
    @Override
    public void DoFeeding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Animal[] GetPreyList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void SetPreyList(Animal[] arrAnimalField) {
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
