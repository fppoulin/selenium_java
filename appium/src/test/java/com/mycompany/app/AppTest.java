package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.concurrent.TimeUnit;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.*;
import static org.openqa.selenium.By.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.selenium.fluent.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

public class AppTest {

	private AppiumDriver driver;

	private WebDriverWait wait;
	static Actions actions;
	private int flexibleWait = 5;
	private int implicitWait = 1;
	private long pollingInterval = 500;
	private String baseUrl = "http://antenna.io/demo/jquery-bar-rating/examples/";

	@BeforeSuite
	public void beforeSuiteMethod() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
				"Nexus_4_API_22");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Browser");
		driver = new AndroidDriver<WebElement>(
				new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver, flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		actions = new Actions(driver);
	}

	@AfterSuite
	public void afterSuiteMethod() throws Exception {
		driver.quit();
	}

	@BeforeMethod
	public void loadPage() {
		driver.get(baseUrl);
	}

	@AfterMethod
	public void resetBrowser() {
		// load blank page
		driver.get("about:blank");
	}

	@Test(enabled = true)
	public void Test2() {
		// Arrange
		WebElement bar = wait.until((WebDriver d) -> {
			WebElement element = null;
			try {
				element = d.findElement(By.cssSelector(
						"section.section-examples div.examples div.box-example-square div.box-body div.br-theme-bars-square"));
			} catch (Exception e) {
				return null;
			}
			return (element.isDisplayed()) ? element : null;
		});
		assertThat(bar, notNullValue());
		actions.moveToElement(bar).build().perform();
		/*
		try {
			Rectangle rect = bar.getRect();
			System.err.println(String.format("Rectangle: %d , %d", rect.x, rect.y));
		} catch (Exception e) {
			// error: Could not proxy command to remote server.
			// Original error: 404 - unknown command:
			// session/.../element/.../rect
			// it also hangs the Appium
			System.err.println("Exception: " + e.toString());
		}
		*/
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		assertTrue(bar.isDisplayed());
		// NOTE:
		// http://stackoverflow.com/questions/22703159/selenium-movebyoffset-doesnt-do-anything
		Action moveM = actions.moveByOffset(0, -100).build();
		moveM.perform();
		for (int cnt = 0; cnt != 15; cnt++) {
			driver.findElementByTagName("body").sendKeys(Keys.DOWN);
			try {
				Point point = bar.getLocation();
				// ((JavascriptExecutor)driver).executeScript("return
				// arguments[0].getBoundingClientRect()[\"top\"]")
				System.err.println(
						String.format("Location: %d , %d / %d", point.x, point.y, cnt));
			} catch (Exception e) {
				// error: Could not proxy command to remote server.
				// Original error: 404 - unknown command:
				// session/f8abfc31025e07403f4c5695cb87d463/element/0.41199909150600433-1/rect
				System.err.println("Exception: " + e.toString());
			}
			try {
				if (driver instanceof JavascriptExecutor) {
					Long result = (Long) ((JavascriptExecutor) driver).executeScript(
							"return arguments[0].getBoundingClientRect()[\"top\"]", bar);
					System.err.println(String.format("Top: %d", result));
				}
			} catch (Exception e) {
				System.err.println("Exception: " + e.toString());
			}

		}

		List<WebElement> ratingElements = bar
				.findElements(By.xpath(".//a[@data-rating-value]"));
		assertTrue(ratingElements.size() > 0);

		Map<String, WebElement> ratings = ratingElements.stream().collect(Collectors
				.toMap(o -> o.getAttribute("data-rating-text"), Function.identity()));
		//
		ratings.keySet().stream().forEach(o -> {
			System.err.println("Mouse over rating: " + o);
			WebElement r = ratings.get(o);
			// hover
			actions.moveToElement(r).click().build().perform();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		});
		// Assert
	}

}