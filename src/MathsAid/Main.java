package MathsAid;

import Model.FileDirector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage _primaryStage;
    public static Pane mainPane;

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/CreationHeader.fxml"));
        primaryStage.setTitle("Maths Learning Aid");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void setPane(String name) {

    }

    //public static void pushScene(Scene scene) {
       // _primaryStage.setScene(scene);                    //REDUNDANT
    //}





    public static void main(String[] args) {
        FileDirector director = FileDirector.instance();
        launch(args);


    }
}
