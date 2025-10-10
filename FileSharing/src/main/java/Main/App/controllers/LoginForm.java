/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import Main.Form.Dashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 250);
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

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        txtUsername = new JTextField();
        gbc.gridx = 1; panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        txtPassword = new JPasswordField();
        gbc.gridx = 1; panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy++;
        btnLogin = new JButton("Đăng nhập");
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        btnRegister = new JButton("Đăng ký");
        panel.add(btnRegister, gbc);

        add(panel);

        btnLogin.addActionListener(evt -> btnLoginActionPerformed(evt));
        btnRegister.addActionListener(evt -> btnRegisterActionPerformed(evt));
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (checkLogin(username, password)) {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            dashboard.setLocationRelativeTo(null);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRegisterActionPerformed(ActionEvent evt) {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
        registerForm.setLocationRelativeTo(null);
        this.dispose();
    }

    // ✅ Kiểm tra tài khoản trong MySQL
    private boolean checkLogin(String username, String password) {
        String url = "jdbc:mysql://127.0.0.1:3306/filesharingsystem"; // 🔹 Thay bằng tên DB của bạn
        String user = "root"; // 🔹 Tên user MySQL
        String pass = "Nhom14@1234"; // 🔹 Mật khẩu MySQL

        String sql = "SELECT * FROM user WHERE Username = ? AND PasswordHash = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Nếu bạn dùng hash, cần mã hóa trước khi so sánh

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Có kết quả nghĩa là đăng nhập đúng
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối database: " + e.getMessage());
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
