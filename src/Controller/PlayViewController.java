package Controller;

import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayViewController implements Initializable {
    Pane _mainPane;
    String _fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();
    @FXML
    public MediaView mediaView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File video = new File(System.getProperty("user.dir") + _fileSeperator + "data" + _fileSeperator + model.getCurrentItem() + _fileSeperator + "video.mp4");
        try {
            Media media = new Media(video.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnEndOfMedia(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../View/MainView.fxml"));
                    //Pane parentPane = findParentPaneForNode(mediaView);
                    _mainPane.getChildren().setAll(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        //CREATE STATIC METHOD AND OBJECT IN MAIN PASS IN PANE IN CONTRSUTVOR

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

    public void setParentPane(Pane pane) {
        _mainPane = pane;
    }
}
