package org.concordion.ext.demo.selenium.web;

import org.concordion.ext.selenium.SeleniumEventLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Manages the browser session. 
 */
public class Browser {
    private WebDriver driver;
    
    public Browser() {
        driver = new FirefoxDriver();
    }
    
    public void close() {
        driver.close();
    }
    
    public void addLogger() {
        EventFiringWebDriver efwd = new EventFiringWebDriver(driver);
        efwd.register(new SeleniumEventLogger());
        driver = efwd;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
