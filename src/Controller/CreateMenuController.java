package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateMenuController implements Initializable{
    private FileDirector model = FileDirector.instance();
    private String fileSeperator = File.separator;

    @FXML
    public TextField inputText;

    //Action for "Submit" button, ensures no duplicate creation then sets pane to recording pane
    public void createCreation() {
        boolean success = model.createDirectory(inputText.getCharacters().toString());
        if (inputText.getCharacters().toString().isEmpty()) {
            emptyMessage();
        } else if  (!success) {
            overwriteMessage();
        } else {
            model.setCurrentItem(inputText.getCharacters().toString());
            model.addToList(inputText.getCharacters().toString());
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
            Parent root = FXMLLoader.load(getClass().getResource(fileSeperator + "View" + fileSeperator + name + ".fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Helper method for displaying the alert box for confirming overwriting the creation/entering a new name
    private void overwriteMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error: Creation Already Exists");
        alert.setContentText("This creation already exists, please select \"Overwrite\" to overwrite \"" + inputText.getCharacters().toString() + "\", or \"OK\" to enter a new name:");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        ButtonType overwrite = new ButtonType("Overwrite");
        ButtonType OK = new ButtonType("OK");
        alert.getButtonTypes().setAll(overwrite,OK);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == overwrite) {
            model.setCurrentItem(inputText.getCharacters().toString());
            setPane("RecordView");
        }
    }

    //Helper method for displaying an alert message when no input text is given
    private void emptyMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - You must enter a name");
        alert.setHeaderText("Error - You must enter a name");
        alert.setContentText("Please enter a name into the text box before clicking \"Submit\"");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Ensures only letters, numbers, underscores and hyphens are accepted. Also enforces character limit
        inputText.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!keyEvent.getCharacter().matches("^[a-zA-Z0-9_-]+")) {
                    keyEvent.consume();
                } else {
                    if (inputText.getCharacters().length() > 50) {
                        keyEvent.consume();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error - Character Limit Reached");
                        alert.setContentText("Maximum Number of Characters Reached");
                        alert.showAndWait();
                    }
                }
            }
        });
        //Sets text colour red if creation already exists, green otherwise
        inputText.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (model.getList().contains(inputText.getCharacters().toString())) {
                    inputText.setStyle(("-fx-text-fill: red"));
                } else {
                    inputText.setStyle(("-fx-text-fill: green"));
                }
            }
        });


    }
}

