package Main.App.aws;
import Main.App.aws.S3Service;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

/**
 * S3Service - Quản lý tương tác với Amazon S3
 * Tự động đọc AWS Access Key/Secret Key từ file cấu hình AWS CLI:
 *   C:\Users\<username>\.aws\credentials
 * Cấu trúc:
 *   [default]
 *   aws_access_key_id=YOUR_ACCESS_KEY_ID
 *   aws_secret_access_key=YOUR_SECRET_ACCESS_KEY
 */
public class S3Service {

    private final S3Client s3;
    private final String bucketName;

    public S3Service() {
        // Có thể thay đổi bucket bằng tham số JVM: -Daws.bucket=my-bucket
        this.bucketName = System.getProperty("aws.bucket", "secure-file-bucket");

        this.s3 = S3Client.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .build();
    }

    public S3Service(String cawSkeycredentialstxt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /** Upload file lên S3 */
    public void uploadFile(String localPath, String fileName) {
        File file = new File(localPath);
        if (!file.exists()) {
            System.err.println("❌ File không tồn tại: " + localPath);
            return;
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName != null ? fileName : file.getName())
                .build();

        s3.putObject(request, file.toPath());
        System.out.println("✅ Uploaded: " + file.getName() + " → " + bucketName);
    }

    /** Tải file từ S3 về thư mục chỉ định */
    public void downloadFile(String fileName, String destDir) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.getObject(request, Paths.get(destDir, fileName));
        System.out.println("✅ Downloaded: " + fileName + " → " + destDir);
    }

    /** Xóa file trên S3 */
    public void deleteFile(String fileName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.deleteObject(request);
        System.out.println("🗑 Deleted: " + fileName);
    }

    /** Liệt kê toàn bộ file trong bucket */
    public void listFiles() {
        ListObjectsV2Response response = s3.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucketName).build()
        );

        System.out.println("📂 Danh sách file trong bucket [" + bucketName + "]:");
        response.contents().forEach(obj ->
                System.out.println(" - " + obj.key() + " (" + obj.size() + " bytes)")
        );
    }
}
