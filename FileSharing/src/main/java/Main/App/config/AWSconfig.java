package Main.App.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AWSconfig {
    private static String accessKey;
    private static String secretKey;

    public static void loadKeysFromFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            accessKey = br.readLine().trim();
            secretKey = br.readLine().trim();
        }
    }

    public static String getAccessKey() {
        return accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }
}
