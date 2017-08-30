package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable{
    private Pane _mainPane;
    private String _fileSeperator = File.separator;
    private Main main;
    private FileDirector model = FileDirector.instance();
    private String _currentItem;
    @FXML
    public ListView<String> creationView;
    @FXML
    public ImageView previewBox;



    public void recordAudio() {
        System.out.println("GGHG");
        //port into bash to use ffmpeg

    }


    public void deleteCreation() {
        model.deleteDirectory();
        creationView.getItems().remove(_currentItem);
    }

    public void overwriteCreation() {

    }

    public void currentSelection() {
        _currentItem = creationView.getSelectionModel().getSelectedItem();
        model.setCurrentItem(_currentItem);
        displayPreview();
    }


    public void populateList() {
        model.populateList();
    }

    private void displayPreview() {
        System.out.println(System.getProperty("user.dir") + _fileSeperator + "data" + _fileSeperator + _currentItem + _fileSeperator + "thumbnail.jpg");
        File imageFile = new File(System.getProperty("user.dir") + _fileSeperator + "data" + _fileSeperator + _currentItem + _fileSeperator + "thumbnail.jpg");
        System.out.println(imageFile.toURI().toString());
        Image image = new Image(imageFile.toURI().toString());

        previewBox.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        creationView.setItems(FXCollections.observableArrayList(model.getList())); //is this downs
    }

    public void setParentPane(Pane pane) {
        _mainPane = pane;
    }
}
