package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Maths Learning Aid");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }


    public static void main(String[] args) {
        FileDirector director = new FileDirector();
        director.directoryTest();
        launch(args);


    }
}
