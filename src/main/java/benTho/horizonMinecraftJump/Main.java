package benTho.horizonMinecraftJump;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Function;

public class Main extends Application {

    public GameDimension _currentDimension = null;
    private InputData _inputData = null;
    public MatchLeben _matchLeben = null;
    private SoundPlayer _soundPlayer = null;

    private WeltenManager weltenManager = null;
    private HighscoreManager _highscoreManager = null;

    public static ServerConnector serverConnector = null; // Static, denn man kann nur maximal zu einem server verbunden sein

    public Queue<Function<Void, Void>> doSomethingQueue = new ConcurrentLinkedDeque<>(); // Used by the server. (Thread-safe)
    public Stage stageRef;

    void switchToMainMenu(Stage stage) {

        stageRef = stage;
        clearOldRoot(stage);
        _soundPlayer.setMusic("mainMenu");

        stage.setScene(Menu.erstelleMenuScene(
                this,
                e -> { // Start Game
                    _soundPlayer.playSound("click", 1);

                    _soundPlayer.playSound("newGame", 1.0);

                    _matchLeben = new MatchLeben(5);
                    goToLevel("o1", stage, false);
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
                },
                e -> {
                    _soundPlayer.playSound("click", 1);

                    switchToServerMenu(stage);
                    return e;
                }
        ));
    }
    void switchToLevelMenu(Stage stage) {
        clearOldRoot(stage);
        _soundPlayer.setMusic("mainMenu");

        stage.setScene(Menu.erstelleLevelAuswahlScene(
                this,
                s -> {
                    _soundPlayer.playSound("click", 1);

                    _matchLeben = new MatchLeben(5);
                    goToLevel(s, stage, false);
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
        clearOldRoot(stage);
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
        clearOldRoot(stage);
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
        clearOldRoot(stage);
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
                    goToLevel("o1", stage, false);
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
                },
                _highscoreManager
        ));
    }
    void switchToFailScreen(Stage stage) {
        clearOldRoot(stage);
        _soundPlayer.setMusic("fail");

        stage.setScene(Menu.erstelleDeathScene(
                this,
                e -> { // Respawn
                    _soundPlayer.playSound("click", 1);

                    _matchLeben = new MatchLeben(5);
                    goToLevel("o1", stage, false);
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
        clearOldRoot(stage);
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

    void switchToServerMenu(Stage stage) {
        clearOldRoot(stage);
        _soundPlayer.setMusic("mainMenu");

//        serverConnector.TryConnection(InetAddress.getLoopbackAddress());
//        System.out.println(serverConnector.getPing());
//        serverConnector.CreateRoom();
        stage.setScene(Menu.erstelleServerMenu(this,
                e -> {
                    _soundPlayer.playSound("click", 1);

                    switchToMainMenu(stage);
                    return e;
        }));
    }

    @Override
    public void start(Stage stage) {

        // --------------------------
        // SPLASHSCREEN (neu!)
        // --------------------------
        ImageView splash = new ImageView(new Image(getClass().getResourceAsStream("/splash.png")));
        splash.setFitWidth(429);
        splash.setFitHeight(764);

        StackPane splashRoot = new StackPane(splash);
        Scene splashScene = new Scene(splashRoot, 429, 764);

        //stage.initStyle(javafx.stage.StageStyle.UNDECORATED);


        stage.setScene(splashScene);
        stage.setResizable(false);
        stage.show();

        // Fade-Out
        FadeTransition fade = new FadeTransition(Duration.seconds(1.0), splashRoot);
        fade.setDelay(Duration.seconds(0.5)); // wie lange Splash bleibt
        fade.setFromValue(1);
        fade.setToValue(0);

        fade.setOnFinished(f -> {



            //ende Splash screen
            _soundPlayer = new SoundPlayer();

            switchToMainMenu(stage);
            stage.setTitle("Horizon Minecraft Jump");
            stage.setResizable(false);
            stage.show();

            stage.centerOnScreen();

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

            serverConnector = new ServerConnector();

            // Spielschleife, also quasy das "Herz" des spiels.
            AnimationTimer timer = new AnimationTimer() {
                double lastTimeNano = System.nanoTime();

                @Override
                public void handle(long currentTimeMillis) {
                    long frameStartTime = System.currentTimeMillis();

                    // Die zeit zwischen diesen und letzten frame berechnen
                    float deltaTime = (float) ((currentTimeMillis - lastTimeNano) / 1_000_000_000.0);
                    lastTimeNano = currentTimeMillis;

                    // Alle funktionen callen, die pro frame vorkommen
                    _inputData.inputSystemUpdate();
                    update(deltaTime * 1.0f);

                    while (!doSomethingQueue.isEmpty()) {
                        // Things are done here like loading Levels in Multiplayer etc.
                        doSomethingQueue.poll().apply(null);
                    }

                    long frameTime = (System.currentTimeMillis() - frameStartTime);
                    if (frameTime > 10) System.out.println("Frametime: " + frameTime + "ms");
                }
            };
            // TESTING ---
            //goToLevel("o1", stage);

            // start Game loop
            timer.start();

            // DAS HIER ÄNDERN LOL
            //switchToStoryMenu(stage);
            // DAS HIER ÄNDERN LOL

        });
        fade.play(); // <--- ebenfalls wichtig!
    }            // <---- UND DAS ist das Ende der start()-Methode!


    private void update(float deltaTime) {
        if(_currentDimension != null) _currentDimension.updateDimension(deltaTime, _inputData);
    }

    public void goToLevel(String key, Stage stage, boolean isCalledByServer) {
        if (serverConnector.isConnected() && !isCalledByServer) {
            int currentRoomID = serverConnector.getCurrentRoomID();
            if (currentRoomID == -1) {
                // In case if we aren't in a room, we cannot start the game. Because yeah, don't be in Server you u want to play offline :P
                return;
            } else {
                serverConnector.changeWorld(currentRoomID, key);
                return;
            }
        }
        System.out.println(isCalledByServer);
        if (isCalledByServer) {
            _matchLeben = new MatchLeben(5); // The Buttons do this normally but online you didn't pressed the button
        }

        // Sichergehen das das aktuelle root WIRKLICH leer ist
        clearOldRoot(stage);

        // Neues level laden
        Pane root = new Pane();
        root.setPrefSize(1280, 720);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        _inputData = new InputData();
        _inputData.initInputSystemOnScene(scene);

        WeakReference<GameDimension> ref = new WeakReference<>(_currentDimension);

        _currentDimension = new GameDimension(key, root, _matchLeben, _soundPlayer,
                s -> { goToLevel(s, stage, false); return null; },
                e -> { switchToWinScreen(stage); return e; },
                e -> { switchToFailScreen(stage); return e; },
                _highscoreManager
        );
        _currentDimension.ladeLevel(weltenManager.getLevelData(key), true);

        System.gc();
        System.out.println("Ref: " + ref.get());
    }

    public void clearOldRoot(Stage stage) {
        // Sichergehen das das aktuelle root WIRKLICH leer ist
        Pane oldRoot = (Pane)stage.getScene().getRoot();
        System.out.println("Cleared Element: " + oldRoot.getChildren().toArray().length);
        oldRoot.getChildren().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}