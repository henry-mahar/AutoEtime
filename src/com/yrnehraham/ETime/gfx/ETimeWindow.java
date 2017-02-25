package com.yrnehraham.ETime.gfx;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import javax.swing.JPasswordField;
import javax.swing.JButton;



public class ETimeWindow {

	public JFrame frame;
	private JTextField tcdbUserTextField;
	private JLabel lblTcdbPassword;
	private JPasswordField tcdbPasswordField;
	private JTextField adpUserTextField;
	private JPasswordField adpPasswordField;


	/**
	 * Create the application.
	 */
	public ETimeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 485, 213);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblTcdbUsername = new JLabel("TCDB Username");
		lblTcdbUsername.setBounds(10, 48, 102, 14);
		panel.add(lblTcdbUsername);
		
		tcdbUserTextField = new JTextField();
		tcdbUserTextField.setBounds(122, 45, 86, 20);
		panel.add(tcdbUserTextField);
		tcdbUserTextField.setColumns(10);
		
		lblTcdbPassword = new JLabel("TCDB Password");
		lblTcdbPassword.setBounds(218, 48, 122, 14);
		panel.add(lblTcdbPassword);
		
		tcdbPasswordField = new JPasswordField();
		tcdbPasswordField.setBounds(350, 45, 86, 20);
		tcdbPasswordField.setColumns(10);
		tcdbPasswordField.setToolTipText("");
		panel.add(tcdbPasswordField);
		
		JLabel lblAdpUsername = new JLabel("ADP Username");
		lblAdpUsername.setBounds(10, 96, 102, 14);
		panel.add(lblAdpUsername);
		
		adpUserTextField = new JTextField();
		adpUserTextField.setBounds(122, 93, 86, 20);
		panel.add(adpUserTextField);
		adpUserTextField.setColumns(10);
		
		JLabel lblAdpPassword = new JLabel("ADP Password");
		lblAdpPassword.setBounds(218, 96, 122, 14);
		panel.add(lblAdpPassword);
		
		adpPasswordField = new JPasswordField();
		adpPasswordField.setColumns(10);
		adpPasswordField.setBounds(350, 93, 86, 20);
		panel.add(adpPasswordField);
		
		JButton submit = new JButton("Submit");
		submit.setBounds(189, 140, 89, 23);
		panel.add(submit);
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Submit pressed");
				try {
					ETime.submitInfo(tcdbUserTextField.getText(), tcdbPasswordField.getPassword(), adpUserTextField.getText(), adpPasswordField.getPassword());
				} catch (FailingHttpStatusCodeException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

		});
		pane.add(submit);
		
	}
}