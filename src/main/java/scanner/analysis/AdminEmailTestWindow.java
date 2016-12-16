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
public class AdminEmailTestWindow {
    static Database db = new Database();
    static int tracker = 0;
    private final static int MAX_ARRAYSIZE = 4;
    static Email[] historyBuffer = new Email[MAX_ARRAYSIZE];
    static Email currentEmail;
    //static Email unprocessedEmail;
    static final JTextArea textArea = new JTextArea();
    final static JButton btnBack = new JButton("Back");
    final static JButton btnForward = new JButton("Forward");
    final static JButton btnCleanEmail = new JButton("Clean Email");
    final static JButton btnConfidential = new JButton("Confidential Email");
    final static JButton btnDone = new JButton("Done");
    final static JFrame frame = new JFrame("Algorithm Training");
    final static Email end = new Email("There are no more emails. Go back to look at other emails or click done.", 0, false, 1);


    /**
     * Add the components to the GUI.
     *
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
        textArea.setMinimumSize(new Dimension(400, 350));
        textArea.setMaximumSize(new Dimension(400, 350));
        textArea.setPreferredSize(new Dimension(400, 350));
        textArea.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(border);
        textArea.requestFocusInWindow();

        textArea.setMinimumSize(new Dimension(500, 200));
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textAreaPanel.add(textArea);
        pane.add(textAreaPanel, BorderLayout.CENTER);

        btnBack.setEnabled(false);
        btnForward.setEnabled(false);

        JPanel scoringPanel = new JPanel();
        scoringPanel.add(btnBack);
        scoringPanel.add(btnCleanEmail);
        scoringPanel.add(btnConfidential);
        scoringPanel.add(btnForward);
        scoringPanel.add(btnDone);

        btnBack.setEnabled(false);
        btnForward.setEnabled(false);

        pane.add(scoringPanel, BorderLayout.SOUTH);

        ActionListener confidentiality = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (btnConfidential.equals(arg0.getSource())) {
                    historyBuffer[tracker].setConfidential(true);
                }
                else if (btnCleanEmail.equals(arg0.getSource())) {
                    historyBuffer[tracker].setConfidential(false);
                }
                loadNextEmail();

                //updateHistory(currentEmail);

            }
        };

        ActionListener travel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (btnBack.equals(arg0.getSource())) {
                    goBack();
                    btnCleanEmail.setEnabled(true);
                    btnConfidential.setEnabled(true);
                }
                if (btnForward.equals(arg0.getSource())) {
                    goForward();
                }
            }
        };

        ActionListener finished = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (btnDone.equals(arg0.getSource())) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            }
        };


        btnConfidential.addActionListener(confidentiality);
        btnCleanEmail.addActionListener(confidentiality);
        btnBack.addActionListener(travel);
        btnForward.addActionListener(travel);
        btnDone.addActionListener(finished);

    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setTitle("Algorithm Training");
        frame.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.out.println("Frame closing...");
                try {
                    clearHistoryBuffer();
                    db.refreshDatabase();
                    db.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                loadNextEmail();
            }
        });
    }

    static void clearHistoryBuffer() {
        if(historyBuffer != null) {
            for (Email email : historyBuffer) {
                if(email != null) {
                    if (email.isConfidential()) {
                        System.out.println("Would increment confidential column");
                        //incrementConfidentialColumn(email.getEmailText());
                    } else {
                        System.out.println("Would increment confidential column");
                        //incrementNormalColumn(email.getEmailText());
                    }
                    try {
                        System.out.println("Would delete email");
                        //db.removeEmailById(email.getId());
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
            }
        }
    }

    static void updateHistory(Email email) {
        if(email == null){
           //shiftDown(end);
            btnForward.setEnabled(false);
            btnConfidential.setEnabled(false);
            btnCleanEmail.setEnabled(false);
            fillBuffer(end);
        } else fillBuffer(email);
    }

    private static void fillBuffer(Email email){
        if (historyBuffer[0] == null) {
            historyBuffer[0] = email;
            loadTextField(historyBuffer[tracker]);
            //goForward();
        } else if (historyBuffer[1] == null) {
            historyBuffer[1] = email;
            goForward();
        } else if (historyBuffer[2] == null) {
            historyBuffer[2] = email;
            goForward();
        } else if (historyBuffer[3] == null) {
            historyBuffer[3] = email;
            goForward();
        } else {
            shiftDown(email);
        }
    }

    private static void shiftDown(Email email){
        Email emailToUpdate = historyBuffer[0];

        historyBuffer[0] = historyBuffer[1];
        historyBuffer[1] = historyBuffer[2];
        historyBuffer[2] = historyBuffer[3];
        historyBuffer[3] = email;

        //move tracker and load new email
        goForward();

        if (emailToUpdate.isConfidential()) {
            if(emailToUpdate.getId() == 0){
                return;
            } else {
                System.out.println("Would increment confidential column");
                //incrementConfidentialColumn(emailToUpdate.getEmailText());
            }
        }
        else {
            if(emailToUpdate.getId() == 0){
                return;
            } else {
                System.out.println("Would increment normal column");
                //incrementNormalColumn(emailToUpdate.getEmailText());
            }
        }
        try {
            if(emailToUpdate.getId() == 0){
                return;
            } else {
                System.out.println("Would delete email");
                //db.removeEmailById(emailToUpdate.getId());
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    protected static void incrementConfidentialColumn(String body) {
        try {
            TextParser tepa = new TextParser(body);
            tepa.parse();

            HashSet<String> w = tepa.getUniqueWords();
            HashSet<String> p = tepa.getUniquePhrases();

            for (String word : w) {
                db.incrementWordConf(word);
            }

            for (String phrase : p) {
                db.incrementPhraseConf(phrase, phrase.split("\\s+").length);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected static void incrementNormalColumn(String body) {
        try {
            TextParser tepa = new TextParser(body);
            tepa.parse();

            HashSet<String> w = tepa.getUniqueWords();
            HashSet<String> p = tepa.getUniquePhrases();

            for (String word : w) {
                db.incrementWordNorm(word);
            }

            for (String phrase : p) {
                db.incrementPhraseNorm(phrase, phrase.split("\\s+").length);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void loadNextEmail() {
        try {
            if (tracker == historyBuffer.length - 1) {
                //email was marked, and we are at the end of the array.
                //So shift down, update the beginning email, and load a new one
                updateHistory(db.getNextEmail());
            } else {
                //we are not at the end, so move forward
                if (historyBuffer[tracker + 1] != null) {
                    goForward();
                } else updateHistory(db.getNextEmail());    //we havent filled up the buffer yet
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }


    }
  
    static void loadTextField(Email email) {

        textArea.setText(email.getEmailText());
    }

    public static void goBack() {
        decrementTracker();
        loadTextField(historyBuffer[tracker]);
    }

    public static void goForward() {
        incrementTracker();
        loadTextField(historyBuffer[tracker]);
    }

    static int decrementTracker() {
        if (tracker > 0) {
            btnForward.setEnabled(true);
            tracker--;
            if (tracker == 0) {
                btnBack.setEnabled(false);
            } else btnBack.setEnabled(true);
        }
        if(historyBuffer[tracker].getId() == 0){
            btnConfidential.setEnabled(false);
            btnCleanEmail.setEnabled(false);
        }
        return tracker;
    }

    static int incrementTracker() {
        if (tracker < 3) {
            btnBack.setEnabled(true);
            tracker++;
            if(tracker == 3 || historyBuffer[tracker + 1] == null){
                btnForward.setEnabled(false);
            } else {
                btnForward.setEnabled(true);
            }
            if(historyBuffer[tracker].getId() == 0){
                btnConfidential.setEnabled(false);
                btnCleanEmail.setEnabled(false);
            }
            return tracker;
        } else
            return tracker;
        }

    /*if(tracker < MAX_ARRAYSIZE - 2){
            btnBack.setVisible(true);
            return ++tracker;
        }
        else if(tracker == MAX_ARRAYSIZE - 2){
            btnForward.setVisible(false);
            return ++tracker;
        }
        else {
            tracker = 3;
            //btnForward.setVisible(false);
        }
        btnBack.setVisible(true);
        return tracker;
        }*/

}
