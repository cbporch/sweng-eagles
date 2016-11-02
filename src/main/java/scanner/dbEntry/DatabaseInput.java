package scanner.dbEntry;

import scanner.Phrase;
import scanner.Word;
import scanner.filtering.Hasher;
import scanner.filtering.LuceneStemmer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
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
    final private static JButton submitButton = new JButton("Submit");
    private static Boolean phraseProbFieldFocus = false;
    private static Boolean phraseTextFieldFocus = false;
    private static Boolean wordProbFieldFocus = false;
    private static Boolean wordTextFieldFocus = false;
    private static JButton uploadFileBtn = new JButton("Upload File");
    private static String phraseHintText = "Enter phrase here..";
    private static String probHintText = "Enter probability..";
    private static String wordsHintText = "Enter word here..";
    final private static  JTextField probField = new JTextField(probHintText);
    final private static JTextField wordsTextField = new JTextField(wordsHintText);
    final private static JRadioButton synBtn = new JRadioButton("Synonyms?");
    final private static JRadioButton numDependentBtn = new JRadioButton("# Dependent?");
    final private static JTextField phraseTextField = new JTextField(phraseHintText);
    final private static JTextField phraseProbField = new JTextField(probHintText);
    final private static JRadioButton phraseNumDependentBtn = new JRadioButton("# Dependent?");


    /**
     * Empty constructor
     */
    public DatabaseInput() {
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
        wordsLabel.setFont(new Font("Serif", Font.BOLD, 20));
        wordsLabel.setForeground(Color.BLACK);
        wordsInputPanel.add(wordsLabel);

        //words text field and options
        JPanel wordOptions = new JPanel();
        wordsInputPanel.add(wordOptions, new BoxLayout(pane, BoxLayout.X_AXIS));
        wordsTextField.setForeground(Color.LIGHT_GRAY);
        wordsTextField.setMinimumSize(new Dimension(300, 30));
        wordsTextField.setMaximumSize(new Dimension(300, 30));
        wordsTextField.setPreferredSize(new Dimension(300, 30));
        probField.setForeground(Color.LIGHT_GRAY);
        probField.setMinimumSize(new Dimension(100, 30));
        probField.setMaximumSize(new Dimension(100, 30));
        probField.setPreferredSize(new Dimension(100, 30));
        //look into hint text
        wordOptions.add(wordsTextField);
        wordOptions.add(synBtn);
        wordOptions.add(numDependentBtn);
        wordOptions.add(probField);

        /**
         * set the hint text for the textfield
         */
        wordsTextField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                System.out.println(wordsTextField.getText());
                if(wordsTextField.getText().equals(wordsHintText)){
                    wordsTextField.setText("");
                    wordTextFieldFocus = true;
                    wordsTextField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if(wordsTextField.getText().equals("")){
                    wordsTextField.setText(wordsHintText);
                    wordsTextField.setForeground(Color.LIGHT_GRAY);
                }

            }
        });

        /**
         * set the hint text for the textfield
         */
        probField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(probField.getText().equals(probHintText)){
                    probField.setText("");
                    wordProbFieldFocus = true;
                    probField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if(probField.getText().equals("")){
                    probField.setText(probHintText);
                    probField.setForeground(Color.LIGHT_GRAY);
                }

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
        phrasesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        phrasesLabel.setForeground(Color.BLACK);
        phrasesInputPanel.add(phrasesLabel);

        //phrase text field and options

        JPanel phraseOptions = new JPanel();
        phrasesInputPanel.add(phraseOptions, new BoxLayout(pane, BoxLayout.X_AXIS));
        phraseTextField.setForeground(Color.LIGHT_GRAY);
        phraseTextField.setMinimumSize(new Dimension(350, 30));
        phraseTextField.setMaximumSize(new Dimension(350, 30));
        phraseTextField.setPreferredSize(new Dimension(350, 30));
        phraseProbField.setForeground(Color.LIGHT_GRAY);
        phraseProbField.setMinimumSize(new Dimension(100, 30));
        phraseProbField.setMaximumSize(new Dimension(100, 30));
        phraseProbField.setPreferredSize(new Dimension(100, 30));

        /**
         * set the hint text for the textfield
         */
        phraseTextField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(phraseTextField.getText().equals(phraseHintText)){
                    phraseTextField.setText("");
                    phraseTextFieldFocus = true;
                    phraseTextField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if(phraseTextField.getText().equals("")){
                    phraseTextField.setText(phraseHintText);
                    phraseTextField.setForeground(Color.LIGHT_GRAY);
                }

            }
        });

        /**
         * set the hint text for the textfield
         */
        phraseProbField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(phraseProbField.getText().equals(probHintText)){
                    phraseProbField.setText("");
                    phraseProbFieldFocus = true;
                    phraseProbField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if(phraseProbField.getText().equals("")){
                    phraseProbField.setText(probHintText);
                    phraseProbField.setForeground(Color.LIGHT_GRAY);
                }

            }
        });

        phraseOptions.add(phraseTextField);
        phraseOptions.add(phraseNumDependentBtn);
        phraseOptions.add(phraseProbField);

        JPanel newPhrasePanel = new JPanel();
        newPhrasePanel.setBackground(Color.WHITE);
        pane.add(newPhrasePanel);

        //bottom label
        JPanel submitPanel = new JPanel();

        /**
         * When the submit button is hit, the code captures the input and processes it
         */
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptInput();
            }
        });


        uploadFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                System.out.println("In action listener");
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                    if (e.getSource() == DatabaseInput.uploadFileBtn) {
                        System.out.println("In first if");
                        int returnVal = fc.showOpenDialog(uploadFileBtn);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            System.out.println("In second if");
                            File file = fc.getSelectedFile();
                            //This is where a real application would open the file.
                            System.out.println("Opening: " + file.getName() + ".%n");
                            ArrayList<Word> words = CSVFileReader.interpretCSVFile(file+"");
                            for(Word word: words){
                                System.out.println(word.getWord());
                            }
                        } else {
                            System.out.println("Open command cancelled by user.%n");
                        }

                    }
                }
                //successLabel.setText("Feature not available yet.");
        });

        successLabel = new JLabel("");
        submitPanel.add(submitButton);
        submitPanel.add(uploadFileBtn);
        submitPanel.add(successLabel);


        pane.add(submitPanel, BorderLayout.SOUTH);
    }


    /**
     * Processes the input. Hashes the words/phrases and inserts them to the database if they aren't in there already
     * @param words - an array of words captured in the GUI
     * @throws Exception
     */
    public static void processWordsSHA(Word[] words) throws Exception {
            try {
                for (Word word : words) {
                    if (Database.getWord(word.getWord()) == null) {
                        Database.insertWords(word.getWord(), word.getRarity(), word.getNum());
                    }
                }
                System.out.println("Word Processing complete");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Processes the input. Hashes the words/phrases and inserts them to the database if they aren't in there already
     * @param phrases - an array of phrases captured in the GUI
     * @throws Exception
     */
    public static void processPhrasesSHA(Phrase[] phrases) throws Exception {
        try {
            for (Phrase phrase : phrases) {
                if (Database.getPhrase(phrase.getPhrase()) == null) {
                    Database.insertPhrases(phrase.getPhrase(), phrase.getRarity(), phrase.getWordcount(), phrase.getNum());
                }
            }
            System.out.println("Phrase Processing complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the GUI.
     * @param args - not necessary
     */
    public static void main(String[] args) {
        //Create and set up the window.
        JFrame frame = new JFrame("Database Input");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
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

    public static void acceptInput(){
        System.out.println("Trying...");
        ArrayList<Word> words = new ArrayList<>();
        ArrayList<Phrase> phrases = new ArrayList<>();

        //set up the words
        if(wordsTextField.getText().equals(wordsHintText)){
            //do nothing
        }
        else{
            Word word = new Word();
            word.setWord(wordsTextField.getText());

            if(numDependentBtn.isSelected()){
                word.setNum(1);
            } else word.setNum(0);

            if(probField.getText().equals(probHintText)){
                word.setRarity(1);
            } else word.setRarity(Float.parseFloat(probField.getText()));

            if(synBtn.isSelected()){
                //insert synonyms
            }
            words.add(word);
        }

        //set up the phrases
        if(phraseTextField.getText().equals(phraseHintText)){
            //do nothing
        }
        else{
            Phrase phrase = new Phrase();
            phrase.setPhrase(phraseTextField.getText());

            if(phraseNumDependentBtn.isSelected()){
                phrase.setNum(1);
            } else phrase.setNum(0);

            if(phraseProbField.getText().equals(probHintText)){
                phrase.setRarity(1);
            } else phrase.setRarity(Float.parseFloat(phraseProbField.getText()));

            int wordCount = phrase.getPhrase().split(" ").length;
            phrase.setWordcount(wordCount);
            phrases.add(phrase);
        }
        try {
            Word[] wordsInput = words.toArray(new Word[words.size()]);
            Phrase[] phrasesInput = phrases.toArray(new Phrase[phrases.size()]);
            //processInputSHA(words, phrases, wordProb, phraseProb, wordNumDep, phraseNumDep);
            processWordsSHA(wordsInput);
            processPhrasesSHA(phrasesInput);
            successLabel.setText("Processing complete");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}