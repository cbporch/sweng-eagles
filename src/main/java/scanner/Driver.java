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

import scanner.dbEntry.DatabaseInput;

/**
 * Created by cbporch on 10/3/2016.
 * Edited by Dan Smith on 10/3/2016 because git is fun
 * Controller for database entry
 */
public class Driver {

    public static void main(String args[]) throws IOException {

        //create an instance of DatabaseInput
        DatabaseInput window = new DatabaseInput();
        window.main(null);

        /* TODO: wait for output from window,  instantiate LuceneStemmer and Encrypter and pass output to them
         * TODO: loop through String array, hash each, and check each against database
         */

        String hashed1 = BCrypt.hashpw("pass", BCrypt.gensalt(10));
        System.out.println(hashed1 + "\n" + BCrypt.checkpw("pass", hashed1) + "\n" + BCrypt.checkpw("ps", hashed1)+ "\n" + BCrypt.checkpw("ss", hashed1)
                +"\n" + BCrypt.checkpw("password", hashed1));


    }
}
