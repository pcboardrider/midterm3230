package midterm3230;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import blackjack.message.MessageFactory;

public class Client {
	private Socket s;
	private ObjectOutputStream output;
	private InetAddress iP;
	private int port = 8989;
	private String user = "Lauren testing";

	public Client() {
		try {
			iP = InetAddress.getByName("137.190.250.174");
			s = new Socket(iP, port);
			// //writer = new PrintWriter(s.getOutputStream());
			//
			// //reader = new BufferedReader(new InputStreamReader(
			// s.getInputStream()));
			// //g = new GraphicalChat(s);
			new Thread(new Reader(s.getInputStream())).start();
			output = new ObjectOutputStream(s.getOutputStream());
			output.writeObject(MessageFactory.getLoginMessage("Lauren testing"));
			System.out.println(output.toString());
			output.flush();
			System.out.println(output);

		} catch (UnknownHostException e) {
			System.out.println("Unknown host");
		} catch (IOException e) {
			System.out.println("Exception");
		}
	}

	private class Reader implements Runnable {
		private ObjectInputStream input;

		public Reader(InputStream in) {
			try {
				input = new ObjectInputStream(in);
				System.out.println(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}

	public static void main(String[] args) {
		new Client();
	}
}
