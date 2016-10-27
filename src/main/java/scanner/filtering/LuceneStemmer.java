package scanner.filtering;

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
 * Utilizes Apache Lucene's stemming capabilities to reduce words to their respective
 * stems in an effort to reduce duplicates in the database further on. Primarily uses
 * a stop word filter that also removes whitespace and the letter 's' from the ends of
 * pluralized words.
 *
 * @author Chris Porch porchc0@students.rowan.edu
 */

public class LuceneStemmer {
    private StemAnalyzer analyzer;

    public LuceneStemmer() {
        analyzer = new StemAnalyzer();
    }

    /**
     * Method reduces each String in an ArrayList of Strings to their root word.
     * @param input An ArrayList of Strings to be stemmed.
     * @return An ArrayList of stemmed words.
     * @throws IOException
     */
    public ArrayList<String> stemWords(ArrayList<String> input) throws IOException {
        ArrayList<String> output = new ArrayList<>(input.size());
        for (String word : input) {
            if (!word.equals("")) {
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
        }
        return output;
    }

    /**
     * Method reduces the words in a String to their root words, splitting by whitespace
     * if there is more than one word, then re-concatenates them, preserving phrases.
     * @param input A String containing a phrase.
     * @return A String containing a phrase that has been stemmed.
     * @throws IOException
     */
    public String stemPhrase(String input) throws IOException {
        String output = "";
        if (!input.equals("") && input != null) {
            TokenStream ts = analyzer.tokenStream("field",
                    new StringReader(input));
            CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
            try {
                ts.reset();
                while (ts.incrementToken()) {
                    if (output.equals("")) {
                        output = termAtt.toString();
                    } else {
                        output = output + termAtt.toString();
                    }
                }
                ts.end();
            } finally {
                ts.close();
            }
        }
        if (!output.equals("")) {
            return output;
        }
        return null;
    }

    /**
     * Splits a given String text into an ArrayList of stemmed Strings.
     * @param text The text to be stemmed, contained in a String.
     * @return An ArrayList containing each stemmed word from the passed string.
     * @throws IOException
     */
    public ArrayList<String> splitText(String text) throws IOException {
        ArrayList<String> stemmedWordList = new ArrayList<>();

        TokenStream ts = analyzer.tokenStream("field",
                new StringReader(text));
        CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
        try {
            ts.reset();
            while (ts.incrementToken()) {
                stemmedWordList.add(termAtt.toString());
            }
            ts.end();
        } finally {
            ts.close();
        }

        return stemmedWordList;
    }
}
