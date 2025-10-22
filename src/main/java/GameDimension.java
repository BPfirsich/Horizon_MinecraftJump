import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameDimension {

    final private String f_dimensionName;
    private ArrayList<Gegner> _gegnerListe;
    private ArrayList<Rectangle> _mapTilesListe;
    private Spieler _spieler;
    private Pane _root;

    public LevelData loadedLevelData;

    private Vector2f _cameraPosition;

    private ArrayList<Projektil> _projektilList;

    public GameDimension(String name, Pane root) {
        f_dimensionName = name;
        _root = root;

        _gegnerListe = new ArrayList<>();
        _projektilList = new ArrayList<>();
        _mapTilesListe = new ArrayList<>();

        _cameraPosition = new Vector2f( 0, 2);

        // Hintergrund aktualisieren
        //_root.setBackground(new Background(new BackgroundFill(new Paint)));
    }

    public void updateDimension(float deltaTime, InputData inputData) {
        // Spieler Updaten
        _spieler.update(deltaTime, inputData);

        // Gegner Updaten
        for (Gegner g : _gegnerListe) {
            g.update(deltaTime);
        }

        // Projektile bewegung
        for (Projektil p : _projektilList) {
            p.update(deltaTime);
        }
    }

    // Fügt einen gegner zur Dimension hinzu, und fügt dessen Rectangle zur scene hinzu
    public void addGegner(Gegner gegner) {
        _gegnerListe.add(gegner);
        _root.getChildren().add(gegner.getFigur());
    }

    // Fügt einen gegner zur Dimension hinzu, und fügt dessen Rectangle zur scene hinzu
    public void setSpieler(Spieler spieler) {
        _spieler = spieler;
        _root.getChildren().add(spieler.getFigur());   // Hitbox (unsichtbar, nur für Kollision)
        _root.getChildren().add(spieler.getSprite());  // Sprite (sichtbar!)
    }

    public void addProjektil(Projektil projektil) {
        _projektilList.add(projektil);
        _root.getChildren().add(projektil.getSprite());
    }

    public void ladeLevel(LevelData lvl) {
        if(!_mapTilesListe.isEmpty()) {
            System.err.println("Eine GameDimension kann nur ein Level laden!");
            return;
        }

        loadedLevelData = lvl;

        for(int y = 0; y < lvl.stufe.length; y++) {
            for(int x = 0; x < lvl.stufe[y].length(); x++) {
                switch(lvl.stufe[y].charAt(x)) {
                    case '#': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y);
                        _mapTilesListe.add(new Rectangle(spawnPos.x+_cameraPosition.x, spawnPos.y+_cameraPosition.y,
                                lvl.BREITE+1, lvl.HOEHE+1)); // +1 um ränderflackern zu beheben
                        _mapTilesListe.getLast().setFill(Color.GREEN);
                        //System.out.println("x: " + _mapTilesListe.getLast().getX() + ", y: " + _mapTilesListe.getLast().getY());
                        break;
                    }
                    case 'P': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y);
                        setSpieler(new Spieler(spawnPos.x+_cameraPosition.x,
                                                    spawnPos.y+_cameraPosition.y,
                                                    250,
                                                    this));

                        break;
                    }
                }
            }
        }

        _root.getChildren().addAll(_mapTilesListe);
    }
}
