/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class UserDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private String username;

    public UserDashboard(String user) {
        this.username = user;
        setTitle("Trang người dùng - " + user);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnUpload = new JButton("Upload file");
        JButton btnDownload = new JButton("Download file");
        model = new DefaultTableModel(new String[]{"ID", "Tên file", "Trạng thái"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(btnUpload);
        bottom.add(btnDownload);
        add(bottom, BorderLayout.SOUTH);

        btnUpload.addActionListener(e -> uploadFile());
        btnDownload.addActionListener(e -> downloadFile());
        loadFiles();
    }

    private void uploadFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try (Connection conn = DBHelper.getConnection()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO files(filename,uploader,status) VALUES(?,?,?)");
                ps.setString(1, file.getName());
                ps.setString(2, username);
                ps.setString(3, "Chờ duyệt");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Tải lên thành công, chờ admin duyệt!");
                loadFiles();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void downloadFile() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String filename = model.getValueAt(row, 1).toString();
        String status = model.getValueAt(row, 2).toString();

        if (!status.equals("Đã duyệt")) {
            JOptionPane.showMessageDialog(this, "File chưa được admin duyệt!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Giả lập tải file: " + filename);
    }

    private void loadFiles() {
        model.setRowCount(0);
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM files WHERE uploader='" + username + "'")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("filename"),
                        rs.getString("status")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách file!");
        }
    }
}
