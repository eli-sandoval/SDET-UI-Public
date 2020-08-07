package main.java.tests;

import org.testng.annotations.Test;

import main.java.pages.HomePage;
import main.java.pages.ProductPage;

public class ResultsPageTests extends AbstractTest {
	private ProductPage productPage;

	@Test
	public void checkSecondProductQuantity() throws InterruptedException {
		productPage = new HomePage()
			.search("Iphone")
			.clickNextPage()
			.clickSecondProduct()
			.checkQuantity(1);
	}
}
