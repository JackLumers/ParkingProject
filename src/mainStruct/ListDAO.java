package mainStruct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ListDAO {

    //*******************************
    //SELECT an Employee
    //*******************************
    public static Client searchEmployee(String empId) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employees WHERE id=" + empId;

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            Client client = getEmployeeFromResultSet(rsEmp);

            //Return employee object
            return client;
        } catch (SQLException e) {
            System.out.println("While searching an employee with " + empId + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Employee Object's attributes and return employee object.
    private static Client getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Client emp = null;
        if (rs.next()) {
            emp = new Client();
            emp.setEmployeeId(rs.getInt("id"));
            emp.setFirstName(rs.getString("first_name"));
            emp.setLastName(rs.getString("last_name"));
            emp.setPhoneNumber(rs.getString("phone"));
            emp.setHoursId(rs.getInt("hours"));
            emp.setPriceId(rs.getInt("price"));
            emp.setSum(rs.getInt("sum"));


        }
        return emp;
    }

    //*******************************
    //SELECT Employees
    //*******************************
    public static ObservableList<Client> searchEmployees() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM parking";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Client> empList = getEmployeeList(rsEmps);

            //Return employee object
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
    //SELECT journal
    //*******************************
    public static ObservableList<Client> searchJournal() throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM journal";

        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);

            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Client> empList = getEmployeeList(rsEmps);

            //Return employee object
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    //Select * from employees operation
    private static ObservableList<Client> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Client> empList = FXCollections.observableArrayList();

        while (rs.next()) {
            Client emp = new Client();
            emp.setEmployeeId(rs.getInt("id"));
            emp.setFirstName(rs.getString("first_name"));
            emp.setLastName(rs.getString("last_name"));
            emp.setPhoneNumber(rs.getString("phone"));
            emp.setHoursId(rs.getInt("hours"));
            emp.setPriceId(rs.getInt("price"));
            emp.setSum(rs.getInt("sum"));

            //Add employee to the ObservableList
            empList.add(emp);
        }
        //return empList (ObservableList of Employees)
        return empList;
    }


    //*************************************
    //DELETE an parking
    //*************************************
    public static void deleteEmpWithId(String Id) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =

                        "   DELETE FROM parking\n" +
                        "         WHERE id ='" + Id + "';\n";


        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
    public static void deleteAllEmp() throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =

                "   DELETE FROM journal;\n";


        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
    //*************************************
    //INSERT an employee
    //*************************************
    public static void insertEmp(int id, String name, String lastname, String phone, int hours, int price, int sum) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String updateStmt =
                        "REPLACE INTO parking\n" +
                        "(id, first_name, last_name, phone, hours, sum , price )\n" +
                        "VALUES\n" +
                        "( '" + id + "','" + name + "', '" + lastname + "','" + phone + "','" + hours + "', '" + sum + "','" + price + "');\n";
        String journalStmt =
                "REPLACE INTO journal\n" +
                        "(id, first_name, last_name, phone, hours, sum , price )\n" +
                        "VALUES\n" +
                        "( '" + id + "','" + name + "', '" + lastname + "','" + phone + "','" + hours + "', '" + sum + "','" + price + "');\n";

        //Execute DELETE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
            DBUtil.dbExecuteUpdate(journalStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}
