package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UpdateItemPage extends BasePage {

    private By itemNameInput = By.id("name");
    private By itemDescriptionInput = By.id("description");
    private By updateButton = By.xpath("//button[text()='Update Item']");
    private By messageDiv = By.id("message");

    public UpdateItemPage(WebDriver driver) {
        super(driver);
    }

    // Method to update item
    public void updateItem(String name, String description) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(itemNameInput));
        WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(itemDescriptionInput));

        nameInput.clear();
        nameInput.sendKeys(name);
        descriptionInput.clear();
        descriptionInput.sendKeys(description);

        WebElement updateButtonElement = driver.findElement(updateButton);
        updateButtonElement.click();
    }

    // Method to get the success message
    public String getMessage() {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(messageDiv));
        return messageElement.getText();
    }
}
