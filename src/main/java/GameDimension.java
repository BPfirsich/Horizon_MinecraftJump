import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class GameDimension {

    final private String f_dimensionName;
    private ArrayList<Gegner> _gegnerListe;
    private ArrayList<ImageView> _mapTilesListe;
    private Spieler _spieler;
    private Pane _root;

    private Image _grassBlockImg;
    private Image _dirtBlockImg;
    private Image _halfLeftGrassImg;
    private Image _halfRightGrassImg;

    private Image _dirtHalfGrassLeftImg;
    private Image _dirtHalfGrassRightImg;

    private Image _dirtHalfLeftImg;
    private Image _dirtHalfRightImg;

    public LevelData loadedLevelData;

    public Vector2f cameraPosition;

    private ArrayList<Projektil> _projektilList;

    public GameDimension(String name, Pane root) {
        f_dimensionName = name;
        _root = root;

        _gegnerListe = new ArrayList<>();
        _projektilList = new ArrayList<>();
        _mapTilesListe = new ArrayList<>();

        cameraPosition = new Vector2f( 0, 0);

        _grassBlockImg = new Image(getClass().getResourceAsStream("/grassblock.png"));
        _dirtBlockImg = new Image(getClass().getResourceAsStream("/erdblock.png"));
        _halfLeftGrassImg = new Image(getClass().getResourceAsStream("/grassblockHalfLeft.png"));
        _halfRightGrassImg = new Image(getClass().getResourceAsStream("/grassblockHalfRight.png"));

        _dirtHalfGrassLeftImg = new Image(getClass().getResourceAsStream("/erdblockHalfGrassLeft.png"));
        _dirtHalfGrassRightImg = new Image(getClass().getResourceAsStream("/erdblockHalfGrassRight.png"));

        _dirtHalfLeftImg = new Image(getClass().getResourceAsStream("/erdblockHalfLeft.png"));
        _dirtHalfRightImg = new Image(getClass().getResourceAsStream("/erdblockHalfRight.png"));

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

        // Camera bewegen
        moveCamera(deltaTime);
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
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _grassBlockImg, lvl);
                        break;
                    }
                    case '+': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _dirtBlockImg, lvl);
                        break;
                    }
                    case '>': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _halfLeftGrassImg, lvl);
                        break;
                    }
                    case '<': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _halfRightGrassImg, lvl);
                        break;
                    }
                    case 'u': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _dirtHalfGrassLeftImg, lvl);
                        break;
                    }
                    case 'i': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _dirtHalfGrassRightImg, lvl);
                        break;
                    }
                    case 'l': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _dirtHalfLeftImg, lvl);
                        break;
                    }
                    case 'r': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _dirtHalfRightImg, lvl);
                        break;
                    }
                    case 'P': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        setSpieler(new Spieler(spawnPos.x+ cameraPosition.x,
                                                    spawnPos.y+ cameraPosition.y,
                                                    250,
                                                    this));

                        break;
                    }
                }
            }
        }

        _root.getChildren().addAll(_mapTilesListe);

        _root.setBackground(new Background(loadedLevelData.levelBackground));
    }

    private void addTileToMapList(Vector2f spawnPos, Image img, LevelData lvl) {
        _mapTilesListe.add(new ImageView(img));
        _mapTilesListe.getLast().setX(spawnPos.x+ cameraPosition.x);
        _mapTilesListe.getLast().setY(spawnPos.y+ cameraPosition.y);
        _mapTilesListe.getLast().setFitWidth(lvl.BREITE+1);
        _mapTilesListe.getLast().setFitHeight(lvl.HOEHE+1);
    }

    // Die Funtkion schaut, wie weit der Spieler von x-mitte des Fensters entfertn ist.
    // Wenn der Spieler die deadzone vom abstant überschreitet, dann wird alles in der welt leicht in die richtung geschoben,
    // um den fehler auszugleichen.
    // Ebenso wird die gesamt bewegung in pixeln in der _cameraPos gespeichet, um später noch nachvollziehen zu können,
    // wie momentant die gesamte welt relativ positioniert ist.
    private void moveCamera(float deltaTime) {
        final float screenWidth = 1280.f;
        final float smoothFactor = 0.7f;
        final float deadzonePixel = 50;
        final float followOffsetPixel = -250;

        float diff =screenWidth/2 - (screenWidth - ((float)_spieler.getFigur().getX()) + followOffsetPixel);

        if (diff < 0 && cameraPosition.x <= 0) return;
        else if(diff <= deadzonePixel && diff >= -deadzonePixel) return;

        // Wenn zu weit links, dann negativ, wenn zu weit rechts, dann positiv

        // Alles zur korrektur bewegen
        float smoothErrorCorrectionValue = diff * smoothFactor * deltaTime * -1;
        cameraPosition.x -= smoothErrorCorrectionValue;

        // Move All Objects
        _spieler.getFigur().setX(_spieler.getFigur().getX() + smoothErrorCorrectionValue);
        for (Gegner g : _gegnerListe) {
            g.getFigur().setX(g.getFigur().getX() + smoothErrorCorrectionValue);
        }
        for (ImageView i : _mapTilesListe) {
            i.setX(i.getX() + smoothErrorCorrectionValue);
        }
    }
}
