package org.concordion.ext.demo.selenium;

import org.concordion.api.extension.Extensions;
import org.concordion.ext.LoggingTooltipExtension;
import org.concordion.ext.demo.selenium.web.GoogleResultsPage;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

/**
 * A fixture class for the LoggingDemo.html specification.
 * <p>
 * This adds the Logging Tooltip Extension to Concordion to show logging detail in the Concordion output.
 * The extension picks up any logging output written to java.util.logging.
 * <p>
 * For the purposes of demonstration, we are logging details of WebDriver (Selenium 2) events using the SeleniumEventLogger.
 * (This logging uses slf4j, which writes to java.util.logging when run with the slf4j-jdk14 jar).
 * <p>
 * Run this class as a JUnit test to produce the Concordion results.
 */
@RunWith(ConcordionRunner.class)
@Extensions(LoggingTooltipExtension.class)
public class LoggingDemo extends GoogleFixture {
	
 	GoogleResultsPage resultsPage;

 	public LoggingDemo() {
        super(true);
    }
    
	/**
	 * Searches for the specified topic, and waits for the results page to load.
	 */
	public void searchFor(String topic) {
		resultsPage = searchPage.searchFor(topic);
	}
	
	/**
	 * Returns the result from Google calculation.
	 */
	public String getCalculatorResult() {
		return resultsPage.getCalculatorResult();
	}
}
