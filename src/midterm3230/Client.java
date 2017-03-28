package midterm3230;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import blackjack.message.Message;
import blackjack.message.Message.MessageType;
import blackjack.message.MessageFactory;
import blackjack.message.StatusMessage;

public class Client extends JFrame {
	private Socket s;
	private ObjectOutputStream output;
	private InetAddress iP;
	private int port = 8989;
	private String user = "L1";
	
	private JTextArea chatDisplay;
	private JTextArea chatType;
	
	private int total = 0;

	public Client() {
		setupGUI();
		try {
			iP = InetAddress.getByName("52.35.72.251");
			s = new Socket(iP, port);
			output = new ObjectOutputStream(s.getOutputStream());
			new Thread(new Reader(s.getInputStream())).start();
			
			output.writeObject(MessageFactory.getLoginMessage(user));
			output.flush();
			
			setupGUI();
			
		} catch (UnknownHostException e) {
			System.out.println("Unknown host");
		} catch (IOException e) {
			System.out.println("Exception");
		}
	}

	private void setupGUI() {
		chatDisplay = new JTextArea();
		chatType = new JTextArea();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout());
		add(contentPanel);
		
		JPanel chatArea = new JPanel();
		chatArea.setLayout(new BorderLayout());
		
		chatDisplay.setEditable(false);
		formatArea(chatDisplay);
		JScrollPane scrollOutput = new JScrollPane(chatDisplay);
		chatArea.add(scrollOutput, BorderLayout.CENTER);
		
		JPanel inputArea = new JPanel();
		inputArea.setLayout(new BoxLayout(inputArea, BoxLayout.Y_AXIS));
		JLabel messageInput = new JLabel("Type your message below:");
		inputArea.add(messageInput);
		
		ArrayList<Integer> keys = new ArrayList<Integer>();
		chatType.setRows(5);
		chatType.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!keys.contains(e.getKeyCode())) {
					keys.add(new Integer(e.getKeyCode()));
				}
				if (keys.contains(new Integer(KeyEvent.VK_CONTROL)) && keys.contains(new Integer(KeyEvent.VK_ENTER))) {
					//TODO sendChat();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				keys.remove(new Integer(e.getKeyCode()));
			}
		});
		formatArea(chatType);
		JScrollPane scrollInput = new JScrollPane(chatType);
		inputArea.add(scrollInput);
		
		JButton send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO sendChat();
				
			}
		});
		inputArea.add(send);
		chatArea.add(inputArea, BorderLayout.SOUTH);
		contentPanel.add(chatArea, BorderLayout.WEST);
		
		JPanel gameArea = new JPanel();
		gameArea.setLayout(new BoxLayout(gameArea, BoxLayout.Y_AXIS));
		JButton newGame = new JButton("Start a new game");
		gameArea.add(newGame);
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					output.writeObject(MessageFactory.getStartMessage());
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton hit = new JButton("Hit");
		hit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					output.writeObject(MessageFactory.getHitMessage());
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gameArea.add(hit);
		JButton stay = new JButton("Stay");
		stay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					output.writeObject(MessageFactory.getStayMessage());
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gameArea.add(stay);
		contentPanel.add(gameArea, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					s.close();
					System.out.println("socket closed\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		setSize(new Dimension(800, 700));
		setVisible(true);
	}
	
	private void formatArea(JTextArea area) {
		Border blackline = BorderFactory.createLineBorder(Color.black, 1);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setBorder(blackline);
	}

	private class Reader implements Runnable {
		private ObjectInputStream input;

		public Reader(InputStream in) {
			try {
				input = new ObjectInputStream(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			//TODO
			
		}

	}

	public static void main(String[] args) {
		new Client();
	}
}
