package scanner.dbEntry;

import scanner.Phrase;
import scanner.filtering.Hasher;
import scanner.filtering.LuceneStemmer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cdeck_000 on 10/5/2016.
 * Edited by cbporch on 10.13.16
 * Launches a GUI and processes the input from the gui into the database
 */
public class DatabaseInput {


    private static JLabel successLabel;
    private static JButton submitButton;
    private static Boolean phraseProbFieldFocus = false;
    private static Boolean phraseTextFieldFocus = false;
    private static Boolean wordProbFieldFocus = false;
    private static Boolean wordTextFieldFocus = false;
    private static JButton newPhraseBtn = new JButton("New Phrase");
    private static JButton newWordBtn = new JButton("New Word");
    private static JButton uploadFileBtn = new JButton("Upload File");
    private static String phraseHintText = "Enter phrase here..";
    private static String probHintText = "Enter probability..";


    /**
     * Empty constructor
     */
    protected DatabaseInput() {
    }

    /**
     * Makes the GUI
     * @param pane - the gui reference
     */
    private static void addComponentsToPane(Container pane) {
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
        final JRadioButton synBtn = new JRadioButton("Synonyms?");
        final JRadioButton numDependentBtn = new JRadioButton("# Dependent?");
        final JTextField probField = new JTextField(probHintText);
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
        pane.add(newWordPanel);

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
        final JTextField phraseTextField = new JTextField(phraseHintText);
        phraseTextField.setForeground(Color.LIGHT_GRAY);
        phraseTextField.setMinimumSize(new Dimension(350, 30));
        phraseTextField.setMaximumSize(new Dimension(350, 30));
        phraseTextField.setPreferredSize(new Dimension(350, 30));
        final JRadioButton phraseSynBtn = new JRadioButton("Synonyms?");
        final JRadioButton phraseNumDependentBtn = new JRadioButton("# Dependent?");
        final JTextField phraseProbField = new JTextField(probHintText);
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

        phraseOptions.add(phraseTextField);
        phraseOptions.add(phraseSynBtn);
        phraseOptions.add(phraseNumDependentBtn);
        phraseOptions.add(phraseProbField);

        JPanel newPhrasePanel = new JPanel();
        newPhrasePanel.setBackground(Color.WHITE);
        pane.add(newPhrasePanel);

        //bottom label
        JPanel submitPanel = new JPanel();
        submitButton = new JButton("Submit");

        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Trying...");

                //set up the words
                String word = wordsTextField.getText();
                ArrayList<String> wordInput = new ArrayList<String>();
                int wordSyn;
                if(synBtn.isSelected()){
                    wordSyn = 1;
                }
                else wordSyn = 0;

                int wordNumDep;
                if(numDependentBtn.isSelected()) {
                    wordNumDep = 1;
                }
                else {
                    wordNumDep = 0;
                }
                Double wordProb;
                if(probField.getText().equals(probHintText)) {
                    wordProb = -1.0;
                }
                else {
                    wordProb = Double.parseDouble(probField.getText());
                }
                System.out.println(wordProb);


                //set up the phrase
                String phrase = phraseTextField.getText();
                ArrayList<String> phraseInput = new ArrayList<String>();
                int phraseSyn;
                if(phraseSynBtn.isSelected()){
                    phraseSyn = 1;
                }
                else phraseSyn = 0;
                int phraseNumDep;
                if(phraseNumDependentBtn.isSelected()) {
                   phraseNumDep = 1;
                }
                else {
                    phraseNumDep = 0;
                }
                Double phraseProb;
                if(phraseProbField.getText().equals(probHintText)) {
                    phraseProb = -1.0;
                }
                else {
                    phraseProb = Double.parseDouble(phraseProbField.getText());
                }
                System.out.println(phraseProb);
                if(phrase.equals(phraseHintText)){
                    //do nothing
                }
                else{
                    phraseInput.add(phrase);
                }
                if(word.equals(phraseHintText)){
                    //do nothing
                }
                else{
                   wordInput.add(word);
                }
                try {
                    String[] words = wordInput.toArray(new String[wordInput.size()]);
                    String[] phrases = phraseInput.toArray(new String[phraseInput.size()]);
                    processInputSHA(words, phrases, wordProb, phraseProb, wordNumDep, phraseNumDep);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });


        newPhraseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("22");
            }
        });

        newWordBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("11");
            }
        });

        uploadFileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("44");
            }
        });

        successLabel = new JLabel("Test");
        submitPanel.add(submitButton);
        submitPanel.add(uploadFileBtn);
        submitPanel.add(successLabel);

        pane.add(submitPanel, BorderLayout.SOUTH);
    }

    /*private static void processInput(String[] words, String[] phrases) throws Exception {
        ArrayList<String>   stemmedWords,
                dbHashedWords,
                dbHashedPhrases,
                unique_words = new ArrayList<>();
        ArrayList<Phrase>   stemmedPhrases = new ArrayList<>(),
                unique_phrases = new ArrayList<>();
        LuceneStemmer ls = new LuceneStemmer();

        try {
            dbHashedWords = Database.getWords();
            dbHashedPhrases = Database.getPhrases();

            // move array into ArrayList for method call
            ArrayList<String> w = new ArrayList<>(Arrays.asList(words));

            stemmedWords = ls.stemWords(w);

            if(stemmedWords.size() != 0) {
                boolean duplicate = false, empty = true;
                int count = 1;
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
                        Database.insertWords(hashedWord, RARITY);
                    }
                    System.out.println("\nWords inserted");
                }
            }

            // stem phrases before checking in database, maintaining word count for each phrase
            for(String phrase: phrases){
                stemmedPhrases.add(new Phrase(ls.stemPhrase(phrase), phrase.split("\\s+").length));
            }

            // find unique phrases in input
            if(stemmedPhrases.size() != 0) {
                boolean duplicate = false, empty = true;
                int count = 1;
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
                        Database.insertPhrases(phrase.getPhrase(), RARITY, phrase.getWordcount());
                    }
                    System.out.println("\nPhrases inserted");
                }

            }
            System.out.println("Processing complete");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    public static void processInputSHA(String[] words, String[] phrases, double wordProb, double phraseProb, int wordNumDep, int phraseNumDep) throws Exception {
        ArrayList<String>   stemmedWords,
                            dbHashedWords,
                            dbHashedPhrases,
                            unique_words   = new ArrayList<>();
        ArrayList<Phrase>   stemmedPhrases = new ArrayList<>(),
                            unique_phrases = new ArrayList<>();
        LuceneStemmer ls = new LuceneStemmer();

        if(wordProb == -1.0){
            wordProb = 1.0;
        }
        if(phraseProb == -1.0){
            phraseProb = 1.0;
        }

        try {
            dbHashedWords = Database.getWords();
            dbHashedPhrases = Database.getPhrases();

            // move array into ArrayList for method call
            ArrayList<String> w = new ArrayList<>(Arrays.asList(words));

            stemmedWords = ls.stemWords(w);

            if(stemmedWords.size() != 0) {
                boolean duplicate = false, empty = true;
                int count = 1;
                String hashedInputWord;

                System.out.print("Checking word ");

                for (String inputWord : stemmedWords) {
                    System.out.print(count++ + ", ");
                    hashedInputWord = Hasher.hashSHA(inputWord);        // hash the inputted word
                    System.out.println("Hashed word: " + hashedInputWord);
                    if (dbHashedWords != null) {
                        for(String hash: dbHashedWords) {
                            if (!duplicate && hash.equals(hashedInputWord)) {
                                // once a match is found, we no longer need to check each word
                                duplicate = true; // should stop if statement from running when it hits a duplicate
                            }
                        }
                    }

                    if(!duplicate){ // word is not in database
                        unique_words.add(hashedInputWord);
                        empty = false;
                    }
                    duplicate = false; // reset variable
                }

                if(!empty) {
                    // hash unique words
                    // unique_words = StringToHash.(unique_words);
                    for (String hashedWord : unique_words) {
                        Database.insertWords(hashedWord, wordProb, wordNumDep);
                    }
                    System.out.println("\nWords inserted");
                }
            }

            // stem phrases before checking in database, maintaining word count for each phrase
            for(String phrase: phrases){
                stemmedPhrases.add(new Phrase(ls.stemPhrase(phrase), phrase.split("\\s+").length));
            }

            // find unique phrases in input
            if(stemmedPhrases.size() != 0) {
                boolean duplicate = false, empty = true;
                int count = 1;
                String hashedInputPhrase;

                System.out.print("Checking phrase ");

                for (Phrase inputPhrase : stemmedPhrases) {
                    hashedInputPhrase = Hasher.hashSHA(inputPhrase.getPhrase());    // hash one of the inputted phrases
                    System.out.print(count++ + ", ");                               // increment count
                    if (dbHashedPhrases != null) {
                        for(String hash : dbHashedPhrases) {
                            if (!duplicate && hash.equals(hashedInputPhrase)) {
                                duplicate = true;
                            }
                        }
                    }

                    if(!duplicate){
                        // reset phrase as its hashed form and add to ArrayList
                        inputPhrase.setPhrase(hashedInputPhrase);
                        unique_phrases.add(inputPhrase);
                        empty = false;
                    }
                }

                if(!empty) {
                    // hash unique phrases
                   // unique_phrases = StringToHash.getPhraseHashes(unique_phrases);
                    for (Phrase phrase: unique_phrases) {
                        Database.insertPhrases(phrase.getPhrase(), phraseProb, phrase.getWordcount(), phraseNumDep);
                    }
                    System.out.println("\nPhrases inserted");
                }

            }
            System.out.println("Processing complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will take in a file name and it will go through it if its a CSV file and seperate it into an arraylist.
     * You must enter either a single words only file, or a phrases only file.
     */
    public static ArrayList<String> interpretCSVFile(String filename)
    {
        BufferedReader br;
        ArrayList<String> listOfWords = new ArrayList<>(); //Returned list of all the words in this file.
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] unsortedWords = line.split(",");
                listOfWords = new ArrayList<>(Arrays.asList(unsortedWords));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return listOfWords;
    }

    public static void main(String[] args) {
        //Create and set up the window.
        JFrame frame = new JFrame("Database Input");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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
}