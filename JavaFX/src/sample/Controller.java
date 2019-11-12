package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Controller {

    @FXML
    private Button loadImage;
    @FXML
    private Button saveAnnotation;
    @FXML
    private Button saveAnnotatedImage;
    @FXML
    private Button newLabel;
    @FXML
    private Button modify;
    @FXML
    private Button delete;
    @FXML
    private TextField labelName;
    @FXML
    private ListView listOfLabels;
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private ImageView imageView;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Pane pane;
    @FXML
    private Button clearAnnotations;

    LinkedList<Annotation> annotations = new LinkedList<>();
    LinkedList<Label> labels = new LinkedList<>();
    LinkedList<Rectangle> rectangles = new LinkedList<>();

    // For annotation
    double firstClickX;
    double firstClickY;
    double dragInClickX;
    double dragInClickY;
    double finalClickX;
    double finalClickY;

    boolean drawNewLabel = false;


    /**
     * Load an image from the disk and set the imageView
     * @param actionEvent
     * @throws FileNotFoundException
     */
    public void loadImage(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        imageView.setImage(new Image(new FileInputStream(fileChooser.showOpenDialog(null))));
    }


    /**
     * Create a label and add it in the list of labels
     * @param actionEvent
     */
    public void createLabel(ActionEvent actionEvent) {
        String textLabel = labelName.getCharacters().toString();
        Color colorLabel = colorPicker.getValue();

        Label label = new Label(textLabel, colorLabel);

        this.labels.add(label);

        listOfLabels.getItems().add(label.getText());
    }


    public void onMousePressed(MouseEvent mouseEvent) {
        System.out.println("MousePressed");
        firstClickX = mouseEvent.getX();
        firstClickY = mouseEvent.getY();
    }


    public void onMouseDragged(MouseEvent mouseEvent) {
        System.out.println("MouseDragged");
        dragInClickX = mouseEvent.getX();
        dragInClickY = mouseEvent.getY();

        if (!rectangles.isEmpty() && drawNewLabel) {
            pane.getChildren().remove(rectangles.getLast());
            rectangles.removeLast();
        }

        drawNewLabel = true;
        Rectangle rectangle = new Rectangle(dragInClickX, dragInClickY);
        rectangle.setFill(null);
        rectangle.setStrokeWidth(5.0);
        rectangle.setX(firstClickX);
        rectangle.setY(firstClickY);
        rectangle.setStroke(labels.getLast().getColor());

        pane.getChildren().add(rectangle);

        rectangles.add(rectangle);

    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        System.out.println("MouseReleased");
        finalClickX = mouseEvent.getX();
        finalClickY = mouseEvent.getY();

        drawNewLabel = false;
    }

    public void clearAll(ActionEvent actionEvent) {
        for(Rectangle r : rectangles) {
            pane.getChildren().remove(r);
           // rectangles.remove(r);
        }
        rectangles.clear();
    }
}
