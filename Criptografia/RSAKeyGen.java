// Gerador de chave assimétrica RSA-2048

import java.security.*;
import java.util.Base64;

public class RSAKeyGen {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048); // 2048 ou 4096 bits

        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey  publicKey  = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String pubBase64  = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("Chave Pública (RSA-2048):\n"  + pubBase64);
        System.out.println("Chave Privada (RSA-2048):\n" + privBase64);
    }
}