package scanner.filter;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

/**
 * Created by Chris on 10/11/2016.
 * Class uses jBCrypt to hash a given string
 */
public class Hasher {

    public Hasher(){

    }

    /*
     * Hashes an ArrayList of strings using a salt generated using the default number of rounds
     */
    public ArrayList<String> hash(ArrayList<String> list){
        ArrayList<String> hashedList = new ArrayList();
        for(String word: list){
            hashedList.add(BCrypt.hashpw(word, BCrypt.gensalt()));
        }
        return hashedList;
    }

}
