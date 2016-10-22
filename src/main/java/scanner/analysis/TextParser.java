package scanner.analysis;

import scanner.Phrase;
import scanner.Word;
import scanner.dbEntry.Database;
import scanner.filtering.LuceneStemmer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chris on 10/22/16.
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

    private Word findWord(String word){
//        Word w = db.getWord;
        return null;
    }

    private Phrase findPhrase(){
//        Phrase p = db.getPhrase();
        return null;
    }

}
