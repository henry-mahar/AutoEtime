package com.yrnehraham.ETime.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebConnection;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.gargoylesoftware.htmlunit.javascript.host.Iterator;
import com.gargoylesoftware.htmlunit.javascript.host.Set;
import com.gargoylesoftware.htmlunit.javascript.host.URL;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class AdpParser {
	
	private HtmlPage page;
	private ArrayList<String> startTimes = new ArrayList<>();
	private ArrayList<String> endTimes = new ArrayList<>();
	private ArrayList<String> dates = new ArrayList<>();
	
	public AdpParser() {
		
	}
	
	public void getPageContent(TimetableParser tcdbParser) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		
		String url = "https://online.adp.com/portal/login.html";
		
		System.setProperty("javax.net.ssl.trustStore", "C:/Users/Henry/workspace/AutoEtime/cacerts");
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        
        webClient.getCache().clear();
        webClient.getCookieManager().clearCookies();
        
        webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setJavaScriptEnabled(true);
       
        
        
        webClient.waitForBackgroundJavaScript(20 * 1000); // will wait JavaScript to execute up to 30s
        
        page = webClient.getPage(url);
        
        
        HtmlTextInput userInput = (HtmlTextInput) page.getElementById("user_id");
        userInput.setValueAttribute("*********");

        HtmlPasswordInput passwordInput = (HtmlPasswordInput) page.getElementById("password");
        passwordInput.setValueAttribute("**********");

        HtmlElement theElement2 = (HtmlElement) page.getFirstByXPath("/html/body/ng-include/div[2]/div[1]/div[2]/div[3]/div/form/div[4]/button");
        page = theElement2.click();

        HtmlAnchor welcomeLink = page.getFirstByXPath("//*[@id='node2']/table/tbody/tr/td/nobr/a");
        
        ScriptResult result = page.executeJavaScript(welcomeLink.getAttribute("onclick"));
        
        page = (HtmlPage) result.getNewPage();
        webClient.waitForBackgroundJavaScript(20 * 1000);      
        
        HtmlAnchor timesheetLink = page.getFirstByXPath("//*[@id='skinContent0']/form/p[3]/font/font/font/a");
        timesheetLink.setAttribute("target", "_self");
        page = (HtmlPage) timesheetLink.click();
        webClient.waitForBackgroundJavaScript(30 * 1000);

        HtmlAnchor myTimesheet = page.getFirstByXPath("//*[@id='kronos']/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr[2]/td/a");
        page = (HtmlPage) myTimesheet.click();
        webClient.waitForBackgroundJavaScript(20 * 1000);
        //Etime Timetable has been parsed at this point
		System.out.println("ETime succesfully parsed");
		HtmlTable payTable = page.getFirstByXPath("//*[@id='kronos']/form[1]/table[2]/tbody/tr[1]/td[1]/table");
		
		webClient.waitForBackgroundJavaScript(5 * 1000);
		
		HtmlTableCell previousCell = null;
		int tcdbCounter = 0;
		String pastDate = "xyz /placeholder";
		ArrayList<String> endTimes = tcdbParser.getEndTimes();
		ArrayList<String> startTimes = tcdbParser.getStartTimes();
		
		//Adds any additional rows that are needed
		for (int i = 0; i < payTable.getRowCount() - 2; i++) {
			if (tcdbCounter != endTimes.size()) {
				HtmlTableRow row;
				List<HtmlTableCell> cells;
				
				String currentTcdbDate = tcdbParser.getDates().get(tcdbCounter).toString();
				String tcdbDay = currentTcdbDate.substring(
						currentTcdbDate.indexOf(" ", currentTcdbDate.indexOf(" ") + 1) + 1);
				
				if (pastDate.substring(0, 3).equals(currentTcdbDate.substring(0, 3)) && pastDate.substring(pastDate.indexOf("/") + 1, pastDate.length() - 1)
						.equals(tcdbDay)) {
					HtmlAnchor addRow =  (HtmlAnchor) previousCell.getFirstElementChild().getFirstElementChild();
					page = (HtmlPage) addRow.click();
					payTable = page.getFirstByXPath("//*[@id='kronos']/form[1]/table[2]/tbody/tr[1]/td[1]/table");
					row = payTable.getRow(i);
					cells = row.getCells();
				} else {
					row = payTable.getRow(i);
					cells = row.getCells();
				}
				String currentAdpMonth = cells.get(1).asText();
				
				if (currentAdpMonth.substring(0, 3).equals(currentTcdbDate.substring(0, 3))
						&& currentAdpMonth.substring(currentAdpMonth.indexOf("/") + 1, currentAdpMonth.length() - 1)
								.equals(tcdbDay)) {
					tcdbCounter++;
					previousCell = cells.get(0);
				}
				pastDate = currentAdpMonth;
			}
			
		}
		
		//Assigns times from tcdbparser to the input fields
		tcdbCounter = 0;
		for (int i = 2; i < payTable.getRowCount() - 2; i++) {
			if (tcdbCounter != endTimes.size()) {
				HtmlTableRow row;
				List<HtmlTableCell> cells;

				String currentTcdbDate = tcdbParser.getDates().get(tcdbCounter).toString();
				String tcdbDay = currentTcdbDate
						.substring(currentTcdbDate.indexOf(" ", currentTcdbDate.indexOf(" ") + 1) + 1);
				row = payTable.getRow(i);
				cells = row.getCells();

				String currentAdpMonth = cells.get(1).asText();
				DomElement inTime = cells.get(4).getFirstElementChild().getFirstElementChild();
				DomElement outTime = cells.get(6).getFirstElementChild().getFirstElementChild();
				
				if (currentAdpMonth.substring(0, 3).equals(currentTcdbDate.substring(0, 3))
						&& currentAdpMonth.substring(currentAdpMonth.indexOf("/") + 1, currentAdpMonth.length() - 1)
								.equals(tcdbDay)) {
					System.out.println("Assignment made");
					inTime.setAttribute("value", startTimes.get(tcdbCounter));
					outTime.setAttribute("value", endTimes.get(tcdbCounter));
					tcdbCounter++;
					previousCell = cells.get(0);
				}
				pastDate = currentAdpMonth;
			}
		}
		
        System.out.println("The function has completed");
        
        String pageAsXml = page.asXml();
        try{
            PrintWriter writer = new PrintWriter("C:/Users/Henry/Desktop/currentadp.html", "UTF-8");
            writer.println("#FULL source after JavaScript execution:\n "+pageAsXml);
            writer.close();
        } catch (IOException e) {
           // do something
        }
        webClient.close();
	}
	
	public void getPageContentLocal() throws IOException {
		
		String url = "file:///C:/Users/Henry/Desktop/TimeAttendance.html";
		System.setProperty("javax.net.ssl.trustStore", "C:/Users/Henry/workspace/AutoEtime/cacerts");
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        
        webClient.getCache().clear();
        webClient.getCookieManager().clearCookies();
        
        webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setJavaScriptEnabled(true);
        //webClient.getOptions().setUseInsecureSSL(true);
       
        
        
        webClient.waitForBackgroundJavaScript(20 * 1000); // will wait JavaScript to execute up to 30s
        
        page = webClient.getPage(url);
        
        
        HtmlTextInput userInput = (HtmlTextInput) page.getElementById("user_id");
        userInput.setValueAttribute("username");

        HtmlPasswordInput passwordInput = (HtmlPasswordInput) page.getElementById("password");
        passwordInput.setValueAttribute("password");

        HtmlElement theElement2 = (HtmlElement) page.getFirstByXPath("/html/body/ng-include/div[2]/div[1]/div[2]/div[3]/div/form/div[4]/button");
        page = theElement2.click();

        HtmlAnchor welcomeLink = page.getFirstByXPath("//*[@id='node2']/table/tbody/tr/td/nobr/a");
        
        ScriptResult result = page.executeJavaScript(welcomeLink.getAttribute("onclick"));
        
        page = (HtmlPage) result.getNewPage();
        webClient.waitForBackgroundJavaScript(20 * 1000);      
        
        HtmlAnchor timesheetLink = page.getFirstByXPath("//*[@id='skinContent0']/form/p[3]/font/font/font/a");
        timesheetLink.setAttribute("target", "_self");
        page = (HtmlPage) timesheetLink.click();
        webClient.waitForBackgroundJavaScript(30 * 1000);

        HtmlAnchor myTimesheet = page.getFirstByXPath("//*[@id='kronos']/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr[2]/td/a");
        page = (HtmlPage) myTimesheet.click();
        webClient.waitForBackgroundJavaScript(20 * 1000);
        //Etime Timetable page has been parsed at this point
        
        HtmlTable payTable = page.getFirstByXPath("//*[@id='kronos']/form[1]/table[2]/tbody/tr[1]/td[1]/table");
        
        
        /*
        DomNodeList<DomNode> payTableNodeRows = payTable.getChildNodes();
        DomNode previous = null;
        for(int i = 0; i > payTableNodeRows.getLength(); i++) {
        	System.out.println(payTableNodeRows.get(i).toString());
        }
        */
        
        Iterable<DomElement> payTableRows = payTable.getChildElements();
        int counter = 0;
        DomElement previous = null;
        for(DomElement row : payTableRows) {
        	System.out.println(row.toString());
        	System.out.println("\n\n\n");
        	if(row.getAttribute("class").toString() == "Date"){
        		
        	}
        }
        
        
        //String pageAsXml = page.asXml();

        //System.out.println("#FULL source after JavaScript execution:\n "+pageAsXml);
        webClient.close();
        
	}
	
	
	public void fillOutAndSubmit(TimetableParser tcdbParser) {
		
		for(int i = 0; i > tcdbParser.getDates().size(); i++) {

		}
		
		
		
		
	}
	
	
}


