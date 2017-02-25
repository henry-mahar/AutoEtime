package com.yrnehraham.ETime.gfx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.yrnehraham.ETime.parser.TimetableParser;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.yrnehraham.ETime.parser.AdpParser;

public class ETimePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    
    public ETimePanel(TimetableParser tcdbParser, AdpParser adpParser) {
    	
    	this.setBackground(Color.gray);
    	//this.setOpaque(true);
    	
    	
    	JTextArea tcdbUsername = new JTextArea("");
    	JTextArea tcdbPassword = new JTextArea("");
    	JTextArea adpUsername = new JTextArea("");
    	JTextArea adpPassword = new JTextArea("");
    	JButton submit = new JButton("Start");;
    	
    	
    	
    	
    	JLabel userLabel = new JLabel("Username: ");
    	userLabel.setDisplayedMnemonic(KeyEvent.VK_U);
    	JTextField userTextField = new JTextField();
    	userLabel.setLabelFor(userTextField);
        this.add(userLabel, BorderLayout.WEST);
        this.add(userTextField, BorderLayout.CENTER);
    	
    	
    	
    	/*
    	this.enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!username.getText().trim().equals("") && !password.getText().trim().equals("")) {
					tcdbParser.attemptParse(username.getText().trim(), password.getText().trim());
					
					try {
						adpParser.getPageContent(tcdbParser);
					} catch (FailingHttpStatusCodeException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					tcdbParser.printTimes();
					
					try {
						Runtime rTime = Runtime.getRuntime();
						String[] command = new String[]{"C:/Users/Henry/Desktop/chromeShortcut.lnk ", "C:/Users/Henry/Desktop/currentadp.html"};
						Process pc = rTime.exec(command);
						pc.waitFor();
					} catch (Exception ex) {
						System.out.println(ex);
					}
					
					System.exit(0);
				}
			}});
			*/
    }
}
