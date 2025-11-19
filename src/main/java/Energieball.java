import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Energieball implements Projektil {

    private static Image myTexture;

    private Vector2f _dire;
    private ImageView imageView;

    @Override
    public void init(Vector2f startPos, Vector2f startDire) {
        if (myTexture == null) {
            myTexture = new Image(getClass().getResourceAsStream("/energieball.png"));
        }

        imageView = new ImageView(myTexture);
        imageView.setX(startPos.x);
        imageView.setY(startPos.y);
        imageView.setFitWidth(256/3);
        imageView.setFitHeight(240/3);

        _dire = new Vector2f(startDire);
    }

    @Override
    public void update(float deltaTime) {
        imageView.setX(imageView.getX() + _dire.x * deltaTime);
        imageView.setY(imageView.getY() + _dire.y * deltaTime);
    }

    @Override
    public ImageView getSprite() {
        return imageView;
    }

    @Override
    public boolean isFromPlayer() {
        return false; // Energiebälle können nur von bossen geschossen werden, deshalb immer false
    }

    @Override
    public boolean doesHitPlayer() {
        return false;
    }
}
