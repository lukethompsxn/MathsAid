package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayViewController implements Initializable {
    String _fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();

    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model.getCurrentItem() != null && !model.getCurrentItem().isEmpty()) {
            File video = new File(System.getProperty("user.dir") + _fileSeperator + "data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "combinedVideo.mp4");
            try {
                Media media = new Media(video.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setOnEndOfMedia(() -> {
                    setPane();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setPane();
    }

    //Helper method for setting the pane to "MainView" thus returning to the main menu
    private void setPane() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(_fileSeperator + "View" + _fileSeperator + "MainView.fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
