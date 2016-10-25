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
import scanner.Phrase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Chris on 10/11/2016.
 * Uses Lucene Stemmer and Hasher classes to filtering input into an array of hashes
 *
 * The copyright block above is required by Apache
 */
public class StringToHash {

    public StringToHash(){}

    /*
     * Takes in a String array, stems it using LuceneStemmer, hashes each word
     * and returns the hashed words, re-concatenating phrases to preserve them
     * if phrases is set as true
     */
//    public static ArrayList<String> getHashes(ArrayList<String> input) throws IOException {
//        LuceneStemmer ls = new LuceneStemmer();
//         return Hasher.hashArrayListBCrypt(ls.stemWords(input));
//    }
//
//    public static ArrayList<Phrase> getPhraseHashes(ArrayList<Phrase> input) throws IOException {
//        LuceneStemmer ls = new LuceneStemmer();
//        ArrayList<Phrase> hashedPhrases = new ArrayList<>();
//        for(Phrase phrase : input){
//            hashedPhrases.add(new Phrase(Hasher.hashStringBCrypt(ls.stemPhrase(phrase.getPhrase())), phrase.getWordcount()));
//        }
//        return hashedPhrases;
//    }

}
