package main.app.aws;

import Main.App.models.FileData;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.app.repository.FileDataRepository;
import Main.App.aws.S3Service;

public class FileService {
    private final FileDataRepository fileRepo;
    private final S3Service s3Service;
    private final ExecutorService executor;

    public FileService() {
        this.fileRepo = new FileDataRepository(); 
        this.s3Service = new S3Service("C:\\AWSkey\\credentials.txt");
        this.executor = Executors.newFixedThreadPool(5);
    }

    // Upload file đa luồng
    public void uploadFile(File file, String uploader) {
        executor.execute(() -> {
            try {
                s3Service.uploadFile(file.getName(), file.getAbsolutePath());
                FileData data = new FileData(0, file.getName(), file.getAbsolutePath(), null, uploader);
                fileRepo.insertFile(data);
                System.out.println("[UPLOAD] File uploaded: " + file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Download file đa luồng
    public void downloadFile(String s3Key, String localPath) {
        executor.execute(() -> {
            try {
                s3Service.downloadFile(s3Key, localPath);
                System.out.println("[DOWNLOAD] File downloaded: " + s3Key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Xóa file đa luồng
    public void deleteFile(int fileId, String s3Key) {
        executor.execute(() -> {
            try {
                s3Service.deleteFile(s3Key);
                fileRepo.deleteFile(fileId);
                System.out.println("[DELETE] File deleted: " + s3Key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Tắt ExecutorService an toàn khi thoát
    public void shutdown() {
        executor.shutdown();
    }
}
