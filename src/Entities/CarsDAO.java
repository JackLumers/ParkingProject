package Entities;

import DBUtilites.DBUtil;
import UpdateEvent.UpdateEventsControl;
import UpdateEvent.UpdateEventsListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarsDAO {

    /** Add new car */
    public static void insertCar(String carNumber, int clientId, String mark, String color) throws SQLException {
        String updateStmt =
                "INSERT INTO cars " +
                        "(car_number, client_id, mark, color) " +
                        "VALUES " +
                        "('" + carNumber + "','" + clientId + "','" + mark + "','" + color + "')";
        DBUtil.dbExecuteUpdate(updateStmt);
        UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_CARS);
    }

    /** Delete existing car */
    public static void deleteCarByNumber(String carNumber) throws SQLException {
        String updateStmt =
                "DELETE FROM cars " +
                        "WHERE car_number = '" + carNumber + "';";
        DBUtil.dbExecuteUpdate(updateStmt);
        UpdateEventsControl.callListeners(UpdateEventsListener.UPDATE_CARS);
    }

    /** Get observable list of cars */
    public static ObservableList<Car> getCarsList() throws SQLException {
        String selectStmt = "SELECT * FROM cars";
        ObservableList<Car> cars = FXCollections.observableArrayList();
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

        while (rs.next()){
            Car car = new Car();
            car.setCarNumber(rs.getString("car_number"));
            car.setClientId(rs.getInt("client_id"));
            car.setMark(rs.getString("mark"));
            car.setColor(rs.getString("color"));

            cars.add(car);
        }

        return cars;
    }

    /** Get observable list of cars by client id*/
    public static ObservableList<Car> getCarsListByClientId(int id) throws SQLException {
        String selectStmt = "SELECT * FROM cars WHERE client_id = '" + id + "'";
        ObservableList<Car> cars = FXCollections.observableArrayList();
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

        while (rs.next()){
            Car car = new Car();
            car.setCarNumber(rs.getString("car_number"));
            car.setClientId(rs.getInt("client_id"));
            car.setMark(rs.getString("mark"));
            car.setColor(rs.getString("color"));

            cars.add(car);
        }

        return cars;
    }

    /** Get observable list of cars numbers */
    public static ObservableList<String> getCarsNumbersList() throws SQLException {
        String selectStmt = "SELECT * FROM cars";
        ObservableList<String> numbers = FXCollections.observableArrayList();
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

        while (rs.next()){
            numbers.add(rs.getString("car_number"));
        }

        return numbers;
    }

    /** Get observable list of cars numbers */
    public static ObservableList<String> getCarsNumbersListByClientId(int id) throws SQLException {
        String selectStmt = "SELECT * FROM cars WHERE client_id = '" + id + "';";
        ObservableList<String> numbers = FXCollections.observableArrayList();
        ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);

        while (rs.next()){
            numbers.add(rs.getString("car_number"));
        }

        return numbers;
    }
}
