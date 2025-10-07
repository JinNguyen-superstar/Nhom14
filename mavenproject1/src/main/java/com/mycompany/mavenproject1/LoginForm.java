/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        txtUsername = new JTextField();
        JLabel lblPass = new JLabel("Mật khẩu:");
        txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnRegister = new JButton("Đăng ký");

        add(lblUser); add(txtUsername);
        add(lblPass); add(txtPassword);
        add(btnLogin); add(btnRegister);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterForm().setVisible(true);
        });
    }

    private void login() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT role FROM users WHERE username=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công (" + role + ")");
                dispose();
                if (role.equalsIgnoreCase("Admin"))
                    new AdminDashboard(user).setVisible(true);
                else
                    new UserDashboard(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
