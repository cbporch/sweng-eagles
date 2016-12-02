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
    private ArrayList<Doublet> p;
    private HashSet<String> uniqueWords, uniquePhrases;
    private Database db;
    //private boolean parsingComplete;
    private PriorityBlockingQueue<String> wordsToFind;
    private PriorityBlockingQueue<NPhrase> phraseToFind;
    private PriorityBlockingQueue<Doublet> pairs;
    boolean pThreadDone;

    public TextParser(String email) throws Exception {

        Comparator<NPhrase> comparator0 = new Comparator<NPhrase>() {
            @Override
            public int compare(NPhrase n1, NPhrase n2) {
                return n1.phrase.compareTo(n2.phrase);
            }
        };
        Comparator<Doublet> comparator1 = new Comparator<Doublet>() {
            @Override
            public int compare(Doublet d1, Doublet d2) {
                return Integer.compare(d1.getNumConf(), d2.getNumConf());
            }
        };

        wordsToFind = new PriorityBlockingQueue<>(10);
        phraseToFind = new PriorityBlockingQueue<>(10, comparator0);
        ls = new LuceneStemmer();
        pairs = new PriorityBlockingQueue<>(10, comparator1);
        db = new Database();
        uniqueWords = new HashSet<>();
        uniquePhrases = new HashSet<>();
        try {
            text = ls.splitText(email);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Takes the text that was inputted in the constructor, finds any confidential words in the database,
     * then gets the conf and norm values from each word, and passes them to CalculateEmailScore to
     * generate a score.
     * @return - A score of how likely the text is to be confidential.
     */
    public double parse(){
        //parsingComplete = false;

        parseThread pThread = new parseThread();
        wordThread wThread = new wordThread();

        while(pThread.isAlive() || wThread.isAlive()){
            if(!phraseToFind.isEmpty()) {

                    NPhrase np = phraseToFind.poll();
                    Phrase p = findPhrase(np.phrase, np.num);
                    while(p == null && !phraseToFind.isEmpty()){
                        np = phraseToFind.poll();
                        p = findPhrase(np.phrase, np.num);

                    }
                    if (p != null) {
                          pairs.add(new Doublet(p.getConf(), p.getNorm()));
                    }

            }
        }

        System.out.println("parse done");
        System.out.println("Pairs: " + pairs.size());
        if(pairs.isEmpty()){
            return 0;
        }

        for(Doublet d: pairs){
            p.add(d);
        }

        return CalculateEmailScore.calculate(p);
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

    public HashSet<String> getUniqueWords(){
        if(uniqueWords != null){
            return uniqueWords;
        }else{
            // if text has not been parsed
            try{
                parse();
                return uniqueWords;
            }catch (Exception e){
                return null;
            }
        }
    }

    public HashSet<String> getUniquePhrases(){
        if(uniquePhrases != null){
            return uniquePhrases;
        }else{
            // if text has not been parsed
            try{
                parse();
                return uniquePhrases;
            }catch (Exception e){
                return null;
            }
        }
    }

    private String NGram(int index, int N){
        String phrase = "";
        for(int i = 0; i < N; i++){
            phrase += text.get(i + index);
        }
        //uniquePhrases.add(phrase);
        return phrase;
    }

    /*
     * The goal of this thread is to handle the lookups for each string
     * and to add them to the pairs ArrayList
     */
    private class parseThread extends Thread{

        parseThread(){
            super("parseThread");
            start();
        }

        public void run(){
            pThreadDone = false;
            System.out.println(1);
            ArrayList<Integer> grams = db.getWordcounts();
            int lastIndex = text.size() - 1;
            for(int index = 0; index <= lastIndex; index++){
                if(uniqueWords.add(text.get(index))){
                    //wordsToFind is thread safe
                    wordsToFind.add(text.get(index));
                    //System.out.println(text.get(index));
                }

                for(int N : grams){
                    if((index + N - 1) <= lastIndex){
                        NPhrase np = new NPhrase();
                        np.num = N;
                        np.phrase = NGram(index, N);
                        //System.out.println(np.phrase);
                        phraseToFind.add(np);
                    }
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("thread done.");
            pThreadDone = true;
        }
    }

    /*
     * The goal of this thread is to handle the lookups for each string
     * and to add them to the pairs ArrayList
     */
    private class wordThread extends Thread{

        wordThread(){
            super("wordThread");
            start();
        }

        public void run(){
            while(!pThreadDone){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(2);
            if (!wordsToFind.isEmpty()) {
                Word w = findWord(wordsToFind.poll());
                    while(w == null && !wordsToFind.isEmpty()){
                        w = findWord(wordsToFind.poll());
                    }
                System.out.println(w.getWord());
                if (w != null) {
                    pairs.add(new Doublet(w.getConf(), w.getNorm()));
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("thread done.");
        }
    }
    /*
     * Struct-like inner class to hold a phrase and its number of words
     * This exists in order to facilitate threading more easily
     */
    public class NPhrase{
        protected String phrase;
        protected int num;

    }
}
