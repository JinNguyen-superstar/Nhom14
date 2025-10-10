/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.time.LocalDateTime;

public class FileUploadFrame extends JFrame {
    private JButton btnUpload;
    private JCheckBox chkPrivate;
    private JComboBox<String> cmbDepartment;
    private JTextField txtUploaderID;

    // Thông tin kết nối Database
    private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/filesharingsystem"; // ⚠️ đổi tên DB
    private final String DB_USER = "root";
    private final String DB_PASS = "Nhom14@1234";

    public FileUploadFrame() {
        setTitle("Upload File");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblUploader = new JLabel("Uploader ID:");
        lblUploader.setBounds(50, 40, 100, 25);
        add(lblUploader);

        txtUploaderID = new JTextField();
        txtUploaderID.setBounds(160, 40, 200, 25);
        add(txtUploaderID);

        JLabel lblDept = new JLabel("Department:");
        lblDept.setBounds(50, 80, 100, 25);
        add(lblDept);

        cmbDepartment = new JComboBox<>();
        cmbDepartment.setBounds(160, 80, 200, 25);
        add(cmbDepartment);

        chkPrivate = new JCheckBox("Private File");
        chkPrivate.setBounds(160, 120, 200, 25);
        add(chkPrivate);

        btnUpload = new JButton("Upload File");
        btnUpload.setBounds(160, 170, 120, 40);
        add(btnUpload);

        // Tải danh sách department từ DB
        loadDepartments();

        // Sự kiện upload
        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileExplorer();
            }
        });
    }

    // Tải danh sách Department từ DB
    private void loadDepartments() {
        String sql = "SELECT DepartmentID, DepartmentName FROM department";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("DepartmentID");
                String name = rs.getString("DepartmentName");
                cmbDepartment.addItem(id + " - " + name);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không tải được danh sách phòng ban: " + e.getMessage());
        }
    }

    // Mở File Explorer
    private void openFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file để tải lên");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Thư mục đích uploads/
            File uploadDir = new File("uploads");
            if (!uploadDir.exists()) uploadDir.mkdirs();

            File destinationFile = new File(uploadDir, selectedFile.getName());

            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                int uploaderID = Integer.parseInt(txtUploaderID.getText().trim());
                int departmentID = Integer.parseInt(cmbDepartment.getSelectedItem().toString().split(" - ")[0]);
                boolean isPrivate = chkPrivate.isSelected();

                // Lưu database
                saveFileToDatabase(selectedFile.getName(), destinationFile.getAbsolutePath(), uploaderID, departmentID, isPrivate);

                JOptionPane.showMessageDialog(this,
                        "Upload file thành công và lưu vào database!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi copy file: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    // Lưu thông tin vào database
    private void saveFileToDatabase(String fileName, String filePath, int uploaderID, int departmentID, boolean isPrivate) {
        String sql = "INSERT INTO file (FileName, FilePath, UploaderID, DepartmentID, UploadDate, IsPrivate) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fileName);
            stmt.setString(2, filePath);
            stmt.setInt(3, uploaderID);
            stmt.setInt(4, departmentID);
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setBoolean(6, isPrivate);

            stmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileUploadFrame().setVisible(true));
    }
}
