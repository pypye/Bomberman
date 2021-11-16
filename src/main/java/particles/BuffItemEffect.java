package particles;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;

public class BuffItemEffect {
    private final ParticleEmitter particleEmitter;
    public BuffItemEffect(Spatial a) {
        particleEmitter = new ParticleEmitter("itemEffect", ParticleMesh.Type.Triangle, 10);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Bomb/Particles/star_04.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setLocalTranslation(a.getLocalTranslation());
        particleEmitter.setImagesX(1);
        particleEmitter.setImagesY(1);
        particleEmitter.setStartColor(new ColorRGBA(1.0f, 0.17f, 1.0f, 1f));
        particleEmitter.setEndColor(new ColorRGBA(1.0f, 0.17f, 1.0f, 1f));
        particleEmitter.setGravity(0, 0, 0);
        particleEmitter.setStartSize(0f);
        particleEmitter.setEndSize(0.3f);
        particleEmitter.setLowLife(1.8f);
        particleEmitter.setHighLife(2.2f);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0.75f, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.5f);
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
