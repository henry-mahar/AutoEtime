package com.yrnehraham.ETime.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebRequest;
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
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class AdpParser {
	
	private HtmlPage page;
	private ArrayList<String> startTimes = new ArrayList<>();
	private ArrayList<String> endTimes = new ArrayList<>();
	
	public AdpParser() {
		
	}
	
	public void getPageContent() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		
		String url = "https://online.adp.com/portal/login.html";
		
		//System.setProperty("javax.net.ssl.trustStore", "C:/Users/Henry/Desktop/Henry/TestKeystore/cacerts");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
        
        
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.getOptions().setJavaScriptEnabled(true);
        
        
        
        page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(20 * 1000); // will wait JavaScript to execute up to 30s
        
        HtmlTextInput userInput = (HtmlTextInput) page.getElementById("user_id");
        userInput.setValueAttribute("***************");

        HtmlPasswordInput passwordInput = (HtmlPasswordInput) page.getElementById("password");
        passwordInput.setValueAttribute("***************");

        HtmlElement theElement2 = (HtmlElement) page.getFirstByXPath("/html/body/ng-include/div[2]/div[1]/div[2]/div[3]/div/form/div[4]/button");
        page = theElement2.click();

        HtmlAnchor welcomeLink = page.getFirstByXPath("//*[@id='node2']/table/tbody/tr/td/nobr/a");
        webClient.waitForBackgroundJavaScript(20 * 1000);
        page = (HtmlPage) welcomeLink.click();
        webClient.waitForBackgroundJavaScript(20 * 1000);
        HtmlAnchor timesheetLink = page.getFirstByXPath("//*[@id='skinContent0']/form/p[3]/font/font/font/a");
        page = (HtmlPage) timesheetLink.click();
        webClient.waitForBackgroundJavaScript(30 * 1000);
        
        page = (HtmlPage) webClient.getWebWindowByName("contentPane").getEnclosedPage();
        
        HtmlAnchor myTimesheet = page.getFirstByXPath("//*[@id='kronos']/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr[2]/td/a");
        page = webClient.getPage(myTimesheet.click().getUrl());
        webClient.waitForBackgroundJavaScript(20 * 1000);
        
        //Etime Timetable has been parsed at this point
        
        
        
        
        
        
        
        String pageAsXml = page.asXml();

        System.out.println("#FULL source after JavaScript execution:\n "+pageAsXml);
        
	}
}


//alternate way of submitting things

/*
HtmlForm form = page.getFormByName("form");
form.getInputByName("user").setValueAttribute("hmahar@grinnell");
form.getInputByName("password").setValueAttribute("N0idontwantWX");
page = (HtmlPage) form.getInputByName("submit").click();
*/
