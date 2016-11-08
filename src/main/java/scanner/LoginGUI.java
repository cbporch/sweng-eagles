package scanner;

import scanner.dbEntry.Database;
import scanner.dbEntry.DatabaseInput;
import scanner.filtering.Hasher;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * Created by cdeck_000 on 11/2/2016.
 */
public class LoginGUI {
/**
 * Created by cdeck_000 on 10/19/2016.
 */

        private static JButton uploadFileBtn = new JButton("Upload File");
        private static JLabel usernameLabel = new JLabel("Username");
        private static JLabel passwordLabel = new JLabel("Password");
        private static JTextField usernameField = new JTextField("");
        private static JPasswordField passwordField = new JPasswordField(15);
        private static JFrame frame = new JFrame("LoginGUI");

        /**
         * Add the components to the GUI.
         * @param pane - the pane for the GUI
         */
        public static void addComponentsToPane(Container pane) {
            pane.setLayout(new BorderLayout());
            JPanel instructionsPanel = new JPanel();
            JLabel instructions = new JLabel("Enter your login information below");
            instructionsPanel.setBackground(Color.LIGHT_GRAY);
            instructionsPanel.add(instructions);
            pane.add(instructionsPanel, BorderLayout.NORTH);

            //add login panel
            JPanel loginPanel = new JPanel();
            loginPanel.setBackground(Color.LIGHT_GRAY);
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            pane.add(loginPanel, BorderLayout.CENTER);

            //add username fields
            JPanel usernamePanel = new JPanel();
            usernamePanel.setBackground(Color.LIGHT_GRAY);
            usernamePanel.add(usernameLabel);
            usernamePanel.add(usernameField);
            usernameField.setBackground(Color.WHITE);
            usernameField.setBorder(border);
            usernameField.requestFocusInWindow();
            usernameField.setFont(new Font("Serif", Font.PLAIN, 16));
            usernameField.setMinimumSize(new Dimension(200,30));
            usernameField.setMaximumSize(new Dimension(200,30));
            usernameField.setPreferredSize(new Dimension(200,30));
            loginPanel.add(usernamePanel, BorderLayout.NORTH);

            //password fields
            JPanel passwordPanel = new JPanel();
            passwordPanel.setBackground(Color.LIGHT_GRAY);
            passwordPanel.add(passwordLabel);
            passwordPanel.add(passwordField);
            passwordField.setBackground(Color.WHITE);
            passwordField.setBorder(border);
            passwordField.requestFocusInWindow();
            passwordField.setFont(new Font("Serif", Font.PLAIN, 16));
            passwordField.setMinimumSize(new Dimension(200,30));
            passwordField.setMaximumSize(new Dimension(200,30));
            passwordField.setPreferredSize(new Dimension(200,30));
            loginPanel.add(passwordPanel, BorderLayout.SOUTH);

            JPanel scoringPanel = new JPanel();
            JButton loginButton = new JButton("Login");
            JButton cancelButton = new JButton("Cancel");
            scoringPanel.add(loginButton);
            scoringPanel.add(cancelButton);
            pane.add(scoringPanel, BorderLayout.SOUTH);

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String username = usernameField.getText();
                        char[] password = passwordField.getPassword();
                        String passwordString = new String(password);
                        Database b = new Database();
                        if(b.checkLogin(username, passwordString)){
                            DatabaseInput databaseInput = new DatabaseInput();
                            databaseInput.main(null);
                        }
                    } catch (Exception ex) {
                        System.out.println(e);                     //print the exception
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
        }

        /**
         * Create the GUI and show it.
         */
        private static void createAndShowGUI() {
            //Create and set up the window.
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //frame.setLocationRelativeTo(null);
            frame.setPreferredSize(new Dimension(500,500));
            frame.setTitle("Login");
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