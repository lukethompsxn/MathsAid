package CreationView;

import Model.FileDirector;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreationPopUp {
    private String _creationName;
    private FileDirector _director;


    public static String display(String name) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error - Duplicate Creation");
        window.setMinHeight(400);
        window.setMinWidth(600);

        Label label = new Label("This creation already exists. Please choose another name or click \"Overwrite\" to overwrite it");
        Button overwriteBtn = new Button("Overwrite");
        Button submitBtn = new Button("Submit");

      //  overwriteBtn.setOnAction(e -> _director.deleteDirectory(name));
        return "";
    }
}


