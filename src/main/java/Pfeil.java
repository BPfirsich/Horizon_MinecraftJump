import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pfeil implements Projektil {

    private final static float GRAVITY = 20f;

    private static Image _arrowIdleImage = null;

    private ImageView _sprite = null;
    private Vector2f _velocity;

    @Override
    public void init(Vector2f startPos, Vector2f startDire) {
        // Die bilder laden, falls dies noch nicht geschehen ist
        if(_arrowIdleImage == null) {
            _arrowIdleImage = new Image(getClass().getResourceAsStream("/pfeil.png"));
        }

        _sprite = new ImageView();
        _sprite.setScaleX(0.35);
        _sprite.setScaleY(0.35);
        _sprite.setImage(_arrowIdleImage);


        _sprite.setX(startPos.x);
        _sprite.setY(startPos.y);

        _velocity = new Vector2f(startDire);

        // Pfeil umdrehen wenn velocity.x negativ ist
        if(_velocity.x < 0) {
            _sprite.setScaleX(_sprite.getScaleX() * -1);
        }
    }

    @Override
    public void update(float deltaTime) {
        // Gravitation auf velocity anwenden
        _velocity.y += GRAVITY + deltaTime;

        // Velocity anwenden
        _sprite.setX(_sprite.getX() + (_velocity.x * deltaTime));
        _sprite.setY(_sprite.getY() + (_velocity.y * deltaTime));
    }

    @Override
    public ImageView getSprite() {
        return _sprite;
    }

    @Override
    public boolean isFromPlayer() {
        // Pfeile können nur vom spieler sein, deshalb immer true
        return true;
    }

    @Override
    public boolean doesHitPlayer(ImageView playerView) {
        return false; // False because the Arrow is only used by the Player
    }

    @Override
    public boolean doesHitBoss(ImageView bossView) {
        if(_sprite == null) return false;

        return Projektil.aabbCollision(_sprite, bossView);
    }
}
