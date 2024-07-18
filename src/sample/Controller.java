package sample;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button NewGameButton;

    @FXML
    private Button ExitButton;
    @FXML
    private Scene NewGameScene;

    @FXML
    void initialize () throws Exception {
        URL url = new File("src/GameStart/NG.fxml").toURI().toURL();
        Parent NewGameRoot = FXMLLoader.load(url);
        NewGameScene = new Scene(NewGameRoot);
        Stage mainStage = Main.prim;

        NewGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainStage.setScene(NewGameScene);
                mainStage.show();
            }
        });


        ExitButton.setOnAction(event -> {
            Stage stage = (Stage) ExitButton.getScene().getWindow();
            stage.close();
        });
    }
}
