import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EmailClient {
	private static InetAddress host;
	private static final int PORT = 1241;
	private static String name;
	private static Scanner networkInput, userEntry;
	private static PrintWriter networkOutput;

	public static void main(String[] args) throws IOException {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Host ID not found!");
			System.exit(1);
		}

		userEntry = new Scanner(System.in);

		do {
			System.out.print("\nEnter name ('Dave' or 'Karen'): ");
			name = userEntry.nextLine();
		} while (!name.equals("Dave") && !name.equals("Karen"));

		talkToServer();
	}

	private static void talkToServer() throws IOException {
		String option = null, message, response;

		do {
			/********************************************************
			 * CREATE A SOCKET, SET UP INPUT AND OUTPUT STREAMS, ACCEPT THE
			 * USER'S REQUEST, CALL UP THE APPROPRIATE METHOD (doSend OR
			 * doRead), CLOSE THE LINK AND THEN ASK IF USER WANTS TO DO ANOTHER
			 * READ/SEND.
			 ********************************************************/
			// create a socket
			Socket link = new Socket(host, PORT);
			// crate streams for input and output
			networkInput = new Scanner(link.getInputStream());
			networkOutput = new PrintWriter(link.getOutputStream(), true);
			// accept user's request
			System.out.println("Select an option: " + "\n1) Send" + "\n2) Read"
					+ "\nn) Quit");
			option = userEntry.nextLine();
			switch (option) {
			case "1":
				doSend();
				break;
			case "2":
				doRead();
				break;
			case "n":
				networkOutput.println(name);
				networkOutput.println("quit");
				link.close();
				break;
			default:
				break;
			}

		} while (!option.equals("n"));

	}

	private static void doSend() throws IOException {
		System.out.println("\nEnter 1-line message: ");
		String message = userEntry.nextLine();
		networkOutput.println(name);
		networkOutput.println("send");
		networkOutput.println(message);
	}

	private static void doRead() throws IOException {
		System.out.println("\nReading messages from inbox");
		networkOutput.println(name);
		networkOutput.println("read");
		int messagesInBox = networkInput.nextInt();
		for (int i = 0; i < messagesInBox + 1; i++) {
			System.out.println(networkInput.nextLine());
		}

	}
}
