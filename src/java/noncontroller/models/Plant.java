package noncontroller.models;

import java.util.HashMap;
import java.util.Map;
import noncontroller.utils.Randomizer;

public class Plant extends Entity {
    // Fields
    private String speciesType;
    private boolean isPoisonous;

    // Constructors
    public Plant(String speciesInput) {
        super();
        this.isPoisonous = false;
    }

    // Setters and Getters


    // Methods
    // Non Abstract
    // TBA

    // Abstract Implementations
    @Override
    public void doInteract(){
        
    }

    @Override
    public Map<String, Object> dataFetch() {
        Map<String, Object> plantData = new HashMap<>();

        // 5. Mengembalikan paket data yang sudah lengkap
        return plantData;
    }

    
}