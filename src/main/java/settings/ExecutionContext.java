package main.java.settings;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExecutionContext {
	private static ExecutionContext instance;
	private WebDriver driver;

	private ExecutionContext() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("path.to.chromedriver"));
		this.driver = new ChromeDriver();
	}

	public static ExecutionContext getInstance() {
		if (Objects.isNull(instance)) {
			instance = new ExecutionContext();
		}
		return instance;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void quit() {
		driver.quit();
		instance = null;
	}
}
