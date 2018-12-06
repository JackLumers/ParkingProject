package mainStruct;

import Entities.JournalEntry;
import Entities.JournalEntryDAO;
import UpdateEvent.UpdateEventsControl;
import UpdateEvent.UpdateEventsListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class JournalController implements UpdateEventsListener {

    @FXML
    private TableColumn<JournalEntry, String> dateColumn;
    @FXML
    private TableColumn<JournalEntry, String> placeNameColumn;
    @FXML
    private TableColumn<JournalEntry, String> nameColumn;
    @FXML
    private TableColumn<JournalEntry, String> carNumColumn;
    @FXML
    private TableColumn<JournalEntry, String> endDate;
    @FXML
    private TableColumn<JournalEntry, Integer> sumColumn;
    @FXML
    private TableColumn<JournalEntry, Integer> priceColumn;
    @FXML
    private TableView journalTable;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        placeNameColumn.setCellValueFactory(cellData -> cellData.getValue().placeNameProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        carNumColumn.setCellValueFactory(cellData -> cellData.getValue().carNumberProperty());
        endDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        sumColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        //Заполнение TableView
        fillJournalTable();

        UpdateEventsControl.addListener(this);
    }

    //Очистка журнала
    @FXML
    private void ClearJournal(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            JournalEntryDAO.cleanJournal();
            UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_JOURNAL);
        } catch (SQLException e) {
            throw e;
        }
    }

    //Обновление TableView
    private void fillJournalTable() {
        try {
            ObservableList<JournalEntry> Data = JournalEntryDAO.getJournalValues();
            journalTable.setItems(Data);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void onDataChanged(byte updateMsg) {
        if(updateMsg == UpdateEventsListener.UPDATE_JOURNAL)
            fillJournalTable();
    }
}
