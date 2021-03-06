package com.yrnehraham.ETime.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;


public class AdpParser {
	
	private HtmlPage page;
	
	public AdpParser() {
		
	}
	
	public boolean getPageContent(TimetableParser tcdbParser, String username, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		
		String url = "https://online.adp.com/portal/login.html";
		
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
        userInput.setValueAttribute(username);
        
        HtmlPasswordInput passwordInput = (HtmlPasswordInput) page.getElementById("password");
        passwordInput.setValueAttribute(password);
        
        HtmlElement theElement2 = (HtmlElement) page.getFirstByXPath("/html/body/ng-include/div[2]/div[1]/div[2]/div[3]/div/form/div[4]/button");
        page = theElement2.click();

        HtmlAnchor welcomeLink = page.getFirstByXPath("//*[@id='node2']/table/tbody/tr/td/nobr/a");
        if(welcomeLink == null) {
        	webClient.close();
        	return false;
        } else {
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
    				DomElement transferCode = cells.get(5).getFirstElementChild().getFirstElementChild();
    				
    				if (currentAdpMonth.substring(0, 3).equals(currentTcdbDate.substring(0, 3))
    						&& currentAdpMonth.substring(currentAdpMonth.indexOf("/") + 1, currentAdpMonth.length() - 1)
    								.equals(tcdbDay)) {
    					inTime.setAttribute("value", startTimes.get(tcdbCounter));
    					inTime.fireEvent(Event.TYPE_CHANGE);
    					transferCode.setAttribute("value", ";220115/856///");
    					transferCode.fireEvent(Event.TYPE_CHANGE);
    					outTime.setAttribute("value", endTimes.get(tcdbCounter));
    					outTime.fireEvent(Event.TYPE_CHANGE);
    					tcdbCounter++;
    					previousCell = cells.get(0);
    				}
    				pastDate = currentAdpMonth;
    			}
    		}
    		
    		HtmlAnchor saveButton = page.getFirstByXPath("//*[@id='saveButton']");
    		page = saveButton.click();
    		
    		//This will be used for displaying the succesfully saved message
    		//HtmlElement saveMessage = page.getFirstByXPath("//*[@id='kronos']/form[1]/div/div");
    		
            webClient.close();
            return true;
        }
        
        
	}
	
}


