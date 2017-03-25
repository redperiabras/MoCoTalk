package SERVER;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import PROCESSES.SpeechProcess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class MainServer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea log;
	private JTextField t;
	private JToggleButton tglbtnStartStop;
	private Server server;
	
	private static SpeechProcess talk = new SpeechProcess();
	public JTextField serverAddress;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				try {
					MainServer frame = new MainServer(1500);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					new Thread() {
						public void run() {
							talk.say("Welcome User!");
							
							if(talk.systemVoice.getName().equals("mbrola_us1"))
								talk.say("I am Sue, your MoCo Talk guide.");
								
							else
								talk.say("I am Zeus, your alternate MoCo Talk user guide.");
					
							talk.say("You are currently running the server application.");
							talk.say("To start the fun of sending secret messages please provide a port number or simply press the start button");
						}
					}.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainServer(int port) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainServer.class.getResource("/IMAGES/ditdot.png")));
		server = null;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(server != null) {
					try {
						server.stop();
						talk.dispose();
					}
					catch(Exception eClose) {}
					server = null;
				}
				dispose();
				System.exit(0);
			}
		});
		setTitle("MoCoTalk Server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 301);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainServer.class.getResource("/IMAGES/design1.jpg")));
		contentPane.add(label, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(0, 0, 385, 157);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPortNumber = new JLabel("Port Number: ");
		lblPortNumber.setForeground(Color.WHITE);
		lblPortNumber.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		lblPortNumber.setBounds(10, 78, 150, 34);
		panel_1.add(lblPortNumber);
		
		t = new JTextField();
		t.setHorizontalAlignment(SwingConstants.TRAILING);
		t.setText("1500");
		t.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		t.setBounds(183, 78, 192, 34);
		panel_1.add(t);
		t.setColumns(10);
		
		tglbtnStartStop = new JToggleButton("START");
		tglbtnStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//to stop the server
				if(server!=null) {
					tglbtnStartStop.setText("START");
					new Thread() {
						public void run() {
							talk.say("Server Closed");
						}
					}.start();
					
					server.stop();
					server = null;
					t.setEditable(true);
					serverAddress.setText("");
					appendEvent("Server Closed\n");
				}
				
				//to start the server
				else {
					int port;
					try {
						port = Integer.parseInt(t.getText().trim());
					}catch(Exception ea) {
						new Thread() {
							public void run() {
								talk.say("Sorry but you have entered an invalid port number");
							}
						}.start();
						JOptionPane.showMessageDialog(rootPane, "Invalid port number","Port Number",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					server = new Server(port, MainServer.this);
					
					new ServerRunning().start();
					tglbtnStartStop.setText("STOP");
					new Thread() {
						public void run() {
							talk.say("Server Started on port "+ port +" and is currently waiting for clients.");
						}
					}.start();
					t.setEditable(false);
				}
			}
		});
		tglbtnStartStop.setBounds(133, 123, 121, 23);
		panel_1.add(tglbtnStartStop);
		
		JLabel lblServerAddress = new JLabel("Server Address:");
		lblServerAddress.setForeground(Color.WHITE);
		lblServerAddress.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		lblServerAddress.setBounds(10, 42, 163, 34);
		panel_1.add(lblServerAddress);
		
		serverAddress = new JTextField();
		serverAddress.setEditable(false);
		serverAddress.setHorizontalAlignment(SwingConstants.TRAILING);
		serverAddress.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		serverAddress.setColumns(10);
		serverAddress.setBounds(183, 42, 192, 34);
		panel_1.add(serverAddress);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(386, 37, 385, 120);
		panel.add(scrollPane);
		
		log = new JTextArea();
		scrollPane.setViewportView(log);
		
		JLabel lblEventLog = new JLabel("Event Log:");
		lblEventLog.setForeground(Color.WHITE);
		lblEventLog.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));
		lblEventLog.setBounds(384, 0, 150, 34);
		panel.add(lblEventLog);
	}
	
	public void appendEvent(String str) {
		log.append(str);
		log.setCaretPosition(log.getText().length()-1);
	}
	public void setAlert(String msg, String header, int type) {
		JOptionPane.showMessageDialog(rootPane,msg,header,type);
	}
	class ServerRunning extends Thread {
		public void run() {
			server.start();         // should execute until if fails
			// the server failed
			tglbtnStartStop.setText("START");
			t.setEditable(true);
			server = null;
		}
	}
}
