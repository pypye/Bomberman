package audio;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector2f;
import cores.Main;
import entities.players.PlayerList;

public class Audio {
    private final AudioNode audio;
    private boolean looping;
    private boolean positional;
    private float volume;
    private final float range;

    public Audio(String path, boolean positional, boolean looping, float volume) {
        audio = new AudioNode(Main.ASSET_MANAGER, path, AudioData.DataType.Buffer);
        this.setPositional(positional);
        this.setLooping(looping);
        this.setVolume(volume);
        this.range = 10;
        Main.ROOT_NODE.attachChild(audio);
    }

    public AudioNode getAudio() {
        return audio;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        audio.setVolume(volume);
    }

    private void setPositional(boolean positional) {
        this.positional = positional;
        audio.setPositional(positional);
    }

    private void setLooping(boolean looping) {
        this.looping = looping;
        audio.setLooping(looping);
    }

    public void setLocalTranslation(float x, float y, float z) {
        audio.setLocalTranslation(x, y, z);
        if (positional) {
            if (PlayerList.getMainPlayer() != null) {
                Vector2f audioPos = new Vector2f(audio.getLocalTranslation().x, audio.getLocalTranslation().z);
                Vector2f playerPos = new Vector2f(PlayerList.getMainPlayer().getPosition().x, PlayerList.getMainPlayer().getPosition().z);
                float distance = (float) Math.sqrt((audioPos.x - playerPos.x) * (audioPos.x - playerPos.x) + (audioPos.y - playerPos.y) * (audioPos.y - playerPos.y));
                if (distance > range) audio.setVolume(0);
                else audio.setVolume(volume * (1 - distance / range));
            }
        }
    }

    public void play() {
        if (looping) audio.play();
        else audio.playInstance();
    }
}
