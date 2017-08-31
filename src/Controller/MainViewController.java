package Controller;

import Model.FileDirector;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable{
    private String _fileSeperator = File.separator;
    private FileDirector model = FileDirector.instance();
    private String _currentItem;
    @FXML
    private ListView<String> creationView;
    @FXML
    private ImageView previewBox;

    //Sets the current clicked on creation to "currentItem" in FileDirector and displays the preview for the creation
    public void currentSelection() {
        _currentItem = creationView.getSelectionModel().getSelectedItem();
        model.setCurrentItem(_currentItem);
        displayPreview();
    }

    //Displays the preview of the current clicked on creation from the list
    private void displayPreview() {
        File imageFile = new File(System.getProperty("user.dir") + _fileSeperator + "data" + _fileSeperator + _currentItem + _fileSeperator + "thumbnail.jpg");
        Image image = new Image(imageFile.toURI().toString());
        previewBox.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        creationView.setItems(FXCollections.observableArrayList(model.getList()));
    }

}