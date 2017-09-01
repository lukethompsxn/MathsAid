package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreationHeaderController implements Initializable {
    private FileDirector model = FileDirector.instance();
    private String _fileSeperator = File.separator;

    @FXML
    private Button playBtn;
    @FXML
    private Button createBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.mainPane = mainPane;
        setPane("MainView", false);
        //disableBtns(true);
    }

    //Action for clicking the "Play" button, sets the pane to the play view
    public void playCreation() {
        setPane("PlayView", false);
    }

    //Action for clicking "Create" button, sets the pane to the create menu view
    public void createCreation() {
        setPane("CreateMenuView", true);
        //
    }

    //Action for clicking "Delete" button, launches pop up to confirm before deleting the creation
    public void deleteCreation() {
    	Alert pbAlert = new Alert(Alert.AlertType.CONFIRMATION);
        pbAlert.setTitle("Are you sure?");
        pbAlert.setHeaderText("Are you sure you want to delete \"" + model.getCurrentItem() + "\" ?");
        pbAlert.setContentText("Press \"Yes\" to delete, or \"No\" to cancel");
        pbAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        pbAlert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = pbAlert.showAndWait();
        if (result.get() == yes) {
        	model.deleteDirectory();
        }
        
    }

    //Helper method for setting the pane, takes the desired pane and a boolean for disabling buttons as arguments
    private void setPane(String name, Boolean bool) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(_fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Parent root = loader.load(); //needs to have a different controller because initialise is causing an infinite loop.
            Main.mainPane.getChildren().setAll(root);
            /*
            if  (name.equals("MainView")) {
                MainViewController controller = loader.<MainViewController>getController();
                controller.setPlayButton(playBtn, deleteBtn);
            }
            */
        } catch (IOException e) {
            e.printStackTrace();
        }
        //disableBtns(bool);
    }

    //Helper method for disabling the buttons, takes a boolean as argument
    private void disableAllBtns(boolean bool) {
        playBtn.setDisable(bool);
        createBtn.setDisable(bool);
        deleteBtn.setDisable(bool);
    }

    private void disableBtns(Boolean bool) {
        playBtn.setDisable(bool);
        deleteBtn.setDisable(bool);
    }

}
