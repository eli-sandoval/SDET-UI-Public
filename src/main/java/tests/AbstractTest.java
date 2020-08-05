package main.java.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import main.java.settings.ExecutionContext;

public class AbstractTest {
	private static ExecutionContext context;

	@BeforeMethod
	public void setup() {
		context = ExecutionContext.getInstance();
	}

	@AfterMethod
	public void cleanUp() {
		context.quit();
	}
}
