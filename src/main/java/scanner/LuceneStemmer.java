package scanner;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Chris on 10/10/2016.
 */
public class LuceneStemmer {
    StemAnalyzer analyzer;

    public LuceneStemmer() {
        StemAnalyzer analyzer = new StemAnalyzer();
    }

    public ArrayList<String> stem(String[] input) throws IOException {
    ArrayList<String> output = new ArrayList<String>(input.length);
        for(String word : input){
            TokenStream ts = analyzer.tokenStream("field",
                    new StringReader(word));
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

    public ArrayList<String> stem(String input) throws IOException{
        ArrayList<String> output = new ArrayList<String>();
        TokenStream ts = analyzer.tokenStream("field",
                new StringReader(input));
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
        return output;
    }
}
