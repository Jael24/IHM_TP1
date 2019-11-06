package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import javax.swing.text.html.ListView;

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
    private Button  delete;
    @FXML
    private TextField labelName;
    @FXML
    private ListView listOfLabels;
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private ImageView image;

}
