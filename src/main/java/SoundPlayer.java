import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class SoundPlayer {

    public HashMap<String, Media> soundHashMap;

    public HashMap<String, MediaPlayer> musicHashMap;
    String _currentMusicKey = "_none_";

    public SoundPlayer() {

        // Setup
        soundHashMap = new HashMap<>();
        soundHashMap.put("death", new Media(getClass().getResource("death.wav").toString()));
        soundHashMap.put("click", new Media(getClass().getResource("click.wav").toString()));
        soundHashMap.put("schuss", new Media(getClass().getResource("shot.wav").toString()));

        // Setup Music
        musicHashMap = new HashMap<>();
        musicHashMap.put("o1", new MediaPlayer(new Media(getClass().getResource("Hinter den Wolken(OW1).mp3").toString())));
        musicHashMap.put("o2", new MediaPlayer(new Media(getClass().getResource("OW2.mp3").toString())));
        musicHashMap.put("o3", new MediaPlayer(new Media(getClass().getResource("Himmel aus Pixeln(OW3).mp3").toString())));
        musicHashMap.put("n1", new MediaPlayer(new Media(getClass().getResource("Himmel aus Pixeln (NE1).mp3").toString())));
        musicHashMap.put("n2", new MediaPlayer(new Media(getClass().getResource("Crimson Echoes(NE2).mp3").toString())));
        musicHashMap.put("n3", new MediaPlayer(new Media(getClass().getResource("NE3.mp3").toString())));
        musicHashMap.put("e1", new MediaPlayer(new Media(getClass().getResource("E1.mp3").toString())));
        musicHashMap.put("e2", new MediaPlayer(new Media(getClass().getResource("E2.mp3").toString())));
        musicHashMap.put("e3", new MediaPlayer(new Media(getClass().getResource("Endboss.mp3").toString())));
        musicHashMap.put("mainMenu", new MediaPlayer(new Media(getClass().getResource("Hinter den Bäumen(Main Menu).mp3").toString())));
        musicHashMap.put("win", new MediaPlayer(new Media(getClass().getResource("Win_music.wav").toString())));
        musicHashMap.put("fail", new MediaPlayer(new Media(getClass().getResource("Fail_music.wav").toString())));

    }

    public void playSound(String id, double volume) {
        MediaPlayer newPlayer = new MediaPlayer(soundHashMap.get(id));
        newPlayer.setVolume(volume);
        newPlayer.play();
    }

    public void setMusic(String id) {
        if (id.equals(_currentMusicKey)) {
            return;
        }

        if(!_currentMusicKey.equals("_none_")) musicHashMap.get(_currentMusicKey).stop();

        musicHashMap.get(id).setCycleCount(MediaPlayer.INDEFINITE);
        musicHashMap.get(id).stop();
        musicHashMap.get(id).play();

        _currentMusicKey = id;
    }


}
