package scanner.analysis;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Created by cdeck_000 on 10/19/2016.
 */


public class EmailTextGUI {
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Enter the email text below");
        instructionsPanel.add(instructions);
        pane.add(instructionsPanel, BorderLayout.NORTH);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBackground(Color.LIGHT_GRAY);
        JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        textAreaPanel.add(textArea);
        pane.add(textAreaPanel, BorderLayout.CENTER);

        JPanel scoringPanel = new JPanel();
        JButton evaluateButton = new JButton("Evaluate Email");
        final JLabel scoreLabel = new JLabel("Test");
        scoringPanel.add(evaluateButton);
        scoringPanel.add(scoreLabel);
        pane.add(scoringPanel, BorderLayout.SOUTH);

        evaluateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scoreLabel.setText("45");
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
        JFrame frame = new JFrame("BoxLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(500,400));
        frame.setTitle("Email Text Input");
        frame.setResizable(true);
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