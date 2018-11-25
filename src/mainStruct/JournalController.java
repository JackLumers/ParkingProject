package mainStruct;

import Entities.JournalEntry;
import Entities.JournalDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class JournalController {

    @FXML
    private TableColumn<JournalEntry, String> dateColumn;
    @FXML
    private TableColumn<JournalEntry, String> placeNameColumn;
    @FXML
    private TableColumn<JournalEntry, String> nameColumn;
    @FXML
    private TableColumn<JournalEntry, String> phoneColumn;
    @FXML
    private TableColumn<JournalEntry, String> endDate;
    @FXML
    private TableColumn<JournalEntry, Integer> sumColumn;
    @FXML
    private TableView journalTable;


    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        placeNameColumn.setCellValueFactory(cellData -> cellData.getValue().placeNameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        endDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        sumColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());


        //Get all Employees information
        ObservableList<JournalEntry> Data = JournalDAO.getJournalValues();
        //Populate Employees on TableView
        fillTableView(Data);
    }

    //Filling TableView with journal objects
    @FXML
    private void fillTableView(ObservableList<JournalEntry> empData) {
        //Set items to the employeeTable
        journalTable.setItems(empData);
    }

    @FXML
    private void ClearJournal(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            JournalDAO.cleanJournal();
            //Get all Employees information
            ObservableList<JournalEntry> Data = JournalDAO.getJournalValues();
            //Populate Employees on TableView
            fillTableView(Data);
        } catch (SQLException e) {
            throw e;
        }
    }

    @FXML
    private void update(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            ObservableList<JournalEntry> Data = JournalDAO.getJournalValues();
            //Populate Employees on TableView
            fillTableView(Data);
        } catch (SQLException e) {
            throw e;
        }
    }
}
