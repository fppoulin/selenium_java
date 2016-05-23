package com.cucumber.CucumberPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends AbstractPageStepDefinition {

  public ContactPage navigateToContactPage(String link) {
    driver.findElement(By.id(link + "_link")).click();
    return new ContactPage();
  }

	public LandingPage navigateToWebApp() {
		driver.navigate().to("http://www.thetestroom.com/webapp");
		return this;
	}
}
