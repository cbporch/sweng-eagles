package scanner.analysis;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Created by cdeck_000 on 10/19/2016.
 */

/**
 * Create a simple GUI to input an email to be scanned. A score for the email will be shown on screen.
 */
public class EmailTextGUI {
    /**
     * Add the components to the GUI.
     * @param pane - the pane for the GUI
     */
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Enter the email text below");
        instructionsPanel.add(instructions);
        pane.add(instructionsPanel, BorderLayout.NORTH);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBackground(Color.LIGHT_GRAY);
        final JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setMinimumSize(new Dimension(400,400));
        textArea.setMaximumSize(new Dimension(400,400));
        textArea.setPreferredSize(new Dimension(400,400));
        textArea.setLineWrap(true);
        textArea.setMinimumSize(new Dimension(500, 200));
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textAreaPanel.add(textArea);
        pane.add(textAreaPanel, BorderLayout.CENTER);

        JPanel scoringPanel = new JPanel();
        JButton evaluateButton = new JButton("Evaluate Email");
        final JLabel scoreLabel = new JLabel("Test");
        JButton uploadFileBtn = new JButton("Upload File");
        scoringPanel.add(evaluateButton);
        scoringPanel.add(uploadFileBtn);
        scoringPanel.add(scoreLabel);

        pane.add(scoringPanel, BorderLayout.SOUTH);

        evaluateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String email = textArea.getText();
                    TextParser textParser = new TextParser(email);
                    double score = textParser.parse();
                    scoreLabel.setText(score+"");
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        });

        uploadFileBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scoreLabel.setText("Feature not yet available.");
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
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setTitle("Email Text Input");
        frame.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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