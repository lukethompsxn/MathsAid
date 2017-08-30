package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class CreateMenuController {
    private FileDirector model = FileDirector.instance();
    private String _fileSeperator = File.separator;

    @FXML
    public TextField inputText;

    //Action for "Submit" button, ensures no duplicate creation then sets pane to recording pane
    public void createCreation() {
        boolean success = model.createDirectory(inputText.getCharacters().toString());
        if (!success) {
            overwriteMessage();
        } else {
            setPane("RecordView");
        }
    }

    //Action for "Cancel" button, returns to main menu
    public void cancelCreation() throws IOException {
        setPane("MainView");
    }

    //Helper method for setting the pane, takes the desired pane as an argument
    private void setPane (String name){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".." + _fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Helper method for displaying the alert box for conforming overwriting the creation/entering a new name
    private void overwriteMessage() {
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
    }

}

