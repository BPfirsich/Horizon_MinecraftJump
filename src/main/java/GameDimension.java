import java.util.ArrayList;
import java.util.function.Function;

import javafx.animation.AnimationTimer;
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

    private Boss _boss;

    private MatchLeben _matchLeben;
    public SoundPlayer _soundPlayer;

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

    private Image _sandImg;
    private Image _sandhalfleftImg;
    private Image _sandhalfrightImg;

    private Image _netherrackImg;
    private Image _lavaImg;
    private Image _crimsonImg;
    private Image _warpedImg;

    private Image _endstoneImg;

    private Image _creeperIdleImg;
    private Image _creeperShootImg;

    private Image _kaktusIdleImg;

    private Image _dragonIdleImg;
    private Image _dragonShootImg;

    public LevelData loadedLevelData;

    public Vector2f cameraPosition;

    private ArrayList<Projektil> _projektilList;
    private Vector2f _chestPos = null;

    private Function<String, Void> _levelLoadFunc;

    public GameDimension(String name, Pane root, MatchLeben leben, SoundPlayer soundPlayer, Function<String, Void> lvlLoadFunc) {
        f_dimensionName = name;
        _root = root;

        _matchLeben = leben;
        _soundPlayer = soundPlayer;

        _levelLoadFunc = lvlLoadFunc;

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

        _sandImg = new Image(getClass().getResourceAsStream("/sandblock.png"));
        _sandhalfleftImg = new Image(getClass().getResourceAsStream("/sandblock_halfleft.png"));
        _sandhalfrightImg = new Image(getClass().getResourceAsStream("/sandblock_halfright.png"));

        _netherrackImg = new Image(getClass().getResourceAsStream("/netherrack.png"));
        _lavaImg = new Image(getClass().getResourceAsStream("/Lava.png"));
        _crimsonImg = new Image(getClass().getResourceAsStream("/crimson.png"));
        _warpedImg = new Image(getClass().getResourceAsStream("/Warped.png"));

        _endstoneImg = new Image(getClass().getResourceAsStream("/endstone.png"));

        _creeperIdleImg = new Image(getClass().getResourceAsStream("/creeper.png"));
        _creeperShootImg = new Image(getClass().getResourceAsStream("/creeper_wutend.png"));

        _kaktusIdleImg = new Image(getClass().getResourceAsStream("/kaktus.png"));

        _dragonIdleImg = new Image(getClass().getResourceAsStream("/enderdrache_normal.png"));
        _dragonShootImg = new Image(getClass().getResourceAsStream("/enderdrache_wutend.png"));

        // Hintergrund aktualisieren
        //_root.setBackground(new Background(new BackgroundFill(new Paint)));

    }

    public void updateDimension(float deltaTime, InputData inputData) {
        if(_spieler == null) return;

        // Spieler Updaten
        _spieler.update(deltaTime, inputData);
        _boss.update(deltaTime, new Vector2f((float)_spieler.getFigur().getX(), (float)_spieler.getFigur().getY()));

        // Gegner Updaten
        for (Gegner g : _gegnerListe) {
            g.update(deltaTime);
        }

        // Projektile bewegung
        for (Projektil p : _projektilList) {
            p.update(deltaTime);
        }

        //System.out.println(_spieler.getFigur().getY());
        // y=0 death checken
        if (_spieler.getFigur().getY() >= 650) {
            sterben();
        }

        // Gucken ob bei chest
        if(_chestPos != null) {
            float distanceToChest = loadedLevelData.calcPixelCordsFromTile((int)_chestPos.x, (int)_chestPos.y, cameraPosition, false)
                    .sub(new Vector2f((int)_spieler.getFigur().getX(), (int)_spieler.getFigur().getY())).length();

            System.out.println(distanceToChest);
            /*if(distanceToChest <= 25) {
                _levelLoadFunc.apply(loadedLevelData.nextLevelKey);
                return;
            }*/
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

    public void ladeLevel(LevelData lvl, boolean showLoadingScreen) {
        if (!_mapTilesListe.isEmpty()) {
            System.err.println("Eine GameDimension kann nur ein Level laden!");
            return;
        }

        ImageView loadingView = new ImageView(lvl.loadingScreen);
        loadingView.setFitWidth(1280);
        loadingView.setFitHeight(720);
        //loadingView.setTranslateZ(1000);

        loadedLevelData = lvl;
        GameDimension self = this;

        AnimationTimer loadingTimer = new AnimationTimer() {
            long startTime = -1;
            final static float LOADING_TIME = 2.0f;

            @Override
            public void handle(long l) {
                if(startTime == -1) {
                    if (!showLoadingScreen) {
                        showLoadingScreen();
                        loadLevelAndDestroyLoadingScreen();
                        stop();
                        return;
                    }

                    showLoadingScreen();
                    startTime = l;
                }
                else if ((float)(l - startTime)/1_000_000_000 >= LOADING_TIME) {
                    loadLevelAndDestroyLoadingScreen();
                    stop();
                }
            }

            void showLoadingScreen() {
                _root.getChildren().add(loadingView);
            }

            void loadLevelAndDestroyLoadingScreen() {
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
                                        self));

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
                                _chestPos = new Vector2f(x, y);
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
                            case 'd': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos, _sandImg, lvl);
                                break;

                            }
                            case '5': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos, _sandhalfleftImg, lvl);
                                break;

                            }
                            case '6': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos, _sandhalfrightImg, lvl);
                                break;

                            }
                            case 'n': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos, _netherrackImg, lvl);
                                break;

                            }
                            case 'm': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos, _lavaImg, lvl);
                                break;

                            }
                            case 'c': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos, _crimsonImg, lvl);
                                break;

                            }
                            case 'v': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos,_warpedImg , lvl);
                                break;

                            }
                            case 'e': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                addTileToMapList(spawnPos,_endstoneImg , lvl);
                                break;
                            }

                            case 'G': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                _boss = new Boss(spawnPos, _creeperIdleImg, _creeperShootImg, Pfeil.class,
                                        1, 500, self, new Vector2f(571 / 5.7f, 1103 / 5.7f));
                                break;
                            }

                            case 'K': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                _boss = new Boss(spawnPos, _kaktusIdleImg, _kaktusIdleImg, Pfeil.class,
                                        1, 500, self, new Vector2f(839 / 5.3f, 1069 / 5.3f));
                                break;
                            }

                            case 'E': {
                                Vector2f spawnPos = lvl.calcPixelCordsFromTile(x, y, cameraPosition, false);
                                _boss = new Boss(spawnPos, _dragonIdleImg, _dragonShootImg, Pfeil.class,
                                        1, 500, self, new Vector2f(571 / 5.7f, 1103 / 5.7f));
                                break;
                            }
                        }
                    }
                }

                _root.getChildren().addAll(_mapTilesListe);
                _root.setBackground(new Background(loadedLevelData.levelBackground));

                if (_boss != null) _root.getChildren().add(_boss.imageView);

                _matchLeben.erstelleHerzen(_root);
                _matchLeben.updateHerzen();

                _soundPlayer.setMusic(lvl.myMusicKey);

                _root.getChildren().remove(loadingView);

                // Die Kamera zum letzten Tile bewegen (Kamerafahrt)
                //moveCameraByValue(-lvl.stufe[0].length() * lvl.BREITE + 3000, 0);
            }
        };

        loadingTimer.start();


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

        //System.out.println("" + ((float)loadedLevelData.stufe[0].length() * (float)loadedLevelData.BREITE - (float)screenWidth*2) + "  " + cameraPosition.x);

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
        _boss.imageView.setX(_boss.imageView.getX() + x);
        for (Gegner g : _gegnerListe) {
            g.getFigur().setX(g.getFigur().getX() + x);
        }
        for (ImageView i : _mapTilesListe) {
            i.setX(i.getX() + x);
        }
    }

    private void sterben() {
        _soundPlayer.playSound("death", 1);

        _matchLeben.herzen--;
        _matchLeben.clearHerzen(_root);

        _root.getChildren().removeAll(_mapTilesListe);
        for(Projektil p : _projektilList) {
            _root.getChildren().remove(p.getSprite());
        }
        for(Gegner g : _gegnerListe) {
            _root.getChildren().remove(g.getFigur());
        }
        _root.getChildren().remove(_spieler.getFigur());
        _root.getChildren().remove(_spieler.getSprite());

        _mapTilesListe.clear();
        _projektilList.clear();
        _gegnerListe.clear();
        _spieler = null;

        ladeLevel(loadedLevelData, false);
    }
}

