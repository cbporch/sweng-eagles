package scanner.filter;

import org.mindrot.jbcrypt.BCrypt;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Chris on 10/11/2016.
 * Wrapper class that uses jBCrypt to hash a given ArrayList of Strings
 */
public class Hasher {
    private static final int SALTSPEED = 5;

    /*
     * Hashes an ArrayList of strings using a salt generated using the default number of rounds
     */
    public static ArrayList<String> hashArrayListBCrypt(ArrayList<String> list){
        ArrayList<String> hashedList = new ArrayList();
        for(String word: list){
            if(word != "" && word != null) {
                hashedList.add(BCrypt.hashpw(word, BCrypt.gensalt(SALTSPEED)));
            }
        }
        return hashedList;
    }

    public static String hashStringBCrypt(String input){
        return BCrypt.hashpw(input, BCrypt.gensalt(10));
    }

    /*
     * Wrapper method for BCrypt's checkpw method
     */
    public static boolean checkHashBCrypt(String word, String hash){
        try {
            return BCrypt.checkpw(word, hash);
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return false;
    }

    public static String hashSHA(String word) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(word.getBytes()).toString();
    }

    public static boolean checkSHA(String word, String hash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(word.getBytes()).equals(hash);
    }
}
