import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bossbar {

    public static final int BOSSBAR_X = 240, BOSSBAR_Y = 50;
    public static final int BOSSBAR_LENGTH = 800, BOSSBAR_HEIGHT = 20;

    private Rectangle _bossbarFill;
    private Rectangle _bossbarBackground;

    private Text _bossbarText;

    private String _displayName;

    private final float _maxLeben;
    private final Font _font;
    private final Color _color;

    public Bossbar(float maxLeben, String displayName, Color color) {
        _displayName = displayName;
        _maxLeben = maxLeben;
        _color = color;

        _font = Font.loadFont(getClass().getResourceAsStream("/minecraft-ten-font/MinecraftTen-VGORe.ttf"), 20);
    }

    public void erstelleBossbar(Pane root) {
        _bossbarFill = new Rectangle(BOSSBAR_X, BOSSBAR_Y, BOSSBAR_LENGTH, BOSSBAR_HEIGHT);
        _bossbarFill.setFill(_color);

        _bossbarBackground = new Rectangle(BOSSBAR_X-3, BOSSBAR_Y -3, BOSSBAR_LENGTH+6, BOSSBAR_HEIGHT+6);
        _bossbarBackground.setFill(Color.BLACK);

        _bossbarText = new Text(_displayName + (int)_maxLeben);
        _bossbarText.setFont(_font);
        _bossbarText.setFill(Color.WHITE);

        updateBossbar(_maxLeben);

        // Add to the scene
        root.getChildren().addAll(_bossbarBackground, _bossbarFill, _bossbarText);
    }

    public void removeBossbar(Pane root) {
        root.getChildren().removeAll(_bossbarBackground, _bossbarFill, _bossbarText);
    }

    public void updateBossbar(float displayHealth) {
        if (_bossbarText == null) return;

        _bossbarFill.setWidth((displayHealth / _maxLeben) * BOSSBAR_LENGTH);
        _bossbarText.setText(_displayName + " - " + (int)displayHealth + "HP");
        updateTextPosToCenter();
    }

    private void updateTextPosToCenter() {
        _bossbarText.setX(BOSSBAR_X + (int)(BOSSBAR_LENGTH / 2) - (int)(_bossbarText.getLayoutBounds().getWidth() / 2));
        _bossbarText.setY(BOSSBAR_Y + BOSSBAR_HEIGHT/1.25 + 1);
    }

}
