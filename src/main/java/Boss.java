import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Boss {

    private Image _idleImage;
    private Image _shootImage;

    private Class<? extends Projektil> _projektil;
    private float _shootInterval;

    private float _timeSinceLastShotSeconds;
    private float _attackDistancePixels;

    private final GameDimension myDimension;

    public ImageView imageView;

    public Boss (Vector2f spawnPos, Image idleImage, Image shootImage, Class<? extends Projektil> projektil, float shootInterval,
                 float attackDistancePixels, GameDimension gameDimension, Vector2f sizePixel) {
        _idleImage = idleImage;
        _shootImage = shootImage;
        _projektil = projektil;
        _shootInterval = shootInterval;
        _attackDistancePixels = attackDistancePixels;

        _timeSinceLastShotSeconds = 0f;

        imageView = new ImageView();
        imageView.setImage(_idleImage);

        imageView.setX(spawnPos.x);
        imageView.setY(spawnPos.y);

        imageView.setFitWidth(sizePixel.x);
        imageView.setFitHeight(sizePixel.y);


        myDimension = gameDimension;
    }

    public void update(float deltaTime, Vector2f playerPixelPos) {
        float distanceToPlayerPixel = playerPixelPos.sub(new Vector2f((float)imageView.getX(), (float)imageView.getY())).length();


    }

}
