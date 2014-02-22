import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class IPFinder {

	public static void main(String[] args) {
		String host;
		Scanner input = new Scanner(System.in);
		InetAddress address;
		System.out.println("\n\nEnter host name: ");
		host = input.next();
		try {
			address = InetAddress.getByName(host);
			System.out.println("IP address: " + address.toString());
		} catch (UnknownHostException e) {
			System.out.println("Could not find " + host);
			System.out.println("Try again.");
		}
		input.close();

	}

}
