/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public AdminDashboard(String admin) {
        setTitle("Bảng điều khiển Admin - " + admin);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Tên file", "Uploader", "Trạng thái"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        JButton btnApprove = new JButton("Duyệt file");
        JButton btnReject = new JButton("Từ chối file");

        JPanel bottom = new JPanel();
        bottom.add(btnApprove);
        bottom.add(btnReject);

        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnApprove.addActionListener(e -> updateStatus("Đã duyệt"));
        btnReject.addActionListener(e -> updateStatus("Từ chối"));
        loadFiles();
    }

    private void updateStatus(String status) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int id = (int) model.getValueAt(row, 0);
        try (Connection conn = DBHelper.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE files SET status=? WHERE id=?");
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            loadFiles();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void loadFiles() {
        model.setRowCount(0);
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM files")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("filename"),
                        rs.getString("uploader"),
                        rs.getString("status")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách file!");
        }
    }
}
