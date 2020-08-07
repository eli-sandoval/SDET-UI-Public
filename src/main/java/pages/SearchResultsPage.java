package main.java.pages;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

@NoArgsConstructor
@Slf4j
public class SearchResultsPage extends BasePage {
	private static final String URL = "wholesale";
	public static final String SCROLL_TO_ELEMENT_SCRIPT = "window.scrollTo(0, 13500)";
	private JavascriptExecutor js = (JavascriptExecutor) getDriver();

	//Using relative Xpath in this case, since there is no better selector to be used
	@FindBy(how = How.XPATH, using = "//button[@aria-label='Page 2, 7 pages']")
	private WebElement nextPageButton;

	//Since there are lots of results for the search, a relative xpath that specifies the product index was necessary in order to click on the second element
	@FindBy(how = How.XPATH, using = "//div[@class='list product-card' and @product-index='1']/div[@class='product-img']")
	private WebElement secondProduct;

	@FindBy(how = How.CLASS_NAME, using = "next-dialog-close")
	private WebElement closePopupButton;

	public SearchResultsPage clickNextPage() {
		closePopupIfNecessary(closePopupButton);
		waitForPageToFinishLoading();
		validateUrl(URL);

		//As aliexpress uses dynamic elements for their search results, a javascript script is needed to scroll into the element so as to be able to click on it. Without scrolling, the element is never actually created.
		js.executeScript(SCROLL_TO_ELEMENT_SCRIPT);

		log.info("Clicking second page button");
		nextPageButton.click();

		return this;
	}

	public ProductPage clickSecondProduct() {
		waitForPageToFinishLoading();

		//Because of how Aliexpress is built, the javascript DOM is refreshed, and by the time the driver attempts to click on the second product, the element is stale, and the driver throws a StaleElementReferenceException. To avoid this, a simple try-catch flow needs to be used that makes the webdriver find the element once again.
		waitUntil(ExpectedConditions.elementToBeClickable(secondProduct));
		log.info("Clicking on second product");
		try {
			secondProduct = getDriver().findElement(By.xpath("//div[@class='list product-card' and @product-index='1']/div[@class='product-img']"));
			secondProduct.click();
		} catch (StaleElementReferenceException e) {
			secondProduct = getDriver().findElement(By.xpath("//div[@class='list product-card' and @product-index='1']/div[@class='product-img']"));
			secondProduct.click();
		}
		return new ProductPage();
	}

	@Override
	protected String getUrl() {
		return URL;
	}
}
