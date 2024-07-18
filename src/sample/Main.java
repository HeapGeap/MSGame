package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {
    public static Stage prim;
    public static Parent root;
    public static Scene MainScene;
    public static URL MainSceneUrl;

    @Override
    public void start(Stage primaryStage) throws Exception {
        prim = primaryStage;
        MainSceneUrl = new File("src/sample/sample.fxml").toURI().toURL();
        root = FXMLLoader.load(MainSceneUrl);
        MainScene = new Scene(root);
        prim.setTitle("Tic'A'Boom");
        prim.setScene(MainScene);
        prim.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}