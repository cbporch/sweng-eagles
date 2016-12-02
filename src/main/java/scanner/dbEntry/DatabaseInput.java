package scanner.dbEntry;

import scanner.Phrase;
import scanner.Word;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by cdeck_000 on 10/5/2016.
 * Edited by cbporch on 10.13.16
 * Launches a GUI and processes the input from the gui into the database
 */
public class DatabaseInput {
    private static JLabel successLabel;
    final private static JButton submitButton = new JButton("Submit");
    private static JButton uploadFileBtn = new JButton("Import CSV File");
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
    protected static Database db;

    /**
     * Empty constructor
     */
    public DatabaseInput() {

        db = new Database();

    }

    /**
     * Makes the GUI
     * @param pane - the gui reference
     */
    private void addComponentsToPane(Container pane) {
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
                if(wordsTextField.getText().equals(wordsHintText)){
                    wordsTextField.setText("");
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
                //launch log in screen
                acceptInput();
            }
        });

        uploadFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                if (e.getSource() == DatabaseInput.uploadFileBtn) {
                    int returnVal = fc.showOpenDialog(uploadFileBtn);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        System.out.println("Opening: " + file.getName() + ".%n");
                        CSVFileReader csvfr = new CSVFileReader();
                        csvfr.interpretCSVFile(file + "");
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
    public void processWordsSHA(ArrayList<Word> words) throws Exception {
          try{
              for(Word word: words) {
                  db.insertWords(word.getWord(), word.getRarity(), word.getNum());
              }
          } catch (Exception e) {
              if(e instanceof SQLException){
                  if(((SQLException) e).getErrorCode()==1062) {
                      //do nothing
                  }
              }
              else System.out.println(e);                     //print the exception
          }
    }

    /**
     * Processes the input. Hashes the words/phrases and inserts them to the database if they aren't in there already
     * @param phrases - an array of phrases captured in the GUI
     * @throws Exception
     */
    public static void processPhrasesSHA(ArrayList<Phrase> phrases) throws Exception {
        try {
            for (Phrase phrase : phrases) {
                   db.insertPhrases(phrase.getPhrase(), phrase.getRarity(), phrase.getWordcount(), phrase.getNum());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the GUI.
     * @param args - not necessary
     */
    public static void main() {
        //Create and set up the window.
        JFrame frame = new JFrame("Database Input");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setMaximumSize(new Dimension(700, 500));
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setPreferredSize(new Dimension(700, 500));
        DatabaseInput databaseInput = new DatabaseInput();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("red_team.jpeg")));
            frame.setIconImage(icon.getImage());
        } catch(Exception e) {
            System.out.println(e);
        }
        //Set up the content pane.
        databaseInput.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Frame closing...");
                wordsTextField.setText(wordsHintText);
                phraseTextField.setText(phraseHintText);
                probField.setText(probHintText);
                phraseProbField.setText(probHintText);
                try {
                    db.close();
                } catch (Exception ex){
                    System.out.println(ex);
                }
            }
        });
    }

    public void acceptInput() {
        //System.out.println("Trying...");
        ArrayList<Word> words = new ArrayList<>();
        ArrayList<Phrase> phrases = new ArrayList<>();

        //set up the words
        if (wordsTextField.getText().equals(wordsHintText)) {
            //do nothing
        } else {
            Word word = new Word();
            word.setWord(wordsTextField.getText());

            if (numDependentBtn.isSelected()) {
                word.setNum(1);
            } else word.setNum(0);

            if (probField.getText().equals(probHintText)) {
                word.setRarity(1);
            } else word.setRarity(Float.parseFloat(probField.getText()));

            if (synBtn.isSelected()) {
                //insert synonyms
            }
            words.add(word);
        }

        //set up the phrases
        if (phraseTextField.getText().equals(phraseHintText)) {
            //do nothing
        } else {
            Phrase phrase = new Phrase();
            phrase.setPhrase(phraseTextField.getText());

            if (phraseNumDependentBtn.isSelected()) {
                phrase.setNum(1);
            } else phrase.setNum(0);

            if (phraseProbField.getText().equals(probHintText)) {
                phrase.setRarity(1);
            } else phrase.setRarity(Float.parseFloat(phraseProbField.getText()));

            phrase.setWordcount(phraseTextField.getText().split("\\s+").length);
            phrases.add(phrase);
        }
        try {
            processWordsSHA(words);
            //System.out.println("Words Processing Complete");
            processPhrasesSHA(phrases);
            //System.out.println("Phrase Processing Complete");
        } catch (Exception e) {
            if (e instanceof SQLException) {
                if (((SQLException) e).getErrorCode() == 1062) {
                    //do nothing
                }
            } else System.out.println(e);                     //print the exception
        }
        successLabel.setText("Processing complete");
    }
}