package main.app.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import java.io.InputStream;

public class EncryptionUtil {

    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128; // in bits

    private static final String MASTER_KEY = loadMasterKey();

    private static String loadMasterKey() {
        // 1️⃣ Thử lấy từ biến môi trường
        String key = System.getenv("MASTER_KEY");
        if (key != null && !key.isEmpty()) {
            System.out.println("MASTER_KEY loaded from environment variable.");
            return key;
        }

        // 2️⃣ Nếu không có, thử lấy từ file config.properties
        try (InputStream input = EncryptionUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                key = prop.getProperty("MASTER_KEY");
                if (key != null && !key.isEmpty()) {
                    System.out.println("MASTER_KEY loaded from config.properties.");
                    return key;
                }
            }
        } catch (Exception ignored) {}

        // 3️⃣ Nếu vẫn không có, báo lỗi
        System.err.println("⚠️ MASTER_KEY chưa được thiết lập (env hoặc config.properties). EncryptionUtil sẽ không hoạt động.");
        throw new IllegalStateException("MASTER_KEY chưa được thiết lập.");
    }

    // 🔒 Hàm mã hóa chuỗi
    public static String encrypt(String data) {
        try {
            byte[] keyBytes = MASTER_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(normalizeKey(keyBytes), "AES");

            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            new SecureRandom().nextBytes(nonce);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            byte[] ciphertext = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            ByteBuffer byteBuffer = ByteBuffer.allocate(nonce.length + ciphertext.length);
            byteBuffer.put(nonce);
            byteBuffer.put(ciphertext);

            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi mã hóa dữ liệu", e);
        }
    }

    // 🔓 Hàm giải mã chuỗi
    public static String decrypt(String encryptedData) {
        try {
            byte[] keyBytes = MASTER_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(normalizeKey(keyBytes), "AES");

            byte[] decoded = Base64.getDecoder().decode(encryptedData);

            ByteBuffer byteBuffer = ByteBuffer.wrap(decoded);
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            byteBuffer.get(nonce);
            byte[] ciphertext = new byte[byteBuffer.remaining()];
            byteBuffer.get(ciphertext);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

            byte[] plainText = cipher.doFinal(ciphertext);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi giải mã dữ liệu", e);
        }
    }

    // 🧩 Chuẩn hóa key về đúng 32 bytes
    private static byte[] normalizeKey(byte[] keyBytes) throws Exception {
        if (keyBytes.length == 32) {
            return keyBytes;
        } else if (keyBytes.length < 32) {
            byte[] padded = new byte[32];
            System.arraycopy(keyBytes, 0, padded, 0, keyBytes.length);
            return padded;
        } else {
            byte[] shortened = new byte[32];
            System.arraycopy(keyBytes, 0, shortened, 0, 32);
            return shortened;
        }
    }
}
