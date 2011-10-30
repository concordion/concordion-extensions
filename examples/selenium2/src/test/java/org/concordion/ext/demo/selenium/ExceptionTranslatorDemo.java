package org.concordion.ext.demo.selenium;

import org.concordion.api.ExpectedToFail;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.Extension;
import org.concordion.ext.TranslatorExtension;
import org.concordion.ext.selenium.SeleniumExceptionMessageTranslator;
import org.concordion.ext.translator.MessageTranslator;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

/**
 * A fixture class for the ExceptionTranslatorDemo.html specification.
 * <p>
 * This adds the TranslatorExtension to Concordion to modify the exception message text. It is configured with a 
 * {@link MessageTranslator} which strips the debug information from the end of the Selenium WebDriver exception messages.  
 * <p>
 * Run this class as a JUnit test to produce the Concordion results.  The test is expected to fail, since it clicks on a 
 * non-existent WebElement.
 */
@RunWith(ConcordionRunner.class)
@ExpectedToFail
public class ExceptionTranslatorDemo extends GoogleFixture {
	@Extension
	public ConcordionExtension translator = new TranslatorExtension(new SeleniumExceptionMessageTranslator());

	/**
	 * Searches for the specified topic, and waits for the results page to load.
	 */
    public void clickOnNonExistentLink() {
		searchPage.clickOnNonExistentLink();
	}
}
