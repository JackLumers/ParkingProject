package Entities;

import DBUtilites.DBUtil;
import UpdateEvent.UpdateEventsControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import UpdateEvent.UpdateEventsListener;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientsDAO {

    /** Add new client */
    public static void insertClient(String name, String phone, String passport) throws SQLException {
        String updateStmt =
                "INSERT INTO clients " +
                        "(name, phone, passport) " +
                        "VALUES " +
                        "('" + name + "','" + phone + "','" + passport + "')";
        DBUtil.dbExecuteUpdate(updateStmt);
        UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_CLIENTS);
    }

    /** Delete existing client */
    public static void deleteClientByName(String name) throws SQLException {
        String updateStmt =
                "DELETE FROM clients " +
                        "WHERE name = '" + name + "';";
        DBUtil.dbExecuteUpdate(updateStmt);
        UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_CLIENTS);
    }

    /** Get observable list of clients */
    public static ObservableList<Client> getClientsList() throws SQLException {
        String selectStmt = "SELECT * FROM clients";
        ObservableList<Client> clients = FXCollections.observableArrayList();
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

        while (rs.next()){
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setName(rs.getString("name"));
            client.setPhone(rs.getString("phone"));
            client.setPassport(rs.getString("passport"));

            clients.add(client);
        }

        return clients;
    }

    /** Get observable list of clients names */
    public static ObservableList<String> getClientsNamesList() throws SQLException {
        String selectStmt = "SELECT * FROM clients";
        ObservableList<String> names = FXCollections.observableArrayList();
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

        while (rs.next()){
            names.add(rs.getString("name"));
        }

        return names;
    }

    /** Get Id from name
     *
     * @return Id or -1 if not found.
     */
    public static int getClientIdByName(String name) throws SQLException{
        String selectStmt = "SELECT * FROM clients WHERE name = '" + name + "';";
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
            if (rs.next()) return rs.getInt("id");
            else return -1;
    }
}
