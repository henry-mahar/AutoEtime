package com.yrnehraham.ETime.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class TimetableParser {

	private Document doc;
	private ArrayList<String> dates = new ArrayList<>();
	private ArrayList<String> startTimes = new ArrayList<>();
	private ArrayList<String> endTimes = new ArrayList<>();

	public TimetableParser() {

	}

	public void attemptParse(String user, String pass) {
		try {
			this.doc = Jsoup.connect("http://tcdb/timesheet.php").data("login_username", user, "login_password", pass).post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(doc.toString());
		Elements timeTable = this.doc.getElementsByClass("ucdb");
		Elements rows = timeTable.get(0).select("tr");
		
		
		for(int i = 1; i < rows.size(); i += 1) {
			Element row = rows.get(i);
		    Elements cols = row.select("td");
		    if(cols.size() > 4) {
		    	dates.add(cols.get(1).toString().substring(cols.get(1).toString().indexOf("\">") + 2, cols.get(1).toString().indexOf("</a>")));
		    	startTimes.add(cols.get(2).toString().substring(4, 9));
			    endTimes.add(cols.get(3).toString().substring(4, 9));
		    }
		}
	}

	public void printTimes() {
		for (int i = 0; i < startTimes.size(); i++)
			System.out.println("Date " + dates.get(i) + " Start Time " + startTimes.get(i) + " End Time " + endTimes.get(i));
	}
	
	public ArrayList<String> getDates() {
		return dates;
	}
	
	public ArrayList<String> getStartTimes() {
		return startTimes;
	}
	
	public ArrayList<String> getEndTimes() {
		return endTimes;
	}
}
