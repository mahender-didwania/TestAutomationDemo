package com.example.stepdefinitions;

import com.example.pages.AddItemPage;
import com.example.pages.ItemListPage;
import com.example.pages.UpdateItemPage;
import com.example.util.BrowserSetup;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(ItemStepDefinitions.class);
    private final BrowserSetup browserSetup = new BrowserSetup();
    private final int WAIT_PERIOD_SECONDS = 5;
    private final String AUT_URI = "http://localhost:9090/index.html";
    private WebDriver driver;
    private ItemListPage itemListPage;
    private AddItemPage addItemPage;
    private UpdateItemPage updateItemPage;

    @io.cucumber.java.BeforeAll
    public static void before_all() {
        BrowserSetup.setUpChromeDriverServer();
    }

    @io.cucumber.java.AfterAll
    public static void after_all() {
        tearDownAll();
    }

    public static void tearDownAll() {
        BrowserSetup.doneAll();
    }

    @io.cucumber.java.Before
    public void setUp() {
        setUp(BrowserSetup.ChosenDriver.CHROME_SERVER);
        logger.debug("Setup called");
    }

    public void setUp(BrowserSetup.ChosenDriver chosenDriver) {
        browserSetup.setUp(chosenDriver, Duration.ofSeconds(WAIT_PERIOD_SECONDS * 2));
        browserSetup.setUpExplicitWait(Duration.ofSeconds(WAIT_PERIOD_SECONDS));
        driver = browserSetup.getDriver();
        itemListPage = new ItemListPage(driver);
        addItemPage = new AddItemPage(driver);
        updateItemPage = new UpdateItemPage(driver);
    }

    @io.cucumber.java.After
    public void tearDown() {
        browserSetup.done();
    }

    @Given("I navigate to the item list page")
    public void navigateToTheItemListPage() {
        driver.get(this.AUT_URI);
    }

    @When("I click on the add item button")
    public void clickOnTheAddItemButton() {
        addItemPage.clickAddItemButton();
    }

    @When("I add an item with name {string} and description {string}")
    public void addAnItemWithNameAndDescription(String name, String description) {
        addItemPage.addItem(name, description);
    }

    @Then("I should see the item {string} in the item list")
    public void checkItemInTheItemList(String itemName) {
        assertTrue(itemListPage.isItemInList(itemName));
    }

    @Then("I should see the success message {string}")
    public void checkSuccessMessage(String message) {
        assertEquals(message, itemListPage.getMessage());
    }

    @When("I click the Update button for item with name {string}")
    public void updateItemWithName(String name) {
        itemListPage.clickUpdateForFirstMatchingItemName(name);
    }

    @When("I update the item to name {string} and description {string}")
    public void updateTheItem(String name, String description) {
        updateItemPage.updateItem(name, description);
    }

    @Then("I should see an item with the new item name {string}")
    public void checkItemUpdatedSuccessfully(String itemName) {
        assertTrue(itemListPage.isItemInList(itemName));
    }

    @When("I add the following items:")
    public void addItemsUsingDataTable(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> item : items) {
            logger.debug("Steps Java: {}", item.toString());
            addItemPage.addItem(item.get("Name"), item.get("Description"));
        }
    }

    @Then("I should see all the items in the list:")
    public void checkAllTheItemsInTheList(DataTable dataTable) {
        List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);
        String listContent = itemListPage.getRawListContent();

        for (Map<String, String> expectedItem : expectedItems) {
            assertTrue(listContent.contains(expectedItem.get("Name")));
            assertTrue(listContent.contains(expectedItem.get("Description")));
        }
    }
}
