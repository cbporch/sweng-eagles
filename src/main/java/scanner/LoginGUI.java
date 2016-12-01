package scanner;

import org.apache.logging.log4j.LogManager;
import scanner.analysis.EmailTextGUI;
import scanner.dbEntry.Database;
import scanner.dbEntry.DatabaseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.*;
import java.io.File;
import java.net.URL;


/**
 * Created by cdeck_000 on 11/2/2016.
 */
public class LoginGUI extends JFrame{
/**
 * Created by cdeck_000 on 10/19/2016.
 */
    private static JLabel usernameLabel = new JLabel("Username");
    private static JLabel passwordLabel = new JLabel("Password");
    private static JTextField usernameField = new JTextField("");
    private static JPasswordField passwordField = new JPasswordField(15);
    private static JFrame frame = new JFrame("LoginGUI");
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginGUI.class.getName());
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
            final JPanel usernamePanel = new JPanel();
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
                            logger.log(Level.INFO, "Login successful for username: " + username);
                            EmailTextGUI gui = new EmailTextGUI();
                            gui.main(null);
                            frame.dispose();
                        }
                        else {
                            logger.log(Level.WARNING, "Login FAILED for username : " + username);
                        }
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Login failed for username: " + usernameField.getText() + ". Message: " + ex.getMessage());
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            frame.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    logger.log(Level.INFO, "Frame closing...Attempting to close loggers.");
                    try {
                        for(Handler h:logger.getHandlers())
                        {
                            h.close();   //must call h.close or a .LCK file will remain.
                        }
                    } catch (Exception ex){
                       logger.log(Level.SEVERE, "Error: + " + ex.getMessage());
                    }
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
        }

        public static void main(String[] args) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        // Create a file handler that write log record to a file called my.log
                        FileHandler handler = new FileHandler("my.log", true);
                        handler.setFormatter(new SimpleFormatter());
                        // Add to the desired logger
                        logger.addHandler(handler);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Error creating log file handler. Message: " + e.getMessage());
                    }
                    createAndShowGUI();
                }
            });
        }
}
