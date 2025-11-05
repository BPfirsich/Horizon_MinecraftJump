import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MatchLeben {
    public static final int HERZ_SIZE_PX = 35;
    public static final int MAX_HERZEN = 5;

    public static final int HERZ_PADDING = 20;
    public static final int HERZ_ABSTAND = 5;

    private Image _fullHerzImage;
    private Image _empyHerzImage;

    private ArrayList<ImageView> herzenViews;

    public int herzen;

    public MatchLeben(int h) {

        herzenViews = new ArrayList<>();

        _fullHerzImage = new Image(getClass().getResourceAsStream("HerzFull.png"));
        _empyHerzImage = new Image(getClass().getResourceAsStream("HerzEmpy.png"));

        herzen = h;
    }


    public void erstelleHerzen(Pane root) {
        herzenViews.clear();

        int xOffset = 1280 - (MAX_HERZEN*HERZ_SIZE_PX + MAX_HERZEN*HERZ_ABSTAND) - HERZ_PADDING;
        for (int i = 0; i < MAX_HERZEN; i++) {
            herzenViews.add(erstelleHerzView(xOffset + i * (HERZ_ABSTAND + HERZ_SIZE_PX)));
        }

        root.getChildren().addAll(herzenViews);
    }

    public void clearHerzen(Pane root) {
        root.getChildren().removeAll(herzenViews);
    }

    public void updateHerzen() {
        for (int i = 0; i < MAX_HERZEN; i++) {
            if (herzen >= i+1) {
                herzenViews.get(i).setImage(_fullHerzImage);
            }
            else {
                herzenViews.get(i).setImage(_empyHerzImage);
            }
        }
    }

    private ImageView erstelleHerzView(int xPos) {
        ImageView v = new ImageView();
        v.setX(xPos);
        v.setY(HERZ_PADDING);
        v.setFitHeight(HERZ_SIZE_PX);
        v.setFitWidth(HERZ_SIZE_PX);
        v.setImage(_fullHerzImage);

        return v;
    }
}
