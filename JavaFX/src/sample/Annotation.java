package sample;

import javafx.scene.paint.Color;

import java.awt.*;

public class Annotation {
    private Label label;
    private Rectangle rectangle;

    public Annotation(Label label, Rectangle rectangle) {
        this.label = label;
        this.rectangle = rectangle;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
