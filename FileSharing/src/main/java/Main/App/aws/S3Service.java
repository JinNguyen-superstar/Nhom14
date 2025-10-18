package Main.App.aws;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

public class S3Service {
    private final S3Client s3;
    private final String bucketName = "secure-file-bucket";

    public S3Service() {
        s3 = S3Client.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .build();
    }

    // Upload file
    public void uploadFile(String localPath, String fileName) {
        File file = new File(localPath);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName != null ? fileName : file.getName())
                .build();

        s3.putObject(request, file.toPath());
        System.out.println("✅ Uploaded: " + file.getName());
    }

    // Download file
    public void downloadFile(String fileName, String destDir) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.getObject(request, Paths.get(destDir, fileName));
        System.out.println("✅ Downloaded: " + fileName);
    }

    // Delete file
    public void deleteFile(String fileName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.deleteObject(request);
        System.out.println("🗑 Deleted: " + fileName);
    }

    // List files
    public void listFiles() {
        ListObjectsV2Response response = s3.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucketName).build()
        );
        response.contents().forEach(obj -> System.out.println("📄 " + obj.key()));
    }
}
