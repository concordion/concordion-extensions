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
 * A WebDriver Page Object corresponding to the Google Results Page.
 */
class GoogleResultsPage {
	
    @CacheLookup
	@FindBy(how = How.ID, using = "res") 
	private WebElement resultWrapper

    @CacheLookup
	@FindBy(how = How.CLASS_NAME, using = "l")
	private WebElement firstResultLink
	
    @CacheLookup
    @FindBy(how = How.CLASS_NAME, using = "r")
    private WebElement calcResultLink

    private Site site

	/**
	 * Initialises the results page and waits for the page to fully load.
	 * Assumes that the results page is already loading.
	 */
	GoogleResultsPage(Site site) {
		this.site = site
		site.initPage(this)
        waitForFooter()
	}

	/**
	 * Checks whether the specified text occurs in any result on the results page.
	 */
	boolean resultsContain(String text) {
		List<WebElement> resultsText = resultWrapper.findElements(By.className('s'))
		return resultsText.any { it.getText()?.contains(text) }
	}

	/**
	 * Returns the text of the topmost result from the results page.
	 */
	String getTopResultTitle() {
		return firstResultLink.getText()
	}
	
    /**
     * Returns the text of the topmost result from the results page.
     */
    String getCalculatorResult() {
        return calcResultLink.getText()
    }
    
	private void waitForFooter() {
		WebDriverWait wait = new  WebDriverWait(site.driver, 30)
		ExpectedCondition condition = new ElementPresent(By.id("foot"))
		wait.until(condition)  
	}
}