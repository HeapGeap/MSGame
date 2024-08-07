package GameStart;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import MineSet.Mines;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;

public class BoardController {
    Button b;

    @FXML
    public GridPane TheBoard;

    @FXML
    public Label winLose;

    @FXML
    private Button BackMenu;

    @FXML
    private Scene NewGameScene;
    @FXML
    void initialize() throws IOException {
        URL url = new File("src/GameStart/NG.fxml").toURI().toURL();
        Parent NewGameRoot = FXMLLoader.load(url);
        NewGameScene = new Scene(NewGameRoot);
        Stage mainStage = Main.prim;
        BackMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainStage.setScene(NewGameScene);
                mainStage.show();
            }
        });

    }

    @FXML
    void ResetBoard(ActionEvent event) throws IOException {



        NewGameController.game = new Mines(NewGameController.rows, NewGameController.columns, NewGameController.mines);

        FXMLLoader loader = new FXMLLoader(); // Create loading object
        loader.setLocation(getClass().getResource("Board.fxml")); // fxml location

        AnchorPane root = loader.load(); // Load layout
        Scene scene = new Scene(root); // Create scene with chosen layout
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        gameStage.setTitle("BOOM"); // Set stage's title

        gameStage.setScene(scene); // Set scene to stage

        BoardController bCont = loader.getController(); // Prepare board in BoardCONTROLLER

        bCont.winLose.setVisible(false);

        for (int i = 0; i < NewGameController.columns; i++) {
            for (int j = 0; j < NewGameController.rows; j++) {
                b = new Button(" ");
                b.setMinSize(30, 30);
                b.setMaxSize(30, 30);
                b.setStyle("-fx-font-size:11");
                bCont.TheBoard.add(b, i, j);
                GridPane.setMargin(b, new Insets(5, 5, 5, 5));
                GridPane.setHalignment(b, HPos.CENTER);
                GridPane.setValignment(b, VPos.CENTER);
            }
        }
        for (int i = 0; i < bCont.TheBoard.getChildren().size(); i++) {
            ((ButtonBase) bCont.TheBoard.getChildren().get(i)).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int x, y;
                    y = (int) ((Button) event.getSource()).getProperties().get("gridpane-column");
                    x = (int) ((Button) event.getSource()).getProperties().get("gridpane-row");
                    if (event.getButton().equals(MouseButton.PRIMARY))
                        NewGameController.game.open(x, y);
                    else if (event.getButton().equals(MouseButton.SECONDARY))
                        NewGameController.game.toggleFlag(x, y);
                    else if (event.getButton().equals(MouseButton.MIDDLE))
                        NewGameController.game.toggleQM(x, y);
                    for (Node child : bCont.TheBoard.getChildren()) {
                        int j = (int) ((Button) child).getProperties().get("gridpane-column");
                        int i = (int) ((Button) child).getProperties().get("gridpane-row");
                        if (NewGameController.game.board[i][j].charAt(1) == 'T') {
                            ((Button) child).setText(NewGameController.game.board[i][j].substring(2, 3));
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'F' && NewGameController.game.board[i][j].charAt(0) == 'D')
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/GtnDxdQ/flag.png)");
                        if (NewGameController.game.board[i][j].charAt(1) == 'F' && NewGameController.game.board[i][j].charAt(0) == '?')
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/fnw3Hr4/QM.jpg)");
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == 'E') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/txvBhCS/empty.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == 'M') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/6Jq9S3g/mine.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == 'B') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/wgRt8v9/boom.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '1') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/5Gd2H0Y/1.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '2') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/GHGj0KM/2.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '3') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/sww9b1n/3.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '4') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/nrTjpqp/4.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '5') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/gZ6Rjrz/5.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '6') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/wh9XnfT/6.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '7') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/k1905rT/7.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (NewGameController.game.board[i][j].charAt(1) == 'T' && NewGameController.game.board[i][j].charAt(2) == '8') {
                            ((Button) child).setStyle(
                                    "-fx-background-image: url(https://i.ibb.co/RCs2s2H/8.jpg)");
                            ((Button) child).setText(" ");
                        }
                        if (Mines.winLose==1) {
                            bCont.winLose.setVisible(true);
                            bCont.winLose.setText("YOU WIN!");
                        }
                        if (Mines.winLose==0) {
                            bCont.winLose.setVisible(true);
                            bCont.winLose.setText("YOU LOSE!");
                        }
                        Mines.winLose=-1;
                    }
                }
            });
        }
        gameStage.show(); // Show stage }
    }
}
