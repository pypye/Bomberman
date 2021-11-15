package cores;

import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class LightUtils {
    public static void setSpatialLight(Spatial spatial) {
        final DirectionalLight sun1 = new DirectionalLight();
        final DirectionalLight sun2 = new DirectionalLight();
        final DirectionalLight sun3 = new DirectionalLight();
        final DirectionalLight sun4 = new DirectionalLight();
        sun1.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        sun2.setDirection(new Vector3f(0.1f, -0.7f, 1.0f));
        sun3.setDirection(new Vector3f(-1.0f, -0.7f, -0.1f));
        sun4.setDirection(new Vector3f(1.0f, -0.7f, 0.1f));
        spatial.addLight(sun1);
        spatial.addLight(sun2);
        spatial.addLight(sun3);
        spatial.addLight(sun4);
    }
}
