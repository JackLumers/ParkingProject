package mainStruct;

import Entities.Car;
import Entities.CarsDAO;
import Entities.ClientsDAO;
import UpdateEvent.UpdateEventsControl;
import UpdateEvent.UpdateEventsListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class CarsController implements UpdateEventsListener {

    @FXML
    public ChoiceBox clientChoiceBox;
    public Text debugText;
    public TableView carsTable;
    public JFXButton deleteButton;
    public JFXTextField deleteTextField;
    public JFXTextField carNumTextField;
    public JFXTextField markTextField;
    public JFXTextField colorTextField;
    public JFXButton addCarButton;
    public TableColumn<Car, String> numberColumn;
    public TableColumn<Car, String> markColumn;
    public TableColumn<Car, String> colorColumn;

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(cellDate -> cellDate.getValue().carNumberProperty());
        markColumn.setCellValueFactory(cellDate -> cellDate.getValue().markProperty());
        colorColumn.setCellValueFactory(cellDate -> cellDate.getValue().colorProperty());

        //Заполнение бокса выбора клиетов
        fillChoiceBox();

        UpdateEventsControl.addListener(this);

        addCarButton.setOnMouseClicked(event -> onAddCar());
        deleteButton.setOnMouseClicked(event -> onDeleteCar());

        clientChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                onShowCars((Integer) newValue);
            }
        });
    }

    /**
     * show cars of the chosen client
     */
    private void onShowCars(int clientItemId) {
        //Filling TableView
        try {
            int clientId = ClientsDAO.getClientIdByName(clientChoiceBox.getItems().get(clientItemId).toString());
            if (clientId != -1) {
                ObservableList<Car> cars = CarsDAO.getCarsListByClientId(clientId);
                fillTableView(cars);
            } else {
                debugText.setText("Клиент не найден! Возможно, он был удален");
            }
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    /**
     * add new car
     */
    private void onAddCar() {
        try {
            String carNum = carNumTextField.getText();
            String mark = markTextField.getText();
            String color = colorTextField.getText();

            String clientName = null;
            if (clientChoiceBox.getValue() != null)
                clientName = clientChoiceBox.getValue().toString();

            if (clientName != null &&
                    !(carNum.equals("") || mark.equals("") || color.equals("") || clientName.equals(""))) {

                int clientId = ClientsDAO.getClientIdByName(clientName);
                CarsDAO.insertCar(carNumTextField.getText(), clientId, markTextField.getText(), colorTextField.getText());
                ObservableList<Car> cars = CarsDAO.getCarsListByClientId(clientId);
                fillTableView(cars);

                carNumTextField.clear();
                markTextField.clear();
                colorTextField.clear();
                debugText.setText("");

            } else {
                debugText.setText("Заполните поля!");
            }

        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    private void onDeleteCar() {
        try {
            int clientId = ClientsDAO.getClientIdByName(clientChoiceBox.getValue().toString());
            if (clientId != -1) {
                String carNum = deleteTextField.getText();
                if (!carNum.equals("")) {
                    CarsDAO.deleteCarByNumber(carNum);
                    ObservableList<Car> cars = CarsDAO.getCarsListByClientId(clientId);
                    fillTableView(cars);

                    deleteTextField.clear();
                    debugText.setText("");
                } else {
                    debugText.setText("Заполните поле для удаления!");
                }
            } else {
                debugText.setText("Выберите клиента!");
            }
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    //Заполнение бокса выбора клиетов
    private void fillChoiceBox() {
        try {
            ObservableList<String> names = ClientsDAO.getClientsNamesList();
            clientChoiceBox.setItems(names);
        } catch (SQLException e){
            debugText.setText(e.getLocalizedMessage());
        }
    }

    private void fillTableView(ObservableList<Car> cars) {
        carsTable.setItems(cars);
    }

    @Override
    public void onDataChanged(byte updateMsg) {
        if(updateMsg == UpdateEventsListener.UPDATE_CLIENTS){
            fillChoiceBox();
            carsTable.setItems(null);
        }
    }
}
