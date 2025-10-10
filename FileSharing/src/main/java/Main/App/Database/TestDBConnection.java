package Main.App.Database;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
/**
 *
 * @author ADMIN
 */
public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = MySQLConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Kết nối MySQL thành công!");
            } else {
                System.out.println("❌ Kết nối thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
