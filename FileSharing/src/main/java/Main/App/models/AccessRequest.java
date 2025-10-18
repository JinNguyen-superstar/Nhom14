package Main.App.models;

import java.time.LocalDateTime;

public class AccessRequest {
    private int id;
    private int fileId;
    private int requesterId;
    private String status; // pending, approved, rejected
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;

    public AccessRequest() {}

    public AccessRequest(int id, int fileId, int requesterId, String status, LocalDateTime requestedAt, LocalDateTime approvedAt) {
        this.id = id;
        this.fileId = fileId;
        this.requesterId = requesterId;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFileId() { return fileId; }
    public void setFileId(int fileId) { this.fileId = fileId; }

    public int getRequesterId() { return requesterId; }
    public void setRequesterId(int requesterId) { this.requesterId = requesterId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
}

