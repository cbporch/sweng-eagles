package scanner.filter;

import org.mindrot.jbcrypt.BCrypt;

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
    public static ArrayList<String> hashArrayList(ArrayList<String> list){
        ArrayList<String> hashedList = new ArrayList();
        for(String word: list){
            if(word != "" && word != null) {
                hashedList.add(BCrypt.hashpw(word, BCrypt.gensalt(SALTSPEED)));
            }
        }
        return hashedList;
    }

    public static String hashString(String input){
        return BCrypt.hashpw(input, BCrypt.gensalt(10));
    }

    /*
     * Wrapper method for BCrypt's checkpw method
     */
    public static boolean checkHash(String word, String hash){
        try {
            return BCrypt.checkpw(word, hash);
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return false;
    }

}
