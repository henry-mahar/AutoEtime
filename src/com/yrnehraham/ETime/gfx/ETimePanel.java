package com.yrnehraham.ETime.gfx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.yrnehraham.ETime.parser.TimetableParser;

public class ETimePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width;
    private int height;
    private JButton enter;
    private JLabel instructions;
    private JTextArea username = new JTextArea("username");
    private JTextArea password = new JTextArea("password");
    
    public ETimePanel(TimetableParser parser) {

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
				if (!username.getText().trim().equals("") && !password.getText().trim().equals("") && parser != null) {
					parser.attemptParse(username.getText().trim(), password.getText().trim());
					parser.printTimes();
				} else if (parser == null) {
					throw new NullPointerException("Parser is null");
				}
			}});
    }
}