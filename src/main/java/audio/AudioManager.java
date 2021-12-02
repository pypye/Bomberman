package audio;

public class AudioManager {
    public static Audio lobby;
    public static Audio placeBomb;
    public static Audio explosion;
    public static Audio powerUp;

    public static void initialize() {
        lobby = new Audio("Sounds/Lobby.wav", false, true, 2);
        placeBomb = new Audio("Sounds/PlaceBomb.ogg", true, false, 1);
        explosion = new Audio("Sounds/Explosion.ogg", true, false, 2);
        powerUp = new Audio("Sounds/Powerup.ogg", true, false, 1);
    }
}
