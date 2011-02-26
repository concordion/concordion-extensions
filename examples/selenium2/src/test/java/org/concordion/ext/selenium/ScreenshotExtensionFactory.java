package org.concordion.ext.selenium;

import org.concordion.ext.ScreenshotExtension;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.openqa.selenium.WebDriver;

public class ScreenshotExtensionFactory implements ConcordionExtensionFactory {

    private static WebDriver driver;

    public static void setDriver(WebDriver driver) {
        ScreenshotExtensionFactory.driver = driver;
    }

    @Override
    public ConcordionExtension createExtension() {
        SeleniumScreenshotTaker screenshotTaker = new SeleniumScreenshotTaker(driver);
        ScreenshotExtension extension = new ScreenshotExtension();
        extension.setScreenshotTaker(screenshotTaker);
//        extension.setMaxWidth(400);
        return extension;
    }
}