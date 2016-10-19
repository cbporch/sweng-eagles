package scanner.dbEntry;


import scanner.filter.Hasher;
import scanner.filter.LuceneStemmer;
import scanner.filter.Phrase;
import scanner.filter.StringToHash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
    private static JLabel successLabel;
    private static JButton submitButton2;
    private static Boolean phraseProbFieldFocus = false;
    private static Boolean phraseTextFieldFocus = false;
    private static Boolean wordProbFieldFocus = false;
    private static Boolean wordTextFieldFocus = false;
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

    /*public static void main(String[] args) {
        JFrame frame = new JFrame("DatabaseInput");
        frame.setContentPane(new DatabaseInput().container);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

       *//* //Create the frame.
        JFrame frame = new JFrame("FrameDemo");
        //Set what happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //3. Create components and put them in the frame.
        //...create emptyLabel...
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        //Size the frame.
        frame.pack();
        //Show it.
        frame.setVisible(true);*//*
    }*/

    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Enter the words/phrases to be inputted below");
        instructionsPanel.add(instructions);
        pane.add(instructionsPanel);


        // words input panel
        JPanel wordsInputPanel = new JPanel();
        wordsInputPanel.setBackground(Color.WHITE);
        pane.add(wordsInputPanel, new BoxLayout(pane, BoxLayout.Y_AXIS));
        JLabel wordsLabel = new JLabel("Words");
        wordsLabel.setFont(new Font("Serif", Font.BOLD, 16));
        wordsLabel.setForeground(Color.BLACK);
        wordsInputPanel.add(wordsLabel);

        //words text field and options
        JPanel wordOptions = new JPanel();
        wordsInputPanel.add(wordOptions, new BoxLayout(pane, BoxLayout.X_AXIS));
        final JTextField wordsTextField = new JTextField("Enter word here...");
        wordsTextField.setForeground(Color.LIGHT_GRAY);
        wordsTextField.setMinimumSize(new Dimension(350, 30));
        wordsTextField.setMaximumSize(new Dimension(350, 30));
        wordsTextField.setPreferredSize(new Dimension(350, 30));
        JRadioButton synBtn = new JRadioButton("Synonyms?");
        JRadioButton numDependentBtn = new JRadioButton("# Dependent?");
        final JTextField probField = new JTextField("Enter probability..");
        probField.setForeground(Color.LIGHT_GRAY);
        probField.setMinimumSize(new Dimension(100, 30));
        probField.setMaximumSize(new Dimension(100, 30));
        probField.setPreferredSize(new Dimension(100, 30));
        //look into hint text
        wordOptions.add(wordsTextField);
        wordOptions.add(synBtn);
        wordOptions.add(numDependentBtn);
        wordOptions.add(probField);

        wordsTextField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                wordsTextField.setText("");
                wordTextFieldFocus = true;
                wordsTextField.setForeground(Color.BLACK);
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });

        probField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                probField.setText("");
                wordProbFieldFocus = true;
                probField.setForeground(Color.BLACK);
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });

        JPanel newWordPanel = new JPanel();
        newWordPanel.setBackground(Color.WHITE);
        JButton newWordBtn = new JButton("New Word");
        newWordPanel.add(newWordBtn);
        pane.add(newWordPanel);

        newWordBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("11");
            }
        });

        // phrases input panel
        JPanel phrasesInputPanel = new JPanel();
        phrasesInputPanel.setBackground(Color.WHITE);
        pane.add(phrasesInputPanel, new BoxLayout(pane, BoxLayout.Y_AXIS));
        JLabel phrasesLabel = new JLabel("Phrases");
        phrasesLabel.setFont(new Font("Serif", Font.BOLD, 16));
        phrasesLabel.setForeground(Color.BLACK);
        phrasesInputPanel.add(phrasesLabel);

        //phrase text field and options

        JPanel phraseOptions = new JPanel();
        phrasesInputPanel.add(phraseOptions, new BoxLayout(pane, BoxLayout.X_AXIS));
        final JTextField phraseTextField = new JTextField("Enter phrase here..");
        phraseTextField.setForeground(Color.LIGHT_GRAY);
        phraseTextField.setMinimumSize(new Dimension(350, 30));
        phraseTextField.setMaximumSize(new Dimension(350, 30));
        phraseTextField.setPreferredSize(new Dimension(350, 30));
        JRadioButton phraseSynBtn = new JRadioButton("Synonyms?");
        JRadioButton phraseNumDependentBtn = new JRadioButton("# Dependent?");
        final JTextField phraseProbField = new JTextField("Enter probability..");
        phraseProbField.setForeground(Color.LIGHT_GRAY);
        phraseProbField.setMinimumSize(new Dimension(100, 30));
        phraseProbField.setMaximumSize(new Dimension(100, 30));
        phraseProbField.setPreferredSize(new Dimension(100, 30));

        phraseTextField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                phraseTextField.setText("");
                phraseTextFieldFocus = true;
                phraseTextField.setForeground(Color.BLACK);
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });

        phraseProbField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                phraseProbField.setText("");
                phraseProbFieldFocus = true;
                phraseProbField.setForeground(Color.BLACK);
            }

            public void focusLost(FocusEvent e) {
                // nothing
            }
        });

        //look into hint text
        phraseOptions.add(phraseTextField);
        phraseOptions.add(phraseSynBtn);
        phraseOptions.add(phraseNumDependentBtn);
        phraseOptions.add(phraseProbField);

        JPanel newPhrasePanel = new JPanel();
        newPhrasePanel.setBackground(Color.WHITE);
        JButton newPhraseBtn = new JButton("New Phrase");
        newPhrasePanel.add(newPhraseBtn);
        pane.add(newPhrasePanel);

        newPhraseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("22");
            }
        });

        //bottom label
        JPanel submitPanel = new JPanel();
        submitButton2 = new JButton("Submit");
        successLabel = new JLabel("Test");
        JButton uploadFileBtn = new JButton("Upload File");
        submitPanel.add(submitButton2);
        submitPanel.add(uploadFileBtn);
        submitPanel.add(successLabel);

        pane.add(submitPanel, BorderLayout.SOUTH);

        submitButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("45");
            }
        });

        uploadFileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("44");
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DatabaseGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Database Input");
        frame.setResizable(true);
        frame.setMaximumSize(new Dimension(700, 500));
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setPreferredSize(new Dimension(700, 500));
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
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
                            if (!duplicate && Hasher.checkHash(inputWord, hash)) {
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
                            if (!duplicate && Hasher.checkHash(inputPhrase.getPhrase(), hash)) {
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

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

