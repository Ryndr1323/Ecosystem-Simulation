package noncontroller.children;

import java.util.Map;

import noncontroller.descriptor.AnimalDescriptor;
import noncontroller.enums.AnimalType;
import noncontroller.models.Animal;
import noncontroller.models.Entity;
import noncontroller.utils.Randomizer;

public class Herbivore extends Animal {
    // Fields
    // Statics
    private static final double MIN_ACT_RANGE = 12;
    private static final double MAX_ACT_RANGE = 18;
    private static final double MIN_SEARCH_RANGE = 100;
    private static final double MAX_SEARCH_RANGE = 125;
    private static final double MIN_BASE_WALK = 1.8;
    private static final double MAX_BASE_WALK = 2.5;
    private static final double MIN_CHASE_MULT = 1.3;
    private static final double MAX_CHASE_MULT = 1.5;

    // Constructors
    // Default Case
    public Herbivore(String breedName) {
        super(breedName);
        this.setAnimalType(AnimalType.HERBIVORE);
        this.setActionRange(Randomizer.getRandomDouble(MIN_ACT_RANGE, MAX_ACT_RANGE));
        this.setSearchRange(Randomizer.getRandomDouble(MIN_SEARCH_RANGE, MAX_SEARCH_RANGE));
        this.setBaseSpeed(Randomizer.getRandomDouble(MIN_BASE_WALK, MAX_BASE_WALK));
        this.setChaseMult(Randomizer.getRandomDouble(MIN_CHASE_MULT, MAX_CHASE_MULT));
    }

    // Overloaded Constructors
    public Herbivore(String breedName, String desc1, String desc2) {
        this(breedName);
        this.descriptor = new AnimalDescriptor(desc1, desc2);
    }

    public Herbivore(String breedName, int maxAgeValue, String desc1, String desc2) {
        this(breedName, desc1, desc2);
        this.maxAge = maxAgeValue * Randomizer.getRandomDouble(1, 1.3);
    }
    
    // Methods
    // Abstract
    @Override
    public Map<String, Object> dataFetch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doInteract() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}