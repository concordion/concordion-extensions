package org.concordion.ext.demo.selenium.web;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * A WebDriver condition class that checks that an element is both present and visible.
 */
public class ElementPresent implements ExpectedCondition<WebElement> {
	private By locator;

	public ElementPresent(By locator) {
		this.locator = locator;
	}

	@Override
	public WebElement apply(WebDriver driver) {
	    WebElement element = driver.findElement(locator);
	    if (!((RenderedWebElement)element).isDisplayed()) {
	    	throw new NotFoundException("Element not displayed");
	    }
		return element;
	    
	}
}
