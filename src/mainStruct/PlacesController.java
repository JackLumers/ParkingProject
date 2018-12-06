package mainStruct;

import Entities.*;
import UpdateEvent.UpdateEventsControl;
import UpdateEvent.UpdateEventsListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class PlacesController implements UpdateEventsListener {

    @FXML
    private ChoiceBox clientChoiceBox;
    @FXML
    private ChoiceBox carChoiceBox;
    @FXML
    private TextField placeNameTextField;
    @FXML
    private Text debugText;
    @FXML
    private TextField placeToDelTextField;
    @FXML
    private TextField hoursTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TableView placesTable;
    @FXML
    private TableColumn<Place, String> nameColumn;
    @FXML
    private TableColumn<Place, String> carNumberColumn;
    @FXML
    private TableColumn<Place, String> placeNameColumn;
    @FXML
    private TableColumn<Place, String> endDateColumn;
    @FXML
    private TableColumn<Place, Integer> sumColumn;


    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        placeNameColumn.setCellValueFactory(cellData -> cellData.getValue().placeNameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        sumColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());
        carNumberColumn.setCellValueFactory(cellData -> cellData.getValue().carNumberProperty());

        //Заполнение ChoiceBox'а клиентов
        updateClientsChoiceBox();

        //Заполнение TableView
        ObservableList<Place> placesData = PlacesDAO.getPlacesList();
        fillTableView(placesData);

        //Слушатель выбора клиента
        clientChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Прогрузить машины выбранного клиента
                fillCarChoiceBox(newValue);
            }
        });

        UpdateEventsControl.addListener(this);
    }

    //Добавление места
    @FXML
    private void insertPlace(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (!(hoursTextField.getText().equals("") || priceTextField.getText().equals("") ||
                    placeNameTextField.getText().equals("") || clientChoiceBox.getValue().toString().equals("") ||
                    carChoiceBox.getValue().toString().equals(""))) {

                int sum = Integer.parseInt(hoursTextField.getText()) * Integer.parseInt(priceTextField.getText());

                PlacesDAO.insertPlace(placeNameTextField.getText(),
                        clientChoiceBox.getValue().toString(),
                        Integer.parseInt(hoursTextField.getText()), Integer.parseInt(priceTextField.getText()), sum, carChoiceBox.getValue().toString());

                JournalEntryDAO.insertJournalEntry(placeNameTextField.getText(), clientChoiceBox.getValue().toString(),
                        carChoiceBox.getValue().toString(), Integer.parseInt(hoursTextField.getText()),
                        Integer.parseInt(priceTextField.getText()), sum);

                carChoiceBox.setValue("");
                clientChoiceBox.setValue("");
                placeNameTextField.clear();
                hoursTextField.clear();
                priceTextField.clear();

                //Обновление TableView
                ObservableList<Place> empData = PlacesDAO.getPlacesList();
                fillTableView(empData);
                debugText.setText("");
            } else {
                debugText.setText("Заполните поля!");
            }
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
            throw e;
        }
    }

    //Удаление места
    @FXML
    private void deletePlace(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            PlacesDAO.deletePlaceWithName(placeToDelTextField.getText());
            placeToDelTextField.clear();
            //Обновление TableView
            ObservableList<Place> empData = PlacesDAO.getPlacesList();
            fillTableView(empData);
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
            throw e;
        }
    }

    //Обновление информации в окне выбора клиента
    private void updateClientsChoiceBox() {
        try {
            ObservableList<String> names = ClientsDAO.getClientsNamesList();
            fillClientChoiceBox(names);
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    //Заполнение TableView
    @FXML
    private void fillTableView(ObservableList<Place> placesData) {
        placesTable.setItems(placesData);
    }

    //Заполнение окна выбора клиентов
    private void fillClientChoiceBox(ObservableList<String> names) {
        clientChoiceBox.setItems(names);
    }

    //Прогрузка машин выбранного клиента
    private void fillCarChoiceBox(Number newValue) {
        try {
            String client = clientChoiceBox.getItems().get((Integer) newValue).toString();
            int clientId = ClientsDAO.getClientIdByName(client);
            ObservableList<String> carNumbers = CarsDAO.getCarsNumbersListByClientId(clientId);
            carChoiceBox.setItems(carNumbers);
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    //Обновить бокс выбора клиентов, если было удаление/добваление оных
    @Override
    public void onDataChanged(byte updateMsg) {
        if(updateMsg == UpdateEventsListener.UPDATE_CLIENTS || updateMsg == UpdateEventsListener.UPDATE_CARS){
            updateClientsChoiceBox();
            carChoiceBox.setItems(null);
        }
    }
}