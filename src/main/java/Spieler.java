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

        // Movement
        float speedMulti = 1.0f;
        if (!_amBoden) speedMulti = 1.2f;

        if (inputData.isTasteLinks()) {
            _figur.setX(_figur.getX() - (_xSpeed * speedMulti * deltaTime));
            _sprite.setScaleX(-1);
        }
        if (inputData.isTasteRechts()) {
            _figur.setX(_figur.getX() + (_xSpeed * speedMulti * deltaTime));
            _sprite.setScaleX(1);
        }

        // Schießen
        if (inputData.isTasteSchuss()) {
            // Neuen Pfeil erstellen und diesen als Projektil zu der aktuellen GameDimension hinzufügen
            float ARROW_VEL_X = 1000;
            float ARROW_VEL_Y = -250;

            Pfeil pfeil = new Pfeil();
            pfeil.init(new Vector2f((float)_figur.getX(), (float)_figur.getY() + 20),
                       new Vector2f(ARROW_VEL_X, ARROW_VEL_Y));
            _myDimension.addProjektil(pfeil);
        }

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
        if (_figur.getY() > _bodenYPlayerScale ) {
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
}
