/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.JFrame;

public class MainForm extends JFrame {
    private String currentUser;

    public MainForm(String username) {
        this.currentUser = username;
        setTitle("Hệ thống quản lý file - Người dùng: " + username);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // 👉 Tại đây là toàn bộ giao diện Upload, Download, List, Search mà bạn đã làm
        // (giữ nguyên code cũ)
    }
}
