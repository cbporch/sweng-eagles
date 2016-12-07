package scanner.analysis;

import scanner.Email;
import scanner.dbEntry.Database;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by cdeck_000 on 10/19/2016.
 */

/**
 * Create a simple GUI to input an email to be scanned. A score for the email will be shown on screen.
 */
public class AdminEmailTestWindow
{
    static Database db = new Database();
    static int tracker;
    static Email[] historyBuffer;
    static Email currentEmail;
    static Email unprocessedEmail;
    static final JTextArea textArea = new JTextArea();

    /**
     * Add the components to the GUI.
     * @param pane - the pane for the GUI
     */
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Current Email");
        instructionsPanel.setBackground(Color.LIGHT_GRAY);
        instructionsPanel.add(instructions);
        pane.add(instructionsPanel, BorderLayout.NORTH);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBackground(Color.LIGHT_GRAY);
        textArea.setBackground(Color.WHITE);
        textArea.setEditable(false);
        textArea.setMinimumSize(new Dimension(400,350));
        textArea.setMaximumSize(new Dimension(400,350));
        textArea.setPreferredSize(new Dimension(400,350));
        textArea.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(border);
        textArea.requestFocusInWindow();

        textArea.setMinimumSize(new Dimension(500, 200));
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textAreaPanel.add(textArea);
        pane.add(textAreaPanel, BorderLayout.CENTER);

        JPanel scoringPanel = new JPanel();
        final JButton btnCleanEmail = new JButton("Clean Email");
        final JButton btnConfidential = new JButton("Confidential Email");
        final JButton btnBack = new JButton("Back");
        final JButton btnForward = new JButton("Forward");
        scoringPanel.add(btnCleanEmail);
        scoringPanel.add(btnConfidential);
        scoringPanel.add(btnBack);
        scoringPanel.add(btnForward);

        pane.add(scoringPanel, BorderLayout.SOUTH);

        ActionListener confidentiality = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (btnConfidential.equals(arg0.getSource()))
                    historyBuffer[tracker].setConfidential(true);
                else if (btnCleanEmail.equals(arg0.getSource()))
                    historyBuffer[tracker].setConfidential(false);

                updateHistory(historyBuffer[tracker]);

                if (tracker == 3)
                {
                    try
                    {
                        historyBuffer[3] = db.getNextEmail();
                    }
                    catch (Exception e)
                    {
                        System.err.println(e);
                    }
                }

                loadTextField(historyBuffer[incrementTracker()]);
            }
        } ;

       ActionListener travel = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (btnBack.equals(arg0.getSource()))
                    decrementTracker();
                else if (btnForward.equals(arg0.getSource()))
                    incrementTracker();

                loadTextField(historyBuffer[tracker]);
            }
        } ;

        btnConfidential.addActionListener(confidentiality);
        btnCleanEmail.addActionListener(confidentiality);
        btnBack.addActionListener(travel);
        btnForward.addActionListener(travel);
    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Algorithm Training");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setTitle("Algorithm Training");
        frame.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.out.println("Frame closing...");
                try {
                    db.close();
                } catch (Exception e){
                    System.err.println(e);
                }
            }
        });
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                tracker = 3;
                historyBuffer  = new Email[4];
                createAndShowGUI();
                loadNextEmail();
            }
        });
    }

    static void updateHistory(Email email)
    {
        Email processingEmail = historyBuffer[0];

        if (processingEmail != null)
        {
            if (processingEmail.isConfidential())
                incrementConfidentialColumn(processingEmail.getEmailText());
            else
                incrementNormalColumn(processingEmail.getEmailText());
        }

        historyBuffer[0] = historyBuffer[1];
        historyBuffer[1] = historyBuffer[2];
        historyBuffer[2] = email;

        try
        {
            //db.removeEmailById(processingEmail.getId());
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }

    protected static void incrementConfidentialColumn(String body)
    {
        // Call lucene here, this is where the body of the email is sent to increase the corresponding words' conf value
        try {
            Database db = new Database();
            TextParser tepa = new TextParser(body);
            tepa.parse();

            HashSet<String> w = tepa.getUniqueWords();
            HashSet<String> p = tepa.getUniquePhrases();

            for(String word : w){
               db.incrementWordConf(word);
            }

            for(String phrase : p){
                db.incrementPhraseConf(phrase, phrase.split("\\s+").length);
            }
        } catch (Exception e) {

        }
    }

    protected static void incrementNormalColumn(String body)
    {
        try {
            Database db = new Database();
            TextParser tepa = new TextParser(body);
            tepa.parse();

            HashSet<String> w = tepa.getUniqueWords();
            HashSet<String> p = tepa.getUniquePhrases();

            for(String word : w){
                db.incrementWordNorm(word);
            }

            for(String phrase : p){
                db.incrementPhraseNorm(phrase, phrase.split("\\s+").length);
            }
        } catch (Exception e) {

        }
    }

    static void loadNextEmail()
    {
        try
        {
            historyBuffer[3] = db.getNextEmail();
            loadTextField(historyBuffer[3]);
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    static void loadTextField(Email email)
    {
        textArea.setText(email.getEmailText());
    }

    static int decrementTracker()
    {
        if (tracker > 0)
        {
            if (historyBuffer[tracker - 1] != null)
                return --tracker;
            else
                return tracker;
        }

        return tracker;
    }

    static int incrementTracker()
    {
        if (tracker < 3)
        {
            if (historyBuffer[tracker + 1] != null)
                return ++tracker;
            else
                return tracker;
        }

        return tracker;
    }
}
