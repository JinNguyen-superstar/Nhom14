/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.App.aws;

/**
 *
 * @author Asus
 */
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

public class S3Service {
    private final S3Client s3;
    private final String bucketName = "secure-file-bucket";

    public S3Service() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(
            "YOUR_ACCESS_KEY_ID",
            "YOUR_SECRET_ACCESS_KEY"
        );

        s3 = S3Client.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();
    }

    // Upload file
    public void uploadFile(String localPath, String fileName) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.putObject(request, Paths.get(localPath));
        System.out.println("✅ Uploaded: " + fileName);
    }

    // Download file
    public void downloadFile(String fileName, String destPath) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.getObject(request, Paths.get(destPath));
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
}
