package benTho.horizonMinecraftJump;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Boss {

    private final Image _idleImage;
    private final Image _shootImage;

    private final Class<? extends Projektil> _projektil;
    private final float _shootInterval;
    private final float _projektilSpeed;
    private final int _shootHeightOffset;

    private float _timeSinceLastShotSeconds;
    private final float _attackDistancePixels;

    private final GameDimension myDimension;

    private final float _startingXPos = 0; // Used for moving
    private final float _walkAroundAmountPixel = 120;
    private final float _walkAroundSpeed = 0.6f;

    public final ImageView imageView;

    public float health;

    public final String bossName;
    public final Color bossColor;

    public Boss (Vector2f spawnPos, Image idleImage, Image shootImage, Class<? extends Projektil> projektil, float shootInterval,
                 float attackDistancePixels, GameDimension gameDimension, Vector2f sizePixel, float projektilSpeed, int shootHeightOffset, float health,
                 String bossName, Color bossColor) {
        _idleImage = idleImage;
        _shootImage = shootImage;
        _projektil = projektil;
        _shootInterval = shootInterval;
        _attackDistancePixels = attackDistancePixels;
        _projektilSpeed = projektilSpeed;
        _shootHeightOffset = shootHeightOffset;
        this.health = health;

        this.bossName = bossName;
        this.bossColor = bossColor;

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
        walkAround(deltaTime);

        Vector2f myPosition = new Vector2f((float)imageView.getX(), (float)imageView.getY());
        float distanceToPlayerPixel = playerPixelPos.sub(myPosition).length();

        //System.out.println(distanceToPlayerPixel);

        if (distanceToPlayerPixel <= _attackDistancePixels) {
            _timeSinceLastShotSeconds += deltaTime;
            if (_timeSinceLastShotSeconds >= _shootInterval) {
                try {
                    shoot(playerPixelPos, myPosition);
                }
                catch (Exception e) {
                    // Should never happen but java wants it lol
                    e.fillInStackTrace();
                }
                _timeSinceLastShotSeconds = 0;

                Random rand = new Random();
                _timeSinceLastShotSeconds -= rand.nextFloat() * 0.3f;
            }
        }
    }

    private void walkAround(float deltaTime) {
        double walkValue = Math.sin(((double)System.currentTimeMillis() / 1000.0) * _walkAroundSpeed) * _walkAroundAmountPixel;

        imageView.setX(imageView.getX() + walkValue * deltaTime);
    }

    private void shoot(Vector2f playerPixelPos, Vector2f myPosition) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        myDimension._soundPlayer.playSound("gegnerSchuss", 1.0);

        // Calc the shooting dire
        Vector2f shootDire = playerPixelPos.sub(new Vector2f(myPosition.x, playerPixelPos.y)).normalize().mul(_projektilSpeed);

        int heightOffset = _shootHeightOffset; // (Normal height)
        Random rand = new Random();
        if (rand.nextBoolean()) {
            heightOffset += 30; // (Floor-Level)
        }

        // Create the Projektil
        Projektil newProjectil = _projektil.getDeclaredConstructor().newInstance();
        newProjectil.init(myPosition.add(new Vector2f(0, heightOffset)), new Vector2f(shootDire.x, 0));
        myDimension.addProjektil(newProjectil);

        AnimationTimer showShootTexture = new AnimationTimer() {
            static final long showLengthMs = (long)(0.25 * 1_000_000_000.0);

            private long startTime = -1;

            @Override
            public void handle(long l) {
                if (startTime == -1) {
                    startTime = l;
                    imageView.setImage(_shootImage);
                }

                if (l >= (startTime + showLengthMs)) {
                    imageView.setImage(_idleImage);
                    this.stop();
                }
            }
        };
        showShootTexture.start();
    }
}
