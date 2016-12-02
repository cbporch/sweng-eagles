package scanner.analysis;

import scanner.LoginGUI;
import scanner.Word;
import scanner.dbEntry.CSVFileReader;
import scanner.dbEntry.Database;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by cdeck_000 on 10/19/2016.
 */

/**
 * Create a simple GUI to input an email to be scanned. A score for the email will be shown on screen.
 */
public class EmailTextGUI {
    protected static Database db;

    private static JButton uploadFileBtn = new JButton("Upload File");
    /**
     * Add the components to the GUI.
     * @param pane - the pane for the GUI
     */
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Enter the email text below");
        instructionsPanel.setBackground(Color.LIGHT_GRAY);
        instructionsPanel.add(instructions);
        pane.add(instructionsPanel, BorderLayout.NORTH);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBackground(Color.LIGHT_GRAY);
        final JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setMinimumSize(new Dimension(500,450));
        textArea.setMaximumSize(new Dimension(500,450));
        textArea.setPreferredSize(new Dimension(500,450));
        textArea.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(border);
        textArea.requestFocusInWindow();
        //textArea.setMinimumSize(new Dimension(500, 200));
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textAreaPanel.add(textArea);
        //JScrollPane scrollPane = new JScrollPane(textAreaPanel);
        pane.add(textAreaPanel, BorderLayout.CENTER);
        //pane.add(scrollPane, BorderLayout.CENTER);

        JPanel scoringPanel = new JPanel();
        JButton evaluateButton = new JButton("Evaluate Email");
        JButton saveEmailButton = new JButton("Save Email");
        final JLabel scoreLabel = new JLabel("");
        JButton importTermsBtn = new JButton("Import Terms");
        scoringPanel.add(evaluateButton);
        scoringPanel.add(saveEmailButton);
        scoringPanel.add(uploadFileBtn);
        scoringPanel.add(importTermsBtn);
        scoringPanel.add(scoreLabel);

        pane.add(scoringPanel, BorderLayout.SOUTH);

        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = textArea.getText();
                    TextParser textParser = new TextParser(email);
                    long f = System.currentTimeMillis();

                    double score = textParser.parse();

                    if (score >= 0.75)
                        scoreLabel.setText("red");
                    else if (score < 0.01)
                        scoreLabel.setText("green");
                    else
                        scoreLabel.setText("yellow");

                    System.out.print((System.currentTimeMillis() - f));

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        saveEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //send email to database
                    System.out.println("Saving Email...");
                    db.insertEmail(textArea.getText());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });


        uploadFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                System.out.println("In action listener");
                if (e.getSource() == EmailTextGUI.uploadFileBtn) {
                    System.out.println("In first if");
                    int returnVal = fc.showOpenDialog(uploadFileBtn);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        System.out.println("In second if");
                        File file = fc.getSelectedFile();
                        //This is where a real application would open the file.
                        System.out.println("Opening: " + file.getAbsolutePath());
                        CSVFileReader csvfr = new CSVFileReader();
                        csvfr.interpretCSVFile(file.getAbsolutePath());
                        //ArrayList<Word> words = CSVFileReader.interpretCSVFile(file+"");
//                        for(Word word: words){
//                            System.out.println(word.getWord());
//                        }
                    } else {
                        System.out.println("Open command cancelled by user.%n");
                    }
                }
            }
            //successLabel.setText("Feature not available yet.");
        });

        importTermsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.main(null);
            }
        });
    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("EmailGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(600,600));
        frame.setTitle("Email Text Input");
        frame.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("red_team.jpeg")));
            frame.setIconImage(icon.getImage());
        } catch(Exception e) {
            System.out.println(e);
        }
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Frame closing...");
                try {
                    db.close();
                } catch (Exception ex){
                    System.out.println(ex);
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
                db = new Database();
            }
        });
    }
}