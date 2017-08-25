package Controller;

import Model.FileDirector;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;


public class CreationController implements Initializable{
    private FileDirector model = FileDirector.instance();
    private String _currentItem;
    @FXML
    public ListView<String> creationView;
    @FXML
    public MediaView mediaViewer;

    public void playCreation() {
        System.out.println("print");
        /*
        String source = System.getProperty("user.dir") + "/data/Town_images.mp4";
        System.out.println(source);
        Media media = new Media(source);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaViewer.setMediaPlayer(mediaPlayer);
        */
    }

    public void createCreation() {
        boolean success = model.createDirectory("KEYBOARD INPUT");
        while (!success) {
            System.out.println("duplicate");
            //PROMPT USER OF DUPLICATE
            success = model.createDirectory("KEYBOARD INPUT");
        }
        creationView.getItems().add("KEYBOARD INPUT");


        //port into bash to use ffmpeg

    }

    public void deleteCreation() {
        model.deleteDirectory(_currentItem);
        creationView.getItems().remove(_currentItem);
    }

    public void currentSelection() {
        _currentItem = creationView.getSelectionModel().getSelectedItem();
    }


    public void populateList() {
        model.populateList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creationView.setItems(FXCollections.observableArrayList(model.getList())); //is this downs
    }
}
