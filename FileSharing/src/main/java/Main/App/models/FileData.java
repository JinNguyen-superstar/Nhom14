package Main.App.models;

import java.time.LocalDateTime;

public class FileData {
    private int id;
    private String fileName;
    private String s3Key;
    private String uploader;
    private LocalDateTime uploadedAt;
    private String fileSize;
    private String fileType;
    private boolean isEncrypted;

    public FileData() {}

    public FileData(String fileName, String s3Key, String uploader, LocalDateTime uploadedAt, String fileSize, String fileType, boolean isEncrypted) {
        this.fileName = fileName;
        this.s3Key = s3Key;
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

    public String getS3Key() { return s3Key; }
    public void setS3Key(String s3Key) { this.s3Key = s3Key; }

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
