import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Spieler {
    private Rectangle _figur;

    private float _xSpeed;
    private float _ySpeed = 100000000;
    private float _gravity = 4000;
    private float _gravityDownMulti = 1.4f;
    private boolean _amBoden = true;

    public Spieler(double x, double y, float speed) {
        _figur = new Rectangle(30, 40, Color.BLUE);
        _figur.setTranslateX(x);
        _figur.setTranslateY(y);
        _xSpeed = speed;
    }

    public Rectangle getFigur() {
        return _figur;
    }

    public void update(float deltaTime, InputData inputData) {
        // Movement
        float speedMulti = 1.0f;
        if (!_amBoden) { speedMulti = 1.2f; }
        if(inputData.isTasteLinks()) {
            _figur.setTranslateX(_figur.getTranslateX() - (_xSpeed * speedMulti * deltaTime));
        }
        if(inputData.isTasteRechts()) {
            _figur.setTranslateX(_figur.getTranslateX() + (_xSpeed * speedMulti * deltaTime));
        }

        // Springen
        if(inputData.isTasteSpringen()) springen();

        // Gravitation
        if(_ySpeed > 0) {
            _ySpeed += _gravity * _gravityDownMulti * deltaTime; // Schwerkraft
        } else {
            _ySpeed += _gravity * deltaTime; // Schwerkraft
        }

        _figur.setTranslateY(_figur.getTranslateY() + (_ySpeed * deltaTime));
        if (_figur.getTranslateY() >= 360) { // Boden bei y=360
            _figur.setTranslateY(360);
            _amBoden = true;
            _ySpeed = 0;
        }
    }

    private void springen() {
        if (_amBoden) {
            _ySpeed = -1000;      // Sprungkraft
            _amBoden = false;
        }
    }
}