import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {

    private Media deathSound;
    public MediaPlayer deathSoundPlayer;
    private Media schussSound;
    public MediaPlayer schussSoundPlayer;
    private Media clickSound;
    public MediaPlayer clickSoundPlayer;

    public SoundPlayer() {
        deathSound = new Media(getClass().getResource("death.wav").toString());
        deathSoundPlayer = new MediaPlayer(deathSound);

        schussSound = new Media(getClass().getResource("/shot.wav").toString());
        schussSoundPlayer = new MediaPlayer(schussSound);
        schussSoundPlayer.setVolume(0.3);

        clickSound = new Media(getClass().getResource("/click.wav").toString());
        clickSoundPlayer = new MediaPlayer(clickSound);
    }


}
