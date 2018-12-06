package Entities;

import DBUtilites.DBUtil;
import UpdateEvent.UpdateEventsControl;
import UpdateEvent.UpdateEventsListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JournalEntryDAO {

    //*******************************
    //SELECT journal
    //*******************************
    public static ObservableList<JournalEntry> getJournalValues() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM journal";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getJournalEntriesList method and get employee object
            ObservableList<JournalEntry> journalValues = getJournalEntriesList(rsEmps);

            //Return employee object
            return journalValues;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from employees operation
    private static ObservableList<JournalEntry> getJournalEntriesList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<JournalEntry> jeList = FXCollections.observableArrayList();

        while (rs.next()) {
            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setId(rs.getInt("id"));
            journalEntry.setPlaceName(rs.getString("place_name"));
            journalEntry.setName(rs.getString("name"));
            journalEntry.setCarNumber(rs.getString("car_number"));
            journalEntry.setEndDate(rs.getString("endDate"));
            journalEntry.setPrice(rs.getInt("price"));
            journalEntry.setSum(rs.getInt("sum"));
            journalEntry.setDate(rs.getString("time"));

            //Add employee to the ObservableList
            jeList.add(journalEntry);
        }
        //return jeList (ObservableList of Employees)
        return jeList;
    }

    public static void cleanJournal() throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =

                "   DELETE FROM journal;\n";


        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
            UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_JOURNAL);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT journal entry
    //*************************************
    public static void insertJournalEntry(String placeName, String name,
                                   String carNumber, int hours, int price, int sum)
            throws SQLException {

        Timestamp endDate = new Timestamp(System.currentTimeMillis() + (hours * 3600000));
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String journalStmt =
                "INSERT INTO journal\n" +
                        "(place_name, name, car_number, endDate, sum , price, time )\n" +
                        "VALUES\n" +
                        "( '" + placeName + "','" + name + "','"  + carNumber + "','" + endDate + "', '" + sum + "','" + price + "','" + time + "');\n";

        try {
            DBUtil.dbExecuteUpdate(journalStmt);
            UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_JOURNAL);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
