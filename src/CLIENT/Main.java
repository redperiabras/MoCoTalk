package CLIENT;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import AUTOMATA.Automaton;
import PROCESSES.About;
import PROCESSES.BeepEmitter;
import PROCESSES.ChatMessage;
import PROCESSES.SpeechProcess;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.io.InputStream;


public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnMorse;
	private JButton btnBroadcast;
	private JButton btnVoice;
	private JLabel lblName;
	private JTextArea txtrCompose;
	private JTextArea txtrReceived;
	private String current="";
	
	private boolean connected;
	private boolean backSpacePressed = false;
	
	Client client;
	
	private BeepEmitter beep = new BeepEmitter();
	private SpeechProcess talk = new SpeechProcess();
	private Automaton auto = new Automaton();
	
	InputStream is_karmatic = getClass().getResourceAsStream("/FONTS/KA1.TTF");
	Font font_karmatic;
	
	InputStream is_vonique = getClass().getResourceAsStream("/FONTS/VONIQUE.TTF");
	Font font_vonique;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/IMAGES/ditdot.png")));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				btnMorse.requestFocus();
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				btnMorse.requestFocus();
				new Thread() {
					public void run() {
						System.out.print(talk.systemVoice.getName());
						talk.say("Welcome User!");
						if(talk.systemVoice.getName().equals("mbrola_us1"))
							talk.say("I am Sue, your MoCo Talk guide.");
							
						else
							talk.say("I am Zeus, your alternate MoCo Talk user guide.");
				
						talk.say("To start the fun, click Connection and connect to Server");
					}
				}.start();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				talk.dispose();
			}
		});
		setRootPaneCheckingEnabled(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 763, 738);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			font_karmatic = Font.createFont(Font.TRUETYPE_FONT, is_karmatic);
			font_vonique = Font.createFont(Font.TRUETYPE_FONT, is_vonique);
			font_vonique = font_vonique.deriveFont(Font.BOLD, 30);
			is_karmatic.close();
			is_vonique.close();
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnMorse = new JButton("");
		btnMorse.addKeyListener(new KeyAdapter() {
			long startTime, endTime=0;
			int keyPressCounter=0;
			
			char c;
			
			@Override
			public void keyPressed(KeyEvent e) {
				c = e.getKeyChar();
				
				if(c == KeyEvent.VK_ENTER){
					e.consume();
					if (isConnected()) {
						if(!getCompose().getText().equals("")) {
							client.sendMessage(new ChatMessage(ChatMessage.MESSAGE,getCompose().getText()));
							getCompose().setText("");
						}
						else {
							new Thread() {
								public void run() {
									talk.say("No message to transmit");
								}
							}.start();
							JOptionPane.showMessageDialog(rootPane, "No message to transmit", "Message", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						new Thread() {
							public void run() {
								talk.say("Cannot broadcast message");
							}
						}.start();
						JOptionPane.showMessageDialog(rootPane, "Cannot broadcast message", "No Connection", JOptionPane.ERROR_MESSAGE);	
					}
				}
				else if(c == KeyEvent.VK_BACK_SPACE){
					e.consume();
					backSpacePressed=true;
					getCompose().setText(getCompose().getText().substring(0, getCompose().getText().length()-1));
				}
				else if (c == KeyEvent.VK_SPACE){
					btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button5.png")));
					beep.playBeep();
					e.consume();
					keyPressCounter++;
					
					if(keyPressCounter == 1) {
						startTime = System.currentTimeMillis();
												
						if((endTime != 0)){
							long timeCount;
							
							if(backSpacePressed) {
								timeCount = 199;
								backSpacePressed = false;
								current = "";
							}
							else
								timeCount = startTime - endTime;
							
							if((timeCount > 400)) {
								if(!(getCompose().getText().charAt(getCompose().getText().length()-1)==' '))
									getCompose().append(" ");
								if(!(getCompose().getText().charAt(getCompose().getText().length()-2)=='/'))
									getCompose().append("/ ");
								current="";
							}
							else if((timeCount>200)) {
								if(!(getCompose().getText().charAt(getCompose().getText().length()-1)==' '))
									getCompose().append(" ");
								current="";
							}
						}
					}
				}
				else {
					e.consume();
					Toolkit.getDefaultToolkit().beep();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button3.png")));
				endTime = System.currentTimeMillis();
				beep.stopBeep();
				keyPressCounter=0;
				
				long timeCount = endTime - startTime;
				
				if(c == KeyEvent.VK_SPACE) {
					e.consume();
					if((timeCount <= 100)) {
						getCompose().append("·");
						current+="·";
					}
					else if((timeCount > 100)) {
						getCompose().append("-");
						current+="-";
					}
					System.out.println(current);
					System.out.println(auto.codeExist(current));
					if(!auto.codeExist(current)){	
						Toolkit.getDefaultToolkit().beep();
						getCompose().setText(getCompose().getText().substring(0, getCompose().getText().length()-current.length()));
						current = "";
					}
				}
			}
		});
		btnMorse.addMouseListener(new MouseAdapter() {
			long startTime, endTime;
			int keyPressCounter=0;
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				beep.playBeep();
				btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button5.png")));
				arg0.consume();
				keyPressCounter++;
				
				if(keyPressCounter == 1) {
					startTime = System.currentTimeMillis();
					
					if(endTime != 0){
						long timeCount = startTime - endTime;
						
						if((timeCount > 400)) {
							if(!(getCompose().getText().charAt(getCompose().getText().length()-1)==' '))
								getCompose().append(" ");
							if(!(getCompose().getText().charAt(getCompose().getText().length()-2)=='/'))
								getCompose().append("/ ");
							current="";
						}
						else if(timeCount>200) {
							if(!(getCompose().getText().charAt(getCompose().getText().length()-1)==' '))
								getCompose().append(" ");
							current="";
						}
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				endTime = System.currentTimeMillis();
				btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button4.png")));
				beep.stopBeep();
				keyPressCounter=0;
				
				long timeCount = endTime - startTime;
				
				if((timeCount <= 100)) {
					getCompose().append("·");
					current+="·";
				}
				else if((timeCount > 100)) {
					getCompose().append("-");
					current+="-";
				}
				System.out.println(current);
				if(!auto.codeExist(current)){	
					Toolkit.getDefaultToolkit().beep();
					getCompose().setText(getCompose().getText().substring(0, getCompose().getText().length()-current.length()));
					current = "";
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button3.png")));
			}
		});
		btnMorse.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button4.png")));
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 165, 0));
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 757, 27);
		contentPane.add(menuBar);
		
		JMenu mnConnection = new JMenu("Connection");
		mnConnection.setForeground(Color.WHITE);
		mnConnection.setContentAreaFilled(false);
		menuBar.add(mnConnection);
		
		JMenuItem mntmConnect = new JMenuItem("Connect to server");
		mntmConnect.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/server.png")));
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(connected) {
					JOptionPane.showMessageDialog(rootPane, "Already connected to a server", "Connection", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					new ConnectionInitializer(Main.this);
				}
				
			}
		});
		mntmConnect.setOpaque(true);
		mntmConnect.setBorder(null);
		mntmConnect.setBackground(Color.WHITE);
		mntmConnect.setContentAreaFilled(false);
		mnConnection.add(mntmConnect);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/disconnect.png")));
		mntmDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isConnected()) {
					client.sendMessage(new ChatMessage(ChatMessage.LOGOUT,""));
					JOptionPane.showMessageDialog(rootPane,"Successfully disconnected","Connection",JOptionPane.INFORMATION_MESSAGE);
					getCompose().setText("");
					lblName.setText("");
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Not Connected", "Connection", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mntmDisconnect.setOpaque(true);
		mntmDisconnect.setBorder(null);
		mntmDisconnect.setBackground(Color.WHITE);
		mnConnection.add(mntmDisconnect);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setForeground(Color.WHITE);
		mnHelp.setContentAreaFilled(false);
		menuBar.add(mnHelp);
		
		JMenuItem mntmMyIp = new JMenuItem("My IP");
		mntmMyIp.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/ip.png")));
		mntmMyIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            InetAddress host = InetAddress.getLocalHost();
		            JOptionPane.showMessageDialog(
		                    rootPane,
		                    "Your IP: " + host.getHostAddress()
		                    + "\nHost Name: " + host.getHostName(),
		                    "My IP Address",
		                    JOptionPane.INFORMATION_MESSAGE,
		                    new javax.swing.ImageIcon(getClass().getResource("/IMAGES/chat_32x32.png")));
		        } catch (UnknownHostException ex) {
		            ex.printStackTrace();
		        }
			}
		});
		mntmMyIp.setBackground(Color.WHITE);
		mntmMyIp.setBorder(null);
		mnHelp.add(mntmMyIp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About about = new About();
				new Thread() {
					public void run() {
						talk.say("Mo Co Talk is made possible by");
						talk.say("Reden tor Pe riabras");
						talk.say("Jay Leonarth Geca ra ne");
						talk.say("Jedi diah Garcia");
						talk.say("Paul Vincent Sablan");
						talk.say("and Jordan Michael Boli nas");
						talk.say("of Computer Science Research");
						talk.say("BSCS 2 4");
					}
				}.start();
				about.setLocationRelativeTo(rootPane);
				about.setVisible(true);
			}
		});
		mntmAbout.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/about.png")));
		mntmAbout.setBackground(Color.WHITE);
		mntmAbout.setBorder(null);
		mnHelp.add(mntmAbout);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setBorder(null);
		scrollPane.setBounds(36, 187, 692, 132);
		contentPane.add(scrollPane);
		
		txtrReceived = new JTextArea();
		txtrReceived.setLineWrap(true);
		txtrReceived.setForeground(Color.WHITE);
		txtrReceived.setFont(font_vonique);
		txtrReceived.setEditable(false);
		txtrReceived.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(txtrReceived);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(36, 396, 692, 132);
		contentPane.add(scrollPane_1);
		
		txtrCompose = new JTextArea();
		txtrCompose.setLineWrap(true);
		txtrCompose.setForeground(Color.WHITE);
		txtrCompose.setFont(font_vonique);
		txtrCompose.setEditable(false);
		txtrCompose.setBackground(Color.GRAY);
		scrollPane_1.setViewportView(txtrCompose);
		
		btnMorse.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/button3.png")));
		btnMorse.setOpaque(false);
		btnMorse.setContentAreaFilled(false);
		btnMorse.setBorderPainted(false);
		btnMorse.setBounds(315, 596, 136, 113);
		contentPane.add(btnMorse);
		
		btnBroadcast = new JButton("");
		btnBroadcast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isConnected()) {
					if(!getCompose().getText().equals("")) {
						client.sendMessage(new ChatMessage(ChatMessage.MESSAGE,getCompose().getText()));
						getCompose().setText("");
					}
					else {
						new Thread() {
							public void run() {
								talk.say("No message to transmit");
							}
						}.start();
						JOptionPane.showMessageDialog(rootPane, "No message to transmit", "Message", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					new Thread() {
						public void run() {
							talk.say("Cannot broadcast message");
						}
					}.start();
					JOptionPane.showMessageDialog(rootPane, "Cannot broadcast message", "No Connection", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnBroadcast.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				btnBroadcast.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/broadcast3.jpg")));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnBroadcast.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/broadcast2.jpg")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnBroadcast.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/broadcast.jpg")));
			}
		});
		btnBroadcast.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				btnBroadcast.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/broadcast2.jpg")));
			}
		});
		btnBroadcast.setFocusPainted(false);
		btnBroadcast.setContentAreaFilled(false);
		btnBroadcast.setBorderPainted(false);
		btnBroadcast.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/broadcast.jpg")));
		btnBroadcast.setBounds(554, 630, 193, 68);
		contentPane.add(btnBroadcast);
		getRootPane().setDefaultButton(btnBroadcast);
		
		btnVoice = new JButton("");
		btnVoice.setToolTipText("Translate Message");
		btnVoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtrReceived.getText().equals("")) {
					if(lblName.getText().equalsIgnoreCase("Broadcasting Message")) {
						String output = auto.genOutput(txtrReceived.getText());
						new Thread() {
							public void run() {
								talk.say(output);
							}
						}.start();
						JOptionPane.showMessageDialog(rootPane, output, lblName.getText(), JOptionPane.PLAIN_MESSAGE);
						btnMorse.requestFocus();
					}
					else {
						String output = auto.genOutput(txtrReceived.getText());
						new Thread() {
							public void run() {
								talk.say(lblName.getText());
								talk.say(output);
							}
						}.start();
						JOptionPane.showMessageDialog(rootPane, output, lblName.getText(), JOptionPane.PLAIN_MESSAGE);
						btnMorse.requestFocus();
					}
					
				}
				else{
					new Thread() {
						public void run() {
							talk.say("Sorry but your inbox is empty.");
						}
					}.start();
					JOptionPane.showMessageDialog(rootPane, "Inbox Empty", "Inbox", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnVoice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnVoice.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/voice.png")));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				btnVoice.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/voice3.png")));
			
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				btnVoice.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/voice2.png")));
			}
		});
		btnVoice.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				btnVoice.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/voice2.png")));
			}
		});
		btnVoice.setFocusPainted(false);
		btnVoice.setOpaque(false);
		btnVoice.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/voice.png")));
		btnVoice.setContentAreaFilled(false);
		btnVoice.setBorderPainted(false);
		btnVoice.setBounds(637, 327, 110, 58);
		contentPane.add(btnVoice);
		
		lblName = new JLabel("");
		lblName.setForeground(Color.DARK_GRAY);
		lblName.setFont(new Font("Karmatic Arcade", Font.PLAIN, 18));
		lblName.setBounds(26, 137, 709, 39);
		contentPane.add(lblName);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(Main.class.getResource("/IMAGES/AutomataUI.jpg")));
		background.setBounds(0, 26, 757, 683);
		contentPane.add(background);
	}
	
	public void receiveMessage(char s) {
		txtrReceived.append(s+"");
	}
	
	public void initReceive() {
		txtrReceived.setText("");
	}
	
	public void setNotif(String notif) {
		new Thread(){
			@Override
			public void run() {
				talk.say(notif);
				/*lblNotif.setText(notif);
				try {
					Thread.sleep(4000);
					lblNotif.setText("");
				} catch (InterruptedException e) {
//				 TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		}.start();
	}
	
	public void setAlert(String alert, String header, int type) {
		JOptionPane.showMessageDialog(rootPane,alert,header,type);
	}
	
	public void connectionFailed() {
		setConnected(false);
	}

	public JTextArea getCompose() {
		return txtrCompose;
	}

	public void setCompose(JTextArea compose) {
		this.txtrCompose = compose;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	void setClient(Client client) {
		this.client=client;
	}
	
	public void setSender(String from, String str) {
		lblName.setText(from+" "+str);
	}
	
	public JLabel getSender() {
		return lblName;
	}
}
