package scanner.analysis;

import scanner.Doublet;
import scanner.Phrase;
import scanner.Word;
import scanner.dbEntry.Database;
import scanner.filtering.LuceneStemmer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chris on 10/22/16.
 *
 * TextParser takes Plaintext input and stems it, searches for those stemmed words in the database,
 * and then uses CalculateEmailScore to derive a score for that email text.
 */
public class TextParser {
    private ArrayList<String> text;
    private LuceneStemmer ls;
    private Database db;
    private ArrayList<Doublet> pairs;

    public TextParser(String email) throws Exception {
        db = new Database();
        ls = new LuceneStemmer();
        pairs = new ArrayList<>();

        try {
            text = ls.splitText(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes the text that was inputted in the constructor, finds any confidential words in the database,
     * then gets the conf and norm values from each word, and passes them to CalculateEmailScore to
     * generate a score.
     * @return - A score of how likely the text is to be confidential.
     */
    public double parse(){
        for(String word: text){
            Word w = findWord(word);
            if(w !=null)
                pairs.add(new Doublet(w.getConf(), w.getNorm()));
        }

        return CalculateEmailScore.calculate(pairs);
    }

    /**
     * Finds a word in the database and creates a Word object for that word
     * @param word - a word to check in the database
     * @return - a Word object for the String word with its database attributes
     */
    private Word findWord(String word){
        return db.getWord(word);
    }

    /**
     * Finds a word in the database and creates a Phrase object for that phrase
     * @param phrase - a phrase to check in the database
     * @return - a Phrase object for the String word with its database attributes
     */
    private Phrase findPhrase(String phrase){
        return db.getPhrase(phrase);
    }

}
