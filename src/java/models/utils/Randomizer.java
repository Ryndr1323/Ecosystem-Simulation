package models.utils;

// Importer
import java.util.Random;
import models.enums.GenderTable;

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

    // Randomizer of Gender
    private static final GenderTable[] GENDER_SLOT = {GenderTable.MALE, GenderTable.FEMALE}; 
    public static GenderTable getRandomGender() {
        return GENDER_SLOT[rand.nextInt(0, GENDER_SLOT.length)];
    }
}
