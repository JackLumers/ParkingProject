package mainStruct;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class JournalController {
    @FXML
    private TableColumn<Client, Integer> jempIdColumn;
    @FXML
    private TableColumn<Client, String> jempNameColumn;
    @FXML
    private TableColumn<Client, String> jempLastNameColumn;
    @FXML
    private TableColumn<Client, String> jempPhoneColumn;
    @FXML
    private TableColumn<Client, Integer>  jempHoursColumn;
    @FXML
    private TableColumn<Client, Integer>  jempPriceColumn;
    @FXML
    private TableColumn<Client, Integer>  jempSumColumn;
    @FXML
    private TableView jemployeeTable;


    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize  ()throws SQLException, ClassNotFoundException {
        /*
        The setCellValueFactory(...) that we set on the table columns are used to determine
        which field inside the Employee objects should be used for the particular column.
        The arrow -> indicates that we're using a Java 8 feature called Lambdas.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe

        We're only using StringProperty values for our table columns in this example.
        When you want to use IntegerProperty or DoubleProperty, the setCellValueFactory(...)
        must have an additional asObject():
        */
        jempIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        jempNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        jempLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        jempPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        jempHoursColumn.setCellValueFactory(cellData -> cellData.getValue().HoursIdProperty().asObject());
        jempPriceColumn.setCellValueFactory(cellData -> cellData.getValue().PriceIdProperty().asObject());


        //Get all Employees information
        ObservableList<Client> Data = ListDAO.searchJournal();
        //Populate Employees on TableView
        jpopulateEmployees(Data);


    }
    //Populate Employees for TableView
    @FXML
    private void jpopulateEmployees (ObservableList<Client> empData) throws ClassNotFoundException {
        //Set items to the employeeTable
        jemployeeTable.setItems(empData);
    }
    @FXML
    private void ClearEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ListDAO.deleteAllEmp();
            //Get all Employees information
            ObservableList<Client> Data = ListDAO.searchJournal();
            //Populate Employees on TableView
            jpopulateEmployees(Data);
        } catch (SQLException e) {
            throw e;
        }
    }
    @FXML
    private void update (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            ObservableList<Client> Data = ListDAO.searchJournal();
            //Populate Employees on TableView
            jpopulateEmployees(Data);
        } catch (SQLException e) {
            throw e;
        }
    }
}
