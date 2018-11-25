package mainStruct;

import Entities.Client;
import Entities.ClientsDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class ClientsController {

    @FXML
    public TableView clientsTable;
    public TableColumn<Client, String> clientNameColumn;
    public TableColumn<Client, String> phoneColumn;
    public TableColumn<Client, String> passportColumn;
    public JFXTextField clNameTextField;
    public JFXTextField phoneTextField;
    public JFXTextField passportTextField;
    public JFXButton addButton;
    public Text debugText;
    public JFXButton deleteClientButton;
    public JFXTextField deleteTextField;

    @FXML
    public void initialize(){
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        passportColumn.setCellValueFactory(cellData -> cellData.getValue().passportProperty());

        //Filling TableView
        try {
            ObservableList<Client> clients = ClientsDAO.getClientsList();
            fillTableView(clients);
        } catch (Exception e){
            debugText.setText(e.getLocalizedMessage());
        }

        addButton.setOnMouseClicked(event -> onAddClient());
        deleteClientButton.setOnMouseClicked(event -> onDeleteClient());
    }

    /** addButton listener */
    private void onAddClient(){
        try {
            //Inserting client in DB
            String name = clNameTextField.getText();
            String phone = phoneTextField.getText();
            String passport = passportTextField.getText();

            if(!name.equals("") && !phone.equals("") && !passport.equals("")) {

                ClientsDAO.insertClient(name, phone, passport);
                //Filling TableView and ChoiceBox
                ObservableList<Client> clients = ClientsDAO.getClientsList();
                fillTableView(clients);
            } else {
                debugText.setText("Заполните поля!");
            }

        } catch (Exception e){
            debugText.setText(e.getLocalizedMessage());
        }

        passportTextField.clear();
        phoneTextField.clear();
        clNameTextField.clear();
    }

    /** deleteButton listener */
    private void onDeleteClient(){
        try {
            //Deleting client from DB
            ClientsDAO.deleteClientByName(deleteTextField.getText());

            //Filling TableView and ChoiceBox
            ObservableList<Client> clients = ClientsDAO.getClientsList();
            fillTableView(clients);
        } catch (Exception e) {
            debugText.setText(e.getLocalizedMessage());
        }
    }

    private void fillTableView(ObservableList<Client> clients){
        clientsTable.setItems(clients);
    }

}
