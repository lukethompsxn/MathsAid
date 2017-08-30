package Controller;

import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class CreateMenuController {
    @FXML
    public TextField inputText;
    private FileDirector model = FileDirector.instance();
    private String _fileSeperator = File.separator;
    @FXML
    public Button overwriteBtn;
    @FXML
    public Label errorLbl;
    @FXML
    public Label enterLbl;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public AnchorPane recordingPane;
    @FXML
    public GridPane createPanel;
    @FXML
    public BorderPane pane;


    public void createCreation() {
        boolean success = model.createDirectory(inputText.getCharacters().toString());
        if (!success) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error: Creation Already Exists");
            alert.setContentText("This creation already exists, please select \"Overwrite\" to overwrite the creation, or \"OK\" to enter a new name:");
            ButtonType overwrite = new ButtonType("Overwrite");
            ButtonType OK = new ButtonType("OK");
            alert.getButtonTypes().setAll(overwrite,OK);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == overwrite) {
                model.setCurrentItem(inputText.getCharacters().toString());
                model.deleteDirectory();
                setPane("RecordView");
            }
        } else {
            setPane("RecordView");

            //creationView.getItems().add("KEYBOARD INPUT");
        }
    }

    public void recordAudio() {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", "CMD"); //change CMD to ffmpeg recording comand
    }

    private void setPane (String name){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".." + _fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Pane parentPane = findParentPaneForNode(pane);
            parentPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void cancelCreation() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainView.fxml"));
        //Scene scene = new Scene(root, 600,400);
        //Node node = pane.getScene().lookup("mainPane");// not work

        Pane parentPane = findParentPaneForNode(pane);
        parentPane.getChildren().setAll(root);

        // Main.pushScene(scene);
        //errorMessage(false);
    }

    private Pane findParentPaneForNode(Node node) {
        Pane parentPane = null ;

        for (Node n = node.getParent(); n != null && parentPane == null; n = n.getParent()) {
            if (n instanceof Pane) {
                parentPane = (Pane) n;
            }
        }

        return parentPane ;
    }
}

