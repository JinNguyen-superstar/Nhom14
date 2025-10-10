/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterForm extends JFrame {
    private JTextField txtUsername, txtDepartment;
    private JPasswordField txtPassword, txtConfirm;
    private JButton btnRegister, btnBack;

    // 🟢 Thông tin kết nối database
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/filesharingsystem"; // 🔹 Thay bằng tên DB thật
    private final String DB_USER = "root"; // 🔹 Tài khoản MySQL
    private final String DB_PASS = "Nhom14@1234"; // 🔹 Mật khẩu MySQL

    public RegisterForm() {
        setTitle("Đăng ký tài khoản mới");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;

        gbc.gridy++;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField();
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField();
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nhập lại mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtConfirm = new JPasswordField();
        panel.add(txtConfirm, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mã phòng ban:"), gbc);
        gbc.gridx = 1;
        txtDepartment = new JTextField();
        panel.add(txtDepartment, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        btnRegister = new JButton("Đăng ký");
        panel.add(btnRegister, gbc);

        gbc.gridx = 1;
        btnBack = new JButton("Quay lại đăng nhập");
        panel.add(btnBack, gbc);

        add(panel);

        // Sự kiện
        btnRegister.addActionListener(e -> handleRegister());
        btnBack.addActionListener(e -> backToLogin());
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirm = new String(txtConfirm.getPassword()).trim();
        String departmentStr = txtDepartment.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() || departmentStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
            return;
        }

        int departmentID;
        try {
            departmentID = Integer.parseInt(departmentStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã phòng ban phải là số!");
            return;
        }

        // Gọi hàm đăng ký vào database
        if (registerUser(username, password, departmentID)) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập để sử dụng.");
            backToLogin();
        }
    }

    private boolean registerUser(String username, String password, int departmentID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // Kiểm tra tên đăng nhập trùng
            String checkSql = "SELECT * FROM user WHERE Username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
                    return false;
                }
            }

            // Thêm tài khoản mới
            String insertSql = "INSERT INTO user (Username, PasswordHash, DepartmentID, Role, CreatedAt) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password); // 🔸 Nếu dùng hash, mã hóa ở đây
                insertStmt.setInt(3, departmentID);
                insertStmt.setString(4, "User"); // Role mặc định
                insertStmt.setString(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi database: " + e.getMessage());
        }
        return false;
    }

    private void backToLogin() {
        this.dispose();
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
        loginForm.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterForm().setVisible(true));
    }
}
