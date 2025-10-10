/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import Main.Form.Dashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        txtUsername = new JTextField();
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        txtPassword = new JPasswordField();
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        btnLogin = new JButton("Đăng nhập");
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        btnRegister = new JButton("Đăng ký");
        panel.add(btnRegister, gbc);

        add(panel);

        // Sự kiện nút đăng nhập
        btnLogin.addActionListener(evt -> btnLoginActionPerformed(evt));

        // Sự kiện nút đăng ký
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
            // 🟢 Nếu đúng thì chuyển thẳng qua Dashboard (không thông báo)
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            dashboard.setLocationRelativeTo(null);

            // 🟥 Đóng form đăng nhập
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRegisterActionPerformed(ActionEvent evt) {
        // Mở form đăng ký
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
        registerForm.setLocationRelativeTo(null);
        this.dispose(); // ẩn form đăng nhập
    }

    // ✅ Kiểm tra tài khoản trong file accounts.txt
    private boolean checkLogin(String username, String password) {
        File file = new File("accounts.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Chưa có tài khoản nào được đăng ký!");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String savedUser = parts[0].trim();
                    String savedPass = parts[1].trim();
                    if (savedUser.equals(username) && savedPass.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
