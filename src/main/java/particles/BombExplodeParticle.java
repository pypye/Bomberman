package particles;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import cores.Main;

public class BombExplodeParticle {
    public static double DURATION = 1000;
    private final double startTime;
    private final ParticleEmitter particleEmitter;

    public BombExplodeParticle(int x, int z) {
        particleEmitter = new ParticleEmitter("bombSpark", ParticleMesh.Type.Triangle, 15);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Particles/bomb_explode.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setLocalTranslation(x * 2f, 1, z * 2f);
        particleEmitter.setImagesX(1);
        particleEmitter.setImagesY(2);
        particleEmitter.setStartColor(new ColorRGBA(1f, 0.1f, 0.1f, 1f));
        particleEmitter.setEndColor(new ColorRGBA(0f, 0f, 0f, 1f));
        particleEmitter.setGravity(0, 0, 0);
        particleEmitter.setStartSize(0.8f);
        particleEmitter.setEndSize(0.0f);
        particleEmitter.setLowLife(1.2f);
        particleEmitter.setHighLife(1.5f);
        particleEmitter.setRotateSpeed(2);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 1, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.5f);
        particleEmitter.emitAllParticles();
        Main.ROOT_NODE.attachChild(particleEmitter);
        startTime = System.currentTimeMillis();
    }
    public void remove(){
        particleEmitter.killAllParticles();
        Main.ROOT_NODE.detachChild(particleEmitter);
    }
    public ParticleEmitter getParticleEmitter() {
        return particleEmitter;
    }

    public double getStartTime() {
        return startTime;
    }
}
