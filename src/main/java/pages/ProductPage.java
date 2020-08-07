package main.java.pages;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

@NoArgsConstructor
@Slf4j
public class ProductPage extends BasePage {
	private static final String URL = "item";

	@FindBy(how = How.CLASS_NAME, using = "next-dialog-close")
	private WebElement closePopupButton;

	@FindBy(how = How.XPATH, using = "//input[@aria-valuemin='1']")
	private WebElement quantityElement;

	public ProductPage checkQuantity(int quantity) {
		changeCurrentWindowHandle();
		waitForPageToFinishLoading();
		validateUrl(URL);

		closePopupIfNecessary(closePopupButton);
		waitUntil(ExpectedConditions.visibilityOf(quantityElement));
		assertThat(Integer.parseInt(quantityElement.getAttribute("value"))).isGreaterThanOrEqualTo(quantity);
		log.info("Product minimum quantity is {}", quantityElement.getAttribute("value"));
		return this;
	}

	@Override
	protected String getUrl() {
		return URL;
	}
}
