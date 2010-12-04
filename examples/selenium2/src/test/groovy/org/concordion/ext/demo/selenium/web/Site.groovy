package org.concordion.ext.demo.selenium.web

import org.concordion.ext.selenium.SeleniumEventLogger

import org.openqa.selenium.WebDriver 
import org.openqa.selenium.firefox.FirefoxDriver 
import org.openqa.selenium.support.PageFactory 
import org.openqa.selenium.support.events.EventFiringWebDriver 

/**
 * Manages the browser session. 
 */
public class Site {
    WebDriver driver
    
    private Site() {
        driver = new FirefoxDriver()
    }
    
    void close() {
        driver.close()
    }
    
    void initPage(page) {
        PageFactory.initElements(driver, page)
    }
    
    void addLogger() {
        EventFiringWebDriver efwd = new EventFiringWebDriver(driver)
        efwd.register(new SeleniumEventLogger())
        driver = efwd
    }
}
