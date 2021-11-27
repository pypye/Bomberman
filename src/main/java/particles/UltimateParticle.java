package particles;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import cores.Main;

public class UltimateParticle {
    private final ParticleEmitter particleEmitter;
    private final Spatial link;

    public UltimateParticle(Spatial link) {
        this.link = link;
        particleEmitter = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(Main.ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", Main.ASSET_MANAGER.loadTexture("Textures/Particles/flame_02.png"));
        particleEmitter.setMaterial(mat_red);
        particleEmitter.setLocalTranslation(new Vector3f(0, 1, 0));
        particleEmitter.setImagesX(1);
        particleEmitter.setImagesY(1);
        particleEmitter.setEndColor(new ColorRGBA(0f, 1f, 1f, 1f));
        particleEmitter.setStartColor(new ColorRGBA(1f, 1f, 0f, 1f));
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        particleEmitter.setStartSize(1.5f);
        particleEmitter.setEndSize(0.1f);
        particleEmitter.setGravity(0, 0, 0);
        particleEmitter.setLowLife(0.5f);
        particleEmitter.setHighLife(1f);
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.3f);
    }

    public void onUpdate(boolean ultimateActivated) {
        if (ultimateActivated) {
            particleEmitter.setLocalTranslation(link.getLocalTranslation().x, 1, link.getLocalTranslation().z);
            show();
        } else hide();
    }

    public void show() {
        if (!Main.ROOT_NODE.hasChild(particleEmitter)) {
            particleEmitter.emitAllParticles();
            Main.ROOT_NODE.attachChild(particleEmitter);
        }
    }

    public void hide() {
        if (Main.ROOT_NODE.hasChild(particleEmitter)) {
            particleEmitter.killAllParticles();
            Main.ROOT_NODE.detachChild(particleEmitter);
        }
    }
}
