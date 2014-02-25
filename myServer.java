/**
 * @author afiqjohari
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class myServer {
	public static void main(String[] args) {
		try {
			// Creating a server
			ServerSocket theConnection;
			theConnection = new ServerSocket(5001);
			// A new socket to pick up the "request"
			Socket theConversation;
			theConversation = theConnection.accept();
			// We create a reader on this socket as well as a writer
			BufferedReader in = new BufferedReader(new InputStreamReader(
					theConversation.getInputStream()));
			PrintStream out = new PrintStream(new BufferedOutputStream(
					theConversation.getOutputStream()));

			// Reading the input from the browser
			String line = in.readLine();

			// get the filename
			String fileName = retrieveFileName(line);

			while (true) {
				if (line.equals("") | line.equals("\n") | line.equals("\r")
						| line.equals("\r\n"))
					break;
				System.out.println(line);
				line = in.readLine();
			}
			System.out.println("Question is read");

			// Answering the browser
			out.println("HTTP 200 OK");
			out.println("Content-type: text/html\n");
			out.println(""); // this line is very important
			// out.print("<html><body>I sleep!</body></html>");
			BufferedReader f = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));

			String l = f.readLine();
			while (l != null) {
				out.println(l);
				l = f.readLine();
			}

			// Closing the output stream
			out.flush();
			out.close();
			in.close();
			theConversation.close();
			// free the serverport
			theConnection.close();

		} catch (IOException e) {
			System.out.println("IO problem");
		}

	}

	private static String retrieveFileName(String line) {
		int i = line.indexOf("/");
		int j = line.lastIndexOf(" ");
		String filename = line.substring(i + 1, j);
		return filename;
	}
}
