import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Echo extends JFrame implements ActionListener {
	private JTextField hostInput, lineToSend;
	private JLabel hostPrompt, messagePrompt;
	private JTextArea received;
	private JButton closeConnection;
	private JButton openConnection;
	private JPanel hostEntryPanel, hostPanel, entryPanel, buttonPanel;

	private final int ECHO = 7;
	private static Socket socket = null;
	private Scanner input;
	private PrintWriter output;

	public static void main(String[] args) {
		Echo frame = new Echo();
		frame.setSize(400, 300);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException ioEx) {
						System.out.println("\n* Unable to close link! *\n");
						System.exit(1);
					}
					System.exit(0);
				} else {
					System.exit(0);
				}
			}
		});
	}

	public Echo() {
		hostEntryPanel = new JPanel();
		hostPanel = new JPanel();
		hostPrompt = new JLabel("Enter host name:");
		hostInput = new JTextField(20);
		hostInput.addActionListener(this);
		hostPanel.add(hostPrompt);
		hostPanel.add(hostInput);

		entryPanel = new JPanel();

		messagePrompt = new JLabel("Enter text:");
		lineToSend = new JTextField(15);
		lineToSend.setEditable(true);
		lineToSend.addActionListener(this);

		/************************************************
		 * ADD COMPONENTS TO PANEL AND APPLICATION FRAME *
		 ************************************************/
		entryPanel.add(messagePrompt);
		entryPanel.add(lineToSend);
		hostEntryPanel.add(hostPanel);
		hostEntryPanel.add(entryPanel);
		add(hostEntryPanel, BorderLayout.NORTH);
		/********************************************
		 * NOW SET UP TEXT AREA AND THE CLOSE BUTTON *
		 ********************************************/
		received = new JTextArea(100, 150);
		received.setWrapStyleWord(true);
		received.setLineWrap(true);
		add(new JScrollPane(received), BorderLayout.CENTER);

		buttonPanel = new JPanel();
		closeConnection = new JButton("Close connection");
		openConnection = new JButton("Open connection");
		buttonPanel.add(openConnection);
		buttonPanel.add(closeConnection);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == closeConnection) {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ioEx) {
					System.out.println("\n* Unable to close link!*\n");
					System.exit(1);
				}
				lineToSend.setEditable(false);
				hostInput.grabFocus();
			}
			return;
		}

		if (event.getSource() == openConnection && !hostInput.getText().equals("")) {
			try {
				received.append("Connecting to " + hostInput.getText() + "...");
				socket = new Socket(hostInput.getText(), ECHO);
				input = new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream());
				if (socket!=null) {
					received.append("The date/time at " + hostInput.getText() + " is " + input.nextLine() + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
