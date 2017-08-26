package Controller;

import Model.FileDirector;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class CreationController implements Initializable{
    private FileDirector model = FileDirector.instance();
    private String _currentItem;
    @FXML
    public ListView<String> creationView;
    @FXML
    public MediaView mediaViewer;
    @FXML
    public TextField inputText;
    @FXML
    public GridPane createPanel;
    @FXML
    public GridPane errorPane;
    @FXML
    public BorderPane pane = new BorderPane();
    @FXML
    public GridPane recordPane = new GridPane();
    @FXML
    public Button recordBtn = new Button("Record");
    @FXML
    public Label recordLabel = new Label("Press \"Record\" to begin recording");
    @FXML
    public Button playBtn;
    @FXML
    public Button createBtn;
    @FXML
    public Button deleteBtn;

    public void playCreation() {
        File video = new File(System.getProperty("user.dir") + "/data/Town_images.mp4");
        try {
            Media media = new Media(video.toURL().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaViewer.setMediaPlayer(mediaPlayer);
            //FIND A WAY TO AUTOCLOSE
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createCreation() {
        System.out.println(inputText.getCharacters().toString());
        boolean success = model.createDirectory(inputText.getCharacters().toString());
        System.out.println(inputText.getCharacters().toString());
        if (!success) {
            errorPane.setVisible(true);
        }
        else {
            creationView.getItems().add("KEYBOARD INPUT");
            createPanel.setVisible(false);
            pane.setLeft(recordPane);
            recordPane.add(recordLabel, 1, 1);
            recordPane.add(recordBtn,1,2);
            recordBtn.setOnAction(e -> recordAudio());
            recordPane.managedProperty().bind(recordPane.visibleProperty());
            recordPane.setVisible(true);

        }



    }

    public void recordAudio() {
        System.out.println("GGHG");
        //port into bash to use ffmpeg

    }
    public void openCreationPanel() {
        playBtn.setDisable(true);
        createBtn.setDisable(true);
        deleteBtn.setDisable(true);
        creationView.setVisible(false);
        createPanel.setVisible(true);
    }

    public void closeCreationPanel() {
        createPanel.setVisible(false);
        creationView.setVisible(true);
        playBtn.setDisable(false);
        createBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    public void deleteCreation() {
        model.deleteDirectory(_currentItem);
        creationView.getItems().remove(_currentItem);
    }

    public void overwriteCreation() {

    }

    public void currentSelection() {
        _currentItem = creationView.getSelectionModel().getSelectedItem();
    }


    public void populateList() {
        model.populateList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createPanel.managedProperty().bind(createPanel.visibleProperty());
        errorPane.managedProperty().bind(createPanel.visibleProperty());
        creationView.managedProperty().bind(creationView.visibleProperty());
        createPanel.setVisible(false);
        errorPane.setVisible(false);

        creationView.setItems(FXCollections.observableArrayList(model.getList())); //is this downs
    }
}
