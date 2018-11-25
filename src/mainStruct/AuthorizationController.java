package mainStruct;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    private void initialize() {
        // Установка слушателя на кнопку
        enteringButton.setOnMouseClicked(event -> handleEnteringButton());
    }

    /** Handling entering button click **/
    private void handleEnteringButton(){
        if(isValidCredentials()) {
            try {
                Parent homePageParent = FXMLLoader.load(getClass().getResource("/layout/mainWindow.fxml"));
                Scene homePageScene = new Scene(homePageParent);
                Stage appStage = (Stage)enteringButton.getScene().getWindow();
                appStage.hide();
                appStage.setScene(homePageScene);
                appStage.setMinHeight(Constants.MIN_WINDOW_HEIGHT);
                appStage.setMinWidth(Constants.MIN_WINDOW_WIDTH);
                appStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            passwordTextField.clear();
            invalidDataLabel.setText("Данные неверны");
        }
    }

    /** Checking credentials method **/
    private boolean isValidCredentials(){
        boolean result = false;
        if (((usernameTextField.getText().equals("admin")))&&(passwordTextField.getText().equals("admin"))){
            result=true;
        }
        return result;
    }

}