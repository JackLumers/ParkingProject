package Entities;

import DBUtilites.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PlacesDAO {

    //*******************************
    //SELECT an Employee
    //*******************************
    public static Place searchPlaceById(String empId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employees WHERE id=" + empId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getPlaceValuesFromResultSet method and get Place object
            Place place = getPlaceValuesFromResultSet(rsEmp);

            //Return employee object
            return place;
        } catch (SQLException e) {
            System.out.println("While searching an employee with " + empId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Employee Object's attributes and return employee object.
    private static Place getPlaceValuesFromResultSet(ResultSet rs) throws SQLException {
        Place emp = null;
        if (rs.next()) {
            emp = new Place();
            emp.setId(rs.getInt("id"));
            emp.setPlaceName(rs.getString("place_name"));
            emp.setName(rs.getString("name"));
            emp.setEndDate(rs.getDate("endDate").toString());
            emp.setPrice(rs.getInt("price"));
            emp.setSum(rs.getInt("sum"));
            emp.setCarNumber(rs.getString("car_number"));


        }
        return emp;
    }


    //*************************************
    //DELETE an parking_place
    //*************************************
    public static void deletePlaceWithName(String placeName) throws SQLException {
        //Declare a DELETE statement
        String updateStmt =
                " DELETE FROM parking_place " +
                        "WHERE place_name = '" + placeName + "';";


        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*************************************
    //INSERT place
    //*************************************
    public static void insertPlace(String placeName, String name, int hours, int price, int sum, String carNumber)
            throws SQLException, ClassNotFoundException {

        Timestamp endDate = new Timestamp(System.currentTimeMillis() + (hours * 3600000));
        String updateStmt =
                "INSERT INTO parking_place\n" +
                        "(place_name, name, sum , price, endDate, car_number )\n" +
                        "VALUES\n" +
                        "( '" + placeName + "','" + name + "','"  + sum +
                        "','" + price + "','" + endDate + "','" + carNumber + "');\n";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    //*******************************
    //SELECT parking_place
    //*******************************
    public static ObservableList<Place> getPlacesList() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM parking_place";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Place> placesValues = getPlacesListForResultSet(rsEmps);

            return placesValues;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Place> getPlacesListForResultSet(ResultSet rs) throws SQLException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Place> empList = FXCollections.observableArrayList();

        while (rs.next()) {
            Place place = new Place();
            place.setId(rs.getInt("id"));
            place.setPlaceName(rs.getString("place_name"));
            place.setName(rs.getString("name"));
            place.setEndDate(rs.getString("endDate"));
            place.setPrice(rs.getInt("price"));
            place.setSum(rs.getInt("sum"));
            place.setCarNumber(rs.getString("car_number"));

            //Add employee to the ObservableList
            empList.add(place);
        }
        //return empList (ObservableList of Employees)
        return empList;
    }
}
