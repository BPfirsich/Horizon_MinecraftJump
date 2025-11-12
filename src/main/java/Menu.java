import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu {

    static Scene erstelleMenuScene(Main classInstance) {
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

        return newScene;
    }

}
