package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable{
    private String fileSeperator = File.separator;
    private FileDirector model = FileDirector.instance();
    private String _currentItem;

    @FXML
    private ListView<String> creationView;
    @FXML
    private ImageView previewBox;

    //Displays the preview of the current clicked on creation from the list
    private void displayPreview() {
        File imageFile = new File(System.getProperty("user.dir") + fileSeperator + "data" + fileSeperator + model.getCurrentItem() + fileSeperator + "thumbnail.jpg");
        Image image = new Image(imageFile.toURI().toString());
        previewBox.setImage(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        creationView.setItems(model.getList());
        //Adds listener for selection events
        creationView.getSelectionModel().selectedItemProperty().addListener((obj, oldCreation, newCreation) -> {
            model.setCurrentItem(newCreation);
            if (newCreation != null) {
                Main.togglePlayDeleteBtns(false);
                displayPreview();
            } else {
                previewBox.setImage(null);
            }

        });

        Main.toggleAllBtns(false);
        Main.togglePlayDeleteBtns(true);
    }


}
