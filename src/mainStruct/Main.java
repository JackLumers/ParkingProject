package mainStruct;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/autorization.fxml"));
        primaryStage.setTitle("Parking System");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.setMinHeight(Constants.AUTORIZATION_WINDOW_HEIGHT);
        primaryStage.setMinWidth(Constants.AUTORIZATION_WINDOW_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }



}
