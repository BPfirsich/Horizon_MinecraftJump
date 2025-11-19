import com.sun.javafx.geom.Vec2f;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Spieler {
    private Rectangle _figur;   // Hitbox (wie vorher!)
    private ImageView _sprite;  // Neues sichtbares Bild

    // Konstanten für die Spielergröße und Boden
    private static final float BREITE = 1000/16;
    private static final float HOEHE = 1100/16;
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
        float speedMulti = 1.0f;
        if (!_amBoden) speedMulti = 1.2f;

        if (inputData.isTasteLinks()) {
            _figur.setX(_figur.getX() - (_xSpeed * speedMulti * deltaTime));
            _sprite.setScaleX(-1);

            // Überprüfen ob wir in ein tile reingelaufen sind
            Vector2f walkedPos = _myDimension.loadedLevelData.calcMapPosFromPixelPos((float)_figur.getX(), (float)_figur.getY(), _myDimension.cameraPosition, true);
            if(_myDimension.loadedLevelData.isBereichSolide(walkedPos.x, (int)walkedPos.y)) {
                // Zurücklaufen da wir nun im tile drinnen sind lol
                _figur.setX(_figur.getX() + (_xSpeed * speedMulti * deltaTime));
            }

            // Spieler nicht nach links rauslaufen lassen
            if(_figur.getX() <= 0) _figur.setX(0);

            // Check if the next pos has the same height as the current. If it is not the case, move back
            int stufeYMitBoden = _myDimension.loadedLevelData.getNextFloorOnX(walkedPos.x, 2);
            int movedHeight =
                    (int)_myDimension.loadedLevelData.calcPixelCordsFromTile((int)walkedPos.x, stufeYMitBoden, _myDimension.cameraPosition, true).y;
            if(calcFloorHeight(movedHeight) < _bodenYPlayerScale && _amBoden) {
                // Zurücklaufen, da andere höhe
                _figur.setX(_figur.getX() + (_xSpeed * speedMulti * deltaTime));
            }
        }
        if (inputData.isTasteRechts()) {
            _figur.setX(_figur.getX() + (_xSpeed * speedMulti * deltaTime));
            _sprite.setScaleX(1);

            // Überprüfen ob wir in ein tile reingelaufen sind
            Vector2f walkedPos = _myDimension.loadedLevelData.calcMapPosFromPixelPos((float)_figur.getX(), (float)_figur.getY(), _myDimension.cameraPosition, true);
            if(_myDimension.loadedLevelData.isBereichSolide(walkedPos.x, (int)walkedPos.y)) {
                // Zurücklaufen da wir nun im tile drinnen sind lol
                _figur.setX(_figur.getX() - (_xSpeed * speedMulti * deltaTime));
            }

            // Check if the next pos has the same height as the current. If it is not the case, move back
            int stufeYMitBoden = _myDimension.loadedLevelData.getNextFloorOnX(walkedPos.x, 2);
            int movedHeight =
                    (int)_myDimension.loadedLevelData.calcPixelCordsFromTile((int)walkedPos.x, stufeYMitBoden, _myDimension.cameraPosition, true).y;
            if(calcFloorHeight(movedHeight) < _bodenYPlayerScale && _amBoden) {
                // Zurücklaufen, da andere höhe
                _figur.setX(_figur.getX() - (_xSpeed * speedMulti * deltaTime));
            }
        }

        // Schießen
        if (inputData.isTasteSchussOnce()) {
            // Neuen Pfeil erstellen und diesen als Projektil zu der aktuellen GameDimension hinzufügen
            _myDimension._soundPlayer.schussSoundPlayer.stop();
            _myDimension._soundPlayer.schussSoundPlayer.play();

            float ARROW_VEL_X = 1000;
            float ARROW_VEL_Y = -250;

            if(_sprite.getScaleX() < 0) ARROW_VEL_X *= -1;

            Pfeil pfeil = new Pfeil();
            pfeil.init(new Vector2f((float)_figur.getX(), (float)_figur.getY() + 20),
                       new Vector2f(ARROW_VEL_X, ARROW_VEL_Y));
            _myDimension.addProjektil(pfeil);
        }

        // Update the floor Y Pos
        Vector2f currentTilePos = _myDimension.loadedLevelData.calcMapPosFromPixelPos((float)_figur.getX(), (float)_figur.getY(), _myDimension.cameraPosition, true);
        int stufeYMitBoden = _myDimension.loadedLevelData.getNextFloorOnX(currentTilePos.x, (int)currentTilePos.y);
        updateFloorYHeight(
                (int)_myDimension.loadedLevelData.calcPixelCordsFromTile((int)currentTilePos.x, stufeYMitBoden, _myDimension.cameraPosition, true).y
        );

        // Springen
        if (inputData.isTasteSpringen()) springen();

        // Gravitation
        if (_ySpeed > 0) _ySpeed += _gravity * _gravityDownMulti * deltaTime;
        else _ySpeed += _gravity * deltaTime;

        // Hitbox & Sprite anpassen
        if (inputData.isTasteDucken()) {
            setImage(_duckenImage);
        }
        else if (inputData.isTasteSchuss()) {
            setImage(_schiessenImage);
        }
        else {
            // Nichts wird gedrückt => Idle-Image
            setImage(_idleImage);
        }

        // Hitbox Y mit Gravitation
        if (!_amBoden) _figur.setY(_figur.getY() + (_ySpeed * deltaTime));

        // Boden-Kollision
        if (_figur.getY() > _bodenYPlayerScale && _ySpeed >= 0) {
            _figur.setY(_bodenYPlayerScale);
            _amBoden = true;
            _ySpeed = 0;
        }

        // X-Position Sprite synchronisieren
        _sprite.setX(_figur.getX());
        _sprite.setY(_figur.getY());
    }






    private void springen() {
        if (_amBoden) {
            _ySpeed = -1000; // Sprungkraft
            _amBoden = false;
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
    }
}
