import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private GameDimension _currentDimension = null;
    private InputData _inputData = null;
    public MatchLeben _matchLeben = null;
    private SoundPlayer _soundPlayer = null;

    private WeltenManager weltenManager = null;
    private HighscoreManager _highscoreManager = null;

    void switchToMainMenu(Stage stage) {
        _soundPlayer.setMusic("mainMenu");

        stage.setScene(Menu.erstelleMenuScene(
                this,
                e -> { // Start Game
                    _soundPlayer.playSound("click", 1);

                    _soundPlayer.playSound("newGame", 1.0);

                    _matchLeben = new MatchLeben(5);
                    goToLevel("o1", stage);
                    return e;
                },
                e -> { // Level Selector
                    _soundPlayer.playSound("click", 1);

                    switchToLevelMenu(stage);
                    return e;
                },
                e -> { // Highscore Scene
                    _soundPlayer.playSound("click", 1);

                    switchToHighscoreMenu(stage);
                    return e;
                },
                e -> { // Story Scene
                    _soundPlayer.playSound("click", 1);

                    switchToStoryMenu(stage);
                    return e;
                }
        ));
    }
    void switchToLevelMenu(Stage stage) {
        _soundPlayer.setMusic("mainMenu");

        stage.setScene(Menu.erstelleLevelAuswahlScene(
                this,
                s -> {
                    _soundPlayer.playSound("click", 1);

                    _matchLeben = new MatchLeben(5);
                    goToLevel(s, stage);
                    return null;
                },
                e -> {
                    _soundPlayer.playSound("click", 1);

                    switchToMainMenu(stage);
                    return e;
                }
        ));
    }
    void switchToHighscoreMenu(Stage stage) {
        _soundPlayer.setMusic("mainMenu");

        stage.setScene(Menu.erstelleHighcoreScene(
                this,
                e -> { // Back bt
                    _soundPlayer.playSound("click", 1);

                    switchToMainMenu(stage);
                    return e;
                },
                _highscoreManager
        ));
    }
    void switchToStoryMenu(Stage stage) {
        _soundPlayer.setMusic("mainMenu");

        stage.setScene(Menu.erstelleStoryScene(
                this,
                e -> { // Back bt
                    _soundPlayer.playSound("click", 1);

                    switchToMainMenu(stage);
                    return e;
                },
                e -> { // Credits bt
                    _soundPlayer.playSound("click", 1);

                    switchToCredits(stage);
                    return e;
                }
        ));
    }
    void switchToWinScreen(Stage stage) {
        _soundPlayer.setMusic("win");

        stage.setScene(Menu.erstelleWinScene(
                this,
                e -> { // Menu bt
                    _soundPlayer.playSound("click", 1);

                    switchToMainMenu(stage);
                    return e;
                },

                e -> { // Restart bt
                    _soundPlayer.playSound("click", 1);

                    _matchLeben = new MatchLeben(5);
                    goToLevel("o1", stage);
                    return e;
                },
                e -> { // Highscore Scene
                    _soundPlayer.playSound("click", 1);

                    switchToHighscoreMenu(stage);
                    return e;
                },
                e -> { // Credits bt
                    _soundPlayer.playSound("click", 1);

                    switchToCredits(stage);
                    return e;
                }

        ));
    }
    void switchToFailScreen(Stage stage) {
        _soundPlayer.setMusic("fail");

        stage.setScene(Menu.erstelleDeathScene(
                this,
                e -> { // Respawn
                    _soundPlayer.playSound("click", 1);

                    _matchLeben = new MatchLeben(5);
                    goToLevel("o1", stage);
                    return e;
                },
                e -> { // Lvl Auswahl
                    _soundPlayer.playSound("click", 1);

                    switchToLevelMenu(stage);
                    return e;
                },
                e -> { // Menu
                    _soundPlayer.playSound("click", 1);

                    switchToMainMenu(stage);
                    return e;
                }
        ));
    }
    void switchToCredits(Stage stage) {
        _soundPlayer.setMusic("credits");

        stage.setScene(Menu.erstelleCreditsScreen(
                this,
                e -> { // Menu bt
                    _soundPlayer.playSound("bossDeath", 1);

                    switchToMainMenu(stage);
                    return e;
                }
        ));
    }

    @Override
    public void start(Stage stage) {

        _soundPlayer = new SoundPlayer();

        switchToMainMenu(stage);
        stage.setTitle("Horizon Minecraft Jump");
        stage.setResizable(false);
        stage.show();

        // Funktionsklassen Setup
        _inputData = new InputData();
        _inputData.initInputSystemOnScene(stage.getScene());

        weltenManager = new WeltenManager();
        _highscoreManager = new HighscoreManager();

        try {
            _highscoreManager.loadOrCreateHighscoreFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        _highscoreManager.save();

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
                update(deltaTime * 1.0f);
            }
        };
        // TESTING ---
        //goToLevel("o1", stage);

        // start Game loop
        timer.start();

        // DAS HIER ÄNDERN LOL
        //switchToCredits(stage);
        // DAS HIER ÄNDERN LOL
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

        _currentDimension = new GameDimension(key, root, _matchLeben, _soundPlayer,
                s -> { goToLevel(s, stage); return null; },
                e -> { switchToWinScreen(stage); return e; },
                e -> { switchToFailScreen(stage); return e; },
                _highscoreManager
        );
        _currentDimension.ladeLevel(weltenManager.getLevelData(key), true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}