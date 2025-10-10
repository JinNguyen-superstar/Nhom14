/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.Database;
import java.sql.*;
/**
 *
 * @author Asus
 */
public class MySQLConnection {
    private static final String URL = "jdbc:mysql://10.0.1.176:3306/filesharingsystem?zeroDateBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";  
    private static final String PASS = "your_password_here"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
