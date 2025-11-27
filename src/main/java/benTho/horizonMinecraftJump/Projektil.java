package benTho.horizonMinecraftJump;

import javafx.scene.image.ImageView;

public interface Projektil {
    void init(Vector2f startPos, Vector2f startDire);
    void update(float deltaTime);

    ImageView getSprite();
    boolean isFromPlayer();

    boolean doesHitPlayer(ImageView playerView);
    boolean doesHitBoss(ImageView bossView);

    // AABB Kollision zwischen 2 ImageViews
    public static boolean aabbCollision(ImageView a, ImageView b) {
        double ax = a.getX();
        double ay = a.getY();
        double aw = a.getFitWidth();
        double ah = a.getFitHeight();

        double bx = b.getX();
        double by = b.getY();
        double bw = b.getFitWidth();
        double bh = b.getFitHeight();

        return ax < bx + bw &&
                ax + aw > bx &&
                ay < by + bh &&
                ay + ah > by;
    }
}
