package main.java.pages;

import org.openqa.selenium.support.PageFactory;

import main.java.settings.ExecutionContext;

public abstract class BasePage {
	private final ExecutionContext executionContext = ExecutionContext.getInstance();

	private static final String BASE_URL = "https://www.aliexpress.com/";

	public BasePage() {
		PageFactory.initElements(executionContext.getDriver(), this);
	}

	protected BasePage open(BasePage page) {
		executionContext.getDriver().get(BASE_URL + page.getUrl());
		return this;
	}

	public void close() {
		executionContext.getDriver().close();
	}

	protected abstract String getUrl();
}
