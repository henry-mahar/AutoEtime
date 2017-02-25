package com.yrnehraham.ETime.engine;


import java.awt.EventQueue;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.yrnehraham.ETime.gfx.ETimeWindow;
import com.yrnehraham.ETime.gfx.ErrorWindow;
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
	}
	
	public static void submitInfo(String tcdbUsername, char[] tcdbPassword, String adpUsername, char[] adpPassword) throws FailingHttpStatusCodeException, IOException {
		if(tcdbUsername.equals("")) {
			generateErrorWindow("Please input a username for TCDB");
		} else if(tcdbPassword.toString().equals("")){
			generateErrorWindow("Please input a password for TCDB");
		} else if(adpUsername.equals("")){
			generateErrorWindow("Please input a username for ADP");
		} else if(adpPassword.toString().equals("")){
			generateErrorWindow("Please input a password for ADP");
		} else {
			TimetableParser tcdbParser = new TimetableParser();
			AdpParser adpParser = new AdpParser();
			try {
				tcdbParser.attemptParse(tcdbUsername.trim(), tcdbPassword.toString().trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			adpParser.getPageContent(tcdbParser, adpUsername.trim(), adpPassword.toString().trim());
			tcdbParser.printTimes();
		}
	}
	
	public static void generateErrorWindow(String error) {
		try {
			ErrorWindow errorWindow = new ErrorWindow(error);
			errorWindow.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
