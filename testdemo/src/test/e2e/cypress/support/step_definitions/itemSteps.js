import {Given, Then, When} from "@badeball/cypress-cucumber-preprocessor";
import ItemListPage from '../page_objects/ItemListPage';
import UpdateItemPage from '../page_objects/UpdateItemPage';

Given('I am on the item list page', () => {
    cy.visit('http://localhost:9090/index.html');
});

When('I click on the add item button', () => {
    ItemListPage.clickAddItem();
});

Then('I should see the item {string} in the list', (itemName) => {
    ItemListPage.verifyItemListed(itemName);
});

When("I add an item with name {string} and description {string}", (name, description) => {
    ItemListPage.addItemWithNameAndDescription(name, description);
});

Then("I should see the item with name {string} and description {string} in the list", (name, description) => {
    ItemListPage.getItemList()
        .should("contain.text", name)
        .and("contain.text", description);
});

Then('I should see a success message {string}', (message) => {
    ItemListPage.getMessage().should('contain.text', message);
});

When('I click the Update button for item with name {string}', (name) => {
    ItemListPage.clickUpdateForFirstMatchingItemName(name);
    UpdateItemPage.verifyURI();
});

When('I update the item with name {string} and description {string}', (name, description) => {
    UpdateItemPage.updateItem(name, description);
});

Then('I should see the item {string} updated successfully', (itemName) => {
    ItemListPage.verifyItemListed(itemName);
});

When("I add the following items:", (dataTable) => {
    const items = dataTable.hashes();

    items.forEach((item) => {
        ItemListPage.addItemWithNameAndDescription(item.Name, item.Description);
    });
});

Then("I should see all the items in the list:", (dataTable) => {
    const expectedItems = dataTable.hashes();

    expectedItems.forEach((item) => {
        ItemListPage.getItemList()
            .should("contain.text", item.Name)
            .and("contain.text", item.Description);
    });
});

