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
import entities.Entity;
import particles.BuffItemEffect;

public class BuffItem extends Entity {
    public BuffItem(Vector3f position, String path) {
        super(position, path);
        Quaternion rot = spatial.getLocalRotation();
        rot.slerp(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y), 0.5f);
        spatial.setLocalRotation(rot);

        Material mat_shad = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_shad.setTexture("ColorMap", Main.ASSET_MANAGER.loadTexture("Textures/PowerUp/Shadow.png"));
        mat_shad.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Spatial shadow = new Geometry("Box", new Box(0.3f, 0f, 0.3f));
        shadow.setMaterial(mat_shad);
        shadow.setQueueBucket(RenderQueue.Bucket.Transparent);
        shadow.setLocalTranslation(position.x, position.y - 0.4f, position.z);
        new BuffItemEffect(spatial);
        Main.ROOT_NODE.attachChild(shadow);
    }
}
