import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Gegner {
    private Rectangle _figur;
    private double _speed;

    public Gegner(double x, double y, double speed) {
        _figur = new Rectangle(30, 30, Color.RED);
        _figur.setTranslateX(x);
        _figur.setTranslateY(y);
        this._speed = speed;
    }

    public Rectangle getFigur() {
        return _figur;
    }

    public void update(float deltaTime) {
        _figur.setTranslateX(_figur.getTranslateX() - (_speed * deltaTime));
        if (_figur.getTranslateX() < -40) {
            _figur.setTranslateX(600 + Math.random() * 200); // respawn
        }
    }
}
