@api
Feature: API Operations

  @testapi
  Scenario: Add a new item
    When I create an item with name "Item21" and description "Description of Item21"
    Then I should receive a 201 status code

  Scenario: Retrieve the list of items
    When I get the list of items
    Then I should see an item with name "Item21" and description "Description of Item21"

  Scenario: Add, update, and delete an item
    Given I create an item with name "Test Item" and description "Test Description"
    Then I should receive a 201 status code
    When I update the item to have name "Updated Test Item" and description "Updated Description"
    Then the item should have name "Updated Test Item" and description "Updated Description"
    When I delete the item
    Then the item should no longer exist

# API Failure Scenarios

  Scenario: Attempt to create an item with an empty name
    When I try to create an item with invalid name "" and description "A valid description"
    Then I should receive a 400 status code
    And I should receive an error message "Name and description must not be empty"

  Scenario: Attempt to create an item with an empty description
    When I try to create an item with invalid name "Valid Name" and description ""
    Then I should receive a 400 status code
    And I should receive an error message "Name and description must not be empty"

  Scenario: Attempt to update a non-existent item
    When I try to update a non-existent item with ID "non-existent-id" and name "Updated Name" and description "Updated Description"
    Then I should receive a 404 status code
    And I should receive an error message "Item not found"

  Scenario: Attempt to delete a non-existent item
    When I try to delete a non-existent item with ID "non-existent-id"
    Then I should receive a 404 status code
    And I should receive an error message "Item not found"

