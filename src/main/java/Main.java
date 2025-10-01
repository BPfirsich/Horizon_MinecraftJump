import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;

public class Main extends Application {

    private GameDimension _currentDimension = null;
    private InputData _inputData = null;

    @Override
    public void start(Stage stage) {
        // JavaFX Setup
        Pane root = new Pane();
        root.setPrefSize(600, 400);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Horizon Minecraft Jump");
        stage.show();

        // Funktionsklassen Setup
        _inputData = new InputData();
        _inputData.initInputSystemOnScene(scene);

        // TESTING ---
        _currentDimension = new GameDimension("Test", root);
        _currentDimension.setSpieler(new Spieler(50, 360, 250, _currentDimension));
        _currentDimension.addGegner(new Gegner(500, 370, 200 ));
        // --- TESTING

        // Spielschleife, also quasy das "Herz" des spiels.
        AnimationTimer timer = new AnimationTimer() {
            double lastTimeNano = System.nanoTime();

            @Override
            public void handle(long currentTimeMillis) {
                // Die zeit zwischen diesen und letzten frame berechnen
                float deltaTime = (float)((currentTimeMillis - lastTimeNano) / 1_000_000_000.0);
                lastTimeNano = currentTimeMillis;

                // Alle funktionen callen, die pro frame vorkommen
                _inputData.inputSystemUpdate();
                update(deltaTime * 1);
            }
        };
        timer.start();
    }

    private void update(float deltaTime) {
        _currentDimension.updateDimension(deltaTime, _inputData);
    }

    public static void main(String[] args) {
        launch(args);
    }
}