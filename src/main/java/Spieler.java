import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
class Spieler {
    Rectangle figur;
    double ySpeed = 0;
    boolean amBoden = true;

    public Spieler(double x, double y) {
        figur = new Rectangle(30, 40, Color.BLUE);
        figur.setTranslateX(x);
        figur.setTranslateY(y);
    }

    public void springen() {
        if (amBoden) {
            ySpeed = -12;      // Sprungkraft
            amBoden = false;
        }
    }

    public void update() {
        ySpeed += 0.5; // Schwerkraft
        figur.setTranslateY(figur.getTranslateY() + ySpeed);

        if (figur.getTranslateY() >= 360) { // Boden bei y=360
            figur.setTranslateY(360);
            amBoden = true;
            ySpeed = 0;
        }
    }
}