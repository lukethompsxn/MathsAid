package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreationHeaderController implements Initializable {
    private FileDirector model = FileDirector.instance();
    @FXML
    public Button playBtn;
    @FXML
    public Button createBtn;
    @FXML
    public Button deleteBtn;
    @FXML
    public Pane mainPane;

    private String _fileSeperator = File.separator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.mainPane = mainPane;
        setPane("MainView", false);

    }

    public void playCreation() {
        setPane("PlayView", false);
    }



    public void createCreation() {
        setPane("CreateMenuView", true);
        //
    }

    public void deleteCreation() {
        model.deleteDirectory();

    }

    private void setPane(String name, Boolean bool) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + _fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Parent root = loader.load(); //needs to have a different controller because initialise is causing an infinite loop.
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //disableBtns(bool);
    }

    private void disableBtns(boolean bool) {
        playBtn.setDisable(bool);
        createBtn.setDisable(bool);
        deleteBtn.setDisable(bool);
    }

}
