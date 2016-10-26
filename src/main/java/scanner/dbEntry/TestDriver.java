package scanner.dbEntry;

import scanner.analysis.TextParser;
import scanner.filtering.LuceneStemmer;

import java.util.ArrayList;

/**
 * Created by cdeck_000 on 10/25/2016.
 */
public class TestDriver {
    public static void main(String [ ] args)
    {
        try {
            ArrayList<String> words = new ArrayList<String>();
            ArrayList<String> phrases = new ArrayList<String>();
            String wordString = "Spring";
            String phraseString = "Spring Break";
            words.add(wordString);
            phrases.add(phraseString);

            LuceneStemmer ls = new LuceneStemmer();
            words = ls.stemWords(words);
            phraseString = ls.stemPhrase(phraseString);

            TextParser tp = new TextParser("Spring");
            String[] word = words.toArray(new String[words.size()]);
            String[] phrase = phrases.toArray(new String[words.size()]);
            DatabaseInput.processInputSHA(word, phrase, .5, .5, 0, 0);

            for(String s: words){
                //use parse method in textparser
            }
            //use parse method in textparser
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
