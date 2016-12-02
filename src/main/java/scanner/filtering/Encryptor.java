package scanner.filtering;

import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Encrypts a word, or phrase, or a given list of either. Also has methods to decrypt any of those types of input too.
 * Implements AES for encryption.
 *
 * @author Tom Miller
 * @since 2016-11-28
 */
public class Encryptor {

    private static String key = "THIS IS THE KEY "; //Unsure what to make the key and initial vector.
    private static String iv = "1234567812345678";  //                  ^^^

    /**
     * This method will take in a word and encrpyt it.
     * @param word the word to be encrypted.
     * @return the encrypted version of the word.
     */
    public static String encrypt(String word) {
        try {
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
    public static String decrypt(String encryptedWord) {
        try {
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
    public static ArrayList<String> encryptList(ArrayList<String> list)
    {
        ArrayList<String> encryptedList = new ArrayList<>();
        for(String word: list){
            if(!word.equals("") && word != null) {
                encryptedList.add(encrypt(word));
            }
        }
        return encryptedList;
    }

    /**
     *
     * @param list list of encrypted terms that will be decrypted.
     * @return list of words in their decrypted form.
     */
    public static ArrayList<String> decryptList(ArrayList<String> list)
    {
        ArrayList<String> decryptedList = new ArrayList<>();
        for(String word: list){
            if(!word.equals("") && word != null) {
                decryptedList.add(decrypt(word));
            }
        }
        return decryptedList;
    }
}