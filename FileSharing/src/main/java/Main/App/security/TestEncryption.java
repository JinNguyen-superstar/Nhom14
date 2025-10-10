package Main.App.security;

import main.app.security.EncryptionUtil;

public class TestEncryption {
    public static void main(String[] args) {
        try {
            String original = "Thông tin khách hàng: Nguyễn Văn A - 0123456789";
            String encrypted = EncryptionUtil.encrypt(original);
            System.out.println("🔒 Encrypted: " + encrypted);

            String decrypted = EncryptionUtil.decrypt(encrypted);
            System.out.println("🔓 Decrypted: " + decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
