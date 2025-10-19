package Main.App.security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Decrypt {

    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    // ✅ Giải mã dữ liệu AES + RSA kết hợp
    public static String decrypt(String base64Input, PrivateKey rsaPrivateKey) {
        try {
            byte[] allBytes = Base64.getDecoder().decode(base64Input);
            ByteBuffer buffer = ByteBuffer.wrap(allBytes);

            // 1️⃣ Lấy độ dài & nội dung khóa AES đã mã hóa bằng RSA
            int rsaKeyLen = buffer.getInt();
            byte[] encryptedAesKey = new byte[rsaKeyLen];
            buffer.get(encryptedAesKey);

            // 2️⃣ Lấy nonce và dữ liệu mã hóa
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            buffer.get(nonce);

            byte[] encryptedData = new byte[buffer.remaining()];
            buffer.get(encryptedData);

            // 3️⃣ Giải mã AES key bằng RSA private key
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            byte[] aesKeyBytes = rsaCipher.doFinal(encryptedAesKey);

            SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");

            // 4️⃣ Giải mã dữ liệu bằng AES key
            Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, spec);

            byte[] decrypted = aesCipher.doFinal(encryptedData);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi giải mã: " + e.getMessage(), e);
        }
    }

    // ✅ Test nhanh
    public static void main(String[] args) throws Exception {
        // Sinh cùng cặp khóa RSA
        java.security.KeyPair keyPair = Encrypt.generateRSAKeyPair();

        String originalText = "Đây là dữ liệu bí mật cần bảo vệ!";
        String encrypted = Encrypt.encrypt(originalText, keyPair.getPublic());
        String decrypted = decrypt(encrypted, keyPair.getPrivate());

        System.out.println("🔒 Đã mã hóa: " + encrypted);
        System.out.println("🔓 Giải mã lại: " + decrypted);
    }
}
