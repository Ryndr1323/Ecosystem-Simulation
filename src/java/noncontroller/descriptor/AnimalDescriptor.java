package noncontroller.descriptor;

import java.util.HashMap;
import java.util.Map;

import noncontroller.interfaces.IEntityDescriptor;

public class AnimalDescriptor implements IEntityDescriptor {
    // Fields
    private final String mainDesc;
    private final String habitantPlace;

    // Constructors
    public AnimalDescriptor(String mainDesc, String habitantPlace) {
        this.mainDesc = mainDesc;
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
        map.put("habitat", habitantPlace);
        return map;
    }
}
