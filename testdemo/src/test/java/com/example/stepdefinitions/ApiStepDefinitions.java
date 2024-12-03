package com.example.stepdefinitions;

import com.example.api.ApiHelper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class ApiStepDefinitions {

    private ApiHelper apiHelper = new ApiHelper();
    private Response response;
    private String lastCreatedItemId; // Stores the ID of the last created item

    @Given("I create an item with name {string} and description {string}")
    public void createItem(String name, String description) {
        response = apiHelper.createItem(name, description);
        checkStatusCode(201);
        String responseBody = response.getBody().asString();
        assertEquals("Item added successfully", responseBody.trim());
        // Fetch all items and find the ID of the newly created item
        Response getResponse = apiHelper.getItems();
        List<Map<String, String>> items = getResponse.jsonPath().getList("$");
        for (Map<String, String> item : items) {
            if (item.get("name").equals(name) && item.get("description").equals(description)) {
                lastCreatedItemId = item.get("id");
                break;
            }
        }

        assertNotNull("ID should not be null for the created item", lastCreatedItemId);
    }

    @Then("I should receive a {int} status code")
    public void checkStatusCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Given("I get the list of items")
    public void getListOfItems() {
        response = apiHelper.getItems();
    }

    @Then("I should see an item with name {string} and description {string}")
    public void checkItemHasNameAndDescription(String name, String description) {
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(name));
        assertTrue(responseBody.contains(description));
    }

    @When("I update the item to have name {string} and description {string}")
    public void updateItem(String name, String description) {
        assertNotNull("Item ID must be set before updating", lastCreatedItemId);
        response = apiHelper.updateItem(lastCreatedItemId, name, description);
        checkStatusCode(200);
        String responseBody = response.getBody().asString();
        assertEquals("Item updated successfully", responseBody.trim());
    }

    @Then("the item should have name {string} and description {string}")
    public void checkItemNameAndDescription(String name, String description) {
        Response getResponse = apiHelper.getItems();
        List<Map<String, String>> items = getResponse.jsonPath().getList("$");
        boolean itemFound = false;

        for (Map<String, String> item : items) {
            if (item.get("id").equals(lastCreatedItemId) && item.get("name").equals(name)
                    && item.get("description").equals(description)) {
                itemFound = true;
                break;
            }
        }

        assertTrue("Updated item should exist in the list", itemFound);
    }

    @When("I delete the item")
    public void deleteItem() {
        assertNotNull("Item ID must be set before deleting", lastCreatedItemId);
        response = apiHelper.deleteItem(lastCreatedItemId);
        checkStatusCode(200);
        String responseBody = response.getBody().asString();
        assertEquals("Item deleted successfully", responseBody.trim());
    }

    @Then("the item should no longer exist")
    public void checkNoLongerExists() {
        Response getResponse = apiHelper.getItems();
        List<Map<String, String>> items = getResponse.jsonPath().getList("$");
        boolean itemFound = items.stream().anyMatch(item -> item.get("id").equals(lastCreatedItemId));
        assertFalse("Deleted item should no longer exist in the list", itemFound);
    }

    @Given("I try to create an item with invalid name {string} and description {string}")
    public void attemptToCreateWithInvalidNameAndOrDescription(String name, String description) {
        response = apiHelper.createItem(name, description);
    }

    @When("I try to update a non-existent item with ID {string} and name {string} and description {string}")
    public void attemptToUpdateNonExistentItem(String id, String name, String description) {
        response = apiHelper.updateItem(id, name, description);
    }

    @When("I try to delete a non-existent item with ID {string}")
    public void iTryToDeleteANonExistentItem(String id) {
        response = apiHelper.deleteItem(id);
    }

    @Then("I should receive an error message {string}")
    public void checkErrorMessage(String expectedMessage) {
        String responseBody = response.getBody().asString();
        assertEquals(expectedMessage, responseBody.trim());
    }

}
