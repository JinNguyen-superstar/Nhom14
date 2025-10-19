package Main.App.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FileData {
    private int id;
    private String fileName;
    private String uploader;
    private LocalDateTime uploadedAt;
    private String fileSize;
    private String fileType;
    private boolean isEncrypted;

    public FileData(int aInt, String string, String string1, Timestamp timestamp, String string2) {}

    // Constructor có id
    public FileData(int id, String fileName, String uploader, LocalDateTime uploadedAt, String fileSize, String fileType, boolean isEncrypted) {
        this.id = id;
        this.fileName = fileName;
        this.uploader = uploader;
        this.uploadedAt = uploadedAt;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.isEncrypted = isEncrypted;
    }

    // Constructor không id (dùng khi thêm mới)
    public FileData(String fileName, String uploader, LocalDateTime uploadedAt, String fileSize, String fileType, boolean isEncrypted) {
        this.fileName = fileName;
        this.uploader = uploader;
        this.uploadedAt = uploadedAt;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.isEncrypted = isEncrypted;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getUploader() { return uploader; }
    public void setUploader(String uploader) { this.uploader = uploader; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public String getFileSize() { return fileSize; }
    public void setFileSize(String fileSize) { this.fileSize = fileSize; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public boolean isEncrypted() { return isEncrypted; }
    public void setEncrypted(boolean encrypted) { isEncrypted = encrypted; }

    @Override
    public String toString() {
        return fileName + " (" + fileSize + ")";
    }
}
