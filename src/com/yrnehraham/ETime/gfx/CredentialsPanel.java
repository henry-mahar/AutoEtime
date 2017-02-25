package com.yrnehraham.ETime.gfx;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CredentialsPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3081975327062862583L;

	private JLabel websiteLabel;
	
	private JPanel userPanel;
	private JLabel userLabel;
	private JTextField userTextField;
	
	private JPanel passPanel;
	private JLabel passLabel;
	private JPasswordField passTextField;
	
	public CredentialsPanel(String website) {
		
		websiteLabel = new JLabel(website);
		
		
		userPanel = new JPanel(new BorderLayout());
		userLabel = new JLabel("Username: ");
	    userLabel.setDisplayedMnemonic(KeyEvent.VK_U);
	    userTextField = new JTextField();
	    userLabel.setLabelFor(userTextField);
	    userPanel.add(websiteLabel, BorderLayout.NORTH);
	    userPanel.add(userLabel, BorderLayout.WEST);
	    userPanel.add(userTextField, BorderLayout.CENTER);

	    passPanel = new JPanel(new BorderLayout());
	    passLabel = new JLabel("Password: ");
	    passLabel.setDisplayedMnemonic(KeyEvent.VK_P);
	    passTextField = new JPasswordField();
	    passLabel.setLabelFor(passTextField);
	    passPanel.add(passLabel, BorderLayout.WEST);
	    passPanel.add(passTextField, BorderLayout.CENTER);
	}
	
	public String getUserInfo() {
		return userTextField.getText().trim();
	}
	public char[] getPassInfo() {
		return passTextField.getPassword();
	}
}
