package com.yrnehraham.ETime.gfx;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.yrnehraham.ETime.engine.*;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class ETimeWindow {

	public JFrame frame;
	private JTextField tcdbUserTextField;
	private JLabel lblTcdbPassword;
	private JPasswordField tcdbPasswordField;
	private JTextField adpUserTextField;
	private JPasswordField adpPasswordField;
	private JLabel lblVersion;
	private JButton btnNewButton;
	
	
	class ImagePanel extends JPanel {

		  /**
		 * 
		 */
		private static final long serialVersionUID = 530913198891235081L;
		private Image img;

		  public ImagePanel(String img) {
		    this(new ImageIcon(img).getImage());
		  }

		  public ImagePanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }

		}


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
		frame.setTitle("Auto ETime");
		
		ImagePanel imagePanel = new ImagePanel(new ImageIcon("infoicon.png").getImage());
		
		
		frame.setBounds(100, 100, 511, 249);
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
		lblTcdbPassword.setBounds(232, 48, 122, 14);
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
		lblAdpPassword.setBounds(232, 96, 122, 14);
		panel.add(lblAdpPassword);

		adpPasswordField = new JPasswordField();
		adpPasswordField.setColumns(10);
		adpPasswordField.setBounds(350, 93, 86, 20);
		panel.add(adpPasswordField);

		JButton submit = new JButton("Submit");
		submit.setMnemonic('S');
		submit.setBounds(183, 176, 89, 23);
		panel.add(submit);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setBounds(159, 151, 146, 14);
		panel.add(progressBar);
		
		lblVersion = new JLabel("Version 0.7.01");
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setBounds(350, 185, 146, 14);
		panel.add(lblVersion);
		
		btnNewButton = new JButton("");
		btnNewButton.setVisible(false);
		btnNewButton.setIcon(new ImageIcon(ETimeWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		btnNewButton.setSelectedIcon(new ImageIcon(ETimeWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		btnNewButton.setBounds(445, 0, 40, 41);
		panel.add(btnNewButton);

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					ETime.submitInfo(tcdbUserTextField.getText(), new String(tcdbPasswordField.getPassword()),
							adpUserTextField.getText(), new String(adpPasswordField.getPassword()));
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} catch (FailingHttpStatusCodeException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
	}
}
