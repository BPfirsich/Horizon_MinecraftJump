package benTho.horizonMinecraftJump;

import com.sun.javafx.geom.Vec2f;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Spieler {
    private Rectangle _figur;   // Hitbox (wie vorher!)
    private ImageView _sprite;  // Neues sichtbares Bild

    // Konstanten für die Spielergröße und Boden
    private static final float BREITE = 821/16;
    private static final float HOEHE = 1046/16;
    private static final float HOEHE_SNEAKEN = 821/16;
    private static final float BODEN_Y = 400;

    // Bilder für verschiedene Aktionen
    private Image _idleImage;
    private Image _schiessenImage;
    private Image _duckenImage;

    private float _xSpeed;
    private float _ySpeed = 100000000;
    private float _gravity = 4000;
    private float _gravityDownMulti = 1.4f;
    private boolean _amBoden = true;

    private float _bodenYPlayerScale = BODEN_Y;

    private GameDimension _myDimension = null;

    public Spieler(double x, double y, float speed, GameDimension myDimension) {
        _myDimension = myDimension;

        // Hitbox (unsichtbar machen, wenn du willst: setOpacity(0))
        _figur = new Rectangle(BREITE, HOEHE, Color.BLUE);
        _figur.setX(x);
        _bodenYPlayerScale = BODEN_Y - HOEHE;
        _figur.setY(_bodenYPlayerScale);
        _figur.setOpacity(0); // unsichtbar

        // Grafiken laden (aus resources-Ordner)
        _idleImage = new Image(getClass().getResourceAsStream("/spieler_idle.png"));
        _schiessenImage = new Image(getClass().getResourceAsStream("/spieler_schiessen.png"));
        _duckenImage = new Image(getClass().getResourceAsStream("/spieler_ducken.png"));

        // Sprite (startet mit Idle)
        _sprite = new ImageView(_idleImage);
        _sprite.setFitWidth(BREITE);
        _sprite.setFitHeight(HOEHE);
        _sprite.setX(x);
        _sprite.setY(y);

        _xSpeed = speed;
    }

    public void nullizeMyDimension() {
        _myDimension = null;
    }

    // Getter wie früher
    public Rectangle getFigur() {
        return _figur;
    }

    // Getter fürs Bild (Sprite)
    public ImageView getSprite() {
        return _sprite;
    }

    public void update(float deltaTime, InputData inputData) {
        double spriteHeight;
        double hitboxHeight;

        //System.out.println(_myDimension.loadedLevelData.calcMapPosFromPixelPos((float)_figur.getX(), (float)_figur.getY(), _myDimension.cameraPosition, true));

        // Movement
        if (inputData.isTasteLinks()) movePlayer(deltaTime, -1, true);
        if (inputData.isTasteRechts()) movePlayer(deltaTime, 1, true);

        // Schießen
        if (inputData.isTasteSchussOnce()) {
            // Neuen Pfeil erstellen und diesen als Projektil zu der aktuellen GameDimension hinzufügen
            _myDimension._soundPlayer.playSound("schuss", 0.3);

            float ARROW_VEL_X = 1000;
            float ARROW_VEL_Y = -250;

            if (isSneaking()) {
                ARROW_VEL_X *= 1.2f;
                ARROW_VEL_Y *= 2f;
            }

            if(_sprite.getScaleX() < 0) ARROW_VEL_X *= -1;

            Pfeil pfeil = new Pfeil();
            pfeil.init(new Vector2f((float)_figur.getX(), (float)_figur.getY() + 20),
                       new Vector2f(ARROW_VEL_X, ARROW_VEL_Y));
            _myDimension.addProjektil(pfeil);
        }

        // Update the floor Y Pos
        // Wir überprüfen die hohe auf der linknen UND rechten seite von dem spieler. Das höher Y gewinnt
        final float tinyOffset = 8; // Damit der spieler nicht in der Luft fliegen kann
        Vector2f currentTilePosLeft = _myDimension.loadedLevelData.calcMapPosFromPixelPos((float)_figur.getX() + tinyOffset, (float)_figur.getY(), _myDimension.cameraPosition, false);
        int stufeYMitBodenLeft = _myDimension.loadedLevelData.getNextFloorOnX(currentTilePosLeft.x, (int)currentTilePosLeft.y);
        Vector2f currentTilePosRechts = _myDimension.loadedLevelData.calcMapPosFromPixelPos((float)_figur.getX() + BREITE - tinyOffset, (float)_figur.getY(), _myDimension.cameraPosition, false);
        int stufeYMitBodenRechts = _myDimension.loadedLevelData.getNextFloorOnX(currentTilePosRechts.x, (int)currentTilePosRechts.y);

        int finalStufeYMitBoden = 0;
        Vector2f finalCurrentTilePos = null;
        if ((float)stufeYMitBodenRechts > stufeYMitBodenLeft) { // Rechts ist eine Wand/Höhere Stelle
            //System.out.println("rechts höher");
            // Wenn der Spieler niedriger als der höste punkte ist, dann läuft er gegen eine wand.
            // D.h. wir müssen den Spieler wieder weg bewegen aus der wand: *KOLLISION :D*
            // -2.9 weil da irgendwie ein komisches offset ist idk
            if ((currentTilePosRechts.y -2.9f) < (float)stufeYMitBodenRechts) {
                movePlayer(deltaTime, -1, false);
                finalStufeYMitBoden = stufeYMitBodenLeft;
                finalCurrentTilePos = currentTilePosLeft;
            }
            else {
                finalStufeYMitBoden = stufeYMitBodenRechts;
                finalCurrentTilePos = currentTilePosRechts;
            }
        }
        else if ((float)stufeYMitBodenLeft > stufeYMitBodenRechts) { // Links ist eine Wand/Höhere Stelle
            //System.out.println("links höher");
            // Wenn der Spieler niedriger als der höste punkte ist, dann läuft er gegen eine wand.
            // D.h. wir müssen den Spieler wieder weg bewegen aus der wand: *KOLLISION :D*
            // -2.9 weil da irgendwie ein komisches offset ist idk
            if (currentTilePosLeft.y-2.9f < (float)stufeYMitBodenLeft) {
                movePlayer(deltaTime, 1, false);
                finalStufeYMitBoden = stufeYMitBodenRechts;
                finalCurrentTilePos = currentTilePosRechts;
            }
            else {
                finalStufeYMitBoden = stufeYMitBodenLeft;
                finalCurrentTilePos = currentTilePosLeft;
            }
        }
        else if (stufeYMitBodenLeft == stufeYMitBodenRechts) {
            finalStufeYMitBoden = stufeYMitBodenLeft;
            finalCurrentTilePos = currentTilePosLeft;
        }

        updateFloorYHeight(
                (int)_myDimension.loadedLevelData.calcPixelCordsFromTile((int)finalCurrentTilePos.x, finalStufeYMitBoden, _myDimension.cameraPosition, false).y
        );

        // Springen
        if (inputData.isTasteSpringen()) springen();

        // Gravitation
        if (_ySpeed > 0) _ySpeed += _gravity * _gravityDownMulti * deltaTime;
        else _ySpeed += _gravity * deltaTime;

        // Hitbox & Sprite anpassen
        if (inputData.isTasteDucken()) {
            if (_sprite.getImage() != _duckenImage) {
                _myDimension._soundPlayer.playSound("sneak", 1.0);
            }

            setImage(_duckenImage);
            _figur.setHeight(HOEHE_SNEAKEN);
            _sprite.setFitHeight(HOEHE_SNEAKEN);
        }
        else if (inputData.isTasteSchuss()) {
            setImage(_schiessenImage);
            _figur.setHeight(HOEHE);
            _sprite.setFitHeight(HOEHE);
        }
        else {
            // Nichts wird gedrückt => Idle-Image
            setImage(_idleImage);
            _figur.setHeight(HOEHE);
            _sprite.setFitHeight(HOEHE);
        }

        // Hitbox Y mit Gravitation
        if (!_amBoden) _figur.setY(_figur.getY() + (_ySpeed * deltaTime));

        // Boden-Kollision
        if (_figur.getY() > _bodenYPlayerScale && _ySpeed >= 0) {
            if(!_amBoden && !isSneaking()) _myDimension._soundPlayer.playSound("jumpLand", 0.3);

            _figur.setY(_bodenYPlayerScale);
            _amBoden = true;
            _ySpeed = 0;
        }

        // X-Position Sprite synchronisieren
        _sprite.setX(_figur.getX());
        _sprite.setY(_figur.getY());
    }

    public boolean isSneaking() {
        return _sprite.getImage() == _duckenImage;
    }



    private void movePlayer(float deltaTime, float direMultiplier, boolean changePlayerDire) {
        float speedMulti = 1.0f;
        if (isSneaking()) speedMulti = 0.3f;
        if (!_amBoden) speedMulti = 1.2f;

        _figur.setX(_figur.getX() + (_xSpeed * speedMulti * deltaTime) * direMultiplier);

        if (changePlayerDire) {
            if (direMultiplier < 0) _sprite.setScaleX(-1);
            else _sprite.setScaleX(1);
        }
    }

    private void springen() {
        if (_amBoden) {
            _ySpeed = -1000; // Sprungkraft
            _amBoden = false;

            _myDimension._soundPlayer.playSound("jump", 1.0);
        }
    }

    private void setImage(Image img) {
        if (_sprite.getImage() != img) {
            _sprite.setImage(img);
        }
    }

    private int calcFloorHeight(int newPixelY) {
        return newPixelY - (int)HOEHE;
    }

    private void updateFloorYHeight(int newPixelY) {
        if(calcFloorHeight(newPixelY) != _bodenYPlayerScale) {
            if(_amBoden) _ySpeed = 0;
            _amBoden = false;
        }

        _bodenYPlayerScale = calcFloorHeight(newPixelY);

        // If sneaking add the offset
        if (isSneaking()) _bodenYPlayerScale += HOEHE - HOEHE_SNEAKEN;

    }
}
