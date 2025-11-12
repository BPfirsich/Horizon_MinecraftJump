import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.Function;

public class Menu {

    static Scene erstelleMenuScene(Main classInstance, Function<Void, Void> loadLevel1,
                                   Function<Void, Void> toLevelScreen) {
        Pane root = new Pane();
        root.setPrefSize(1280, 720);
        Scene newScene = new Scene(root);

        BackgroundImage _homescreen_img = new BackgroundImage(
                new Image(classInstance.getClass().getResourceAsStream("/Homescreen_text.png")),
                BackgroundRepeat.NO_REPEAT, // Wiederholung horizontal
                BackgroundRepeat.NO_REPEAT, // Wiederholung vertikal
                BackgroundPosition.CENTER,  // Position
                new  BackgroundSize(1280, 720, false, false, true, true) // Größe
        );

        root.setBackground(new Background(_homescreen_img));

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

    static Scene erstelleLevelAuswahlScene(Main classInstance, Function<String, Void> loadFunction, Function<Void, Void> backFunction) {
        Pane root = new Pane();
        root.setPrefSize(1280, 720);
        Scene newScene = new Scene(root);

        BackgroundImage _levels_img = new BackgroundImage(
                new Image(classInstance.getClass().getResourceAsStream("/screen_lvl.png")),
                BackgroundRepeat.NO_REPEAT, // Wiederholung horizontal
                BackgroundRepeat.NO_REPEAT, // Wiederholung vertikal
                BackgroundPosition.CENTER,  // Position
                new  BackgroundSize(1280, 720, false, false, true, true) // Größe
        );
        root.setBackground(new Background(_levels_img));

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

}
