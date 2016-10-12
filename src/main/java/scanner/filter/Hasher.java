package scanner.filter;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

/**
 * Created by Chris on 10/11/2016.
 * Wrapper class that uses jBCrypt to hash a given ArrayList of Strings
 */
public class Hasher {

    /*
     * Hashes an ArrayList of strings using a salt generated using the default number of rounds
     */
    public static ArrayList<String> hashArrayList(ArrayList<String> list){
        ArrayList<String> hashedList = new ArrayList();
        for(String word: list){
            hashedList.add(BCrypt.hashpw(word, BCrypt.gensalt()));
        }
        return hashedList;
    }

    /*
     * Wrapper method for BCrypt's checkpw method
     */
    public static boolean checkHash(String word, String hash){
        return BCrypt.checkpw(word, hash);
    }

}
