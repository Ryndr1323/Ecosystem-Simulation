package com.mycompany.bigproject.interfaces;

import com.mycompany.bigproject.models.Animal;

public interface AnimalFeeding {
    public Animal[] GetPreyList();
    public void SetPreyList(Animal[] arrAnimalField);
}
