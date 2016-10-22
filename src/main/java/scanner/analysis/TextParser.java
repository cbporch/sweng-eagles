package scanner.analysis;

import scanner.filtering.LuceneStemmer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chris on 10/22/16.
 */
public class TextParser {
    private ArrayList<String> text;
    private LuceneStemmer ls;

    public TextParser(String email){
        ls = new LuceneStemmer();
        try {
            text = ls.splitText(email);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
