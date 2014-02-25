import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GetRemoteTime extends JFrame implements ActionListener {

	private JTextField hostInput;
	private JTextArea display;
	private JButton timeButton;
	private JButton exitButton;
	private JPanel buttonPanel;
	private static Socket socket = null;

	public static void main(String[] args) {
		GetRemoteTime frame = new GetRemoteTime();
		frame.setSize(400, 300);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e1) {
						System.out.println("Unable to close link!!\n");
						System.exit(1);
					}
					System.exit(0);
				}
			}
		});

	}

	public GetRemoteTime() {
		hostInput = new JTextField(20);
		add(hostInput, BorderLayout.NORTH);
		display = new JTextArea(10, 15);

		// following two lines ensure that word-wrapping
		// occurs within the JTextArea...
		display.setWrapStyleWord(true);
		display.setLineWrap(true);
		add(new JScrollPane(display), BorderLayout.CENTER);
		buttonPanel = new JPanel();
		timeButton = new JButton("Get date and time ");
		timeButton.addActionListener(this);
		buttonPanel.add(timeButton);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == exitButton) {
			System.exit(0);
		}

		String theTime;

		// accept host name from the user...
		String host = hostInput.getText();
		final int DAYTIME_PORT = 1300;

		try {
			// crate a socket object to connect to the
			// specified host on the relevant port...
			socket = new Socket(host, DAYTIME_PORT);

			// create an input stream for the above socket
			// and add string-reading functionality...
			Scanner input = new Scanner(socket.getInputStream());
			// Accept the hosts response via the above system
			theTime = input.nextLine();

			// add the host's response to the text in the Jtextarea..
			display.append("The date/time at " + host + " is " + theTime + "\n");
			hostInput.setText("");

		} catch (UnknownHostException e) {
			display.append("No such host!\n");
			hostInput.setText("");
		} catch (IOException e) {
			display.append(e.toString() + "\n");
		} finally {
			if (socket != null) {
				try {
					// close link to host
					socket.close();
				} catch (IOException e) {
					System.out.println("Unable to disconnect!");
					System.exit(1);
				}
			}
		}

	}

}
