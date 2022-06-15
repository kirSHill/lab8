package client.gui.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {

    private TranslateTransition translate;

    public Shake(Node node) {
        translate = new TranslateTransition(Duration.millis(100), node);
        translate.setFromX(-9f);
        translate.setByX(10f);
        translate.setCycleCount(3);
        translate.setAutoReverse(true);
    }

    public void animate() {
        translate.playFromStart();
    }
}
