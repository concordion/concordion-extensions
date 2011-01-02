package org.concordion.ext.demo.selenium.web

import org.openqa.selenium.By 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy 
import org.openqa.selenium.support.How 
import org.openqa.selenium.support.PageFactory 
import org.openqa.selenium.support.ui.ExpectedCondition 
import org.openqa.selenium.support.ui.WebDriverWait 


/**
 * A WebDriver Page Object corresponding to the Google Search Page.
 */
class GoogleSearchPage {
	
    @CacheLookup
	@FindBy(name = "q") 
	private WebElement queryBox
	
    @CacheLookup
	@FindBy(name = "btnG") 
	private WebElement submitButton
	
	private Site site
    
	/**
	 * Opens the Google Search Page.
	 */
	GoogleSearchPage(Site site) {
		this.site = site
		site.initPage(this)
		site.driver.get('http://www.google.co.nz')
	}

    /**
     * Searches for the specified string and opens the results page, 
     * waiting for the page to fully load. 
     */
	GoogleResultsPage searchFor(String query) {
		queryBox.sendKeys(query)
		submitButton.click()
		return new GoogleResultsPage(site)
	}
}