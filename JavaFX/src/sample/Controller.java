package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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

    LinkedList<Annotation> annotations = new LinkedList<>();
    LinkedList<Label> labels = new LinkedList<>();


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


    public void createLabel(ActionEvent actionEvent) {
        String textLabel = labelName.getCharacters().toString();
        Color colorLabel = colorPicker.getValue();

        Label label = new Label(textLabel, colorLabel);

        this.labels.add(label);

        listOfLabels.getItems().add(label.getText());
    }
}
