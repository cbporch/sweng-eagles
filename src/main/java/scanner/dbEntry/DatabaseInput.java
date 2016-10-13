package scanner.dbEntry;


import scanner.filter.StringToHash;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by cdeck_000 on 10/5/2016.
 * Edited by cbporch on 10.9.16
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


    public DatabaseInput() {

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //word storing and cutting off white space
                word = wordsTextField.getText();
                word = word.replaceAll("\\s+", "");

                // separate the words into its own index in an array
                words = word.split(",");

                // print out original word and the now separated words
                //System.out.println(word);
                //for (String word : words) {
                //    word = word.replaceAll(" ", "");   // remove spaces
                //    System.out.println(word);
                //}

                //phrase storing
                phrase = phrasesTextField.getText();

                //split each phrase into its own index in an array
                phrases = phrase.split(",");

                //print out the original phrases unchanged
                //System.out.println(phrase);

                //print out all the phrases one by one
                // System.out.println(phrases.toString());              -> this was printing a toString of an array
                //for (String word : phrases) {
                //    word = word.replaceAll("\\A\\s+\\b", "");       // remove all spaces before the first word boundary
                //    System.out.println(word);                       // before, it was removing all spaces between words
                //}

                try {
                    for(String word: words) {
                        insertWords(word, 1);
                    }
                    for(String phrase: phrases) {
                        if(phrase.substring(0,1).equals(" "))
                            phrase = phrase.substring(1,phrase.length());
                        insertPhrases(phrase, 1);
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }

                //have lucene run through the inputs to take out filler words before going into the database
                //word with number is more confidential
                processInput(words, phrases);
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

        try{
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

        try{
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

    public void processInput(String[] words, String[] phrases) {
        //TODO: loop through input String array, and check each against database
        StringToHash sth = new StringToHash();
        ArrayList<String> stemmedWords, stemmedPhrases;
        try {
            stemmedWords   = sth.getHashes(words, false);
            stemmedPhrases = sth.getHashes(phrases, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

