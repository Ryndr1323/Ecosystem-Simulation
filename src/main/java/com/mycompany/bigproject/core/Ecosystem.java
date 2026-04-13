package com.mycompany.bigproject.core;

// Importer
import com.mycompany.bigproject.children.Carnivore;
import com.mycompany.bigproject.children.Herbivore;
import com.mycompany.bigproject.children.Omnivore;
import com.mycompany.bigproject.models.Animal;
import com.mycompany.bigproject.models.Entity;
import com.mycompany.bigproject.models.Plant;
import com.mycompany.bigproject.utils.Randomizer;

public class Ecosystem extends Entity {
    // Constant Values
    // Modifier of Ecosystem Size
    public static final double PLANT_SIZE = 0.5;
    public static final double ANIMAL_SIZE = 0.5;
    // Modifier of Animal Size
    public static final double TYPE_HERB_MULT = 0.6;
    public static final double TYPE_CARN_MULT = 0.1;
    public static final double TYPE_OMNI_MULT = 0.3;

    // Modifier of Array Allocation
    public static final int GLOBAL_ENTITY_CAP = 250;
    public Entity[] Simulatist = new Entity[GLOBAL_ENTITY_CAP];
    public static final double ALLOCATION_MULT = 0.8;

    // Allocating
    public static final int ACTIVE_SLOT = (int) Math.round(GLOBAL_ENTITY_CAP <= 0 ? -1 : GLOBAL_ENTITY_CAP * ALLOCATION_MULT);
    public static final int ACTIVE_PLANTINGS = (int) (ACTIVE_SLOT == -1 ? -1 : Math.round(ACTIVE_SLOT <= 1 ? -1 : ACTIVE_SLOT * PLANT_SIZE));
    public static final int ACTIVE_ANIMALS = (int) (ACTIVE_SLOT == -1 ? -1 : Math.round(ACTIVE_SLOT <= 1 ? -1 : ACTIVE_SLOT * ANIMAL_SIZE));
    public static final int ACTIVE_HERBS = (int) (ACTIVE_ANIMALS == -1 ? -1 : Math.round(ACTIVE_ANIMALS <= 1 ? -1 : ACTIVE_ANIMALS * TYPE_HERB_MULT));
    public static final int ACTIVE_CARNS = (int) (ACTIVE_ANIMALS == -1 ? -1 : Math.round(ACTIVE_ANIMALS <= 1 ? -1 : ACTIVE_ANIMALS * TYPE_CARN_MULT));
    public static final int ACTIVE_OMNIS = (int) (ACTIVE_ANIMALS == -1 ? -1 :Math.round(ACTIVE_ANIMALS <= 1 ? -1 : ACTIVE_ANIMALS * TYPE_OMNI_MULT));

    

    // Methods
    public void AddEntity(Entity value) {

    }

    public void AddEntity() {
        int HasBeenAdded = 0;

        System.out.printf("""
            Size of Limit:
            o-o-GLOBAL_ENTITY_CAP: %d
            o-o-ACTIVE_SLOT: %d
            |-o-ACTIVE_PLANTINGS: %d
            |-o-ACTIVE_ANIMALS: %d
            | |-ACTIVE_HERBS: %d
            | |-ACTIVE_CARNS: %d
            o-o-ACTIVE_OMNIS: %d
            """, GLOBAL_ENTITY_CAP, ACTIVE_SLOT, ACTIVE_PLANTINGS, ACTIVE_ANIMALS, ACTIVE_HERBS, ACTIVE_CARNS, ACTIVE_OMNIS);
        // Le Plant
        String[] PlantNaming = {
            "Grass",
            "Elephant Grass",    // Cocok untuk makanan utama
            "Red Clover",        // Nutrisi tinggi
            "Wild Berry",        // Bisa ditambahkan logika isPoisonous secara acak
            "Dandelion",         // Tanaman liar umum
            "River Fern",        // Biasanya tumbuh di area lembap
            "Golden Wheat",      // Nutrisi sangat tinggi
            "Blue Nightshade",   // Bagus untuk testing 'isPoisonous = true'
            "Alfalfa",           // Favorit herbivora
            "Oak Sapling",       // Bisa jadi pohon besar nantinya
            "Morning Glory"      // Tanaman merambat
        };
        // Le Grassy
        String[] HerbNaming = {
            "Cow",
            "Horse",
            "Elephant",
            "Girrafe",
            "Deer",
            "Koala",
            "Panda",
            "Alpaca"
        };
        // Le Meat
        String[] CarnNaming = {
            "Bengal Tiger",
            "Arctic Wolf",
            "Komodo",
            "Black Panther",
            "Golden Eagle",
            "Leopard",
            "Saltwater Crocodile",
            "Jaguars"
        };
        // Le Versatille
        String[] OmniNaming = {
            "Grizzly Bear",
            "Wild Boar",
            "Red Fox",
            "Raccoon",
            "Chimpanzee",
            "Opossum",
            "Chipmunks",
            "Skunks"
        };

        for(int i = 0; HasBeenAdded < ACTIVE_SLOT && i < ACTIVE_PLANTINGS; i++) {
            Simulatist[HasBeenAdded++] = new Plant(PlantNaming[Randomizer.getRandomInt(0, PlantNaming.length - 1)]);
        }

        for(int i = 0; HasBeenAdded < ACTIVE_SLOT && i < ACTIVE_HERBS; i++) {
            Simulatist[HasBeenAdded++] = new Herbivore(HerbNaming[Randomizer.getRandomInt(0, HerbNaming.length - 1)]);
        }

        for(int i = 0; HasBeenAdded < ACTIVE_SLOT && i < ACTIVE_CARNS; i++) {
            Simulatist[HasBeenAdded++] = new Carnivore(CarnNaming[Randomizer.getRandomInt(0, CarnNaming.length - 1)]);
        }

        for(int i = 0; HasBeenAdded < ACTIVE_SLOT && i < ACTIVE_OMNIS; i++) {
            Simulatist[HasBeenAdded++] = new Omnivore(OmniNaming[Randomizer.getRandomInt(0, OmniNaming.length - 1)]);
        }

        for (int idx = 0; idx < ACTIVE_SLOT; idx++) {
            String outputter = "";
            outputter += String.format("[Index %03d] | ID of Index{%04d} ", idx, Simulatist[idx].getID());
            if (Simulatist[idx] instanceof Plant) {
                Plant currPlant = (Plant) Simulatist[idx];
                outputter += String.format("""
                    | TypeField: %s | Name: %s | Nutrition Value: %.03f
                    """, currPlant.GetEntityType().getLabel(), currPlant.GetPlantName(), currPlant.GetNutritionValue());
            } else if (Simulatist[idx] instanceof Animal) {
                Animal currAnimal = (Animal) Simulatist[idx];
                outputter += String.format("""
                    | TypeField: %s | Name: %s | Gender: %s | Energy {Curr: %.03f | MinThreshold: %.03f}
                    """, currAnimal.GetEntityType().getLabel(), currAnimal.GetAnimalName(), currAnimal.GetAnimalGender().getLabel(), currAnimal.GetEnergyStat(), currAnimal.GetEnergyThreshold());
            }
            System.out.println(outputter);
        }
    }

    public void Purgatory() {

    }

    public void Simulate() {
        
    }
}
