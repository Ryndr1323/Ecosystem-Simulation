package noncontroller.interfaces;

import java.util.Map;

public interface IEntityDescriptor {
    String getDescriptor();
    Map<String, Object> toMap();
}
