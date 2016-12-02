package scanner.analysis;

/*import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class AdminEmailTestWindow extends JFrame
{
 *//**
 *
 *//*
	private static final long serialVersionUID = -6568337478597164028L;

	JPanel emailPanel = new JPanel();
	JTextArea emailSpace;
	JScrollPane scroll;

	JPanel buttonPanel = new JPanel();
	JButton btnConfidential = new JButton("Confidential");
	JButton btnClean = new JButton("Clean Email");

	ArrayList<Email> emailList;
	Email[] historyBuffer = new Email[3];

	public AdminEmailTestWindow(String title, ArrayList<Email> emailList)
	{
		this.emailList = emailList;

		setTitle(title);
		setLayout(new GridLayout(2,1));

		emailSpace  = new JTextArea(20, 50);
		scroll = new JScrollPane(emailSpace);

		emailSpace.setEditable(false);
		emailSpace.setWrapStyleWord(true);
		emailSpace.setLineWrap(true);

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(475, 375));
		emailPanel.setPreferredSize(new Dimension(500, 400));

		emailPanel.add(scroll);

		add(emailPanel);

		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.setPreferredSize(new Dimension(20, 20));

		buttonPanel.add(btnClean, null);
		buttonPanel.add(btnConfidential, null);

		add(buttonPanel);

		this.loadTextField(this.emailList.get(0));
		emailList.remove(0);

		MouseListener buttonPress = new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (this.equals(btnClean))
				{
					incrementNormalColumn(emailList.get(0).getBody());
				}
				else if (this.equals(btnConfidential))
				{
					incrementConfidentialColumn(emailList.get(0).getBody());
				}
			}


  * Unused Methods

			@Override
			public void mouseClicked(MouseEvent e) { }

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }
		} ;

		btnConfidential.addMouseListener(buttonPress);
		btnClean.addMouseListener(buttonPress);
	}

	protected void incrementConfidentialColumn(String body)
	{

	}

	protected void incrementNormalColumn(String body)
	{

	}

	public void loadTextField(Email email)
	{
		emailSpace.setText(email.getBody());
	}
}
  */

//package scanner.analysis;

//import scanner.LoginGUI;
//import scanner.Word;
//import scanner.dbEntry.CSVFileReader;
//import scanner.dbEntry.Database;

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
    static int tracker = 0;
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
                    currentEmail.setConfidential(true);
                else if (btnCleanEmail.equals(arg0.getSource()))
                    currentEmail.setConfidential(false);

                updateHistory(currentEmail);

                /**switch (tracker)
                {
                    case 3:
                    {
                        updateHistory(currentEmail);
                        break;
                    }
                    case 2:
                    {
                        historyBuffer[tracker -1] = currentEmail;
                        //currentEmail = unprocessedEmail;
                        //tracker = 3;
                        //break;
                    }
                    case 1: historyBuffer[tracker - 1] = currentEmail;
                    case 0:
                    {
                        historyBuffer[tracker] = currentEmail;
                    }
                }*/

                loadNextEmail();
            }
        } ;

       ActionListener travel = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if (btnBack.equals(arg0.getSource()))
                {
                    if (tracker == 3)
                    {
                        unprocessedEmail = currentEmail;
                        currentEmail = historyBuffer[decrementTracker()];
                    }
                    else
                        currentEmail = historyBuffer[decrementTracker()];
                }
                else if (btnForward.equals(arg0.getSource()))
                {
                    if (tracker < 2 & historyBuffer[tracker + 1] != null)
                        currentEmail = historyBuffer[incrementTracker()];
                }
                loadTextField(currentEmail);
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
                historyBuffer  = new Email[3];
                createAndShowGUI();
                loadNextEmail();
            }
        });
    }

    static void updateHistory(Email email)
    {
        if (historyBuffer[0] == null)
        {
            historyBuffer[0] = email;
            tracker = 1;
        }
        else if(historyBuffer[1] == null)
        {
            historyBuffer[1] = email;
            tracker = 2;
        }
        else if(historyBuffer[2] == null)
        {
            historyBuffer[2] = email;
            tracker = 3;
        }
        else
        {
            Email emailToUpdate = historyBuffer[0];

            historyBuffer[0] = historyBuffer[1];
            historyBuffer[1] = historyBuffer[2];
            historyBuffer[2] = email;

            if (emailToUpdate.isConfidential())
                incrementConfidentialColumn(emailToUpdate.getEmailText());
            else
                incrementNormalColumn(emailToUpdate.getEmailText());

            try
            {
                db.removeEmailById(emailToUpdate.getId());
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
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
            currentEmail = db.getNextEmail();
            loadTextField(currentEmail);
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
            return --tracker;

        tracker = 0;
        return tracker;
    }

    static int incrementTracker()
    {
        if (tracker < 2)
            return ++tracker;

        tracker = 2;
        return tracker;
    }
}
