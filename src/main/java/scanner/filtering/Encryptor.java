package scanner.filtering;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.*;
import java.security.spec.*;


/**
 * Encrypts a word, or phrase, or a given list of either. Also has methods to decrypt any of those types of input too.
 * Implements AES for encryption.
 *
 * @author Tom Miller
 * @since 2016-11-28
 */
public class Encryptor {

    private PublicKey pub;
    private PrivateKey priv;

    /**
     * So for the generation of the public and private keys, I did this in the terminal.
     * $ openssl genrsa -out keypair.pem 2048
     * $ openssl rsa -in keypair.pem -outform DER -pubout -out public.der
     * $ openssl pkcs8 -topk8 -nocrypt -in keypair.pem -outform DER -out private.der
     * $ locate private.der
     * $ sudo launchctl load -w /System/Library/LaunchDaemons/com.apple.locate.plist
     * $ locate public.der
     * $ sudo launchctl load -w /System/Library/LaunchDaemons/com.apple.locate.plist
     */

    public byte[] readFileBytes(String filename) throws IOException
    {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public void readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        pub = keyFactory.generatePublic(publicSpec);
    }

    public void readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        priv = keyFactory.generatePrivate(keySpec);
    }

    public byte[] encrypt(byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        return cipher.doFinal(plaintext);
    }

    public byte[] decrypt(byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, priv);
        return cipher.doFinal(ciphertext);
    }

}