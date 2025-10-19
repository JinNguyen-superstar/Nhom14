package Main.App.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Encrypt {

    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    // ✅ Sinh cặp khóa RSA (thực tế bạn có thể lưu ra file và chỉ dùng lại)
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // ✅ Hàm mã hóa AES + RSA kết hợp
    public static String encrypt(String plainText, PublicKey rsaPublicKey) {
        try {
            // 1️⃣ Tạo khóa AES ngẫu nhiên
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(AES_KEY_SIZE);
            SecretKey aesKey = keyGen.generateKey();

            // 2️⃣ Mã hóa dữ liệu bằng AES-256-GCM
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(nonce);

            Cipher aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, spec);
            byte[] encryptedData = aesCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // 3️⃣ Mã hóa khóa AES bằng RSA public key
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            byte[] encryptedAesKey = rsaCipher.doFinal(aesKey.getEncoded());

            // 4️⃣ Gộp dữ liệu: [RSAkeyLength][RSA_AES_Key][Nonce][AES_Encrypted_Data]
            ByteBuffer buffer = ByteBuffer.allocate(4 + encryptedAesKey.length + nonce.length + encryptedData.length);
            buffer.putInt(encryptedAesKey.length);
            buffer.put(encryptedAesKey);
            buffer.put(nonce);
            buffer.put(encryptedData);

            return Base64.getEncoder().encodeToString(buffer.array());

        } catch (Exception e) {
            throw new RuntimeException("Lỗi mã hóa: " + e.getMessage(), e);
        }
    }

    // ✅ Test nhanh
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateRSAKeyPair();

        String originalText = "Đây là dữ liệu bí mật cần bảo vệ!";
        String encrypted = encrypt(originalText, keyPair.getPublic());

        System.out.println("🔹 Chuỗi gốc: " + originalText);
        System.out.println("🔒 Dữ liệu mã hóa: " + encrypted);
    }
}
