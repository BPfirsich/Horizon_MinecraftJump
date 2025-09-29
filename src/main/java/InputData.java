import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

// Die Klasse erhält die Input-Daten von JavaFX. Wenn das Update der Klasse gecallt wird,
// dann setzt die Klasse die entsprechnde game taste auf true. Je nach tastenverhalten wird dann die
// dann Taste in nächsten frame schon wieder auf false gesetzt (bsp. Springen). Links/Rechts tasten bleiben aber
// solange false, bis JavaFX den Befehl sendet, dass diese losgelasssen wurde.
public class InputData {
    private boolean _tasteRechts;
    private boolean _tasteLinks;
    private boolean _springenTaste;
    private boolean _duckenTaste;       // neu
    private boolean _tasteSchuss;

    private boolean _gameTasteRechts;
    private boolean _gameTasteLinks;
    private boolean _gameTasteSpringen;
    private boolean _gameTasteDucken;   // neu


    public void initInputSystemOnScene(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) _springenTaste = true;
            if (e.getCode() == KeyCode.A) _tasteLinks = true;
            if (e.getCode() == KeyCode.D) _tasteRechts = true;
            if (e.getCode() == KeyCode.S) _duckenTaste = true;       // neu
               });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) _tasteLinks = false;
            if (e.getCode() == KeyCode.D) _tasteRechts = false;
            if (e.getCode() == KeyCode.S) _duckenTaste = false;      // neu

        });
        scene.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown()) _tasteSchuss = true;
        });
        scene.setOnMouseReleased(e -> {
            _tasteSchuss = false;
        });
    }

    public void inputSystemUpdate() {
        // Links/Rechts/Ducken wie gehabt
        _gameTasteLinks = _tasteLinks;
        _gameTasteRechts = _tasteRechts;
        _gameTasteDucken = _duckenTaste;



        // Springen wie bisher
        if(_springenTaste && _gameTasteSpringen) {
            _springenTaste = false;
            _gameTasteSpringen = false;
        }
        if(_springenTaste) {
            _gameTasteSpringen = true;
        }
    }


    // Die Getter übergeben NUR die Gametasten. Die internen JavaFX verlassen die Klasse NICHT.
    public boolean isTasteRechts() {
        return _gameTasteRechts;
    }
    public boolean isTasteLinks() {
        return _gameTasteLinks;
    }
    public boolean isTasteSpringen() {
        return _gameTasteSpringen;
    }
    public boolean isTasteDucken() {           // neu
        return _gameTasteDucken;
    }

    public boolean isTasteSchuss() {
        return _tasteSchuss;
    }
}
