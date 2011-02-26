package org.concordion.ext.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumEventLogger implements WebDriverEventListener {

    final Logger logger = LoggerFactory.getLogger("selenium.events");
    private String oldValue;

    @Override
    public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
        oldValue = arg0.getValue();
    }

    @Override
    public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
        String elementName = getElementName(arg0);
        String newValue = "";
        try {
            newValue = arg0.getValue();
            if (!newValue.equals(oldValue)) {
                if (newValue.isEmpty()) {
                    logger.info("Cleared value of {}", elementName);
                } else {
                    logger.info("Changed value of {} to {}", elementName, newValue);
                }
            }
        } catch (Exception e) {
            logger.info("Changed value of {}", elementName);
        }
    }

    @Override
    public void afterClickOn(WebElement arg0, WebDriver arg1) {
    }

    @Override
    public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
        logger.info("Found element {}", arg0);
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
        logger.info("Clicked on {}", getElementName(arg0));
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
        logger.error(arg0.getClass().getName(), arg0);
    }

    private String getElementName(WebElement arg0) {
        try {
            String id = arg0.getAttribute("id");
            if (id != null && !id.isEmpty())
                return id;
            String name = arg0.getAttribute("name");
            if (name != null && !name.isEmpty())
                return name;
        } catch (Exception ignore) {
        }
        return "unknown";
    }
}