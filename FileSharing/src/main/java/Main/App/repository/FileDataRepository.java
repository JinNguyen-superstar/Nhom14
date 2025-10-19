package main.app.repository;

import Main.App.Database.MySQLConnection;
import Main.App.models.FileData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Main.App.models.FileData;
import Main.App.Database.MySQLConnection;

public class FileDataRepository {
    private final Connection conn;

    // Constructor không tham số (Cách 1)
    public FileDataRepository() throws SQLException {
        this.conn = MySQLConnection.getConnection();
    }

    // Constructor có tham số (Cách 2)
    public FileDataRepository(Connection conn) {
        this.conn = conn;
    }

    // Thêm file vào CSDL
    public synchronized boolean insertFile(FileData file) {
        String sql = "INSERT INTO file_data (file_name, file_path, upload_time, uploader) VALUES (?, ?, NOW(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, file.getFileName());
            ps.setString(2, file.getFileSize());
            ps.setString(3, file.getUploader());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả file
    public synchronized List<FileData> getAllFiles() {
        List<FileData> list = new ArrayList<>();
        String sql = "SELECT * FROM file_data";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                FileData f = new FileData(
                    rs.getInt("id"),
                    rs.getString("file_name"),
                    rs.getString("file_path"),
                    rs.getTimestamp("upload_time"),
                    rs.getString("uploader")
                );
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa file theo ID
    public synchronized boolean deleteFile(int id) {
        String sql = "DELETE FROM file_data WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm file theo ID
    public synchronized FileData getFileById(int id) {
        String sql = "SELECT * FROM file_data WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FileData(
                        rs.getInt("id"),
                        rs.getString("file_name"),
                        rs.getString("file_path"),
                        rs.getTimestamp("upload_time"),
                        rs.getString("uploader")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
