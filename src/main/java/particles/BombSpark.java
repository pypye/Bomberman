package particles;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import cores.Main;

public class BombSpark {
    private final ParticleEmitter particleEmitter;

    public BombSpark(Spatial a) {
        particleEmitter = new ParticleEmitter("bombSpark", ParticleMesh.Type.Triangle, 15);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Bomb/bomb_spark.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setLocalTranslation(a.getLocalTranslation());
        particleEmitter.getLocalTranslation().addLocal(0, 1.35f, 0.4f);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.setStartColor(new ColorRGBA(1f, 1f, 0.1f, 1f));
        particleEmitter.setEndColor(new ColorRGBA(1f, 0.1f, 0.1f, 1f));
        particleEmitter.setGravity(0, 0, 0);
        particleEmitter.setStartSize(0.5f);
        particleEmitter.setEndSize(0.2f);
        particleEmitter.setLowLife(0.3f);
        particleEmitter.setHighLife(0.4f);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(1f);
        particleEmitter.emitAllParticles();
        Main.ROOT_NODE.attachChild(particleEmitter);
    }

    public void remove() {
        particleEmitter.killAllParticles();
        Main.ROOT_NODE.detachChild(particleEmitter);
    }

    public ParticleEmitter getParticleEmitter() {
        return particleEmitter;
    }
}
