package mainStruct;

import Entities.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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

/**
 * Created by ONUR BASKIRT on 23.02.2016.
 */
public class PlacesController {

    public ChoiceBox clientChoiceBox;
    public ChoiceBox carChoiceBox;
    @FXML
    private TextField placeName;
    @FXML
    private Text debugText;
    @FXML
    private TextField empIdText;
    @FXML
    private TextField hours;
    @FXML
    private TextField price;
    @FXML
    private TableView employeeTable;
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


    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        placeNameColumn.setCellValueFactory(cellData -> cellData.getValue().placeNameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        sumColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());
        carNumberColumn.setCellValueFactory(cellData -> cellData.getValue().carNumberProperty());

        //Filling ChoiceBox
        try {
            ObservableList<String> names = ClientsDAO.getClientsNamesList();
            fillClientChoiceBox(names);
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }

        //Get all Employees information
        ObservableList<Place> empData = PlacesDAO.getPlacesList();
        //Populate Employees on TableView
        fillTableView(empData);



        //Слушатель выбора клиента
        clientChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                fillCarChoiceBox(newValue);
            }
        });
    }

    //Filling TableView
    @FXML
    private void fillTableView(ObservableList<Place> empData) {
        //Set items to the employeeTable
        employeeTable.setItems(empData);
    }

    //Insert an employee to the DB
    @FXML
    private void insertPlace(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            int sum = Integer.parseInt(hours.getText()) * Integer.parseInt(price.getText());

            PlacesDAO.insertPlace(placeName.getText(),
                    clientChoiceBox.getValue().toString(),
                    Integer.parseInt(hours.getText()), Integer.parseInt(price.getText()), sum, carChoiceBox.getValue().toString());

            JournalDAO.insertJournalEntry(placeName.getText(), clientChoiceBox.getValue().toString(),
                    carChoiceBox.getValue().toString(), Integer.parseInt(hours.getText()),
                    Integer.parseInt(price.getText()), sum);

            placeName.clear();
            hours.clear();
            price.clear();
            //Get all places information
            ObservableList<Place> empData = PlacesDAO.getPlacesList();
            //Populate places on TableView
            fillTableView(empData);
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
            throw e;
        }
    }

    //Delete place with a given place Id from DB
    @FXML
    private void deletePlace(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            PlacesDAO.deletePlaceWithName(empIdText.getText());
            empIdText.clear();
            //Get all Employees information
            ObservableList<Place> empData = PlacesDAO.getPlacesList();
            //Populate Employees on TableView
            fillTableView(empData);
        } catch (SQLException e) {
            debugText.setText(e.getLocalizedMessage());
            throw e;
        }
    }

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
        } catch (Exception e){
            debugText.setText(e.getLocalizedMessage());
        }
    }
}