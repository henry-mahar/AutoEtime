package com.yrnehraham.ETime.engine;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.yrnehraham.ETime.gfx.CredentialsPanel;
import com.yrnehraham.ETime.gfx.ETimePanel;
import com.yrnehraham.ETime.gfx.ETimeWindow;
import com.yrnehraham.ETime.parser.TimetableParser;
import com.yrnehraham.ETime.parser.AdpParser;

public class ETime {
    
	public static void main(String[] args) {
		
		
		System.setProperty("javax.net.ssl.trustStore","C:/Users/Henry/workspace/AutoEtime/cacerts");
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ETimeWindow window = new ETimeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("AutoETime");
		
		addComponentsToPane(frame.getContentPane(), frame);
		Graphics g = frame.getGraphics();
		frame.paintAll(g);
		frame.pack();
		System.out.println("Frame Packed");
        //frame.setVisible(true);
	}
	
	public static void addComponentsToPane(Container pane, JFrame frame) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        
        CredentialsPanel tcdbPanel = new CredentialsPanel("tcdb");
		CredentialsPanel adpPanel = new CredentialsPanel("adp");
		
		tcdbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		tcdbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		pane.add(tcdbPanel);
		pane.add(adpPanel);
		
		JButton submit = new JButton("Submit");
		submit.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
        
    }
	
	public static void submitInfo(String tcdbUsername, char[] tcdbPassword, String adpUsername, char[] adpPassword) throws FailingHttpStatusCodeException, IOException {
		
		if(tcdbUsername.equals("")) {
			//Please enter tcdbUsername
		} else if(tcdbPassword == null){
			//Please enter tcdbPassword
		} else if(adpUsername == null){
			//Please enter adpUsername
		} else if(adpPassword == null){
			//Please enter adpPassword
		} else {
			TimetableParser tcdbParser = new TimetableParser();
			AdpParser adpParser = new AdpParser();
			tcdbParser.attemptParse(tcdbUsername, tcdbPassword.toString());
			adpParser.getPageContent(tcdbParser);
			tcdbParser.printTimes();
		}
	}
}
