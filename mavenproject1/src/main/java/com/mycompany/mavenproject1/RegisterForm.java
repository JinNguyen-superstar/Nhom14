/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword, txtConfirm;
    private JComboBox<String> cbRole;

    public RegisterForm() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        txtUsername = new JTextField();
        JLabel lblPass = new JLabel("Mật khẩu:");
        txtPassword = new JPasswordField();
        JLabel lblConfirm = new JLabel("Nhập lại mật khẩu:");
        txtConfirm = new JPasswordField();
        JLabel lblRole = new JLabel("Chức vụ:");
        cbRole = new JComboBox<>(new String[]{"User", "Admin"});
        JButton btnRegister = new JButton("Đăng ký");
        JButton btnBack = new JButton("Quay lại");

        add(lblUser); add(txtUsername);
        add(lblPass); add(txtPassword);
        add(lblConfirm); add(txtConfirm);
        add(lblRole); add(cbRole);
        add(btnRegister); add(btnBack);

        btnRegister.addActionListener(e -> register());
        btnBack.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
    }

    private void register() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirm.getPassword());
        String role = cbRole.getSelectedItem().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ!");
            return;
        }
        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
            return;
        }

        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username,password,role) VALUES(?,?,?)");
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, role);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            dispose();
            new LoginForm().setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
