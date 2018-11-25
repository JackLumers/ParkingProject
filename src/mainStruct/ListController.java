package mainStruct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.sql.SQLException;

/**
 * Created by ONUR BASKIRT on 23.02.2016.
 */
public class ListController {

    @FXML
    private TextField id;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField nameText;
    @FXML
    private TextField empIdText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField PhoneText;
    @FXML
    private TextField hours;
    @FXML
    private TextField price;
    @FXML
    private TableView employeeTable;
    @FXML
    private TableColumn<Client, Integer>  empIdColumn;
    @FXML
    private TableColumn<Client, String>  empNameColumn;
    @FXML
    private TableColumn<Client, String> empLastNameColumn;
    @FXML
    private TableColumn<Client, String> empPhoneColumn;
    @FXML
    private TableColumn<Client, Integer>  empHoursColumn;
    @FXML
    private TableColumn<Client, Integer>  empPriceColumn;
    @FXML
    private TableColumn<Client, Integer>  empSumColumn;


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
        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        empPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        empHoursColumn.setCellValueFactory(cellData -> cellData.getValue().HoursIdProperty().asObject());
        empPriceColumn.setCellValueFactory(cellData -> cellData.getValue().PriceIdProperty().asObject());


        //Get all Employees information
        ObservableList<Client> empData = ListDAO.searchEmployees();
        //Populate Employees on TableView
        populateEmployees(empData);


    }

    //Populate Employee
    @FXML
    private void populateEmployee (Client emp) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Client> empData = FXCollections.observableArrayList();
        //Add employee to the ObservableList
        empData.add(emp);
        //Set items to the employeeTable
        employeeTable.setItems(empData);
    }

    //Set Employee information to Text Area
    @FXML
    private void setEmpInfoToTextArea ( Client emp) {
        resultArea.setText("First Name: " + emp.getFirstName() + "\n" +
                "Last Name: " + emp.getLastName());
    }

    //Populate Employee for TableView and Display Employee on TextArea
    @FXML
    private void populateAndShowEmployee(Client emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmployee(emp);
            setEmpInfoToTextArea(emp);
        } else {
            resultArea.setText("This employee does not exist!\n");
        }
    }

    //Populate Employees for TableView
    @FXML
    private void populateEmployees (ObservableList<Client> empData) throws ClassNotFoundException {
        //Set items to the employeeTable
        employeeTable.setItems(empData);
    }


    //Insert an employee to the DB
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            int sum = Integer.parseInt(hours.getText())*Integer.parseInt(price.getText());
            ListDAO.insertEmp(Integer.parseInt(id.getText()),surnameText.getText(),nameText.getText(),PhoneText.getText(),Integer.parseInt(hours.getText()),Integer.parseInt(price.getText()), sum);
            nameText.clear();
            id.clear();
            surnameText.clear();
            hours.clear();
            price.clear();
            PhoneText.clear();
            resultArea.setText("Employee inserted! \n");
            //Get all Employees information
            ObservableList<Client> empData = ListDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while inserting employee " + e);
            throw e;
        }
    }

    //Delete an employee with a given employee Id from DB
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ListDAO.deleteEmpWithId(empIdText.getText());
            empIdText.clear();
            resultArea.setText("Employee deleted! Employee id: " + empIdText.getText() + "\n");
            //Get all Employees information
            ObservableList<Client> empData = ListDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while deleting employee " + e);
            throw e;
        }
    }


}