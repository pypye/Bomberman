package scenes;

public abstract class Scene {
    protected boolean isActive;

    public Scene() {
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public abstract void update(float tpf);

    public abstract void show();

    public abstract void remove();

    public abstract void restart();

    public int getLevel(){
        return 0;
    }
}
