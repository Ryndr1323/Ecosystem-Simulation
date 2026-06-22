package models.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.concretes.Plant;
import models.enums.SeedType;
import models.utils.Randomizer;

public class PlantDescriptor {
    // Fields
    // Static Values
    private static final double MULT_TOP_CEIL = 0.97;
    private static final double MULT_BOT_CEIL = 1.16;


    // Name Schema and Descriptions
    private final String botanicName;
    private final String mainDescription;
    private final String seedType;

    private final boolean isPoisonous;
    private final double ageSeedMature;
    private final double maxAge;

    // Attachment Address
    private final String img_addr;

    // Constructors
    public PlantDescriptor(ResultSet rs) throws SQLException {
        this.botanicName = rs.getString("plant_name");
        this.mainDescription = rs.getString("main_desc");
        this.seedType = rs.getString("seed_type");
        
        this.isPoisonous = rs.getBoolean("poisonous");
        this.ageSeedMature = rs.getDouble("min_age");
        this.maxAge = rs.getDouble("max_age");

        this.img_addr = rs.getString("img_addr");
    }

    // Setters and Getters
    public String getBotanicName() { return this.botanicName; }
    public String getWikiDescription() {return this.mainDescription; }
    public String getSeedlingType() { return this.seedType; }
    public Double getMaxAge() { return this.maxAge; }
    public String getImgAddr() { return this.img_addr; }


    // Methods
    public void injectSimulatedData(Plant plantData) {
        plantData.setEntityName(botanicName);
        plantData.setMaturingAge(ageSeedMature);
        plantData.setMaxAge(maxAge * Randomizer.getRandomDouble(MULT_BOT_CEIL, MULT_TOP_CEIL));
        plantData.setPoisonValue(isPoisonous);
        
        try {
            SeedType type = SeedType.valueOf(seedType.trim().toUpperCase());
            plantData.setSeedlingType(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            plantData.setSeedlingType(SeedType.INV);
        }
    }

    public void retrieveMainWikiData(Plant plantData) {
        plantData.setMaturingAge(ageSeedMature);
        plantData.setMaxAge(maxAge);
        plantData.setPoisonValue(isPoisonous);

        try {
            SeedType type = SeedType.valueOf(seedType.trim().toUpperCase());
            plantData.setSeedlingType(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            plantData.setSeedlingType(SeedType.INV);
        }
    }
}
