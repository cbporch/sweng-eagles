package scanner.dbEntry;


import scanner.filter.Hasher;
import scanner.filter.StringToHash;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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


    public DatabaseInput() {

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

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://asrcemail.cfz28h3zsskv.us-east-1.rds.amazonaws.com/asrcemail";
        String username = "asrc";
        String password = "rOwan!Sw3ng?";
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connected");
        return conn;
    }

    public static void insertWords(String wordIn, int rarityIn) throws Exception {
        final String word = wordIn;
        final int rarity = rarityIn;

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("insert into Words (word, rarity) Values ('%s', %2d);", word, rarity);
            System.out.println(sql);
            statement.executeUpdate(sql);                   //execute the update
            System.out.println("insert completed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertPhrases(String phraseIn, int rarityIn) throws Exception {
        final String phrase = phraseIn;
        final int rarity = rarityIn;
        final int count = phraseIn.split("\\s+").length;

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("insert into Phrases (phrase, rarity, count) Values ('%s', %2d, %2d);", phrase, rarity, count);
            System.out.println(sql);
            statement.executeUpdate(sql);                   //execute the update
            System.out.println("insert completed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<String> getWords() throws Exception {
        ArrayList<String> words = new ArrayList<String>();

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("select word from Words");
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                words.add(rs.getString(1));
            }
//            System.out.println(words);
//            System.out.println("select words completed");
            return words;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public static ArrayList<String> getPhrases() throws Exception {
        ArrayList<String> phrases = new ArrayList<String>();

        try {
            Connection conn = getConnection();              //get connection
            Statement statement = conn.createStatement();   //create statement
            String sql = String.format("select phrase from Phrases");
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);     //execute the select query
            while (rs.next()) {
                phrases.add(rs.getString(1));
            }
//            System.out.println(phrases);
//            System.out.println("select phrases completed");
            return phrases;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void processInput(String[] words, String[] phrases) throws Exception {
        //TODO: loop through input String array, and check each against database before adding
        ArrayList<String>   stemmedWords,
                            stemmedPhrases,
                            dbHashedWords,
                            dbHashedPhrases;
        String[] unique_words = new String[words.length],
                 unique_phrases = new String[phrases.length];

        try {
            dbHashedWords = getWords();
            dbHashedPhrases = getPhrases();

            if(words.length != 0) {
                boolean duplicate = false, empty = true;
                int i = 0;
                for (String inputWord : words) {
                    for(String hash: dbHashedWords) {
                        if (!duplicate && Hasher.checkHash(inputWord, hash)) {
                            // once a match is found, we no longer need to check each word
                            duplicate = true; // should stop if statement from running
                        }
                    }
                    if(!duplicate){ // word is not in database
                        unique_words[i++] = inputWord;
                        empty = false;
                    }
                    duplicate = false; // reset variable
                }

                if(!empty) {
                    stemmedWords = StringToHash.getHashes(unique_words, false);
                    for (String hashedWord : stemmedWords) {
                        insertWords(hashedWord, RARITY);
                    }
                }
            }


            if(phrases.length != 0) {
                boolean duplicate = false, empty = true;
                int i = 0;
                for (String inputPhrase : phrases) {
                    for(String hash : dbHashedPhrases) {
                        if (!duplicate && Hasher.checkHash(inputPhrase, hash)) {
                            duplicate = true;
                        }
                    }
                    if(!duplicate){
                        unique_words[i++] = inputPhrase;
                        empty = false;
                    }
                }

                if(!empty) {
                    stemmedPhrases = StringToHash.getHashes(unique_phrases, true);
                    for (String hashedPhrase : stemmedPhrases) {
                        insertPhrases(hashedPhrase, RARITY);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

