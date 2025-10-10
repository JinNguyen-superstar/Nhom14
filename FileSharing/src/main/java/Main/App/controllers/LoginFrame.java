/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername, txtDepartmentCode;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JLabel lblConfirmPassword, lblDepartmentCode;
    private JButton btnSwitch, btnSubmit;
    private boolean isLoginMode = true; // true = login, false = register

    public LoginFrame() {
        setTitle("Đăng nhập");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Tiêu đề
        JLabel lblTitle = new JLabel("Đăng nhập hệ thống", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // --- Tên đăng nhập ---
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        // --- Mật khẩu ---
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        // --- Nhập lại mật khẩu ---
        gbc.gridx = 0;
        gbc.gridy++;
        lblConfirmPassword = new JLabel("Nhập lại mật khẩu:");
        panel.add(lblConfirmPassword, gbc);
        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(15);
        panel.add(txtConfirmPassword, gbc);

        // --- Mã phòng ban ---
        gbc.gridx = 0;
        gbc.gridy++;
        lblDepartmentCode = new JLabel("Mã phòng ban:");
        panel.add(lblDepartmentCode, gbc);
        gbc.gridx = 1;
        txtDepartmentCode = new JTextField(15);
        panel.add(txtDepartmentCode, gbc);

        // --- Nút xác nhận ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSubmit = new JButton("Đăng nhập");
        panel.add(btnSubmit, gbc);

        // --- Nút chuyển đổi ---
        gbc.gridy++;
        btnSwitch = new JButton("Chưa có tài khoản? Đăng ký ngay");
        btnSwitch.setBorderPainted(false);
        btnSwitch.setContentAreaFilled(false);
        btnSwitch.setForeground(Color.BLUE);
        panel.add(btnSwitch, gbc);

        add(panel, BorderLayout.CENTER);

        // Ẩn 2 dòng khi ở login
        switchToLogin();

        // Sự kiện chuyển chế độ
        btnSwitch.addActionListener(e -> {
            if (isLoginMode) switchToRegister();
            else switchToLogin();
        });

        // Sự kiện xử lý
        btnSubmit.addActionListener(e -> handleSubmit());
    }

    private void switchToLogin() {
        isLoginMode = true;

        // Ẩn cả label lẫn ô nhập
        lblConfirmPassword.setVisible(false);
        txtConfirmPassword.setVisible(false);
        lblDepartmentCode.setVisible(false);
        txtDepartmentCode.setVisible(false);

        btnSubmit.setText("Đăng nhập");
        btnSwitch.setText("Chưa có tài khoản? Đăng ký ngay");
        setTitle("Đăng nhập");
        repaint();
        revalidate();
    }

    private void switchToRegister() {
        isLoginMode = false;

        // Hiện lại label + ô nhập
        lblConfirmPassword.setVisible(true);
        txtConfirmPassword.setVisible(true);
        lblDepartmentCode.setVisible(true);
        txtDepartmentCode.setVisible(true);

        btnSubmit.setText("Đăng ký");
        btnSwitch.setText("Đã có tài khoản? Đăng nhập");
        setTitle("Đăng ký");
        repaint();
        revalidate();
    }

    private void handleSubmit() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (isLoginMode) {
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên và mật khẩu!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công (demo)!");
            dispose();
        } else {
            String confirmPassword = new String(txtConfirmPassword.getPassword()).trim();
            String deptCode = txtDepartmentCode.getText().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || deptCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
                return;
            }

            JOptionPane.showMessageDialog(this, "Đăng ký thành công (demo)!");
            switchToLogin();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
