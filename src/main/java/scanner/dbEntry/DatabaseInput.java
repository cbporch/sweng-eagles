package scanner.dbEntry;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;

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
                System.out.println(word);
                for (String word : words) {
                    word = word.replaceAll(" ", "");   // remove spaces
                    System.out.println(word);
                }

                //phrase storing
                phrase = phrasesTextField.getText();

                //split each phrase into its own index in an array
                phrases = phrase.split(",");

                //print out the original phrases unchanged
                System.out.println(phrase);

                //print out all the phrases one by one
                // System.out.println(phrases.toString());              -> this was printing a toString of an array
                for (String word : phrases) {
                    word = word.replaceAll("\\A\\s+\\b", "");       // remove all spaces before the first word boundary
                    System.out.println(word);                       // before, it was removing all spaces between words
                }

                //have lucene run through the inputs to take out filler words before going into the database
                //word with number is more confidential
                processWords(words, phrases);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DatabaseInput");
        frame.setContentPane(new DatabaseInput().container);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        try {
            getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://asrcemail.cfz28h3zsskv.us-east-1.rds.amazonaws.com";
        String username = "asrc";
        String password = "rOwan!Sw3ng?";
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connected");
        return null;
    }

    public void processWords(String[] words, String[] phrases){
        //TODO: pass output to StringToHash
        //TODO: loop through String array, hash each, and check each against database
    }
}

