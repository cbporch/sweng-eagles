package scanner.dbEntry;


import scanner.filter.Hasher;
import scanner.filter.LuceneStemmer;
import scanner.filter.Phrase;
import scanner.filter.StringToHash;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import static scanner.dbEntry.Database.*;


/**
 * Created by cdeck_000 on 10/5/2016.
 * Edited by cbporch on 10.13.16
 */
public class DatabaseInput {

    private JPanel container;
    private JLabel titleLabel;
    private JLabel wordInputLabel;
    private JTextField wordsTextField;
    private JLabel phrasesInputLabel;
    private JTextField phrasesTextField;
    private JButton submitButton;
    private JTextArea noteText2;
    private String word;
    private String[] words;
    private String phrase;
    private String[] phraseToWords;
    private String[] phrases;
    private final int RARITY = 10;


    protected DatabaseInput() {

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //word storing and cutting off white space
                word = wordsTextField.getText();
                word = word.replaceAll("\\s+", "");

                // separate the words into its own index in an array
                words = word.split(",");

                //phrase storing
                phrase = phrasesTextField.getText();

                //split each phrase into its own index in an array
                phrases = phrase.split(",");

                //have lucene run through the inputs to take out filler words before going into the database
                //word with number is more confidential
                try {
                    // remove empty input
                    if(phrases.length == 1 && phrases[0].equals("")){
                        phrases = new String[0];
                    }
                    if(words.length == 1 && words[0].equals("")){
                        words = new String[0];
                    }
                        processInput(words, phrases);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DatabaseInput");
        frame.setContentPane(new DatabaseInput().container);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void processInput(String[] words, String[] phrases) throws Exception {
        ArrayList<String>   stemmedWords = new ArrayList<String>(),
                dbHashedWords,
                dbHashedPhrases,
                unique_words = new ArrayList<String>();
        ArrayList<Phrase>   stemmedPhrases = new ArrayList<Phrase>(),
                unique_phrases = new ArrayList<Phrase>();

        try {
            dbHashedWords = getWords();
            dbHashedPhrases = getPhrases();

            // move array into ArrayList for method call
            ArrayList<String> w = new ArrayList<>();
            for (String word : words){
                w.add(word);
            }
            stemmedWords = LuceneStemmer.stemWords(w);

            if(stemmedWords.size() != 0) {
                boolean duplicate = false, empty = true;
                int i = 0, count = 1;
                System.out.print("Checking word ");
                for (String inputWord : stemmedWords) {
                    System.out.print(count++ + ", ");
                    if (dbHashedWords != null) {
                        for(String hash: dbHashedWords) {
                            if (!duplicate && Hasher.checkHashBCrypt(inputWord, hash)) {
                                // once a match is found, we no longer need to check each word
                                duplicate = true; // should stop if statement from running when it hits a duplicate
                            }
                        }
                    }

                    if(!duplicate){ // word is not in database
                        unique_words.add(inputWord);
                        empty = false;
                    }
                    duplicate = false; // reset variable
                }

                if(!empty) {
                    // hash unique words
                    unique_words = StringToHash.getHashes(unique_words);
                    for (String hashedWord : unique_words) {
                        insertWords(hashedWord, RARITY);
                    }
                    System.out.println("\nWords inserted");
                }
            }

            // stem phrases before checking in database, maintaining word count for each phrase
            for(String phrase: phrases){
                stemmedPhrases.add(new Phrase(LuceneStemmer.stemPhrase(phrase), phrase.split("\\s+").length));
            }

            // find unique phrases in input
            if(stemmedPhrases.size() != 0) {
                boolean duplicate = false, empty = true;
                int i = 0, count = 1;
                System.out.print("Checking phrase ");
                for (Phrase inputPhrase : stemmedPhrases) {
                    System.out.print(count++ + ", ");
                    if (dbHashedPhrases != null) {
                        for(String hash : dbHashedPhrases) {
                            if (!duplicate && Hasher.checkHashBCrypt(inputPhrase.getPhrase(), hash)) {
                                duplicate = true;
                            }
                        }
                    }
                    if(!duplicate){
                        unique_phrases.add(inputPhrase);
                        empty = false;
                    }
                }

                if(!empty) {
                    // hash unique phrases
                    unique_phrases = StringToHash.getPhraseHashes(unique_phrases);
                    for (Phrase phrase: unique_phrases) {
                        insertPhrases(phrase.getPhrase(), RARITY, phrase.getWordcount());
                    }
                    System.out.println("\nPhrases inserted");
                }

            }
            System.out.println("Processing complete");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

