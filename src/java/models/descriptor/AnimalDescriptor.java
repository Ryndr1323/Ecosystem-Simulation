package models.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.abstracts.Animal;
import models.enums.AnimalType;
import models.utils.Randomizer;


public class AnimalDescriptor {
    // Fields
    // Static Values
    private static final double MULT_TOP_CEIL = 0.96;
    private static final double MULT_BOT_CEIL = 1.20;

    // Name Schema and Descriptions
    private final String breedName;
    private final String mainDescription;
    private final String animalType;

    // Simulation Datas
    private final double speedValue;
    private final double chaseMult;
    private final double ageMating;
    private final double maxAge;

    // Attachment Address
    private final String img_addr;

    // Constructors
    public AnimalDescriptor(ResultSet rs) throws SQLException {
        this.breedName = rs.getString("animal_name");
        this.mainDescription = rs.getString("main_desc");
        this.animalType = rs.getString("diet_type");

        this.speedValue = rs.getDouble("base_speed");
        this.chaseMult = rs.getDouble("chase_mult");
        this.ageMating = rs.getDouble("age_mating");
        this.maxAge = rs.getDouble("max_age");

        this.img_addr = rs.getString("img_addr");
    }

    // Setters and Getters
    public String getBreedName() { return this.breedName; }
    public String getWikiDescription() { return this.mainDescription; }
    public String getAnimalType() { return this.animalType; }
    public Double getMaxAge() { return this.maxAge; }
    public String getImgAddr() { return this.img_addr; }

    // Methods
    public void injectSimulatedData(Animal animalData) {
        animalData.setEntityName(breedName);
        animalData.setBaseSpeed(speedValue);
        animalData.setChaseMultiplier(chaseMult);
        animalData.setMatingAge(ageMating);
        animalData.setMaxAge(maxAge * Randomizer.getRandomDouble(MULT_BOT_CEIL, MULT_TOP_CEIL));

        try {
            AnimalType type = AnimalType.valueOf(animalType.trim().toUpperCase());
            animalData.setAnimalType(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            animalData.setAnimalType(AnimalType.INVALID); 
        }
    }
}
