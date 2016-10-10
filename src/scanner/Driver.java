package scanner;

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
import org.mindrot.jbcrypt.BCrypt;

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
                new StringReader("this is a test string, to display the features of Lucene after running"));
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

        String hashed1 = BCrypt.hashpw("pass", BCrypt.gensalt(11));
        System.out.println(hashed1 + "\n" + BCrypt.checkpw("password", "$2a$11$GHUCFlb6.D/pNu8nFBNAsOEjgqadbkht.0FgtsrfHNpRuY7Pyli86"));


    }
}
