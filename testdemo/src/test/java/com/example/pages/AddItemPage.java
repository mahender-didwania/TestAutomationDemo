package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddItemPage extends BasePage {

    private final By itemName = By.id("name");
    private final By itemDescription = By.id("description");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public AddItemPage(WebDriver driver) {
        super(driver);
    }

    public void addItem(String name, String description) {
        WebElement nameField = wait
                .until(ExpectedConditions.visibilityOfElementLocated(itemName));
        nameField.clear();
        nameField.sendKeys(name);

        WebElement descriptionField = wait
                .until(ExpectedConditions.visibilityOfElementLocated(itemDescription));
        descriptionField.clear();
        descriptionField.sendKeys(description);

        submitForm();
    }

    public void submitForm() {
        clickAddItemButton();
    }

    public void clickAddItemButton() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        addButton.click();
    }

}
