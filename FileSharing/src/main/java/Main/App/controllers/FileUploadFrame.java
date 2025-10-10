/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class FileUploadFrame extends JFrame {
    private JButton btnUpdate;

    public FileUploadFrame() {
        setTitle("Tải File Lên");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Nút Update
        btnUpdate = new JButton("Update File");
        btnUpdate.setBounds(130, 70, 120, 40);
        add(btnUpdate);

        // Sự kiện khi bấm nút
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileExplorer();
            }
        });

        setLocationRelativeTo(null);
    }

    // Mở cửa sổ File Explorer để chọn file
    private void openFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file để tải lên");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Thư mục đích "uploads" trong thư mục dự án
            File uploadDir = new File("uploads");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Sao chép file được chọn vào thư mục uploads
            File destinationFile = new File(uploadDir, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this,
                        "Tải file thành công!\n" + selectedFile.getName(),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tải file: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileUploadFrame().setVisible(true));
    }
}
