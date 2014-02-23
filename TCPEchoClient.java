import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPEchoClient {
	private static InetAddress host;
	private static final int PORT = 1240;

	public static void main(String[] args) {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Host ID not found");
			System.exit(1);
		}
		accessServer();

	}

	private static void accessServer() {
		Socket link = null;
		try {
			link = new Socket(host, PORT);
			Scanner input = new Scanner(link.getInputStream());
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);

			// set up stream for keyboard entry..
			Scanner userEntry = new Scanner(System.in);
			String message, response;
			do {
				System.out.println("Enter message:");
				message = userEntry.nextLine();
				output.println(message);
				response = input.nextLine();
				System.out.println("\nSERVER> " + response);
			} while (!message.equals("bye"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\n Closing connection...");
			try {
				link.close();
			} catch (IOException e) {
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}

}
