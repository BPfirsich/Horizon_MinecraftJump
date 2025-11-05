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

    private MatchLeben _matchLeben;

    private Image _grassBlockImg;
    private Image _dirtBlockImg;
    private Image _halfLeftGrassImg;
    private Image _halfRightGrassImg;
    private Image _waterImg;
    private Image _ChestImg;

    private Image _dirtHalfGrassLeftImg;
    private Image _dirtHalfGrassRightImg;

    private Image _dirtHalfLeftImg;
    private Image _dirtHalfRightImg;

    private Image _waterhalfleftImg;
    private Image _waterhalfrightImg;

    private Image _watergrassleftrightImg;
    private Image _watergrassrightleftImg;

    private Image _waterdirtleftrightImg;
    private Image _waterdirtrightleftImg;

    private Image _snowblockImg;
    private Image _snowhalfleftImg;
    private Image _snowhalfrightImg;

    public LevelData loadedLevelData;

    public Vector2f cameraPosition;

    private ArrayList<Projektil> _projektilList;

    public GameDimension(String name, Pane root, MatchLeben leben) {
        f_dimensionName = name;
        _root = root;

        _matchLeben = leben;

        _gegnerListe = new ArrayList<>();
        _projektilList = new ArrayList<>();
        _mapTilesListe = new ArrayList<>();

        cameraPosition = new Vector2f( 0, 0);

        _grassBlockImg = new Image(getClass().getResourceAsStream("/grassblock.png"));
        _dirtBlockImg = new Image(getClass().getResourceAsStream("/erdblock.png"));
        _halfLeftGrassImg = new Image(getClass().getResourceAsStream("/grassblockHalfLeft.png"));
        _halfRightGrassImg = new Image(getClass().getResourceAsStream("/grassblockHalfRight.png"));
        _waterImg = new Image(getClass().getResourceAsStream("/Wasser.png"));
        _ChestImg = new Image(getClass().getResourceAsStream("/Chest.png"));

        _dirtHalfGrassLeftImg = new Image(getClass().getResourceAsStream("/erdblockHalfGrassLeft.png"));
        _dirtHalfGrassRightImg = new Image(getClass().getResourceAsStream("/erdblockHalfGrassRight.png"));

        _dirtHalfLeftImg = new Image(getClass().getResourceAsStream("/erdblockHalfLeft.png"));
        _dirtHalfRightImg = new Image(getClass().getResourceAsStream("/erdblockHalfRight.png"));

        _waterhalfleftImg = new Image(getClass().getResourceAsStream("/Wasser_halfleft.png"));
        _waterhalfrightImg = new Image(getClass().getResourceAsStream("/Wasser_halfright.png"));

        _watergrassleftrightImg = new Image(getClass().getResourceAsStream("/Wassergrass_leftright.png"));
        _watergrassrightleftImg = new Image(getClass().getResourceAsStream("/Wassergrass_rightleft.png"));

        _waterdirtleftrightImg = new Image(getClass().getResourceAsStream("/Wassererdblock_leftright.png"));
        _waterdirtrightleftImg = new Image(getClass().getResourceAsStream("/Wassererdblock_rightleft.png"));

        _snowblockImg = new Image(getClass().getResourceAsStream("/schneeblock.png"));
        _snowhalfleftImg = new Image(getClass().getResourceAsStream("/schneeblock_halfleft.png"));
        _snowhalfrightImg = new Image(getClass().getResourceAsStream("/schneeblock_halfright.png"));

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
        if (!_mapTilesListe.isEmpty()) {
            System.err.println("Eine GameDimension kann nur ein Level laden!");
            return;
        }

        loadedLevelData = lvl;

        cameraPosition.x = 0;
        //cameraPosition.y = 0;

        // Alle Tiles von dem Level erzeugen
        for (int y = 0; y < lvl.stufe.length; y++) {
            for (int x = 0; x < lvl.stufe[y].length(); x++) {
                switch (lvl.stufe[y].charAt(x)) {
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
                        setSpieler(new Spieler(spawnPos.x,
                                spawnPos.y,
                                250,
                                this));

                        break;
                    }
                    case 'w': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _waterImg, lvl);
                        break;
                    }
                    case 'C': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _ChestImg, lvl);
                        break;
                    }
                    case 'q': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _waterhalfleftImg, lvl);
                        break;
                    }
                    case 'o': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _waterhalfrightImg, lvl);
                        break;

                    }
                    case '1': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _watergrassleftrightImg, lvl);
                        break;

                    }
                    case '2': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _watergrassrightleftImg, lvl);
                        break;

                    }
                    case '3': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _waterdirtleftrightImg, lvl);
                        break;

                    }
                    case '4': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _waterdirtrightleftImg, lvl);
                        break;

                    }
                    case 's': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _snowblockImg, lvl);
                        break;

                    }
                    case '7': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _snowhalfleftImg, lvl);
                        break;

                    }
                    case '8': {
                        Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                        addTileToMapList(spawnPos, _snowhalfrightImg, lvl);
                        break;

                    }
                }
            }
        }

                    _root.getChildren().addAll(_mapTilesListe);
                    _root.setBackground(new Background(loadedLevelData.levelBackground));

                    _matchLeben.erstelleHerzen(_root);
                    _matchLeben.updateHerzen();

                    // Die Kamera zum letzten Tile bewegen (Kamerafahrt)
                    //moveCameraByValue(-lvl.stufe[0].length() * lvl.BREITE + 3000, 0);
                }

                private void addTileToMapList (Vector2f spawnPos, Image img, LevelData lvl){
                    _mapTilesListe.add(new ImageView(img));
                    _mapTilesListe.getLast().setX(spawnPos.x);
                    _mapTilesListe.getLast().setY(spawnPos.y);
                    _mapTilesListe.getLast().setFitWidth(lvl.BREITE + 1);
                    _mapTilesListe.getLast().setFitHeight(lvl.HOEHE + 1);
                }

                // Die Funtkion schaut, wie weit der Spieler von x-mitte des Fensters entfertn ist.
                // Wenn der Spieler die deadzone vom abstant überschreitet, dann wird alles in der welt leicht in die richtung geschoben,
                // um den fehler auszugleichen.
                // Ebenso wird die gesamt bewegung in pixeln in der _cameraPos gespeichet, um später noch nachvollziehen zu können,
                // wie momentant die gesamte welt relativ positioniert ist.
                private void moveCamera ( float deltaTime){
                    final float screenWidth = 1280.f;
                    final float smoothFactor = 0.7f;
                    final float deadzonePixel = 50;
                    final float followOffsetPixel = -250;

                    float diff = screenWidth / 2 - (screenWidth - ((float) _spieler.getFigur().getX()) + followOffsetPixel);

                    if (diff < 0 && cameraPosition.x <= 0) return;
                    else if (diff <= deadzonePixel && diff >= -deadzonePixel) return;

                    // Wenn zu weit links, dann negativ, wenn zu weit rechts, dann positiv

                    // Alles zur korrektur bewegen
                    float smoothErrorCorrectionValue = diff * smoothFactor * deltaTime * -1;

                    System.out.println("" + ((float)loadedLevelData.stufe[0].length() * (float)loadedLevelData.BREITE - (float)screenWidth*2) + "  " + cameraPosition.x);

                    if ((float)loadedLevelData.stufe[0].length() * (float)loadedLevelData.BREITE - (float)screenWidth <
                            cameraPosition.x - smoothErrorCorrectionValue && smoothErrorCorrectionValue < 0) {
                        return;
                    };

                    moveCameraByValue(smoothErrorCorrectionValue, 0);
                }

                // y ist noch nicht implemented
                private void moveCameraByValue(float x, float y) {
                    cameraPosition.x -= x;

                    // Move All Objects
                    _spieler.getFigur().setX(_spieler.getFigur().getX() + x);
                    for (Gegner g : _gegnerListe) {
                        g.getFigur().setX(g.getFigur().getX() + x);
                    }
                    for (ImageView i : _mapTilesListe) {
                        i.setX(i.getX() + x);
                    }
                }
            }

