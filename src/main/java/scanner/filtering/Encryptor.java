package scanner.filtering;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
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

    private static String privateKeyLocation = "src" + File.separatorChar
            + "main" + File.separatorChar
            + "java" + File.separator
            + "scanner" + File.separatorChar
            + "filtering" + File.separatorChar
            + "private.der";

    String publicKeyLocation = "src" + File.separatorChar
            + "main" + File.separatorChar
            + "java" + File.separator
            + "scanner" + File.separatorChar
            + "filtering" + File.separatorChar
            + "public.der";

    public Encryptor(){

    }

    //********** ALL RSA STUFF ***************\\

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

    public PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    public PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }


    public static byte[] encryptKey(Key pubkey, String text) {
        try {
            Cipher rsa = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            rsa.init(Cipher.ENCRYPT_MODE, pubkey);
            return rsa.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String decryptKey(Key decryptionKey, byte[] buffer) {
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            rsa.init(Cipher.DECRYPT_MODE, decryptionKey);
            byte[] utf8 = rsa.doFinal(buffer);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public byte[] encryptKey(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plaintext);
    }

    public byte[] decryptKey(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciphertext);
    }*/


    //*****************************************\\





    //********KEY RETRIEVING METHODS*********\\

    public static String getAESKey()
    {
        //Using RSA, we will get the actual key for this.
        return "5PWmaEPPxFRqG26v";
    }


    public static String getAESIV()
    {
        //Using RSA, we will get the actual key for this.
        return "z8o2q4oymf791VTQ";
    }





    //UNDER THIS COMMENT IS ALL AES STUFF

    /**
     * This method will take in a word and encrpyt it.
     * @param word the word to be encrypted.
     * @return the encrypted version of the word.
     */
    public static String AESencrypt(String word) {
        try {
            String iv = getAESIV();
            String key = getAESKey();

            IvParameterSpec initVec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVec);

            byte[] encryptedWord = cipher.doFinal(word.getBytes());

            return DatatypeConverter.printBase64Binary(encryptedWord);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return null;
    }

    /**
     * This method will take in an enrypted word and decrpyt it.
     * @param encryptedWord the encrypted word that needs to be decrypted.
     * @return the original version of the word.
     */
    public static String AESdecrypt(String encryptedWord) {
        try {
            String iv = getAESIV();
            String key = getAESKey();

            IvParameterSpec initVec = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, initVec);

            byte[] originalWord = cipher.doFinal(DatatypeConverter.parseBase64Binary(encryptedWord));

            return new String(originalWord);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param list list of words that need to be encrypted.
     * @return the encrypted list of words.
     */
    public static ArrayList<String> AESencryptList(ArrayList<String> list)
    {
        ArrayList<String> encryptedList = new ArrayList<>();
        for(String word: list){
            if(!word.equals("") && word != null) {
                encryptedList.add(AESencrypt(word));
            }
        }
        return encryptedList;
    }

    /**
     *
     * @param list list of encrypted terms that will be decrypted.
     * @return list of words in their decrypted form.
     */
    public static ArrayList<String> AESdecryptList(ArrayList<String> list)
    {
        ArrayList<String> decryptedList = new ArrayList<>();
        for(String word: list){
            if(!word.equals("") && word != null) {
                decryptedList.add(AESdecrypt(word));
            }
        }
        return decryptedList;
    }

    public static String getPrivatePath(){
        return privateKeyLocation;
    }
}