import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.css.Match;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.rmi.server.ExportException;

public class Main extends Application {

    private GameDimension _currentDimension = null;
    private InputData _inputData = null;
    public MatchLeben _matchLeben = null;
    private SoundPlayer _soundPlayer = null;

    private WeltenManager weltenManager = null;

    @Override
    public void start(Stage stage) {
        // JavaFX Setup
        //Pane root = new Pane();
        //root.setPrefSize(1280, 720);

        //Scene Menu = new Scene(root);
        //stage.setScene(Menu);


        //Scene Level = new Scene(root);
        //stage.setScene(Level);
        stage.setScene(Menu.erstelleMenuScene(
                this,
                e -> {
                    _matchLeben = new MatchLeben(5);
                    goToLevel("o1", stage);
                    return e;
                }
        ));
        stage.setTitle("Horizon Minecraft Jump");
        stage.show();


        // Funktionsklassen Setup
        _inputData = new InputData();
        _inputData.initInputSystemOnScene(stage.getScene());

        _soundPlayer = new SoundPlayer();

        weltenManager = new WeltenManager();

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
        // TESTING ---
        //goToLevel("o1", stage);

        // start Game loop
        timer.start();
    }

    private void update(float deltaTime) {
        if(_currentDimension != null) _currentDimension.updateDimension(deltaTime, _inputData);
    }

    private void goToLevel(String key, Stage stage) {
        Pane root = new Pane();
        root.setPrefSize(1280, 720);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        _inputData = new InputData();
        _inputData.initInputSystemOnScene(scene);

        _currentDimension = new GameDimension(key, root, _matchLeben, _soundPlayer);
        _currentDimension.ladeLevel(weltenManager.getLevelData(key), true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}