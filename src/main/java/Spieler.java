import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Spieler {
    private Rectangle _figur;   // Hitbox (wie vorher!)
    private ImageView _sprite;  // Neues sichtbares Bild

    // Bilder für verschiedene Aktionen
    private Image _idleImage;
    private Image _schiessenImage;
    private Image _duckenImage;

    private float _xSpeed;
    private float _ySpeed = 100000000;
    private float _gravity = 4000;
    private float _gravityDownMulti = 1.4f;
    private boolean _amBoden = true;

    public Spieler(double x, double y, float speed) {
        // Hitbox (unsichtbar machen, wenn du willst: setOpacity(0))
        _figur = new Rectangle(30, 40, Color.BLUE);
        _figur.setTranslateX(x);
        _figur.setTranslateY(y);
        _figur.setOpacity(0); // unsichtbar

        // Grafiken laden (aus resources-Ordner)
        _idleImage = new Image(getClass().getResourceAsStream("/spieler_idle.png"));
        _schiessenImage = new Image(getClass().getResourceAsStream("/spieler_schiessen.png"));
        _duckenImage = new Image(getClass().getResourceAsStream("/spieler_ducken.png"));

        // Sprite (startet mit Idle)
        _sprite = new ImageView(_idleImage);
        _sprite.setFitWidth(30);
        _sprite.setFitHeight(40);
        _sprite.setTranslateX(x);
        _sprite.setTranslateY(y);

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
        // Standard = Idle
        setImage(_idleImage);

        // Movement
        float speedMulti = 1.0f;
        if (!_amBoden) { speedMulti = 1.2f; }

        if (inputData.isTasteLinks()) {
            _figur.setTranslateX(_figur.getTranslateX() - (_xSpeed * speedMulti * deltaTime));
            _sprite.setScaleX(-1); // spiegeln nach links
        }
        if (inputData.isTasteRechts()) {
            _figur.setTranslateX(_figur.getTranslateX() + (_xSpeed * speedMulti * deltaTime));
            _sprite.setScaleX(1); // normale Richtung
        }

        // Springen
        if (inputData.isTasteSpringen()) springen();

        // Ducken
        if (inputData.isTasteDucken()) {
            setImage(_duckenImage);
        }

        // Schießen
        if (inputData.isTasteSchiessen()) {
            setImage(_schiessenImage);
        }

        // Gravitation
        if (_ySpeed > 0) {
            _ySpeed += _gravity * _gravityDownMulti * deltaTime;
        } else {
            _ySpeed += _gravity * deltaTime;
        }

        _figur.setTranslateY(_figur.getTranslateY() + (_ySpeed * deltaTime));

        // Boden-Kollision
        if (_figur.getTranslateY() >= 360) {
            _figur.setTranslateY(360);
            _amBoden = true;
            _ySpeed = 0;
        }

        // Sprite folgt immer der Hitbox
        _sprite.setTranslateX(_figur.getTranslateX());
        _sprite.setTranslateY(_figur.getTranslateY());
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
