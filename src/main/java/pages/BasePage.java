package main.java.pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.java.settings.ExecutionContext;

@Slf4j
public abstract class BasePage {
	private static final String BASE_URL = "https://www.aliexpress.com/";

	private final ExecutionContext executionContext = ExecutionContext.getInstance();
	private WebDriver driver = executionContext.getDriver();

	public BasePage() {
		PageFactory.initElements(executionContext.getDriver(), this);
	}

	<T extends BasePage> T open(T page, String url) {
		driver.get(BASE_URL + url);

		driver.manage().window().maximize();
		waitForPageToFinishLoading();
		validateUrl(url);
		return page;
	}

	BasePage waitUntil(ExpectedCondition expectedCondition) {
		waitUntil(expectedCondition, Duration.ofSeconds(Integer.parseInt(System.getProperty("timeout"))));
		return this;
	}

	public BasePage waitUntil(ExpectedCondition expectedCondition, Duration timeout) {
		WebDriverWait webDriverWait = new WebDriverWait(executionContext.getDriver(), timeout);
		webDriverWait.until(expectedCondition);
		return this;
	}

	/*
	In order to give the elements time to load, a method is executed that waits for the javascript document to have a readyState=complete
	 */
	public void waitForPageToFinishLoading() {
		waitUntil((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}

	public void validateUrl(String url) {
		waitUntil(ExpectedConditions.urlContains(url));
		assertThat(executionContext.getDriver().getCurrentUrl()).contains(url);
		log.info("Assertion passed. The URL contains '{}'", url);
	}

	/*
	Aliexpress is offering coupons to new customers, which prompts popups that appear randomly throughout the website. Thus, I implemented a method that locates the element if visible and closes it.
	 */
	public void closePopupIfNecessary(WebElement element) {
		try {
			waitUntil(ExpectedConditions.visibilityOf(element), Duration.ofSeconds(5));
			element.click();
		} catch (TimeoutException e) {
		}
	}

	public void changeCurrentWindowHandle() {
		for (String winHandle : getDriver().getWindowHandles()) {
			getDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
		}
	}

	public void close() {
		executionContext.getDriver().close();
	}

	protected WebDriver getDriver() {
		return executionContext.getDriver();
	}

	protected abstract String getUrl();
}
