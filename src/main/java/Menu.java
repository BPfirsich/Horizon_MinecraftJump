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

    static Scene erstelleMenuScene(Main classInstance, Function<Void, Void> loadLevel1) {
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
        root.getChildren().add(startGameButton);

        return newScene;
    }

}
