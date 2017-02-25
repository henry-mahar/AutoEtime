package com.yrnehraham.ETime.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TimetableParser {

	private HtmlPage page;
	private ArrayList<String> dates = new ArrayList<>();
	private ArrayList<String> startTimes = new ArrayList<>();
	private ArrayList<String> endTimes = new ArrayList<>();

	public TimetableParser() {

	}

	public void attemptParse(String user, String pass) throws FailingHttpStatusCodeException, MalformedURLException, IOException  {
		
		String url = "http://tcdb/timesheet.php";
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        
		page = webClient.getPage(url);

        HtmlForm loginForm = page.getFirstByXPath("/html/body/form");
		
		
        HtmlTextInput userInput = loginForm.getInputByName("login_username");
        userInput.setValueAttribute(user);

        HtmlPasswordInput passwordInput = loginForm.getInputByName("login_password");
        passwordInput.setValueAttribute(pass);
        
        HtmlSubmitInput submitButton = page.getFirstByXPath("/html/body/form/table/tbody/tr[2]/td/input[3]");
        
        page = submitButton.click();
        
        HtmlTable timesheetTable = page.getFirstByXPath("/html/body/table/tbody/tr/td[1]/table");
        HtmlTableRow row;
        List<HtmlTableCell> cells;
        
        for(int i = 1; i < timesheetTable.getRowCount() - 3; i++) {
        	row = timesheetTable.getRow(i);
			cells = row.getCells();
			if(cells.size() >= 3) {
				dates.add(cells.get(1).asText());
				startTimes.add(cells.get(2).asText());
				endTimes.add(cells.get(3).asText());
			}
        }
        webClient.close();
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
