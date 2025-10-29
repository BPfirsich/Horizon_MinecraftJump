import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private GameDimension _currentDimension = null;
    private InputData _inputData = null;

    private LevelData _overworld;

    private BackgroundImage _background_1_1;

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

        // Hintergründe laden
        _background_1_1 = new BackgroundImage(
                new Image(getClass().getResourceAsStream("/screen_ow_1.png")),
                BackgroundRepeat.NO_REPEAT, // Wiederholung horizontal
                BackgroundRepeat.NO_REPEAT, // Wiederholung vertikal
                BackgroundPosition.CENTER,  // Position
                BackgroundSize.DEFAULT      // Größe
        );

        // Erstellen der Level
        _overworld = new LevelData("Overworld", 4, _background_1_1);
        _overworld.stufe[3] = "-----";
        _overworld.stufe[2] = "-----";
        _overworld.stufe[1] = "-P--#####";
        _overworld.stufe[0] = "###########";

        // TESTING ---
        _currentDimension = new GameDimension("Test", root);
        _currentDimension.addGegner(new Gegner(500, 370, 200 ));
        _currentDimension.ladeLevel(_overworld);
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