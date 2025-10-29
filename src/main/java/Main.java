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
    private LevelData _testworld;

    private BackgroundImage _background_1_1;

    @Override
    public void start(Stage stage) {
        // JavaFX Setup
        Pane root = new Pane();
        root.setPrefSize(1280, 720);

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
        _overworld = new LevelData("Overworld", 11, _background_1_1);
        _overworld.stufe[10] = "-----------------------------";
        _overworld.stufe[9]  = "-----------------------------";
        _overworld.stufe[8]  = "-----------------------------";
        _overworld.stufe[7]  = "-----------------------------";
        _overworld.stufe[6]  = "-----------------------------";
        _overworld.stufe[5]  = "-----------------------------";
        _overworld.stufe[4]  = "-----------------------------";
        _overworld.stufe[3]  = "-----------------------------";
        _overworld.stufe[2]  = "------------P--#####---------";
        _overworld.stufe[1]  = "----#####----##+++++###------";
        _overworld.stufe[0]  = "####+++++####++++++++++######";

        _testworld = new LevelData("Testlevel", 11, _background_1_1);
        _testworld.stufe[10] = "-----------------------------";
        _testworld.stufe[9]  = "-----------------------------";
        _testworld.stufe[8]  = "-----------------------------";
        _testworld.stufe[7]  = "-----------------------------";
        _testworld.stufe[6]  = "-----------------------------";
        _testworld.stufe[5]  = "-----------------------------";
        _testworld.stufe[4]  = "-----------------------------";
        _testworld.stufe[3]  = "--------------#--------------";
        _testworld.stufe[2]  = "--------------#--------------";
        _testworld.stufe[1]  = "-P--------#---#--------------";
        _testworld.stufe[0]  = "#######---#---#--------------";

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