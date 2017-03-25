package PROCESSES;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class About extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public About() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(0, 0, 220, 104);
			lblNewLabel.setIcon(new ImageIcon(About.class.getResource("/IMAGES/design1.jpg")));
			contentPanel.add(lblNewLabel);
		}
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(About.class.getResource("/IMAGES/design1.jpg")));
		label.setBounds(-336, 0, 770, 104);
		contentPanel.add(label);
		
		JTextArea about = new JTextArea();
		about.setFont(new Font("Monospaced", Font.PLAIN, 12));
		about.setText("Developers:\r\n\tRedentor Periabras\tJay Leonarth Gecarane\r\n\tJedidiah Garcia\t\tPaul Vincent Sablan\r\n\tJordan Michael Bolinas\r\n\t\t\tBSCS 2-4");
		about.setWrapStyleWord(true);
		about.setEditable(false);
		about.setBounds(10, 115, 414, 102);
		contentPanel.add(about);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
	}
}
