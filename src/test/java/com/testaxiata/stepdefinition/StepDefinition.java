package com.testaxiata.stepdefinition;

import static org.testng.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.testaxiata.Constans;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StepDefinition {
	WebDriver driver;

	@Test(priority = 0)
	public void go_to_gistgithubcom() {
		WebDriverManager.chromedriver().browserVersion("98.0.4758.82").setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://gist.github.com/");
	}

	@Test(priority = 1)
	public void login_to_gistgithubcom() {
		WebElement signIn = driver.findElement(By.cssSelector(
				"body > div.position-relative.js-header-wrapper > div > div.Header-item.f4.mr-0 > a.HeaderMenu-link.no-underline.mr-3"));
		signIn.click();
		WebElement loginField = driver.findElement(By.id("login_field"));
		loginField.sendKeys(Constans.USER);
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(Constans.PASSWORD);
		WebElement btnSignIn = driver.findElement(By.cssSelector(
				"#login > div.auth-form-body.mt-3 > form > div > input.btn.btn-primary.btn-block.js-sign-in-button"));
		btnSignIn.submit();
		WebElement pageHead = driver.findElement(By.xpath("//*[@id=\"gist-pjax-container\"]/div[1]/div/div/div/h1"));

		assertThat(pageHead.getText().toLowerCase(), containsString("discover gists"));
	}

	@Test(priority = 2)
	public void create_a_gist() {
		waiting(1000);
		WebElement octionPlus = driver
				.findElement(By.cssSelector("body > div.position-relative.js-header-wrapper > div > div:nth-child(6)"));
		octionPlus.click();
		WebElement contentName = driver
				.findElement(By.xpath("//*[@id=\"gists\"]/div[2]/file-attachment/div/div[1]/div[1]/input[2]"));
		contentName.sendKeys("test java");
		WebElement codeMirror = driver.findElement(By.xpath("//*[@id=\"code-editor\"]/div/pre"));
		codeMirror.sendKeys("test selenium");
		WebElement btnCreate = driver.findElement(By.xpath("//*[@id=\"new_gist\"]/div/div[2]/div/button"));
		btnCreate.click();
		WebElement txtTest = driver.findElement(By.xpath("//*[@id=\"file-test-java\"]/div[1]/div[2]/a/strong"));

		assertEquals(txtTest.getText(), "test java");
	}

	@Test(priority = 3)
	public void see_the_list_of_gist() {
		WebElement allGists = driver.findElement(By.cssSelector(
				"body > div.position-relative.js-header-wrapper > div > div.Header-item.Header-item--full.js-site-search.flex-column.flex-md-row.width-full.flex-order-2.flex-md-order-none.mr-0.mr-md-3.mt-3.mt-md-0.Details-content--hidden-not-important.d-md-flex > nav > a:nth-child(1)"));
		allGists.click();
		WebElement pageHead = driver.findElement(By.xpath("//*[@id=\"gist-pjax-container\"]/div[1]/div/div/div/h1"));

		assertThat(pageHead.getText().toLowerCase(), containsString("discover gists"));
	}
	@Test(priority = 4)
	public void edit_Gist() {
		WebElement testGists = driver.findElement(By.cssSelector(
				"#gist-pjax-container > div > div > div.col-9.col-md-9.col-12 > div:nth-child(2) > div.gist-snippet-meta.d-flex.flex-lg-row.flex-column.width-full > div > div.d-inline-block.px-lg-2.px-0 > span > a:nth-child(2)"));
		testGists.click();
		waiting(2000);
		WebElement edit = driver.findElement(By.xpath("//*[@id=\"gist-pjax-container\"]/div[1]/div/div[1]/ul/li[1]/a"));
		edit.click();
		WebElement codeMirror = driver.findElement(By.xpath("//*[@id=\"code-editor\"]/div/pre"));
		codeMirror.sendKeys(Keys.END);
		codeMirror.sendKeys(" update");
		waiting(2000);
		WebElement updateGist = driver
				.findElement(By.xpath("/html/body/div[6]/div/main/div[2]/div/form[1]/div/div[2]/button"));
		updateGist.click();
		WebElement txtTest = driver.findElement(By.xpath("//*[@id=\"file-test-LC1\"]"));

		assertThat(txtTest.getText().toLowerCase(), containsString("update"));

	}
	
	
	@Test(priority = 5)
	public void delete_Gist() {
		WebElement userLink = driver.findElement(By.id("user-links"));
		userLink.click();
		waiting(2000);
		WebElement yourGist = driver
				.findElement(By.cssSelector("#user-links > details > details-menu > a:nth-child(3)"));
		yourGist.click();
		WebElement testGists = driver.findElement(By.cssSelector(
				"#gist-pjax-container > div > div > div.col-9.col-md-9.col-12 > div:nth-child(2) > div.gist-snippet-meta.d-flex.flex-lg-row.flex-column.width-full > div > div.d-inline-block.px-lg-2.px-0 > span > a:nth-child(2)"));
		testGists.click();
		WebElement del = driver.findElement(By.cssSelector(
				"#gist-pjax-container > div.gisthead.pagehead.pb-0.pt-3.mb-4 > div > div.mb-3.d-flex.px-3.px-md-3.px-lg-5 > ul > li:nth-child(2) > form > button"));
		del.click();
		driver.switchTo().alert().accept();
		WebElement msgDel = driver.findElement(By.cssSelector("#js-flash-container > div > div"));

		assertThat(msgDel.getText().toLowerCase(), containsString("gist deleted successfully"));
	} 



	@Test(priority = 6)
	public void logout_from_gistgithubcom() {
		WebElement userLink = driver.findElement(By.id("user-links"));
		userLink.click();
		waiting(1000);
		WebElement logout = driver.findElement(By.xpath("//*[@id=\"user-links\"]/details/details-menu/form/button"));
		logout.click();
		WebElement signOut = driver.findElement(By.xpath("//*[@id=\"js-pjax-container\"]/div/form/input[2]"));
		signOut.click();
		WebElement h1 = driver.findElement(By.xpath("/html/body/div[4]/main/div[1]/div[1]/div[1]/div/div/div[1]/h1"));
		
		assertThat(h1.getText().toLowerCase(), containsString("software"));
		
	}

	private void waiting(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
