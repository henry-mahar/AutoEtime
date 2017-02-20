package com.yrnehraham.ETime.gfx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.yrnehraham.ETime.parser.TimetableParser;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.yrnehraham.ETime.parser.AdpParser;

public class ETimePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width;
    private int height;
    private JButton enter;
    private JLabel instructions;
    private JTextArea username = new JTextArea("********");
    private JTextArea password = new JTextArea("********");
    
    public ETimePanel(TimetableParser tcdbParser, AdpParser adpParser) {

    	this.width = 400;
    	this.height = 500;
    	this.enter = new JButton("Start");
    	this.instructions = new JLabel("Test");
    	this.setPreferredSize(new Dimension(width, height));
    	this.setBackground(Color.gray);
    	this.add(enter, BorderLayout.SOUTH);
    	this.add(instructions);
    	this.username.setEditable(true);
    	this.password.setEditable(true);
    	this.username.setBackground(Color.BLUE);
    	this.username.setBackground(Color.WHITE);
    	//this.setOpaque(true);
    	
    	this.add(username, BorderLayout.CENTER);
    	this.add(password, BorderLayout.CENTER);
    	
    	this.enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!username.getText().trim().equals("") && !password.getText().trim().equals("")) {
					tcdbParser.attemptParse(username.getText().trim(), password.getText().trim());
					try {
						adpParser.getPageContent();
					} catch (FailingHttpStatusCodeException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tcdbParser.printTimes();
				}
				
			}});
    }
}
