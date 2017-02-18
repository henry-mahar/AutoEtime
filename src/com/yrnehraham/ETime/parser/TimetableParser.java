package com.yrnehraham.ETime.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class TimetableParser {
	
	private Document doc;
	
	public TimetableParser() {
		
	}
	
	public void attemptParse(String doc, String pass, String user) {
		try {
			this.doc = Jsoup.connect(doc).data("login_username", user, "login_password", pass).post();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
