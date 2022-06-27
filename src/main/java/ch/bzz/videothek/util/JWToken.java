package ch.bzz.videothek.util;

import ch.bzz.videothek.service.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


/**
 * a JSON WebToken
 */
public class JWToken {
    /**
     * builds the token
     * @param data the token data
     * @param duration the duration of this token in minutes
     * @param role the user role ("user" or "admin")
     * @return JSON web token as String
     */
    public static String buildToken(String data, int duration, String role) {
        try {
            byte[] keyBytes = Config.getProperty("jwtSecret").getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
            Date now = new Date();
            Date expiration = new Date(now.getTime() + duration * 60000);

            return Jwts.builder()
                    .setIssuer("BookUltimate")
                    .setSubject(encrypt(data, getJwtEncrypt()))
                    .claim("role", role)
                    .setExpiration(expiration)
                    .setIssuedAt(now)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * encrypts the string
     * @author Lokesh Gupta (https://howtodoinjava.com/security/java-aes-encryption-example/)
     * @param strToEncrypt  string to be encrypted
     * @return encrypted string
     */
    private static String encrypt(String strToEncrypt, String secret) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(
                    cipher.doFinal(
                            strToEncrypt.getBytes(StandardCharsets.UTF_8)
                    )
            );
        } catch (Exception ex) {
            System.out.println("Error while encrypting: " + ex.toString());
        }
        return null;
    }

    /**
     * decrypts the string
     * @author Lokesh Gupta (https://howtodoinjava.com/security/java-aes-encryption-example/)
     * @param strToDecrypt  string to be dencrypted
     * @return decrypted string
     */
    private static String decrypt(String strToDecrypt, String secret) {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception ex) {
            System.out.println("Error while decrypting: " + ex.toString());
        }
        return null;
    }

    /**
     * gets the jwtkey from the propierties
     * @return the jwtKey
     */
    private static String getJwtEncrypt() {
        String rawKey = Config.getProperty("jwtKey");
        int multi8 = rawKey.length() / 8;
        return rawKey.substring(0, (multi8 * 8));
    }

}
