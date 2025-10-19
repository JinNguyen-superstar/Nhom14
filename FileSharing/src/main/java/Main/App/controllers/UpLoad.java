/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.controllers;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpLoad extends JFrame {

    private JTextField txtFilePath;
    private JButton btnSearch, btnConfirm;
    private JComboBox<String> cbbPhongBan;
    private File selectedFile;
    private FrmMain mainForm;

    public UpLoad(FrmMain mainForm) {
        this.mainForm = mainForm;
        initComponents();
    }

    private void initComponents() {
        setTitle("Upload File");
        setSize(450, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblFile = new JLabel("Chọn file:");
        lblFile.setBounds(20, 20, 80, 25);
        add(lblFile);

        txtFilePath = new JTextField();
        txtFilePath.setBounds(100, 20, 200, 25);
        txtFilePath.setEditable(false);
        add(txtFilePath);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(310, 20, 100, 25);
        add(btnSearch);

        JLabel lblPhongBan = new JLabel("Mã phòng ban:");
        lblPhongBan.setBounds(20, 60, 100, 25);
        add(lblPhongBan);

        cbbPhongBan = new JComboBox<>(new String[]{"PB001", "PB002", "PB003", "PB004"});
        cbbPhongBan.setBounds(130, 60, 150, 25);
        add(cbbPhongBan);

        btnConfirm = new JButton("Xác nhận Upload");
        btnConfirm.setBounds(130, 100, 200, 30);
        add(btnConfirm);

        // Sự kiện
        btnSearch.addActionListener(e -> chooseFile());
        btnConfirm.addActionListener(e -> confirmUpload());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            txtFilePath.setText(selectedFile.getName());
        }
    }

    private void confirmUpload() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file trước!");
            return;
        }

        String fileName = selectedFile.getName();
        long fileSizeKB = selectedFile.length() / 1024;
        String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String maPhongBan = cbbPhongBan.getSelectedItem().toString();

        // Gửi dữ liệu sang form chính
        mainForm.addFileToTable(fileName, fileSizeKB, currentTime, maPhongBan);

        JOptionPane.showMessageDialog(this, "Upload thành công!");
        this.dispose(); // Đóng form Upload, quay lại form chính
    }
}

