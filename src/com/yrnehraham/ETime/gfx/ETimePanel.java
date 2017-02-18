package com.yrnehraham.ETime.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private JTextField username;
    private JTextField password;
    
    public ETimePanel(TimetableParser parser) {
    	this.width = 200;
    	this.height = 100;
    	this.enter = new JButton("Start");
    	this.instructions = new JLabel("Test");
    	this.setPreferredSize(new Dimension(width, height));
    	this.setBackground(Color.gray);
    	this.add(enter);
    	this.add(instructions);
    	this.enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}});
    }
}
