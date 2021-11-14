package cores;

import com.jme3.anim.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.SafeArrayList;

/**
 * @author Ali-RS
 */
public class AnimUtils {

    public static SkinningControl findSkinningControl(Spatial model) {
        Spatial s = getAnimRoot(model);
        SkinningControl ac = s == null ? null : s.getControl(SkinningControl.class);
        return ac;
    }

    public static AnimComposer findAnimComposer(Spatial model) {
        Spatial s = getAnimRoot(model);
        AnimComposer ac = s == null ? null : s.getControl(AnimComposer.class);
        return ac;
    }

    public static Spatial getAnimRoot(Spatial model) {
        // Find spatial with the composer
        // For the moment, we'll guess
//    Spatial animRoot = ((Node) model).getChild("root");
//    if (animRoot == null) {
//        // Have to find it the hard way
//        animRoot = findAnimRoot(model);
//    }
        return findAnimRoot(model);
    }

    protected static Spatial findAnimRoot(Spatial s) {
        if (s.getControl(AnimComposer.class) != null) {
            return s;
        }
        if (s instanceof Node) {
            for (Spatial child : ((Node) s).getChildren()) {
                Spatial result = findAnimRoot(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static AnimClip retargetClip(AnimClip sourceClip, Spatial target) {
        Spatial animRoot = getAnimRoot(target);
        if (animRoot == null) {
            System.err.println("Anim root is null!");
            return null;
        }

        SkinningControl sc = animRoot.getControl(SkinningControl.class);
        if (target.getUserData("hasInitialPose") == null) {
            sc.getArmature().applyBindPose();
            sc.getArmature().saveInitialPose();
            target.setUserData("hasInitialPose", Boolean.TRUE);
        }

        SafeArrayList<AnimTrack> tracks = new SafeArrayList<>(AnimTrack.class);
        for (AnimTrack animTrack : sourceClip.getTracks()) {
            if (animTrack instanceof TransformTrack) {
                TransformTrack sourceTrack = (TransformTrack) animTrack;
                Joint sourceJoint = (Joint) sourceTrack.getTarget();
                // I am using a standard humanoid rig for all my characters so joint
                // names are the same on all rigs
                Joint targetJoint = sc.getArmature().getJoint(sourceJoint.getName());
                if (targetJoint == null) {
                    System.err.println("Joint with name " + sourceJoint.getName() + " not fount on target");
                    continue;
                }

                // Only Rotations are cloned
                TransformTrack targetTrack = new TransformTrack(targetJoint, sourceTrack.getTimes(), null, sourceTrack.getRotations(), null);//sourceTrack.jmeClone();
                targetTrack.setTarget(targetJoint);

                if (targetJoint.getName().equals("root.x")) {
                    System.out.println("Transferring root motion...");
                    // Modify translations to fit in target rig
                    if (sourceTrack.getTranslations() != null) {
                        Vector3f[] translations = new Vector3f[sourceTrack.getTimes().length];
                        boolean convertToInPlace = false;
                        Vector3f start = new Vector3f(sourceTrack.getTranslations()[0]);
                        Vector3f end = new Vector3f(sourceTrack.getTranslations()[sourceTrack.getTranslations().length - 1]);
                        start.y = 0;
                        end.y = 0;
                        if (start.distance(end) > 0.30f) {
                            convertToInPlace = true;
                            System.out.println("Converting to InPlace:" + sourceClip.getName());
                        }

                        for (int i = 0; i < sourceTrack.getTranslations().length; i++) {
                            // delta translation = track translation - bind translation
                            Vector3f deltaTranslation = sourceTrack.getTranslations()[i].subtract(sourceJoint.getLocalTranslation());
                            if (convertToInPlace) {
                                deltaTranslation.x = 0f;
                                deltaTranslation.z = 0f;
                            }
                            Vector3f targetTranslation = new Vector3f(targetJoint.getInitialTransform().getTranslation());
                            targetTranslation.addLocal(deltaTranslation);
                            translations[i] = targetTranslation;
                        }
                        targetTrack.setKeyframesTranslation(translations);
                    }
                }
                tracks.add(targetTrack);
            }
        }

        AnimClip targetClip = new AnimClip(sourceClip.getName());
        targetClip.setTracks(tracks.getArray());
        return targetClip;
    }
}
