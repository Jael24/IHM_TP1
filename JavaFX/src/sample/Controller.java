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

import java.io.*;
import java.util.LinkedList;
import java.util.Optional;

public class Controller {

    @FXML
    private Button loadImage;
    @FXML
    private Button saveAnnotation;
    @FXML
    private Button delete;
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
    boolean saved = true;
    boolean imageLoaded = false;

    Annotation current = null;
    int currentAnnotId = 0;

    private String imageURL;


    /**
     * Load an image from the disk and set the imageView
     * @param actionEvent
     * @throws FileNotFoundException
     */
    public void loadImage(ActionEvent actionEvent) throws FileNotFoundException {
        delete.setDisable(true);

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(null);

        if(file != null) {
            imageURL = file.getAbsolutePath();
            Optional<ButtonType> result = null;
            if(!saved) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Current work not saved");
                alert.setHeaderText("Your current work will be lost if you continue");
                alert.setContentText("Do you want to continue ?");

                result = alert.showAndWait();
            }
            if (saved || result.get() == ButtonType.OK){
                imageView.setImage(new Image(new FileInputStream(file)));
                if(imageLoaded) {
                    clearAnnotations(false);
                }
                imageLoaded = true;
            }
        }
    }

    public void saveImage(ActionEvent actionEvent) throws FileNotFoundException {
        if(annotations.size() > 0) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text files (txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extensionFilter);

            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println("Image Path, Image Size, Label, Color, Coordinates");
                for (Annotation a : annotations) {
                    writer.println(this.imageURL + ", "
                            + "(" + imageView.getFitWidth() + ";" + imageView.getFitHeight() + "), "
                            + a.getLabel().getText() + ", "
                            + a.getLabel().getColor().toString() + ", "
                            + "(" + a.getRectangle().getX() + ";" + a.getRectangle().getY() + ";" + a.getRectangle().getHeight() + ";" + a.getRectangle().getWidth() + ")");
                }
                writer.close();
                saved = true;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saving");
            alert.setHeaderText(null);
            alert.setContentText("No annotation to save !");

            alert.showAndWait();
        }
    }

    private void clearAnnotations(boolean showAlert) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting ...");
        alert.setHeaderText("You are going to delete ALL labels");
        alert.setContentText("Do you want to continue ?");
        Optional<ButtonType> result = null;
        if(showAlert) {
            result = alert.showAndWait();
        }

        if (!showAlert || result.get() == ButtonType.OK){
            for(Rectangle r : rectangles) {
                pane.getChildren().remove(r);
            }
            rectangles.clear();
            labels.clear();
            annotations.clear();
            listOfLabels.getItems().clear();
        }
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        if(imageLoaded) {
            System.out.println("MousePressed");
            firstClickX = mouseEvent.getX();
            firstClickY = mouseEvent.getY();
        }
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        if(imageLoaded) {
            System.out.println("MouseDragged");
            dragInClickX = mouseEvent.getX();
            dragInClickY = mouseEvent.getY();

            if (!rectangles.isEmpty() && drawNewLabel) {
                pane.getChildren().remove(rectangles.getLast());
                rectangles.removeLast();
            }

            drawNewLabel = true;
            Rectangle rectangle = new Rectangle(dragInClickX - firstClickX, dragInClickY - firstClickY);
            rectangle.setFill(null);
            rectangle.setStrokeWidth(5.0);
            rectangle.setX(firstClickX);
            rectangle.setY(firstClickY);
            rectangle.setStroke(colorPicker.getValue());

            pane.getChildren().add(rectangle);

            rectangles.add(rectangle);
            saved = false;
        }
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        if(imageLoaded) {
            System.out.println("MouseReleased");
            finalClickX = mouseEvent.getX();
            finalClickY = mouseEvent.getY();

            drawNewLabel = false;

            newAnnotation(rectangles.getLast());
        }
    }

    private void newAnnotation(Rectangle last) {
        TextInputDialog dialog = new TextInputDialog("label value");
        dialog.setTitle("New label");
        dialog.setHeaderText("Please enter a value for the label");
        dialog.setContentText("Label name :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Label label = new Label(result.get(), colorPicker.getValue());
            listOfLabels.getItems().add(label.getText());
            annotations.add(new Annotation(label, last));
            saved = false;
        } else {
            pane.getChildren().remove(last);
            rectangles.remove(last);
        }
    }

    public void clearAll(ActionEvent actionEvent) {
        clearAnnotations(true);
    }

    public void selectAnnotation(MouseEvent mouseEvent) {
        currentAnnotId = listOfLabels.getSelectionModel().getSelectedIndex();
        current = annotations.get(currentAnnotId);
        delete.setDisable(false);
    }

    public void deleteAnnotation(ActionEvent actionEvent) {
        if(current != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting ...");
            alert.setHeaderText("You are going to delete label : " + current.getLabel().getText());
            alert.setContentText("Do you want to continue ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                pane.getChildren().remove(current.getRectangle());
                rectangles.remove(current.getRectangle());
                listOfLabels.getItems().remove(currentAnnotId);
                annotations.remove(current);
            }
            current = null;
            delete.setDisable(true);
        }
    }
}
