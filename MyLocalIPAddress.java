import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyLocalIPAddress {
	public static void main(String[] args) {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			System.out.println(address);
		} catch (UnknownHostException e) {
			System.out.println("Could not find local address!");
		}

	}

}
