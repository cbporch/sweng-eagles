package scanner;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by cdeck_000 on 10/5/2016.
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
                word = wordsTextField.getText().toString();
                word.replaceAll("\\s+","");

                //seperate the words into its own index in an array
                words = word.split(",");

                //print out original word and the now separated words
                System.out.println(word);
                for(String word: words){
                    System.out.println(word);
                }


                //phrase storing
                phrase = phrasesTextField.getText();

                //split each phrase into its own index in an array
                phrases = phrase.split(",");

                //split the phrases by white space and ignore commas
                phraseToWords = phrase.replaceAll(",", "").split("\\s+");

                //print out the original phrases unchanged
                System.out.println(phrase);

                //print out all the phrases one by one
                System.out.println(phrases.toString());
                for(String word: phrases){
                    word.replaceAll("\\s+","");
                    System.out.println(word);
                }

                //print out all the words in the phrases, one by one
                System.out.println(phraseToWords.toString());
                for(String word: phraseToWords){
                    word.replaceAll("\\s+","");
                    System.out.println(word);
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
}
