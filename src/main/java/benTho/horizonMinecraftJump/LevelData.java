package benTho.horizonMinecraftJump;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;


public class LevelData {
    public final int BREITE = 50, HOEHE = 50;
    public final ArrayList<Character> SOLIDS = new ArrayList<Character>(
            Arrays.asList('#', '+', '<', '>', 'l', 'r', 'u', 's', '7', '8', 'd', '5', '6', 'n', 'c', 'v', 'e')
    );

    public LevelData(String name, int anzahlFloors, BackgroundImage background, Image loading, String nextKey, String myMusicKey, String myKey) {
        stufe = new String[anzahlFloors];
        levelName = name;
        levelBackground = background;
        loadingScreen = loading;
        nextLevelKey = nextKey;
        this.myMusicKey = myMusicKey;
        this.myKey = myKey;
    }

    // array[y]: y=0 ist der Boden, x=0 ist links der anfang vom level. (vom string)
    public String[] stufe;
    public String levelName;
    //public Color backgroundColor = Color.WHITE;

    public final BackgroundImage levelBackground;
    public final Image loadingScreen;
    public final String nextLevelKey;
    public final String myKey;

    public final String myMusicKey;

    public boolean isBereichSolide(float x, int y) {
        x = (float)Math.floor(x);
        return stufe[y].charAt((int)x) == '#';
    }

    // The centeringOffset is meant to for the player. Its just moves the x pos by 0.5 tiles, so the center of
    // the calculation result is actually in the middle of a tile, instead on the edge

    public Vector2f calcPixelCordsFromTile(int x, int y, Vector2f cameraOffset, boolean centeringOffset) {
        //x += (int)cameraOffset.x;
        //y += (int)cameraOffset.y;

        Vector2f res = new Vector2f(0, 0);

        res.x = x*BREITE - cameraOffset.x;
        res.y = 710 - (y*HOEHE) - 40 - cameraOffset.y; // -40 da die fensterleiste auch dazugerechnet wird...

        if(centeringOffset) res.x -= (float)BREITE/1.5f;

        return res;
    }

    public Vector2f calcMapPosFromPixelPos(float x, float y, Vector2f cameraOffset, boolean centeringOffset) {
        x += cameraOffset.x;
        y += cameraOffset.y;

        if(centeringOffset) x += (float)BREITE/1.5f;

        Vector2f res = new Vector2f(0, 0);

        res.x = x / BREITE;
        res.y = ((710 + 40 - y) / HOEHE);

        return res;
    }

    // Schaut, wo der boden ist. Läuft von startStufe zu höhe 0. (von oben nach unten)
    // Returns -1 wenn kein boden gefunden wurde
    public int getNextFloorOnX(float x, int startStufe) {
        x = (float)Math.floor(x);

        if(startStufe >= stufe.length) startStufe = stufe.length -1;

       for (int i = startStufe; i >= 0; i--) {
           if(stufe[i].length() <= x) continue;;

            if(SOLIDS.contains(stufe[i].charAt((int)x))) {
                return i;
            }
        }

      return -1;
   }

    // Symbol Map
    // - = leer
    // # = grass
    // P = Spieler
    // G = Gegner
    // + = dirt
    // < = half right grass
    // > = half left grass
    // w = Water
    // C = Chest

    // u = dirt half grass left
    // i = dirt half grass right

    // l = dirt half left
    // r = dirt half right

    //q = water half left
    //o = water half right

    //1 = water left grass right
    //2 = water right grass left

    //3 = water left dirt right
    //4 = water right dirt left

    // s = Snowblock
    // 7 = Snow half left
    // 8 = Snow half right

    // n = netherrack
    // m = lava
    // c = crimson
    // v = warped

    // e = endstone
}
