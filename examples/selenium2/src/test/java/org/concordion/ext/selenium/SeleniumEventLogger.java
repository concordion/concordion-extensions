package org.concordion.ext.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Requires Selenium 2.23.0 or later to retrieve element names from WebElement.toString(). 
public class SeleniumEventLogger implements WebDriverEventListener {

    final Logger logger = LoggerFactory.getLogger("selenium.events");
    private String oldValue;

    @Override
    public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
        oldValue = arg0.getAttribute("value");
    }

    @Override
    public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
        String elementName = getElementName(arg0);
        try {
            String newValue = arg0.getAttribute("value");
            if (!newValue.equals(oldValue)) {
                if (newValue.length() == 0) {
                    logger.info("[{}] - cleared value", elementName);
                } else {
                    logger.info("[{}] - changed value to '{}'", elementName, newValue);
                }
            }
        } catch (Exception e) {
            logger.info("[{}] - changed value", elementName);
        }
    }

    @Override
    public void afterClickOn(WebElement arg0, WebDriver arg1) {
    }

    @Override
    public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
        logger.debug("[{}] - found", arg0);
    }

    @Override
    public void afterNavigateBack(WebDriver arg0) {
        logger.info("Navigated back");
    }

    @Override
    public void afterNavigateForward(WebDriver arg0) {
        logger.info("Navigated forward");
    }

    @Override
    public void afterNavigateTo(String arg0, WebDriver arg1) {
        logger.info("Navigated to '{}'", arg0);
    }

    @Override
    public void afterScript(String arg0, WebDriver arg1) {
        logger.info("Ran script '{}'", arg0);
    }

    @Override
    public void beforeClickOn(WebElement arg0, WebDriver arg1) {
        logger.info("[{}] - clicked", getElementName(arg0));
    }

    @Override
    public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
    }

    @Override
    public void beforeNavigateBack(WebDriver arg0) {
    }

    @Override
    public void beforeNavigateForward(WebDriver arg0) {
    }

    @Override
    public void beforeNavigateTo(String arg0, WebDriver arg1) {
    }

    @Override
    public void beforeScript(String arg0, WebDriver arg1) {

    }

    @Override
    public void onException(Throwable arg0, WebDriver arg1) {
        logger.debug(arg0.getClass().getName(), arg0);
    }

    private String getElementName(WebElement arg0) {
        String foundBy = arg0.toString();
        if (foundBy != null) {
            int arrowIndex = foundBy.indexOf("->");
            if (arrowIndex >= 0) {
                return "By." + foundBy.substring(arrowIndex + 3, foundBy.length() - 1);
            }
        }
        return "unknown";
    }
}