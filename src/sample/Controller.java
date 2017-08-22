package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


public class Controller implements Initializable{
    private List<String> _listOfFiles = FXCollections.observableArrayList();
    private FileDirector director = new FileDirector();
    private String _currentItem;

    @FXML
    public ListView<String> creationView;
    @FXML
    public MediaView mediaViewer;
    //public Set<String> items;
    //ObservableList observableList = new FXCollections().observableArrayList();
    //bev

    public void playCreation() {
        String source = System.getProperty("user.dir") + "/data/Town_images.mp4";
        System.out.println(source);
        Media media = new Media(source);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaViewer.setMediaPlayer(mediaPlayer);

    }

    public void createCreation() {
        boolean success = director.createDirectory("KEYBOARD INPUT");
        while (!success) {
            System.out.println("duplicate");
            //PROMPT USER OF DUPLICATE
            success = director.createDirectory("KEYBOARD INPUT");
        }
        creationView.getItems().add("KEYBOARD INPUT");

        //port into bash to use ffmpeg

    }

    public void deleteCreation() {
        director.deleteDirectory(_currentItem);
        creationView.getItems().remove(_currentItem);
    }

    public void currentSelection() {
        _currentItem = creationView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _listOfFiles = director.populateList();
        creationView.setItems(FXCollections.observableArrayList(_listOfFiles));






    }
}
