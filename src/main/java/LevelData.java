import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;


public class LevelData {
    public final int BREITE = 50, HOEHE = 50;
    public final ArrayList<Character> SOLIDS = new ArrayList<Character>(
            Arrays.asList('#')
    );

    public LevelData(String name, int anzahlFloors, BackgroundImage background) {
        stufe = new String[anzahlFloors];
        levelName = name;
        levelBackground = background;
    }

    // array[y]: y=0 ist der Boden, x=0 ist links der anfang vom level. (vom string)
    public String[] stufe;
    public String levelName;
    //public Color backgroundColor = Color.WHITE;

    public final BackgroundImage levelBackground;



    public boolean isBereichSolide(float x, int y) {
        x = (float)Math.floor(x);
        return stufe[y].charAt((int)x) == '#';
    }

    // The centeringOffset is meant to for the player. Its just moves the x pos by 0.5 tiles, so the center of
    // the calculation result is actually in the middle of a tile, instead on the edge

    public Vector2f calcPixelCordsFromTile(int x, int y, Vector2f cameraOffset, boolean centeringOffset) {
        x += (int)cameraOffset.x;
        y += (int)cameraOffset.y;

        Vector2f res = new Vector2f(0, 0);

        res.x = x*BREITE;
        res.y = 710 - (y*HOEHE) - 40; // -40 da die fensterleiste auch dazugerechnet wird...

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

}
