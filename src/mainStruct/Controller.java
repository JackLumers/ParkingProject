package mainStruct;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Controller {

    @FXML
    private TextField username_box;
    @FXML
    private Label invalid_label;
    @FXML
    private TextField password_box;
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        //Меняем сцену
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/layout/mainWindow.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //Проверяем данные, если функция isValidCredentials() вернула true, значит данные верны
        if(isValidCredentials()) {
            app_stage.hide(); //optional
            app_stage.setScene(home_page_scene);
            app_stage.setMinHeight(Constants.MIN_WINDOW_HEIGHT);
            app_stage.setMinWidth(Constants.MIN_WINDOW_WIDTH);
            app_stage.show();
        }
        else {
            username_box.clear();
            password_box.clear();
            invalid_label.setText("Неверные данные!!!");
        }

    }
    //Функция проверки данных
    private boolean isValidCredentials(){
        boolean result=false;
        if (((username_box.getText().equals("admin")))&&(password_box.getText().equals("admin"))){
            result=true;
        }
        return result;
    }

}