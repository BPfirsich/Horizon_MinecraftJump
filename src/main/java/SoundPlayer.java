import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {

    private Media deathSound;
    public MediaPlayer deathSoundPlayer;

    public SoundPlayer() {
        deathSound = new Media(getClass().getResource("death.wav").toString());
        deathSoundPlayer = new MediaPlayer(deathSound);
    }


}
