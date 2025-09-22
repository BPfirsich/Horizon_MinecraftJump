import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    Spieler spieler;
    Gegner gegner;
    boolean tasteLinks, tasteRechts;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPrefSize(600, 400);

        // Objekte erstellen
        spieler = new Spieler(50, 360);
        gegner = new Gegner(500, 370, 3);

        root.getChildren().addAll(spieler.figur, gegner.figur);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Jump and Run");
        stage.show();

        // Tastensteuerung mit A/D
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) spieler.springen();
            if (e.getCode() == KeyCode.A) tasteLinks = true;
            if (e.getCode() == KeyCode.D) tasteRechts = true;
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) tasteLinks = false;
            if (e.getCode() == KeyCode.D) tasteRechts = false;
        });

        // Spielschleife
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    private void update() {
        // Spieler-Bewegung links/rechts
        if (tasteLinks) spieler.figur.setTranslateX(spieler.figur.getTranslateX() - 4);
        if (tasteRechts) spieler.figur.setTranslateX(spieler.figur.getTranslateX() + 4);

        spieler.update();
        gegner.update();

        // Kollision
        if (spieler.figur.getBoundsInParent().intersects(gegner.figur.getBoundsInParent())) {
            spieler.figur.setFill(Color.GRAY); // Spieler „verliert“
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}