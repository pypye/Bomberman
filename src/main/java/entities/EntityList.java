package entities;

import java.util.ArrayList;

public abstract class EntityList {
    protected static final ArrayList<Entity> entityList = new ArrayList<>();
    public static void add(Entity entity) {
        entityList.add(entity);
    }
}
