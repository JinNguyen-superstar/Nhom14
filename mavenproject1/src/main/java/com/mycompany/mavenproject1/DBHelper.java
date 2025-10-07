/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.sql.*;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    static {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            // Bảng người dùng
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL)");
            // Bảng file
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS files (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "filename TEXT," +
                    "uploader TEXT," +
                    "status TEXT)");
        } catch (Exception e) {
            System.err.println("Database init error: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}

