package mainStruct;

import Entities.Car;
import Entities.CarsDAO;
import Entities.ClientsDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class CarsController {

    @FXML
    public JFXButton showCars;
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

    private int clientId = -1;

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(cellDate -> cellDate.getValue().carNumberProperty());
        markColumn.setCellValueFactory(cellDate -> cellDate.getValue().markProperty());
        colorColumn.setCellValueFactory(cellDate -> cellDate.getValue().colorProperty());

        //Filling ChoiceBox
        try {
            ObservableList<String> names = ClientsDAO.getClientsNamesList();
            fillChoiceBox(names);
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }

        showCars.setOnMouseClicked(event -> onShowCars());
        addCarButton.setOnMouseClicked(event -> onAddCar());
        deleteButton.setOnMouseClicked(event -> onDeleteCar());
    }

    /**
     * show cars of the chosen client
     */
    private void onShowCars() {
        //Filling TableView
        try {
            clientId = ClientsDAO.getClientIdByName(clientChoiceBox.getValue().toString());
            if (clientId != -1) {
                ObservableList<Car> cars = CarsDAO.getCarsListByClientId(clientId);
                fillTableView(cars);
            } else {
                debugText.setText("Клиент не найден!");
            }
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    /**
     * add new car
     */
    private void onAddCar() {
        try {
            clientId = ClientsDAO.getClientIdByName(clientChoiceBox.getValue().toString());
            if (clientId != -1) {
                String carNum = carNumTextField.getText();
                String mark = markTextField.getText();
                String color = colorTextField.getText();
                if (!carNum.equals("") && !mark.equals("") && !color.equals("")) {

                    CarsDAO.insertCar(carNumTextField.getText(), clientId, markTextField.getText(), colorTextField.getText());
                    ObservableList<Car> cars = CarsDAO.getCarsListByClientId(clientId);
                    fillTableView(cars);

                    carNumTextField.clear();
                    markTextField.clear();
                    colorTextField.clear();

                } else {
                    debugText.setText("Заполните поля для добавления!");
                }
            } else {
                debugText.setText("Выберите клиента!");
            }
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    private void onDeleteCar() {
        try {
            clientId = ClientsDAO.getClientIdByName(clientChoiceBox.getValue().toString());
            if (clientId != -1) {
                String carNum = deleteTextField.getText();
                if (!carNum.equals("")) {
                    CarsDAO.deleteCarByNumber(carNum);
                    ObservableList<Car> cars = CarsDAO.getCarsListByClientId(clientId);
                    fillTableView(cars);

                    deleteTextField.clear();
                } else {
                    debugText.setText("Заполните поле для удаления!");
                }
            } else {
                debugText.setText("Выберите клиента!");
            }
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    private void fillChoiceBox(ObservableList<String> names) {
        clientChoiceBox.setItems(names);
    }

    private void fillTableView(ObservableList<Car> cars) {
        carsTable.setItems(cars);
    }

}
