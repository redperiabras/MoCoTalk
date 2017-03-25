package CLIENT;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import PROCESSES.SpeechProcess;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConnectionInitializer extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtLocalhost;
	private JTextField textPortNumber;
	private JTextField txtUsername;
	private Client client;
	int port;
	String username;
	String localhost;
	
	private SpeechProcess talk = new SpeechProcess();

	/**
	 * Launch the application.
	 */
/**
	 * Create the dialog.
	 */
	public ConnectionInitializer(Main mc){
		addWindowListener(new WindowAdapter() {
		@Override
		public void windowOpened(WindowEvent e) {
			txtUsername.requestFocus();
			}
		});
		setVisible(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(mc);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon(ConnectionInitializer.class.getResource("/IMAGES/design1.jpg")));
		label.setBounds(0, 0, 820, 104);
		contentPanel.add(label);
		
		JLabel lblPortNumber = new JLabel("Port Number :");
		lblPortNumber.setForeground(Color.WHITE);
		lblPortNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblPortNumber.setBounds(10, 147, 99, 21);
		contentPanel.add(lblPortNumber);
		
		JLabel lblServerAddress = new JLabel("Server Address :");
		lblServerAddress.setForeground(Color.WHITE);
		lblServerAddress.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblServerAddress.setBounds(10, 115, 105, 21);
		contentPanel.add(lblServerAddress);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblUsername.setBounds(10, 179, 73, 21);
		contentPanel.add(lblUsername);
		
		txtLocalhost = new JTextField();
		txtLocalhost.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtLocalhost.setText("localhost");
		txtLocalhost.setBounds(134, 115, 290, 20);
		contentPanel.add(txtLocalhost);
		txtLocalhost.setColumns(10);
		
		textPortNumber = new JTextField();
		textPortNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textPortNumber.setText("1500");
		textPortNumber.setColumns(10);
		textPortNumber.setBounds(134, 147, 290, 20);
		contentPanel.add(textPortNumber);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsername.setText("Client");
		txtUsername.setColumns(10);
		txtUsername.setBounds(134, 180, 290, 20);
		contentPanel.add(txtUsername);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Connect");
				okButton.setFont(new Font("Segoe UI Light", Font.BOLD, 11));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						username = txtUsername.getText();
						localhost = txtLocalhost.getText();
						try {
							port = Integer.parseInt(textPortNumber.getText());
						}catch(NumberFormatException ea) {
							textPortNumber.requestFocus();
							JOptionPane.showMessageDialog(rootPane, "Invalid Port Number", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						client = new Client(localhost, port, username, mc);
						
						if(!client.start()) {
							//mc.setAlert("Connection Failed", "Connection", JOptionPane.INFORMATION_MESSAGE);
							mc.setConnected(false);
						}
						else {
							new Thread() {
								public void run() {
									talk.say("Hey " + txtUsername.getText() + "! You are now connected to " + txtLocalhost.getText() +" at port number "+textPortNumber.getText());
								}
							}.start();
							mc.setAlert("Succesfully connected", "Connection", JOptionPane.INFORMATION_MESSAGE);
							mc.setClient(client);
							mc.getCompose().setText("");
							mc.setConnected(true);
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Segoe UI Light", Font.BOLD, 11));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public String getUsername() {
		return username;
	}
	public String getLocalHost() {
		return localhost;
	}
	public int getPort() {
		return port;
	}
}
