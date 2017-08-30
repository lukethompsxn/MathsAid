package Controller;

import MathsAid.Main;
import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.IOException;

public class RecordViewController {
    String _fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();

    @FXML
    private Button recordBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private ProgressBar progressBar;

    //Action for "Record" button, loads process into bash and uses ffmpeg to record audio and generate video
    public void recordAudio() {    //NEEDS MULTITHREADING
        System.out.println("RECORDING SHIT");
    }

    //Action for the "Cancel" button, returns to main menu
    public void cancel() {
        setPane("MainView");
    }

    //Helper method for setting the pane, takes the pane name as an argument
    private void setPane (String name){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".." + _fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Main.mainPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
