package scanner.filter;

/*
 * Copyright 2016 Christopher Porch, Christopher Deck, Steve Leonetti, Tom Miller, Dan Smith, Mike Bayruns
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Chris Porch on 10/10/2016.
 * Utilizes Lucene's stemming capabilities to reduce words to their respective
 * stems in an effort to reduce duplicates in the database further on.
 *
 * The copyright block above is required by Apache
 */

public class LuceneStemmer {
    private static StemAnalyzer analyzer;

    public LuceneStemmer() {
        analyzer = new StemAnalyzer();
    }

    /*
     * Method reduces each String in an array to its root word
     */
    public static ArrayList<String> stemWords(String[] input) throws IOException {
    ArrayList<String> output = new ArrayList<String>(input.length);
        for(String word : input){
            TokenStream ts = analyzer.tokenStream("myfield", new StringReader(word));
            CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
            try {
                ts.reset();
                while (ts.incrementToken()) {
                    output.add(termAtt.toString());
                }
                ts.end();
            } finally {
                ts.close();
            }
        }
       return output;
    }

    /*
     * Method reduces the words in a String to its root word, splitting by whitespace
     * if there are more than one word, then re-concatenates them, preserving phrases
     */
    public static String stemPhrase(String input) throws IOException{
        String output = "";
        TokenStream ts = analyzer.tokenStream("field",
                new StringReader(input));
        CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
        try {
            ts.reset();
            while (ts.incrementToken()) {
                if(output.equals("")) {
                    output = termAtt.toString();
                }else{
                    output = output + termAtt.toString();
                }
            }
            ts.end();
        } finally {
            ts.close();
        }
        return output;
    }
}
