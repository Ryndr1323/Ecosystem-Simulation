package com.mycompany.bigproject.children;

import com.mycompany.bigproject.enums.EntityType;
import com.mycompany.bigproject.enums.GenderTable;
import com.mycompany.bigproject.interfaces.AnimalFeeding;
import com.mycompany.bigproject.models.Animal;

public class Carnivore extends Animal implements AnimalFeeding {
    // Fields
    private Animal[] targetField;

    // Constructors
    public Carnivore(String nameCarn, GenderTable genderCarn, double initEnergyCarn) {
        super(nameCarn, genderCarn, initEnergyCarn);
        this.SetEntityType(EntityType.A_CARN);
    }

    public Carnivore(String nameCarn) {
        super(nameCarn);
        this.SetEntityType(EntityType.A_CARN);
    }

    // Methods
    // Interface-Implementations
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
}
