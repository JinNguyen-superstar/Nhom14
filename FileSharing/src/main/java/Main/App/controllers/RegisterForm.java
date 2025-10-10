/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegisterForm extends JFrame {
    private JTextField txtUsername, txtDepartment;
    private JPasswordField txtPassword, txtConfirm;
    private JButton btnRegister, btnBack;

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
        String department = txtDepartment.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
            return;
        }

        // Kiểm tra trùng tên đăng nhập
        File file = new File("accounts.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
                    reader.close();
                    return;
                }
            }
            reader.close();

            // Ghi tài khoản mới
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(username + "," + password + "," + department);
            writer.newLine();
            writer.close();

            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập để sử dụng.");

            // 🟢 Chuyển về LoginForm sau khi đăng ký xong
            this.dispose();
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            loginForm.setLocationRelativeTo(null);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi ghi dữ liệu!");
        }
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