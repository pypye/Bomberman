package entities.buffs;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import cores.Main;
import cores.Map;
import entities.Entity;
import entities.players.Player;
import particles.BuffItemParticle;

import java.util.Random;

public class BuffItem extends Entity {
    private BuffItemParticle buffEffect;
    private Spatial shadow;
    public BuffItem(Vector3f position, String path) {
        super(position, path);
        Quaternion rot = spatial.getLocalRotation();
        rot.slerp(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y), 0.5f);
        spatial.setLocalRotation(rot);

        Material mat_shad = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_shad.setTexture("ColorMap", Main.ASSET_MANAGER.loadTexture("Textures/PowerUp/Shadow.png"));
        mat_shad.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        shadow = new Geometry("Box", new Box(0.3f, 0f, 0.3f));
        shadow.setMaterial(mat_shad);
        shadow.setQueueBucket(RenderQueue.Bucket.Transparent);
        shadow.setLocalTranslation(position.x, position.y - 0.4f, position.z);
        buffEffect = new BuffItemParticle(spatial);
        Main.ROOT_NODE.attachChild(shadow);
    }

    public static void generateBuffItem(int cordX, int cordY) {
        Random random = new Random();
        int lower = 5, upper = 9;
        Map.setObject(cordX, cordY, random.nextInt(upper - lower) + lower, null);
    }

    public void buff(Player player) {
        int x = (int) this.getCord().x;
        int y = (int) this.getCord().y;
        Map.setObject(x, y, Map.GRASS, null);
        buffEffect.remove();
        Main.ROOT_NODE.detachChild(shadow);
        shadow = null;
    }
}
