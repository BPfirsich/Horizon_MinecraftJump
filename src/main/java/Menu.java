import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.function.Function;

public class Menu {

    private static Scene createSceneBase(Main classInstance, Pane root, String backgroundImgPath) {
        root.setPrefSize(1280, 720);
        Scene newScene = new Scene(root);

        BackgroundImage _background_img = new BackgroundImage(
                new Image(classInstance.getClass().getResourceAsStream(backgroundImgPath)),
                BackgroundRepeat.NO_REPEAT, // Wiederholung horizontal
                BackgroundRepeat.NO_REPEAT, // Wiederholung vertikal
                BackgroundPosition.CENTER,  // Position
                new  BackgroundSize(1280, 720, false, false, true, true) // Größe
        );

        root.setBackground(new Background(_background_img));

        return newScene;
    }

    public static Scene erstelleMenuScene(Main classInstance, Function<Void, Void> loadLevel1,
                                   Function<Void, Void> toLevelScreen, Function<Void, Void> toHighscoreScene, Function<Void, Void> toStoryScreen) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/Homescreen_text.png");

        Font minecraftFont = Font.loadFont(classInstance.getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 30);

        Button startGameButton = new Button();
        startGameButton.setPrefWidth(240);
        startGameButton.setPrefHeight(80);
        startGameButton.setLayoutX(463);
        startGameButton.setLayoutY(310);
        startGameButton.setOnAction(event -> {loadLevel1.apply(null);});
        startGameButton.setOpacity(0);
        root.getChildren().add(startGameButton);

        Button changeLvlButton = new Button();
        changeLvlButton.setPrefWidth(240);
        changeLvlButton.setPrefHeight(80);
        changeLvlButton.setLayoutX(463);
        changeLvlButton.setLayoutY(405);
        changeLvlButton.setOnAction(event -> {toLevelScreen.apply(null);});
        changeLvlButton.setOpacity(0);
        root.getChildren().add(changeLvlButton);

        Button highscoreButton = new Button("highscore");
        highscoreButton.setPrefWidth(240);
        highscoreButton.setPrefHeight(80);
        highscoreButton.setLayoutX(463);
        highscoreButton.setLayoutY(485);
        highscoreButton.setOnAction(event -> {toHighscoreScene.apply(null);});
        highscoreButton.setOpacity(0);
        root.getChildren().add(highscoreButton);

        Button storyButton = new Button("story");
        storyButton.setPrefWidth(240);
        storyButton.setPrefHeight(80);
        storyButton.setLayoutX(463);
        storyButton.setLayoutY(575);
        storyButton.setOnAction(event -> {toStoryScreen.apply(null);});
        storyButton.setOpacity(0);
        root.getChildren().add(storyButton);

        Button endBt = new Button();
        endBt.setPrefWidth(240);
        endBt.setPrefHeight(80);
        endBt.setLayoutX(1010);
        endBt.setLayoutY(530);
        endBt.setOnAction(event -> {System.exit(0);});
        endBt.setOpacity(0);
        root.getChildren().add(endBt);

        Text version = new Text("version: 1.0");
        version.setFill(Color.YELLOW);
        version.setFont(minecraftFont);
        version.setX(5);
        version.setY(30);
        root.getChildren().add(version);

        return newScene;
    }

    public static Scene erstelleLevelAuswahlScene(Main classInstance, Function<String, Void> loadFunction, Function<Void, Void> backFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_lvl.png");

        Button backBt = new Button();
        backBt.setPrefWidth(240);
        backBt.setPrefHeight(80);
        backBt.setLayoutX(145);
        backBt.setLayoutY(220);
        backBt.setOnAction(event -> {backFunction.apply(null);});
        backBt.setOpacity(0);
        root.getChildren().add(backBt);

        Button o1Button = new Button();
        o1Button.setPrefWidth(240);
        o1Button.setPrefHeight(80);
        o1Button.setLayoutX(463);
        o1Button.setLayoutY(405);
        o1Button.setOnAction(event -> {loadFunction.apply("o1");});
        o1Button.setOpacity(0);
        root.getChildren().add(o1Button);

        Button o2Button = new Button();
        o2Button.setPrefWidth(240);
        o2Button.setPrefHeight(80);
        o2Button.setLayoutX(463);
        o2Button.setLayoutY(495);
        o2Button.setOnAction(event -> {loadFunction.apply("o2");});
        o2Button.setOpacity(0);
        root.getChildren().add(o2Button);

        Button o3Button = new Button();
        o3Button.setPrefWidth(240);
        o3Button.setPrefHeight(80);
        o3Button.setLayoutX(463);
        o3Button.setLayoutY(585);
        o3Button.setOnAction(event -> {loadFunction.apply("o3");});
        o3Button.setOpacity(0);
        root.getChildren().add(o3Button);

        Button n1Button = new Button();
        n1Button.setPrefWidth(240);
        n1Button.setPrefHeight(80);
        n1Button.setLayoutX(720);
        n1Button.setLayoutY(405);
        n1Button.setOnAction(event -> {loadFunction.apply("n1");});
        n1Button.setOpacity(0);
        root.getChildren().add(n1Button);

        Button n2Button = new Button();
        n2Button.setPrefWidth(240);
        n2Button.setPrefHeight(80);
        n2Button.setLayoutX(720);
        n2Button.setLayoutY(495);
        n2Button.setOnAction(event -> {loadFunction.apply("n2");});
        n2Button.setOpacity(0);
        root.getChildren().add(n2Button);

        Button n3Button = new Button();
        n3Button.setPrefWidth(240);
        n3Button.setPrefHeight(80);
        n3Button.setLayoutX(720);
        n3Button.setLayoutY(585);
        n3Button.setOnAction(event -> {loadFunction.apply("n3");});
        n3Button.setOpacity(0);
        root.getChildren().add(n3Button);

        Button e1Button = new Button();
        e1Button.setPrefWidth(240);
        e1Button.setPrefHeight(80);
        e1Button.setLayoutX(980);
        e1Button.setLayoutY(405);
        e1Button.setOnAction(event -> {loadFunction.apply("e1");});
        e1Button.setOpacity(0);
        root.getChildren().add(e1Button);

        Button e2Button = new Button();
        e2Button.setPrefWidth(240);
        e2Button.setPrefHeight(80);
        e2Button.setLayoutX(980);
        e2Button.setLayoutY(495);
        e2Button.setOnAction(event -> {loadFunction.apply("e2");});
        e2Button.setOpacity(0);
        root.getChildren().add(e2Button);

        Button e3Button = new Button();
        e3Button.setPrefWidth(240);
        e3Button.setPrefHeight(80);
        e3Button.setLayoutX(980);
        e3Button.setLayoutY(585);
        e3Button.setOnAction(event -> {loadFunction.apply("e3");});
        e3Button.setOpacity(0);
        root.getChildren().add(e3Button);

        return newScene;
    }

    public static Scene erstelleHighcoreScene(Main classInstance, Function<Void, Void> backFunction, HighscoreManager highscore) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_bestzeiten.png");

        Font minecraftFont = Font.loadFont(classInstance.getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 35);

        Button backBt = new Button("Zurück");
        backBt.setPrefWidth(240);
        backBt.setPrefHeight(80);
        backBt.setLayoutX(820);
        backBt.setLayoutY(465);
        backBt.setOnAction(event -> {backFunction.apply(null);});
        backBt.setOpacity(0);
        root.getChildren().add(backBt);

        Text hsForest = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("o1")));
        hsForest.setFill(Color.YELLOW);
        hsForest.setFont(minecraftFont);
        hsForest.setX(363);
        hsForest.setY(115);
        root.getChildren().add(hsForest);

        Text hsSnowyPlains = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("o2")));
        hsSnowyPlains.setFill(Color.YELLOW);
        hsSnowyPlains.setFont(minecraftFont);
        hsSnowyPlains.setX(363);
        hsSnowyPlains.setY(187);
        root.getChildren().add(hsSnowyPlains);

        Text hsDesert = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("o3")));
        hsDesert.setFill(Color.YELLOW);
        hsDesert.setFont(minecraftFont);
        hsDesert.setX(363);
        hsDesert.setY(258);
        root.getChildren().add(hsDesert);

        Text hsNetherWaste = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("n1")));
        hsNetherWaste.setFill(Color.YELLOW);
        hsNetherWaste.setFont(minecraftFont);
        hsNetherWaste.setX(363);
        hsNetherWaste.setY(323);
        root.getChildren().add(hsNetherWaste);

        Text hsCrimsonForest = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("n2")));
        hsCrimsonForest.setFill(Color.YELLOW);
        hsCrimsonForest.setFont(minecraftFont);
        hsCrimsonForest.setX(363);
        hsCrimsonForest.setY(393);
        root.getChildren().add(hsCrimsonForest);

        Text hsWarpedForest = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("n3")));
        hsWarpedForest.setFill(Color.YELLOW);
        hsWarpedForest.setFont(minecraftFont);
        hsWarpedForest.setX(363);
        hsWarpedForest.setY(463);
        root.getChildren().add(hsWarpedForest);

        Text hsEndIsland = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("e1")));
        hsEndIsland.setFill(Color.YELLOW);
        hsEndIsland.setFont(minecraftFont);
        hsEndIsland.setX(363);
        hsEndIsland.setY(533);
        root.getChildren().add(hsEndIsland);

        Text hsEndCity = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("e2")));
        hsEndCity.setFill(Color.YELLOW);
        hsEndCity.setFont(minecraftFont);
        hsEndCity.setX(363);
        hsEndCity.setY(601);
        root.getChildren().add(hsEndCity);

        Text hsDragonIsland = new Text(HighscoreManager.toMM_SS_String(highscore.highscoreMap.get("e3")));
        hsDragonIsland.setFill(Color.YELLOW);
        hsDragonIsland.setFont(minecraftFont);
        hsDragonIsland.setX(363);
        hsDragonIsland.setY(668);
        root.getChildren().add(hsDragonIsland);

        Text hsDragonTotal = new Text(HighscoreManager.toMM_SS_String(
                highscore.highscoreMap.get("o1") +
                        highscore.highscoreMap.get("o2") +
                        highscore.highscoreMap.get("o3") +
                        highscore.highscoreMap.get("n1") +
                        highscore.highscoreMap.get("n2") +
                        highscore.highscoreMap.get("n3") +
                        highscore.highscoreMap.get("e1") +
                        highscore.highscoreMap.get("e2") +
                        highscore.highscoreMap.get("e3")
        ));
        hsDragonTotal.setFill(Color.YELLOW);
        hsDragonTotal.setFont(minecraftFont);
        hsDragonTotal.setX(760);
        hsDragonTotal.setY(173);
        root.getChildren().add(hsDragonTotal);

        return newScene;
    }

    public static Scene erstelleStoryScene(Main classInstance, Function<Void, Void> backFunction, Function<Void, Void> creditsFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_story_mc.png");

        Button backBt = new Button("Zurück");
        backBt.setPrefWidth(220);
        backBt.setPrefHeight(95);
        backBt.setLayoutX(935);
        backBt.setLayoutY(250);
        backBt.setOnAction(event -> {backFunction.apply(null);});
        backBt.setOpacity(0);
        root.getChildren().add(backBt);

        Button creditsBt = new Button("Credits");
        creditsBt.setPrefWidth(220);
        creditsBt.setPrefHeight(95);
        creditsBt.setLayoutX(935);
        creditsBt.setLayoutY(105);
        creditsBt.setOnAction(event -> {creditsFunction.apply(null);});
        creditsBt.setOpacity(0);
        root.getChildren().add(creditsBt);

        return newScene;
    }

    public static Scene erstelleWinScene(Main classInstance, Function<Void, Void> meunFunction, Function<Void, Void> restartFunction,
                                         Function<Void, Void> toHighscoreScene, Function<Void, Void> creditsFunction ) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_win_mc.png");

        Font minecraftFont = Font.loadFont(classInstance.getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 40);

        Button menuBt = new Button("Menu");
        menuBt.setPrefWidth(180);
        menuBt.setPrefHeight(60);
        menuBt.setLayoutX(875);
        menuBt.setLayoutY(620);
        menuBt.setOnAction(event -> {meunFunction.apply(null);});
        menuBt.setOpacity(0);
        root.getChildren().add(menuBt);

        Button restartBt = new Button("Restart");
        restartBt.setPrefWidth(180);
        restartBt.setPrefHeight(60);
        restartBt.setLayoutX(875);
        restartBt.setLayoutY(550);
        restartBt.setOnAction(event -> {restartFunction.apply(null);});
        restartBt.setOpacity(0);
        root.getChildren().add(restartBt);

        Button highscoreButton = new Button("highscore");
        highscoreButton.setPrefWidth(180);
        highscoreButton.setPrefHeight(60);
        highscoreButton.setLayoutX(1070);
        highscoreButton.setLayoutY(620);
        highscoreButton.setOnAction(event -> {toHighscoreScene.apply(null);});
        highscoreButton.setOpacity(0);
        root.getChildren().add(highscoreButton);

        Button creditsBt = new Button("Credits");
        creditsBt.setPrefWidth(180);
        creditsBt.setPrefHeight(60);
        creditsBt.setLayoutX(1070);
        creditsBt.setLayoutY(550);
        creditsBt.setOnAction(event -> {creditsFunction.apply(null);});
        creditsBt.setOpacity(0);
        root.getChildren().add(creditsBt);

        Text scoreText = new Text("Highscore");
        scoreText.setFont(minecraftFont);
        scoreText.setFill(Color.RED);
        scoreText.setX(100);
        scoreText.setY(650);
        root.getChildren().add(scoreText);

        return newScene;
    }

    public static Scene erstelleDeathScene(Main classInstance, Function<Void, Void> respawnFunction, Function<Void, Void> lvlAuswahlFunction, Function<Void, Void> menuFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/death_screen.png");

        Button respawnBt = new Button("Respawn");
        respawnBt.setPrefWidth(260);
        respawnBt.setPrefHeight(85);
        respawnBt.setLayoutX(860);
        respawnBt.setLayoutY(345);
        respawnBt.setOnAction(event -> {respawnFunction.apply(null);});
        respawnBt.setOpacity(0);
        root.getChildren().add(respawnBt);

        Button lvlAuswwahlBt = new Button("Menu Auswahl");
        lvlAuswwahlBt.setPrefWidth(260);
        lvlAuswwahlBt.setPrefHeight(85);
        lvlAuswwahlBt.setLayoutX(865);
        lvlAuswwahlBt.setLayoutY(450);
        lvlAuswwahlBt.setOnAction(event -> {lvlAuswahlFunction.apply(null);});
        lvlAuswwahlBt.setOpacity(0);
        root.getChildren().add(lvlAuswwahlBt);

        Button menuBt = new Button("Menu");
        menuBt.setPrefWidth(260);
        menuBt.setPrefHeight(85);
        menuBt.setLayoutX(863);
        menuBt.setLayoutY(550);
        menuBt.setOnAction(event -> {menuFunction.apply(null);});
        menuBt.setOpacity(0);
        root.getChildren().add(menuBt);

        return newScene;
    }

    public static Scene erstelleCreditsScreen(Main classInstance, Function<Void, Void> afterCreditsActions) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_end_3.png");

        Font minecraftFont = Font.loadFont(classInstance.getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 40);

        String creditsText = """
        DANKE FURS SPIELEN!
        Ein Crossover von Minecraft & Horizon

        PROJEKTLEITUNG
        Executive Producer – Benjamin T.
        Creative Director – Benjamin T.
        Projektleitung – Benjamin T.

        GAME DESIGN
        Lead Game Designer – Benjamin T.
        JR-World Designer – Benjamin T.
        Kampf- & Bossdesigner – Benjamin T.
        Maschinenverhalten-Design – Benjamin T.

        ART DEPARTMENT
        Art Director – ChatGPT
        Character Artists – ChatGPT
        Maschinen Artists – ChatGPT
        Environment Artists – ChatGPT
        UI Artists – Benjamin T.

        PROGRAMMIERUNG
        Lead Programmer – Benjamin T.
        Gameplay Programmierung – Benjamin T.
        Boss-KI Programmierung – Benjamin T.
        Weltgenerierung – Benjamin T.

        AUDIO & SOUND
        Musik – Youtube/Copyright free sounds
        Sound Design – Benjamin T.
        Synchronisation – Blocky

        STORY 
        Hauptautor – ChatGPT
        Dialogautoren – Benjamin T.

        QUALITY ASSURANCE
        QA Leitung – Benjamin T.
        Tester – Team Blockytest

        MARKETING
        Trailer Produktion – Benjamin T.

        TOOLS
        intelij - JetBrains
        JavaFX – Oracle
        Github - Github

        SPECIAL THANKS
        Danke an alle Spieler und besonders an Blocky
        Und an DICH – furs Spielen!

        RECHTLICHES
        Fanprojekt / Schulprojekt 
        Inspiriert von Minecraft & Horizon
        Einige Bild und Ton Rechte liegen bei Microsoft und Guerilla
        """;

        Text text = new Text(creditsText);
        text.setFont(minecraftFont);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(javafx.scene.paint.Color.WHITE); // Text weiß für sichtbarkeit
        root.getChildren().add(text);

        double textHight = text.getLayoutBounds().getHeight() + 720;

        AnimationTimer playTimer = new AnimationTimer() {
            private long startTime = -1;
            private long duration = 162L * 1_000_000_000L;

            @Override
            public void handle(long l) {
                if (startTime == -1) {
                    startTime = l;
                    return;
                }

                double progress = (double)(l - startTime) / (double)duration;
                System.out.println(progress);
                text.setY(-textHight * progress + 720);

                if (progress > 1.0) {
                    stop();
                    afterCreditsActions.apply(null);
                }
            }
        };
        playTimer.start();

        newScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                playTimer.stop();
                afterCreditsActions.apply(null);
            }
        });

        return newScene;
    }

}
