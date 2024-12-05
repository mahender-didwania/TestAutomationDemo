package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ItemListPage extends BasePage {

    private final By messageDiv = By.id("message");
    private final By itemsList = By.id("items-list");
    @FindBy(id = "message")
    private WebElement anotherWayForMessageDiv;

    // <button
    // onclick="editItem('0e155e2c-33c9-4bb7-92ce-b5ff0a9432fd')">Update</button>
    public ItemListPage(WebDriver driver) {
        super(driver);
    }

    public String getMessage() {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(messageDiv));
        return messageElement.getText();
    }

    public boolean isItemInList(String itemName) {
        WebElement itemRow = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='" + itemName + "']")));
        return itemRow.isDisplayed();
    }

    public void clickUpdate(String UUID) {
        // //button[text()='Update'][(contains(@onclick,'0e155e2c-33c9-4bb7-92ce-b5ff0a9432f'))]
        String xpath = "//button[text()='Update'][(contains(@onclick,'" + UUID + "'))]";
        WebElement updateButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        updateButtonElement.click();
        // //td[text()='Item
        // 4']/following-sibling::td[2]/button[text()='Update'][(contains(@onclick,'0e155e2c-33c9-4bb7-92ce-b5ff0a9432f'))]
    }

    public void clickUpdateForFirstMatchingItemName(String itemName) {
        String xpath = "//td[text()='" + itemName + "']/following-sibling::td[2]/button[text()='Update']";
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
        List<WebElement> updateButtonElement = driver.findElements(By.xpath(xpath));
        updateButtonElement.get(0).click();
    }

    public String getRawListContent() {
        WebElement itemsListElement = wait.until(ExpectedConditions.visibilityOfElementLocated(itemsList));
        return itemsListElement.getText();
    }
}
