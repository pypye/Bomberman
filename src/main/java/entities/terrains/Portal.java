package entities.terrains;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import cores.Main;
import entities.Entity;

public class Portal extends Entity {
    private static int REMAIN = 1;
    private ParticleEmitter mainPortal;
    private ParticleEmitter cloud;
    private ParticleEmitter twirl;
    private Geometry space;

    public Portal(Vector3f position) {
        generateMainTeleport(position);
        generateCloud(position);
        generateTwirl(position);
        generateOtherSpace(position);
        mainPortal.emitAllParticles();
        cloud.emitAllParticles();
        twirl.emitAllParticles();
        Main.ROOT_NODE.attachChild(mainPortal);
        Main.ROOT_NODE.attachChild(cloud);
        Main.ROOT_NODE.attachChild(twirl);
        Main.ROOT_NODE.attachChild(space);
    }

    public void generateMainTeleport(Vector3f position) {
        mainPortal = new ParticleEmitter("mainPortal", ParticleMesh.Type.Triangle, 20);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Particles/Portal/Circle_10.png"));
        mainPortal.setMaterial(material);
        mainPortal.setLocalTranslation(position.x, position.y + 0.2f, position.z);
        mainPortal.setImagesX(1);
        mainPortal.setImagesY(1);
        mainPortal.setStartColor(new ColorRGBA(1f, 0.1f, 1f, 1f));
        mainPortal.setEndColor(new ColorRGBA(1f, 0.1f, 1f, 1f));
        mainPortal.setStartSize(1f);
        mainPortal.setEndSize(1f);
        mainPortal.setRotateSpeed(25f);
        mainPortal.setLowLife(1f);
        mainPortal.setHighLife(1f);
        mainPortal.setFaceNormal(Vector3f.UNIT_Y);
    }

    public void generateCloud(Vector3f position) {
        cloud = new ParticleEmitter("cloud", ParticleMesh.Type.Triangle, 3);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Particles/Portal/Circle_16.png"));
        cloud.setMaterial(material);
        cloud.setLocalTranslation(position.x, position.y + 0.1f, position.z);
        cloud.setImagesX(1);
        cloud.setImagesY(1);
        cloud.setStartColor(new ColorRGBA(0.1f, 0.1f, 1f, 1f));
        cloud.setEndColor(new ColorRGBA(0.1f, 0.1f, 1f, 1f));
        cloud.setStartSize(1.6f);
        cloud.setEndSize(1.6f);
        cloud.setRotateSpeed(10f);
        cloud.setLowLife(1f);
        cloud.setHighLife(0.9f);
        cloud.setFaceNormal(Vector3f.UNIT_Y);
    }

    public void generateTwirl(Vector3f position) {
        twirl = new ParticleEmitter("bombSpark", ParticleMesh.Type.Triangle, 3);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Particles/twirl_03.png"));
        twirl.setMaterial(material);
        twirl.setLocalTranslation(position.x, position.y + 0.1f, position.z);
        twirl.setImagesX(1);
        twirl.setImagesY(1);
        twirl.setStartColor(new ColorRGBA(0f, 0.1f, 1f, 1f));
        twirl.setEndColor(new ColorRGBA(0f, 0f, 0f, 1f));
        twirl.setStartSize(1.5f);
        twirl.setEndSize(1.5f);
        twirl.setRotateSpeed(10f);
        twirl.setLowLife(1f);
        twirl.setHighLife(0.9f);
        twirl.setFaceNormal(Vector3f.UNIT_Y);
    }

    public void generateOtherSpace(Vector3f position) {
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setTexture("ColorMap", Main.ASSET_MANAGER.loadTexture("Textures/Particles/Portal/portal.png"));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        space = new Geometry("Box", new Box(1f, 0f, 1f));
        space.setQueueBucket(RenderQueue.Bucket.Transparent);
        space.setMaterial(material);
        space.setLocalTranslation(position.x, position.y + 0.01f, position.z);
    }

    @Override
    public void remove() {
        mainPortal.killAllParticles();
        cloud.killAllParticles();
        twirl.killAllParticles();
        Main.ROOT_NODE.detachChild(mainPortal);
        Main.ROOT_NODE.detachChild(cloud);
        Main.ROOT_NODE.detachChild(twirl);
        Main.ROOT_NODE.detachChild(space);
        REMAIN = 1;
    }

    public static boolean hasRemain() {
        if (REMAIN > 0) {
            REMAIN--;
            return true;
        }
        return false;
    }
}
