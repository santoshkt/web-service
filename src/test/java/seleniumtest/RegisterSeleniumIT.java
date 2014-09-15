package seleniumtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegisterSeleniumIT extends TestBase {

	@BeforeClass
	public static void beforeTest() {
		try {
			initialize();
		} catch (IOException e) {
			System.out.println("Error while initializing the test");
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterTest() {
		driver.close();
	}

	@Test
	public void testEmptyFields() {
		navigateToRegistrationPage();

		getObject("register.submit").click();
		assertTrue(getObject("register.form").isDisplayed());
	}

	@Test
	public void testPasswordLessThanSixChar() {
		navigateToRegistrationPage();

		// Fill the registration form
		getObject("register.firstName").sendKeys("fname");
		getObject("register.lastName").sendKeys("lname");
		getObject("register.emailAddress").sendKeys(
				RandomStringUtils.randomAlphabetic(20) + "@example.com");
		getObject("register.password").sendKeys("test");
		getObject("register.confirmPassword").sendKeys("test");

		getObject("register.submit").click();

		// Validate that the error message is displayed
		assertEquals(getObject("register.errorMessage").getText(),
				"The minimum password length is 6");
		assertTrue(getObject("register.form").isDisplayed());
	}

	@Test
	public void testInvalidEmailAddress() {
		navigateToRegistrationPage();

		// Fill the registration form
		getObject("register.firstName").sendKeys("fname");
		getObject("register.lastName").sendKeys("lname");
		getObject("register.emailAddress").sendKeys("lnamefnamegmail.com");
		getObject("register.password").sendKeys("test123");
		getObject("register.confirmPassword").sendKeys("test123");

		getObject("register.submit").click();

		// Validate that the error message is displayed
		assertEquals(getObject("register.errorMessage").getText(),
				"\"Email\" is not a valid email address.");
		assertTrue(getObject("register.form").isDisplayed());
	}

	@Test
	public void testDifferentPasswords() {
		navigateToRegistrationPage();

		// Fill the registration form
		getObject("register.firstName").sendKeys("fname");
		getObject("register.lastName").sendKeys("lname");
		getObject("register.emailAddress").sendKeys(
				RandomStringUtils.randomAlphabetic(20) + "@example.com");
		getObject("register.password").sendKeys("test123");
		getObject("register.confirmPassword").sendKeys("test1234");

		getObject("register.submit").click();

		// Validate that the error message is displayed
		assertEquals(getObject("register.errorMessage").getText(),
				"Please make sure your passwords match.");
		assertTrue(getObject("register.form").isDisplayed());
	}

	@Test
	public void testRegistrationSuccess() {
		navigateToRegistrationPage();

		// Fill the registration form
		getObject("register.firstName").sendKeys("fname");
		getObject("register.lastName").sendKeys("lname");
		getObject("register.emailAddress").sendKeys(
				RandomStringUtils.randomAlphabetic(20) + "@example.com");
		getObject("register.password").sendKeys("test123");
		getObject("register.confirmPassword").sendKeys("test123");

		getObject("register.submit").click();

		// Validate that the error message is displayed
		assertEquals(
				getObject("register.successMessage").getText(),
				"Thank you for submitting form. Your registration is successful. Verify your account here");
	}
}
