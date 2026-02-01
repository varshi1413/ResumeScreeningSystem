package com.resume;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; 
    private static final String USER = "resume1";
    private static final String PASSWORD = "Resume123"; 

    public static Connection getConnection() {
        Connection conn = null; 
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully!"); 
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found. Make sure ojdbcX.jar is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed. Check URL, username, password, and database status.");
            e.printStackTrace();
        }
        return conn; // Return the connection
    }

    
    public static void main(String[] args) {
        Connection testConn = getConnection();
        if (testConn != null) {
            try {
                System.out.println("Test connection successful!");
                testConn.close(); 
                
            } catch (SQLException e) {
                System.err.println("Error closing test connection.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to get a database connection.");
        }
    }
}