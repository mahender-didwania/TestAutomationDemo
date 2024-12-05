package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    public static Duration DEFAULT_WAIT_DURATION = Duration.ofSeconds(10);
    WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver) {
        if (null != driver) {
            this.driver = driver;
            wait = new WebDriverWait(driver, DEFAULT_WAIT_DURATION);
            PageFactory.initElements(driver, this);
        } else {
            logger.debug("Driver not inited, not setting up wait.");
        }
    }
}
