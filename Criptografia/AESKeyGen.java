// Gerador de chave simétrica AES-256

import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESKeyGen {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // 128, 192 ou 256 bits
        SecretKey secretKey = keyGen.generateKey();

        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Chave AES-256: " + base64Key);
    }
}