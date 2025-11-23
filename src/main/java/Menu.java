import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        highscoreButton.setOpacity(1);
        root.getChildren().add(highscoreButton);

        Button storyButton = new Button("story");
        storyButton.setPrefWidth(240);
        storyButton.setPrefHeight(80);
        storyButton.setLayoutX(463);
        storyButton.setLayoutY(575);
        storyButton.setOnAction(event -> {toStoryScreen.apply(null);});
        storyButton.setOpacity(1);
        root.getChildren().add(storyButton);

        Button endBt = new Button();
        endBt.setPrefWidth(240);
        endBt.setPrefHeight(80);
        endBt.setLayoutX(1010);
        endBt.setLayoutY(530);
        endBt.setOnAction(event -> {System.exit(0);});
        endBt.setOpacity(0);
        root.getChildren().add(endBt);

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

    public static Scene erstelleHighcoreScene(Main classInstance, Function<Void, Void> backFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_bestzeiten.png");

        Font minecraftFont = Font.loadFont(classInstance.getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 40);

        Button backBt = new Button("Zurück");
        backBt.setPrefWidth(240);
        backBt.setPrefHeight(80);
        backBt.setLayoutX(980);
        backBt.setLayoutY(585);
        backBt.setOnAction(event -> {backFunction.apply(null);});
        backBt.setOpacity(1);
        root.getChildren().add(backBt);

        Text hsForest = new Text("forest");
        hsForest.setFill(Color.YELLOW);
        hsForest.setFont(minecraftFont);
        hsForest.setX(50);
        hsForest.setY(50);
        root.getChildren().add(hsForest);

        Text hsSnowyPlains = new Text("snowy-plains");
        hsSnowyPlains.setFill(Color.YELLOW);
        hsSnowyPlains.setFont(minecraftFont);
        hsSnowyPlains.setX(100);
        hsSnowyPlains.setY(100);
        root.getChildren().add(hsSnowyPlains);

        Text hsDesert = new Text("desert");
        hsDesert.setFill(Color.YELLOW);
        hsDesert.setFont(minecraftFont);
        hsDesert.setX(150);
        hsDesert.setY(150);
        root.getChildren().add(hsDesert);

        Text hsNetherWaste = new Text("netherWaste");
        hsNetherWaste.setFill(Color.YELLOW);
        hsNetherWaste.setFont(minecraftFont);
        hsNetherWaste.setX(200);
        hsNetherWaste.setY(200);
        root.getChildren().add(hsNetherWaste);

        Text hsCrimsonForest = new Text("CrimsonForest");
        hsCrimsonForest.setFill(Color.YELLOW);
        hsCrimsonForest.setFont(minecraftFont);
        hsCrimsonForest.setX(250);
        hsCrimsonForest.setY(250);
        root.getChildren().add(hsCrimsonForest);

        Text hsWarpedForest = new Text("WarpedForest");
        hsWarpedForest.setFill(Color.YELLOW);
        hsWarpedForest.setFont(minecraftFont);
        hsWarpedForest.setX(300);
        hsWarpedForest.setY(300);
        root.getChildren().add(hsWarpedForest);

        Text hsEndIsland = new Text("End Island");
        hsEndIsland.setFill(Color.YELLOW);
        hsEndIsland.setFont(minecraftFont);
        hsEndIsland.setX(350);
        hsEndIsland.setY(350);
        root.getChildren().add(hsEndIsland);

        Text hsEndCity = new Text("End City");
        hsEndCity.setFill(Color.YELLOW);
        hsEndCity.setFont(minecraftFont);
        hsEndCity.setX(400);
        hsEndCity.setY(400);
        root.getChildren().add(hsEndCity);

        Text hsDragonIsland = new Text("Dragon Island");
        hsDragonIsland.setFill(Color.YELLOW);
        hsDragonIsland.setFont(minecraftFont);
        hsDragonIsland.setX(450);
        hsDragonIsland.setY(450);
        root.getChildren().add(hsDragonIsland);

        return newScene;
    }

    public static Scene erstelleStoryScene(Main classInstance, Function<Void, Void> backFunction, Function<Void, Void> creditsFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_story_mc.png");

        Button backBt = new Button("Zurück");
        backBt.setPrefWidth(240);
        backBt.setPrefHeight(80);
        backBt.setLayoutX(980);
        backBt.setLayoutY(585);
        backBt.setOnAction(event -> {backFunction.apply(null);});
        backBt.setOpacity(1);
        root.getChildren().add(backBt);

        Button creditsBt = new Button("Credits");
        creditsBt.setPrefWidth(240);
        creditsBt.setPrefHeight(80);
        creditsBt.setLayoutX(980);
        creditsBt.setLayoutY(300);
        creditsBt.setOnAction(event -> {creditsFunction.apply(null);});
        creditsBt.setOpacity(1);
        root.getChildren().add(creditsBt);

        return newScene;
    }

    public static Scene erstelleWinScene(Main classInstance, Function<Void, Void> meunFunction, Function<Void, Void> restartFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/screen_win.png");

        Font minecraftFont = Font.loadFont(classInstance.getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 40);

        Button menuBt = new Button("Menu");
        menuBt.setPrefWidth(240);
        menuBt.setPrefHeight(80);
        menuBt.setLayoutX(980);
        menuBt.setLayoutY(585);
        menuBt.setOnAction(event -> {meunFunction.apply(null);});
        menuBt.setOpacity(1);
        root.getChildren().add(menuBt);

        Button restartBt = new Button("Restart");
        restartBt.setPrefWidth(240);
        restartBt.setPrefHeight(80);
        restartBt.setLayoutX(980);
        restartBt.setLayoutY(300);
        restartBt.setOnAction(event -> {restartFunction.apply(null);});
        restartBt.setOpacity(1);
        root.getChildren().add(restartBt);

        Text scoreText = new Text("Highscore");
        scoreText.setFont(minecraftFont);
        scoreText.setFill(Color.RED);
        scoreText.setX(100);
        scoreText.setY(100);
        root.getChildren().add(scoreText);

        return newScene;
    }

    public static Scene erstelleDeathScene(Main classInstance, Function<Void, Void> respawnFunction, Function<Void, Void> lvlAuswahlFunction, Function<Void, Void> menuFunction) {
        Pane root = new Pane();
        Scene newScene = createSceneBase(classInstance, root, "/death_screen.png");

        Button respawnBt = new Button("Respawn");
        respawnBt.setPrefWidth(240);
        respawnBt.setPrefHeight(80);
        respawnBt.setLayoutX(980);
        respawnBt.setLayoutY(185);
        respawnBt.setOnAction(event -> {respawnFunction.apply(null);});
        respawnBt.setOpacity(1);
        root.getChildren().add(respawnBt);

        Button lvlAuswwahlBt = new Button("Menu Auswahl");
        lvlAuswwahlBt.setPrefWidth(240);
        lvlAuswwahlBt.setPrefHeight(80);
        lvlAuswwahlBt.setLayoutX(980);
        lvlAuswwahlBt.setLayoutY(385);
        lvlAuswwahlBt.setOnAction(event -> {lvlAuswahlFunction.apply(null);});
        lvlAuswwahlBt.setOpacity(1);
        root.getChildren().add(lvlAuswwahlBt);

        Button menuBt = new Button("Menu");
        menuBt.setPrefWidth(240);
        menuBt.setPrefHeight(80);
        menuBt.setLayoutX(980);
        menuBt.setLayoutY(585);
        menuBt.setOnAction(event -> {menuFunction.apply(null);});
        menuBt.setOpacity(1);
        root.getChildren().add(menuBt);

        return newScene;
    }

}
