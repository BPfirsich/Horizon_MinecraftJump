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



    public boolean isBereichSolide(int x, int y) {
        return stufe[y].charAt(x) == '#';
    }

    public Vector2f calcPixelCordsFromTile(int x, int y) {
        Vector2f res = new Vector2f(0, 0);

        res.x = x*BREITE;
        res.y = 400 - (y*HOEHE) - 40; // -40 da die fensterleiste auch dazugerechnet wird...

        return res;
    }

    public Vector2f calcMapPosFromPixelPos(float x, float y) {
        Vector2f res = new Vector2f(0, 0);

        res.x = x / BREITE;
        res.y = (y + 400 + 40) / HOEHE;

        return res;
    }

    // Schaut, wo der boden ist. Läuft von startStufe zu höhe 0. (von oben nach unten)
    // Returns -1 wenn kein boden gefunden wurde
    public int getNextFloorOnX(int x, int startStufe) {
        if(startStufe >= stufe.length) startStufe = stufe.length -1;

       for (int i = startStufe; i >= 0; i--) {
           if(stufe[i].length() <= x) continue;;

            if(SOLIDS.contains(stufe[i].charAt((x)))) {
                return i;
            }
        }

      return -1;
   }

    // Symbol Map
    // - = leer
    // # = boden
    // P = Spieler
    // G = Gegner

}
