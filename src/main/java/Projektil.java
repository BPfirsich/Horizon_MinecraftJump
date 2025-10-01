import javafx.scene.image.ImageView;

public interface Projektil {
    void init(Vector2f startPos, Vector2f startDire);
    void update(float deltaTime);

    ImageView getSprite();
    boolean isFromPlayer();
}
