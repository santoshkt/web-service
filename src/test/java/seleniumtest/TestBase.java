package seleniumtest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class TestBase {

	// Initalizing the property file reading
	public static Properties CONFIG = null;
	public static Properties OR = null;
	public static WebDriver dr = null;
	public static EventFiringWebDriver driver = null;

	public static void initialize() throws IOException {
		if (driver == null) {

			// Config property file
			CONFIG = new Properties();
			FileInputStream fn = new FileInputStream(
					System.getProperty("user.dir")
							+ "/src/test/java/config/config.properties");
			CONFIG.load(fn);

			// OR Properties
			OR = new Properties();
			fn = new FileInputStream(System.getProperty("user.dir")
					+ "/src/test/java/config/OR.properties");
			OR.load(fn);

			// Initalize the webdriver and EventFiringWebDriver
			if (CONFIG.getProperty("browser").equals("Firefox")) {
				dr = new FirefoxDriver();
			} else if (CONFIG.getProperty("browser").equals("IE")) {
				dr = new InternetExplorerDriver();
			} else if (CONFIG.getProperty("browser").equals("Chrome")) {
				dr = new ChromeDriver();
			}

			driver = new EventFiringWebDriver(dr);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			driver.get(CONFIG.getProperty("baseURL"));
		}
	}

	public static WebElement getObject(String xpathKey) {
		try {
			return driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		} catch (Throwable t) {
			// report error
			System.out.println("Exception occured while finding an element");
			return null;
		}

	}

	public static void navigateToRegistrationPage() {
		driver.get(CONFIG.getProperty("baseURL"));
	}
}