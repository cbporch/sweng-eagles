package scanner.analysis;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import scanner.dbEntry.Database;
import scanner.dbEntry.DatabaseInput;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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

    private static JButton evaluateFileBtn = new JButton("Scan File");
    static JLabel scoreLabel = new JLabel("");
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
        final JButton evaluateButton = new JButton("Evaluate Email");
        JButton saveEmailButton = new JButton("Save Email");
        JButton importTermsBtn = new JButton("Import Terms");
        scoringPanel.add(evaluateButton);
        scoringPanel.add(saveEmailButton);
        scoringPanel.add(evaluateFileBtn);
        scoringPanel.add(importTermsBtn);
        scoringPanel.add(scoreLabel);

        pane.add(scoringPanel, BorderLayout.SOUTH);

        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textArea.getText();
                scoreLabel.setText("Working..");
                evaluate(email);
            }
        });

        saveEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //send email to database
                    System.out.println("Saving Email...");
                    scoreLabel.setText("Saving Email....");
                    db.insertEmail(textArea.getText());
                    scoreLabel.setText("Email Saved.");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });


        evaluateFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                System.out.println("In action listener");
                if (e.getSource() == EmailTextGUI.evaluateFileBtn) {
                    System.out.println("In first if");
                    int returnVal = fc.showOpenDialog(evaluateFileBtn);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {

                        scoreLabel.setText("Working..");
                        System.out.println("In second if");
                        File file = fc.getSelectedFile();
                        //This is where a real application would open the file.
                        System.out.println("Opening: " + file.getAbsolutePath());
                        TikaWrapper tika = new TikaWrapper();
                        String body = "";
                        try {
                            body = tika.parseToTXT(file.getAbsolutePath());
                            evaluate(body);
                        } catch (IOException e1) {
                            System.err.print(e1);
                        } catch (TikaException e1) {
                            System.err.print(e1);
                        } catch (SAXException e1) {
                            System.err.print(e1);
                        }

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
                DatabaseInput DBI = new DatabaseInput();
                DBI.main();
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

    private static void evaluate(String text){
        scoreLabel.setText("Working..");
        TextParser textParser = null;
        try {
            textParser = new TextParser(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long f = System.currentTimeMillis();

        double score = textParser.parse();
        System.out.println(score);
        if (score >= 0.75)
            scoreLabel.setText("red");
        else if (score < 0.01)
            scoreLabel.setText("green");
        else
            scoreLabel.setText("yellow");

        System.out.print((System.currentTimeMillis() - f));
    }
}