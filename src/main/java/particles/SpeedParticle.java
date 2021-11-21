package particles;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;

public class SpeedParticle {
    private final ParticleEmitter particleEmitter;
    private final Spatial link;

    public SpeedParticle(Spatial link) {
        this.link = link;
        particleEmitter = new ParticleEmitter("bombSpark", ParticleMesh.Type.Triangle, 15);
        Material material = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Particles/lightning1.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setLocalTranslation(link.getLocalTranslation().x, 1, link.getLocalTranslation().z);
        particleEmitter.setImagesX(1);
        particleEmitter.setImagesY(1);
        particleEmitter.setFacingVelocity(true);
        particleEmitter.setStartColor(new ColorRGBA(1.0f, 0.17f, 1.0f, 1f));
        particleEmitter.setEndColor(new ColorRGBA(1.0f, 0.17f, 1.0f, 1f));
        particleEmitter.setGravity(0, 0, 0);
        particleEmitter.setStartSize(1f);
        particleEmitter.setEndSize(1f);
        particleEmitter.setLowLife(0.5f);
        particleEmitter.setHighLife(0.6f);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 1.35f, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(1.45f);
    }

    public void onUpdate(boolean speedBuffActivated) {
        if (speedBuffActivated) {
            particleEmitter.setLocalTranslation(link.getLocalTranslation().x, 1, link.getLocalTranslation().z);
            if (!Main.ROOT_NODE.hasChild(particleEmitter)) {
                particleEmitter.emitAllParticles();
                Main.ROOT_NODE.attachChild(particleEmitter);
            }
        } else {
            if (Main.ROOT_NODE.hasChild(particleEmitter)) {
                particleEmitter.killAllParticles();
                Main.ROOT_NODE.detachChild(particleEmitter);
            }
        }

    }

}
