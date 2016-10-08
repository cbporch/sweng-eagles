package scanner;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by cbporch on 10/3/2016.
 * Edited by Dan Smith on 10/3/2016 because git is fun
 * Just a placeholder.
 */
public class Driver {

    public static void main(String args[]) throws IOException {
        System.out.println("Not Broken");

        StemAnalyzer analyzer = new StemAnalyzer();
        TokenStream ts = analyzer.tokenStream("field",
                new StringReader("this is a test string, to display the features of Lucene"));
        CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
        try {
            ts.reset();
            while (ts.incrementToken()) {
                System.out.println(termAtt.toString());
            }
            ts.end();
        } finally {
            ts.close();
        }


    }
}
