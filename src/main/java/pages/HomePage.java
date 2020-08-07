package main.java.pages;

import static com.google.common.base.Preconditions.checkNotNull;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class HomePage extends BasePage {
	private static final String URL = StringUtils.EMPTY;

	@FindBy(how = How.ID, using = "search-key")
	private WebElement searchTextbox;

	@FindBy(how = How.CLASS_NAME, using = "search-button")
	private WebElement searchButton;

	@FindBy(how = How.CLASS_NAME, using = "close-layer")
	private WebElement closePopupButton;

	public HomePage() {
		super();
		log.info("Opening Home page");
		open(this, URL);
		closePopupIfNecessary(closePopupButton);
	}

	public SearchResultsPage search(String searchTerm) {
		checkNotNull(searchTerm, "The loan amount cannot be null");
		log.info("Entering searchTerm={}", searchTerm);
		//Validating element is visible before performing operations on it.
		waitUntil(ExpectedConditions.visibilityOf(searchTextbox));
		searchTextbox.click();
		searchTextbox.sendKeys(searchTerm);
		searchButton.click();
		return new SearchResultsPage();
	}

	@Override
	protected String getUrl() {
		return URL;
	}
}
