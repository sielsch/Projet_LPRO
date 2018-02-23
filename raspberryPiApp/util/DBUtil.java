package util;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import com.mysql.jdbc.Driver;

/**
 * Created by ONUR BASKIRT on 22.02.2016.
 */
public class DBUtil {
    //Declare JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
 
    //Connection
    private static Connection conn = null;
 
    //Connection String
    //String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";
    //Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
   // private static final String connStr = "jdbc:oracle:thin:HR/HR@localhost:1521/xe";
 
 
    //Connect to DB
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        //Setting Oracle JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Mysql JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
 
        System.out.println("Mysql JDBC Driver Registered!");
 
        //Establish the Oracle Connection using Connection String
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root", "root");
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
    public static List<String> dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet rs = null;
        List<String> Values = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
 
            //Create statement
            stmt = conn.createStatement();
 
            //Execute select (query) operation
            rs = stmt.executeQuery(queryStmt);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();  
	    System.out.println(columnsNumber+" columns");
	    Values = new ArrayList<>();
            while (rs.next()) {
                for(int i=1;i<=columnsNumber;i++){
                                 Values.add(rs.getString(i));
                        }
                
                }
 
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (rs != null) {
                //Close resultSet
                rs.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return Values;
    }
 
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
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
