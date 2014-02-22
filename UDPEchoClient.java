import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPEchoClient {
	private static InetAddress host;
	private static final int PORT = 1250;
	private static DatagramSocket datagramSocket;
	private static DatagramPacket inPacket, outPacket;
	private static byte[] buffer;

	public static void main(String[] args) {

		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Host ID not found!");
			System.exit(1);
		}
		accessServer();
	}

	private static void accessServer() {
		try {
			datagramSocket = new DatagramSocket();
			// Set up stream for keyboard entry...
			Scanner userEntry = new Scanner(System.in);

			String message = "", response = "";
			do {
				System.out.println("Enter message: ");
				message = userEntry.nextLine();
				if (!message.equals("bye")) {
					outPacket = new DatagramPacket(message.getBytes(),
							message.length(), host, PORT);
					datagramSocket.send(outPacket);
					buffer = new byte[256];
					inPacket = new DatagramPacket(buffer, buffer.length);
					datagramSocket.receive(inPacket);
					response = new String(inPacket.getData(), 0,
							inPacket.getLength());
					System.out.println("\nSERVER> " + response);
				}

			} while (!message.equals("bye"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\n Closing connection...");
			datagramSocket.close();
		}
	}

}
