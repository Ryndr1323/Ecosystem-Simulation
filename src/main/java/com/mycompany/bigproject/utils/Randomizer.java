package com.mycompany.bigproject.utils;

// Importer
import java.util.Random;

import com.mycompany.bigproject.enums.GenderTable;

public class Randomizer {
    private static final Random rand = new Random();

    // Randomizer of Int
    public static int getRandomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    // Randomizer of Double
    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * rand.nextDouble();
    }

    public static GenderTable getRandomGender() {
        GenderTable[] GenderSlot = {GenderTable.MALE, GenderTable.FEMALE}; 
        return GenderSlot[rand.nextInt(0, GenderSlot.length)];
    }
}
