package org.concordion.ext.demo.selenium;

import org.concordion.ext.demo.selenium.web.GoogleSearchPage;
import org.concordion.ext.demo.selenium.web.Site;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.After;
import org.junit.runner.RunWith;

/** 
 * A base class for Google search tests that opens up the Google site
 * at the Google search page, and closes the browser once the test is complete.
 */
@RunWith(ConcordionRunner.class)
public abstract class GoogleFixture {

	protected Site site;
	protected GoogleSearchPage searchPage;

    GoogleFixture() {
        this(false);
    }
    
	GoogleFixture(boolean logWebDriverEvents) {
		site = new Site();
        if (logWebDriverEvents) {
            site.addLogger();
        }
		searchPage = new GoogleSearchPage(site.getDriver());	
	}
	
	@After
	public void close() {
		site.close();
	}
}
