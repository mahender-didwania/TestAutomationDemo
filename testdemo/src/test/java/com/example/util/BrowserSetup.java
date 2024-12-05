package com.example.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

public class BrowserSetup {

    public static final ChosenDriver DEFAULT_DRIVER = BrowserSetup.ChosenDriver.FIREFOX;
    private static final Logger logger = LoggerFactory.getLogger(BrowserSetup.class);
    private static ChromeDriverService service;
    private static ChosenDriver chosenDriver;
    JavascriptExecutor js;
    private WebDriver driver;
    private WebDriverWait wait;
    private boolean setUpDone = false;

    public static synchronized boolean setUpChromeDriverServer() {
        if (null == service) {
            try {
                service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File("src/test/resources/chromedriver"))
                        .usingAnyFreePort()
                        .build();
                logger.debug("Attempting to start Chrome driver service");
                service.start();
                return true;
            } catch (IOException e) {
                logger.error("Failed to set up chrome driver service: {}", e.getMessage());
            }
        }
        return service.isRunning();
    }

    public static void stopChromeServer() {
        service.stop();
    }

    public static void doneAll() {
        if (chosenDriver.equals(ChosenDriver.CHROME_SERVER)) {
            stopChromeServer();
        }
    }

    public static String getRandomCustomUA() {
        String[] UAs = new String[]{
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36"};
        return Arrays.asList(UAs).get(new Random().nextInt(0, UAs.length));
    }

    public void setUp(Duration timeout) {
        setUp(chosenDriver == null ? DEFAULT_DRIVER : chosenDriver, timeout);
    }

    public void setUp(ChosenDriver chosenDriver, Duration timeout) {
        switch (chosenDriver) {
            case FIREFOX:
                setUpFirefoxDriver(timeout);
                break;
            case CHROME:
                setUpChromeDriver(timeout);
                break;
            case CHROME_SERVER:
                if (setUpChromeDriverServer())
                    setUpChromeDriverUsingService(timeout);
                else {
                    logger.error("Chrome driver service could not be set up, using standalone");
                    setUpChromeDriver(timeout);
                }
                break;
        }

        postSetup();

    }

    private void setUpChromeDriverUsingService(Duration timeout) {
        if (null != service && service.isRunning()) {
            chosenDriver = ChosenDriver.CHROME_SERVER;
            ChromeOptions options = configureChromeOptions(timeout);
            driver = new RemoteWebDriver(service.getUrl(), options);
            logger.debug("Chrome driver service at {}, options: {}", service.getUrl(), options);
        } else {
            logger.debug("Chrome driver service not found to be running! Did not set up remote driver.");
        }
    }

    private void setUpChromeDriver(Duration timeout) {
        chosenDriver = ChosenDriver.CHROME;
        ChromeOptions options = configureChromeOptions(timeout);
        driver = new ChromeDriver(options);
        logger.debug("Chrome driver with options {}", options);
    }

    private void setUpFirefoxDriver(Duration timeout) {
        chosenDriver = ChosenDriver.FIREFOX;
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        String userAgent = getRandomCustomUA();
        profile.setPreference("general.useragent.override", userAgent);
        options.setBinary("src/test/resources/firefox/firefox");
        options.addArguments("-headless");
        options.setPageLoadTimeout(Duration.ofSeconds(timeout.getSeconds()));
        options.setScriptTimeout(Duration.ofSeconds(timeout.getSeconds()));
        options.setProfile(profile);
        driver = new FirefoxDriver(options);
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_PERIOD_SECONDS));
        // // we are using explicit waits everywhere
    }

    public void postSetup() {
        js = (JavascriptExecutor) driver;
        setUpDone = true;
    }

    public boolean isSetUpDone() {
        return setUpDone;
    }

    private ChromeOptions configureChromeOptions(Duration timeout) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=" + getRandomCustomUA());
        options.setBinary("src/test/resources/chrome-linux64/chrome");
        options.addArguments("-headless");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.setPageLoadTimeout(Duration.ofSeconds(timeout.getSeconds()));
        options.setScriptTimeout(Duration.ofSeconds(timeout.getSeconds()));
        options.addArguments("--no-sandbox");
        return options;
    }

    public void done() {
        driver.quit();
    }

    public void setUpExplicitWait(Duration duration) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(duration.getSeconds()));
    }

    public WebDriverWait getConfiguredWait() {
        return wait;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public JavascriptExecutor getDriverAsJavascriptExecutor() {
        return js;
    }

    public ChosenDriver getChosenDriver() {
        return chosenDriver;
    }

    public enum ChosenDriver {
        FIREFOX,
        CHROME,
        CHROME_SERVER
    }
}
