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
    String fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();
    
    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Sets the video the play in the media player
        if (model.getCurrentItem() != null && !model.getCurrentItem().isEmpty()) {
            File video = new File(System.getProperty("user.dir") + fileSeperator + "data" + fileSeperator + model.getCurrentItem() + fileSeperator + "combinedVideo.mp4");
            try {
                Media media = new Media(video.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setOnEndOfMedia(() -> {
                	mediaPlayer.dispose();
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
            Parent root = FXMLLoader.load(getClass().getResource(fileSeperator + "View" + fileSeperator + "MainView.fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
