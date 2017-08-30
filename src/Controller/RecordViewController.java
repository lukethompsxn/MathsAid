package Controller;

import Model.FileDirector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class RecordViewController {
    String _fileSeperator = File.separator;
    FileDirector model = FileDirector.instance();
    @FXML
    public Button recordBtn;
    @FXML
    public Button cancelBtn;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public HBox mainHBOX;

    public void recordAudio() {    //NEEDS MULTITHREADING
        System.out.println("RECORDING SHIT");
    }

    public void cancel() {
        setPane("MainView");
    }

    private void setPane (String name){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(".." + _fileSeperator + "View" + _fileSeperator + name + ".fxml"));
            Pane parentPane = findParentPaneForNode(mainHBOX);
            parentPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
