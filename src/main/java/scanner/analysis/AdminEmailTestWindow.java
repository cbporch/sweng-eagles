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

import java.awt.*;
import java.awt.event.*;
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
    //protected static Database db;

    Email[] historyBuffer = new Email[3];
    Email currentEmail = new Email("", "");

    /**
     * Add the components to the GUI.
     * @param pane - the pane for the GUI
     */
    public void addComponentsToPane(Container pane) {
        pane.setLayout(new BorderLayout());
        JPanel instructionsPanel = new JPanel();
        JLabel instructions = new JLabel("Current Email");
        instructionsPanel.setBackground(Color.LIGHT_GRAY);
        instructionsPanel.add(instructions);
        pane.add(instructionsPanel, BorderLayout.NORTH);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBackground(Color.LIGHT_GRAY);
        final JTextArea textArea = new JTextArea();
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
        JButton btnCleanEmail = new JButton("Clean Email");
        JButton btnConfidential = new JButton("Confidential Email");
        JButton btnBack = new JButton("Back");
        JButton btnForward = new JButton("Forward");
        scoringPanel.add(btnCleanEmail);
        scoringPanel.add(btnConfidential);
        scoringPanel.add(btnBack);
        scoringPanel.add(btnForward);

        pane.add(scoringPanel, BorderLayout.SOUTH);

        MouseListener buttonPress = new MouseListener()
        {
            @Override
            public void mouseReleased(MouseEvent arg0)
            {
                updateHistory(currentEmail);
            }

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
        btnCleanEmail.addMouseListener(buttonPress);
    }

    /**
     * Create the GUI and show it.
     */
    private void createAndShowGUI() {
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
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Frame closing...");
                try {
                    //db.close();
                } catch (Exception ex){
                    System.out.println(ex);
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
                AdminEmailTestWindow aetw = new AdminEmailTestWindow();
                aetw.createAndShowGUI();
                //db = new Database();
            }
        });
    }

    void updateHistory(Email e)
    {
        if (historyBuffer[0].equals(null))
            historyBuffer[0] = e;
        else if(historyBuffer[1].equals(null))
            historyBuffer[1] = e;
        else if(historyBuffer[2].equals(null))
            historyBuffer[2] = e;
        else
        {
            Email email = historyBuffer[0];

            historyBuffer[0] = historyBuffer[1];
            historyBuffer[1] = historyBuffer[2];
            historyBuffer[2] = e;

            if (email.isConfidential())
                incrementConfidentialColumn(email.getBody());
            else
                incrementNormalColumn(email.getBody());
        }
    }

    protected void incrementConfidentialColumn(String body)
    {
        // Call lucene here, this is where the body of the email is sent to increase the corresponding words' conf value
    }

    protected void incrementNormalColumn(String body)
    {
        // Same but opposite :)
    }
}
