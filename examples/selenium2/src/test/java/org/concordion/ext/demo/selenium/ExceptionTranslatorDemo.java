package org.concordion.ext.demo.selenium;

import org.concordion.api.ExpectedToFail;
import org.concordion.api.extension.Extensions;
import org.concordion.ext.TimestampFormatterExtension;
import org.concordion.ext.selenium.ExceptionTranslatorExtensionFactory;
import org.concordion.ext.selenium.SeleniumScreenshotTaker;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import spec.concordion.ext.screenshot.ScreenshotExtensionFactory;

/**
 * A fixture class for the ExceptionDemo.html specification.
 * <p>
 * This adds the Screenshot Extension to Concordion to include screenshots on error in the Concordion output.
 * By default this extension uses java.awt.Robot to take the screenshot. 
 * <p>
 * To include just the browser screen in the results, we configure the extension using the {@link ScreenshotExtensionFactory}
 * and {@link SeleniumScreenshotTaker} to take screenshots using WebDriver's TakesScreenshot interface.
 * <p>
 * This example also demonstrates the {@link TimestampFormatterExtension}, which changes the Concordion footer to show times
 * in hours, minutes and seconds. 
 * <p>
 * Run this class as a JUnit test to produce the Concordion results.  The test is expected to fail, since Google displays
 * "Netherlands" in the results.
 */
@RunWith(ConcordionRunner.class)
@ExpectedToFail
@Extensions(ExceptionTranslatorExtensionFactory.class)
public class ExceptionTranslatorDemo extends GoogleFixture {
	
	/**
	 * Searches for the specified topic, and waits for the results page to load.
	 */
    public void clickOnNonExistentLink() {
		searchPage.clickOnNonExistentLink();
	}
}
