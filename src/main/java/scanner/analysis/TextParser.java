package scanner.analysis;

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

    public TextParser(String email) throws Exception {
        db = new Database();
        ls = new LuceneStemmer();
        try {
            text = ls.splitText(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Word findWord(String word){
        return db.getWord(word);
    }

    public Phrase findPhrase(String phrase){
        return db.getPhrase(phrase);
    }

}
