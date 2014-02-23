import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * Creates a server and listens indefinitely to the client until he sends bye
 * 
 */
public class TCPEchoServer {
	/*
	 * Step 1: Create a ServerSocket object
	 */
	private static ServerSocket serverSocket;
	private static final int PORT = 1240;

	public static void main(String[] args) {

		System.out.println("Opening port...");
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException iex) {
			System.out.println("Unable to attach to port!");
			System.exit(1);
		}
		do {
			handeClient();
		} while (true);
	}

	private static void handeClient() {
		Socket link = null;
		try {
			/*
			 * Step 2: put the server into a waiting state
			 */
			link = serverSocket.accept();
			/*
			 * Step 3 : set up input and output streams between the server and
			 * the client
			 */
			Scanner input = new Scanner(link.getInputStream());
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);

			int numMessages = 0;
			String message = input.nextLine();
			while (!message.equals("bye")) {
				System.out.println("Message received.");
				numMessages++;
				/*
				 * Step 4 : Send and receive data
				 */
				// send data to output
				output.println("Message " + numMessages + ": " + message);
				// get data from input
				input.nextLine();
			}
			output.println(numMessages + " messages received.");

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		} finally {
			try {
				System.out.println("Closing connection...");
				/*
				 * Step 5 : close the connection
				 */
				link.close();
				serverSocket.close();
			} catch (Exception e) {
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}

	}

}
