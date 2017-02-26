package com.yrnehraham.ETime.gfx;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ErrorWindow {

	public JFrame frame;


	public ErrorWindow(String error) {
		initialize(error);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String error) {
		
		frame = new JFrame();
		int scale = frame.getFontMetrics(new Font("Tahoma", Font.PLAIN, 11)).stringWidth(error) / 2;
		frame.setBounds(100, 100, 250 + scale, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 234 + scale, 110);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnOk = new JButton("OK");
		btnOk.setMnemonic('O');
		btnOk.setBounds(87 + scale / 2, 77, 61, 22);
		panel.add(btnOk);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}

		});
		
		JLabel lblErrorMessage = new JLabel(error);
		lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorMessage.setBounds(10, 11, 214 + scale, 55);
		panel.add(lblErrorMessage);
		
	}
}
