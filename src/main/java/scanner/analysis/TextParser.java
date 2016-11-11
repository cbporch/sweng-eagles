package scanner.analysis;

import scanner.Doublet;
import scanner.Phrase;
import scanner.Word;
import scanner.dbEntry.Database;
import scanner.filtering.Hasher;
import scanner.filtering.LuceneStemmer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by chris on 10/22/16.
 *
 * TextParser takes Plaintext input and stems it, searches for those stemmed words in the database,
 * and then uses CalculateEmailScore to derive a score for that email text.
 */
public class TextParser {
    private ArrayList<String> text;
    private LuceneStemmer ls;
    private ArrayList<Doublet> pairs;
    private HashSet<String> unique;
    private Database db;
    private boolean threadDone, parsingComplete;
    private PriorityBlockingQueue<String> wordsToFind;
    private PriorityBlockingQueue<NPhrase> phraseToFind;

    public TextParser(String email) throws Exception {

        Comparator<NPhrase> comparator = new Comparator<NPhrase>() {
            @Override
            public int compare(NPhrase n1, NPhrase n2) {
                return n1.phrase.compareTo(n2.phrase);
            }
        };

        wordsToFind = new PriorityBlockingQueue<String>(10);
        phraseToFind = new PriorityBlockingQueue<NPhrase>(10, comparator);
        ls = new LuceneStemmer();
        pairs = new ArrayList<>();
        db = new Database();
        try {
            text = ls.splitText(email);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes the text that was inputted in the constructor, finds any confidential words in the database,
     * then gets the conf and norm values from each word, and passes them to CalculateEmailScore to
     * generate a score.
     * @return - A score of how likely the text is to be confidential.
     */
    public double parse(){
        threadDone = false;
        parsingComplete = false;
        int lastIndex = text.size() - 1;
        ArrayList<Integer> grams;
        grams = db.getWordcounts();
        unique = new HashSet<>();
        // get hashed n-grams

        wordThread wThread = new wordThread();


        for(int index = 0; index <= lastIndex; index++){
            if(unique.add(text.get(index))){
//                Word w = findWord(text.get(index));
//                if(w !=null) {
//                    pairs.add(new Doublet(w.getConf(), w.getNorm()));
//                }
                wordsToFind.add(text.get(index));

            }

            for(int N : grams){
                if((index + N - 1) <= lastIndex){
//                    Phrase p = findPhrase(NGram(index, N), N);
//                    if(p!= null){
//                        pairs.add(new Doublet(p.getConf(),p.getNorm()));
//                    }
                    NPhrase np = new NPhrase();
                    np.num = N;
                    np.phrase = NGram(index, N);
                    phraseToFind.add(np);
                }

            }
            wThread.run();
        }
        parsingComplete = true;

        while(!threadDone){
            // wait for thread to finish, if needed
        }

        return CalculateEmailScore.calculate(pairs);
    }

    /**
     * Finds a word in the database and creates a Word object for that word
     * @param word - a word to check in the database
     * @return - a Word object for the String word with its database attributes
     */
    private Word findWord(String word){
        try {
            return db.getWord(Hasher.hashSHA(word));
        } catch (Exception e) {
           System.out.println(e);
        }
       return null;
    }

    /**
     * Finds a word in the database and creates a Phrase object for that phrase
     * @param phrase - a phrase to check in the database
     * @return - a Phrase object for the String word with its database attributes
     */
    private Phrase findPhrase(String phrase, int N){
        try {
            return db.getPhrase(Hasher.hashSHA(phrase), N);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    private String NGram(int index, int N){
        String phrase = "";
        for(int i = 0; i < N; i++){
            phrase += text.get(i + index);
        }
        return phrase;
    }

    /*
     * The goal of this thread is to handle the lookups for each string
     * and to add them to the pairs ArrayList
     */
    private class wordThread implements Runnable{
        public void run(){
            threadDone = false;
            if(!parsingComplete || !wordsToFind.isEmpty() || !phraseToFind.isEmpty()){
                if(!wordsToFind.isEmpty()){
                    Word w = findWord(wordsToFind.remove());
                    if(w !=null) {
                        pairs.add(new Doublet(w.getConf(), w.getNorm()));
                    }
                }
                if(!phraseToFind.isEmpty()){
                    NPhrase np = phraseToFind.remove();
                    Phrase p = findPhrase(np.phrase, np.num);
                    if(p!= null){
                        pairs.add(new Doublet(p.getConf(),p.getNorm()));
                    }
                }
            }
            threadDone = true;
        }
    }

    /*
     * Struct-like nested class to hold a phrase and its number of words
     * This exists in order to facilitate threading more easily
     */
    public class NPhrase{
        protected String phrase;
        protected int num;

    }
}
