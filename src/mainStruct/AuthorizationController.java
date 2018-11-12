package mainStruct;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label invalidDataLabel;
    @FXML
    private TextField passwordTextField;
    @FXML
    private JFXButton enteringButton;

    private Scene homePageScene;

    @FXML
    private void initialize() throws IOException {
        // Предзагрузка страницы
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/layout/mainWindow.fxml"));
        homePageScene = new Scene(home_page_parent);

        // Установка слушателя на кнопку
        enteringButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleEnteringButton();
            }
        });
    }

    /** Handling entering button click **/
    @FXML
    private void handleEnteringButton() {
        if(isValidCredentials()) {
            Stage appStage = (Stage)enteringButton.getScene().getWindow();
            appStage.hide();
            appStage.setScene(homePageScene);
            appStage.setMinHeight(Constants.MIN_WINDOW_HEIGHT);
            appStage.setMinWidth(Constants.MIN_WINDOW_WIDTH);
            appStage.show();
        }
        else {
            passwordTextField.clear();
            invalidDataLabel.setText("Данные неверны");
        }
    }

    /** Checking credentials method **/
    private boolean isValidCredentials(){
        boolean result=false;
        if (((usernameTextField.getText().equals("admin")))&&(passwordTextField.getText().equals("admin"))){
            result=true;
        }
        return result;
    }

}