import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PortScanner extends JFrame implements ActionListener {
	private JLabel prompt;
	private JTextField hostInput;
	private JTextArea report;
	private JButton seekButton, exitButton;
	private JPanel hostPanel, buttonPanel;
	private static Socket socket = null;

	public static void main(String[] args) {
		PortScanner frame = new PortScanner();
		frame.setSize(400, 300);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e1) {
						System.out.println("\nUnable to close link!\n");
						System.exit(1);
					}
				}
				System.exit(0);
			}
		});
	}

	public PortScanner() {
		hostPanel = new JPanel();
		prompt = new JLabel("Host name: ");

		hostInput = new JTextField("localhost", 25);
		hostPanel.add(prompt);
		hostPanel.add(hostInput);
		add(hostPanel, BorderLayout.NORTH);
		report = new JTextArea(10, 25);
		add(report, BorderLayout.CENTER);

		buttonPanel = new JPanel();
		seekButton = new JButton("Seek server ports ");
		seekButton.addActionListener(this);
		buttonPanel.add(seekButton);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton)
			System.exit(0);
		// must have been the 'seek' button that was pressed,
		// so clear the output area of any previous output...
		report.setText("");

		// retrieve the url from the input text field...
		String host = hostInput.getText();

		try {
			// convert the url string into and InetAddress object..
			InetAddress theAddress = InetAddress.getByName(host);
			report.append("IP address: " + theAddress + "\n");
			report.append("Scanning start ...");
			for (int i = 0; i < 30 ; i++) {
				try {
					// attempt to establish a socket on port i...
					socket = new Socket(host, i);
					// if no IOException thrown, there must
					// be a service running on the port...
					report.append("There is a server on  port " + i + ".\n");
					socket.close();
				} catch (IOException ioEx) {
				}
			}
			report.append("Scanning completed");
		} catch (UnknownHostException uHEx) {
			report.setText("Unknown host!");
		}

	}

}
