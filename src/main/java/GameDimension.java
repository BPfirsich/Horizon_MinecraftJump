import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameDimension {

    final private String f_dimensionName;
    private ArrayList<Gegner> _gegnerListe;
    private Spieler _spieler;
    private Pane _root;

    public GameDimension(String name, Pane root) {
        f_dimensionName = name;
        _root = root;

        _gegnerListe = new ArrayList<>();
    }

    public void updateDimension(float deltaTime, InputData inputData) {
        // Spieler Updaten
        _spieler.update(deltaTime, inputData);

        // Gegner Updaten
        for (Gegner g : _gegnerListe) {
            g.update(deltaTime);
        }
    }

    // Fügt einen gegner zur Dimension hinzu, und fügt dessen Rectangle zur scene hinzu
    public void addGegner(Gegner gegner) {
        _gegnerListe.add(gegner);
        _root.getChildren().add(gegner.getFigur());
    }

    // Fügt einen gegner zur Dimension hinzu, und fügt dessen Rectangle zur scene hinzu
    public void setSpieler(Spieler spieler) {
        _spieler = spieler;
        _root.getChildren().add(spieler.getFigur());
    }

}
