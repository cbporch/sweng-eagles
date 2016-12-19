package scanner.filtering;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Hasher class uses MessageDigest to hash a given String using SHA-512
 *
 * @author Chris Porch porchc0@students.rowan.edu
 * @since 2016-10-26
 */
public class Hasher {
//    private static final int SALTSPEED = 5;

    /*
     * Hashes an ArrayList of strings using a salt generated using the default number of rounds
     */
//    public static ArrayList<String> hashArrayListBCrypt(ArrayList<String> list){
//        ArrayList<String> hashedList = new ArrayList<>();
//        for(String word: list){
//            if(!word.equals("") && word != null) {
//                hashedList.add(BCrypt.hashpw(word, BCrypt.gensalt(SALTSPEED)));
//            }
//        }
//        return hashedList;
//    }
//
//    public static String hashStringBCrypt(String input){
//        return BCrypt.hashpw(input, BCrypt.gensalt(10));
//    }
//
//    /*
//     * Wrapper method for BCrypt's checkpw method
//     */
//    public static boolean checkHashBCrypt(String word, String hash){
//        try {
//            return BCrypt.checkpw(word, hash);
//        } catch (Exception e) {
//           // e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * Hashes a given String using SHA-512, encodes it as Base64 and returns it.
     * @param word A String.
     * @return A SHA-512 hash
     */
    public static String hashSHA(String word) {

        word = word.toLowerCase();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.encode(md.digest(word.getBytes()));
    }

    public static ArrayList<String> hashArrayListSHA(ArrayList<String> list) {
        ArrayList<String> hashedList = new ArrayList<>();
        for(String word: list){
            if(!word.equals("") && word != null) {
                hashedList.add(hashSHA(word));
            }
        }
        return hashedList;
    }

    /**
     * Hashes a given word and checks it against the given hash.
     * Note: preferred way to check SHA hashes is to call hashSHA() on the word then call a .equals()
     * against the hash to be checked against
     * @param word A String to be tested against the hash.
     * @param hash A SHA-512 hash of a String
     * @return true if the given word matches the given hash when it is hashed
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkSHA(String word, String hash) throws NoSuchAlgorithmException {
        return hashSHA(word).equals(hash);
    }
}
