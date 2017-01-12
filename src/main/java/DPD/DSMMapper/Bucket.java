package DPD.DSMMapper;

import java.util.HashMap;

/**
 * Created by Justice on 1/12/2017.
 */
public class Bucket extends HashMap<String, Entity> {

    private int pocket;

    public int nextPocket() {
        return ++pocket;
    }

    public void addIfNotExists(String... entityIds) {
        for(String entityId: entityIds) {
            if(! keySet().contains(entityId))
                put(entityId, new Entity());
        }
    }
}
