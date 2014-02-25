import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DayTimeServer {
	public static void main(String[] args) {
		ServerSocket server;
		final int DAYTIME_PORT = 1300;
		Socket socket;

		try {
			server = new ServerSocket(DAYTIME_PORT);
			do {
				socket = server.accept();
				PrintWriter output = new PrintWriter(socket.getOutputStream(),
						true);
				Date date = new Date();
				output.println(date);
				// method toString executed in live above

				socket.close();
				System.exit(0);
			} while (true);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
