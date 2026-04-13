package com.mycompany.bigproject.models;

// Importer
import com.mycompany.bigproject.enums.GenderTable;
import com.mycompany.bigproject.utils.Randomizer;

public abstract class Animal extends Entity {
    // Fields
    private String nameAnimal;
    private GenderTable genderAnimal;
    private double energyAnimal;
    private double minEnergyThreshold;

    // Constructors
    public Animal(String name, GenderTable gender, double initEnergy) {
        super();
        // Check Name
        this.nameAnimal = (name == null || name.isEmpty()) ? "unknown" : name;
        // Check Gender
        this.genderAnimal = (gender == GenderTable.INV) ? Randomizer.getRandomGender() : gender;
        // Apply Raw Energy
        this.energyAnimal = Math.max(0, Math.min(1.0, initEnergy));
        // Apply Automatically; Min Threshold
        this.minEnergyThreshold = Randomizer.getRandomDouble(0.4, 0.65);

        // End Check
        if (initEnergy <= 0.0) {
            this.SetAliveStatus(false);
            System.out.printf("%s {%d} Dead Upon Birth; Energy Upon Birth: %.1f\n", this.nameAnimal, this.getID(), initEnergy);
        }
    }

    public Animal(String name) {
        this((name == null || name.isEmpty()) ? "unknown" : name , GenderTable.INV, Randomizer.getRandomDouble(0, 1));
    }

    // Setters and Getters
    public String GetAnimalName() {
        return this.nameAnimal;
    }

    public GenderTable GetAnimalGender() {
        return this.genderAnimal;
    }

    public double GetEnergyStat() {
        return this.energyAnimal;
    }

    public double GetEnergyThreshold() {
        return this.minEnergyThreshold;
    }

    // Methods
    // Abstracts
    public abstract void DoFeeding();

    // Non Abstracts
    public void ConsumeEnergy(double amount) {
        this.energyAnimal -= amount;
    }
    public void StatusChecker(String reasoning) {
        if (energyAnimal <= 0) {
            this.energyAnimal = 0;
            SetAliveStatus(false);
            if (reasoning != null && !reasoning.isEmpty()) {
                System.out.printf("%s {%d} died | Reason: %s\n", nameAnimal, this.getID(), reasoning);
            } else {
                System.out.printf("%s {%d} died | Reason: Unknown\n", nameAnimal, this.getID());
            }
        }
    }

    public void DoAction(String actType, double energyReq) {
        if (this.energyAnimal < energyReq) {
            System.out.printf("Action of %s cannot be executed by %s {%d}; Requires More %.1f Energy\n", actType, this.GetAnimalName(), this.getID(), energyReq - this.GetEnergyStat());
        } else {
            this.energyAnimal -= energyReq;
            System.out.printf("Action of %s executed by %s {%d}; %.1f Energy Consumed\n", actType, this.GetAnimalName(), this.getID(), energyReq);
            this.StatusChecker("Exhaustion");
        }
    }

    public void Reproduction(Animal targetAnimal) {
        if ((this.nameAnimal.equalsIgnoreCase(targetAnimal.GetAnimalName())) && (this.genderAnimal != targetAnimal.GetAnimalGender())) {
            if ((this.energyAnimal >= this.minEnergyThreshold) && (targetAnimal.GetEnergyStat() >= targetAnimal.GetEnergyThreshold())) {
                this.ConsumeEnergy(this.minEnergyThreshold);
                targetAnimal.ConsumeEnergy(targetAnimal.GetEnergyThreshold());
                this.StatusChecker("Exhaustion From Breeding");
                targetAnimal.StatusChecker("Exhaustion From Breeding");
                // Add Entity Soon
            } else {
                String formatter = "";
                if (this.energyAnimal < this.minEnergyThreshold) {
                    formatter += this.nameAnimal + "have insufficient energy (" + (this.minEnergyThreshold - this.energyAnimal) + ") required\n";
                }
                if (targetAnimal.GetEnergyStat() < targetAnimal.GetEnergyThreshold()) {
                    formatter += targetAnimal.GetAnimalName() + "have insufficient energy (" + (targetAnimal.GetEnergyThreshold() - targetAnimal.GetEnergyStat()) + ") required\n";
                }

                System.out.println("Cannot Reproduce, Exhausted Animals: \n" + formatter);
            }
        } else {
            if (!this.nameAnimal.equalsIgnoreCase(targetAnimal.GetAnimalName())) {
                System.out.println("Cannot Reproduce, Different Animal Type");
            } else if (this.genderAnimal == targetAnimal.GetAnimalGender()) {
                System.out.println("Cannot Reproduce, Same Genders");
            }
        }
    }
}
