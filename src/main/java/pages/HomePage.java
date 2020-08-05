package main.java.pages;

import org.apache.commons.lang3.StringUtils;

public class HomePage extends BasePage {
	private static final String URL = StringUtils.EMPTY;

	public HomePage() {
		super();
		open(this);
	}

	@Override
	protected String getUrl() {
		return URL;
	}
}
