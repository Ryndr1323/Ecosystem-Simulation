package noncontroller.descriptor;

import java.util.HashMap;
import java.util.Map;

import noncontroller.interfaces.IEntityDescriptor;

public class PlantDescriptor implements IEntityDescriptor {
    // Fields
    private final String mainDesc;
    private final String seedType;
    private final String habitantPlace;

    // Constructors
    public PlantDescriptor(String mainDesc, String seedType, String habitantPlace) {
        this.mainDesc = mainDesc;
        this.seedType = seedType;
        this.habitantPlace = habitantPlace;
    }

    // Override Methods
    @Override
    public String getDescriptor() {
        return this.mainDesc;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("description", mainDesc);
        map.put("seedType", seedType);
        map.put("habitat", habitantPlace);
        return map;
    }
}
