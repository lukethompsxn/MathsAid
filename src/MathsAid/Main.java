package MathsAid;

import Model.FileDirector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static Pane mainPane;
    private static Button playBtn;
    private static Button createBtn;
    private static Button deleteBtn;

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/CreationHeader.fxml"));
        primaryStage.setTitle("Maths Learning Aid");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        FileDirector director = FileDirector.instance();
        launch(args);
    }

    //Used for toggling the buttons to disable, called from multiple places across the gui (play/delete btns)
    public static void togglePlayDeleteBtns(Boolean bool) {
        playBtn.setDisable(bool);
        deleteBtn.setDisable(bool);
    }

    //Used for toggling the buttons to disable, called from multiple places across the gui (all btns)
    public static void toggleAllBtns(Boolean bool) {
        playBtn.setDisable(bool);
        createBtn.setDisable(bool);
        deleteBtn.setDisable(bool);
    }

    //Used for setting the buttons, called from the intialize method in CreationHeaderController
    public static void setBtns(Button play, Button create, Button delete) {
        playBtn = play;
        createBtn = create;
        deleteBtn = delete;
    }
}
