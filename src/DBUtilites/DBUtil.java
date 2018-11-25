package DBUtilites;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {

    //Connection
    private static Connection conn = null;

    //Connection String
    private static final String connStr = "jdbc:mysql://localhost:3306/parking?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String username = "root";
    private static final String password = "1234";


    //Connect to DB
    public static void dbConnect() throws SQLException {
        try {
            conn = DriverManager.getConnection(connStr, username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }

    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
            throw e;
        }
    }

    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cachedRawSet;
        try {
            //Connect to DB
            dbConnect();

            //Create statement
            stmt = conn.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet is used to cache query and prevent
            //"java.sql.SQLRecoverableException: Closed Connection: next" error
            cachedRawSet = new CachedRowSetImpl();
            cachedRawSet.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }

        //Return CachedRowSet
        return cachedRawSet;
    }

    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
}