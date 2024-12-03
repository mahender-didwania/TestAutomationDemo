@cypress
Feature: Cypress - Scenario Outline Add

  Scenario: Add a new item
    Given I am on the item list page
    When I add an item with name "<Item Name>" and description "<Item Description>"
    Then I should see the item with name "<Item Name>" and description "<Item Description>" in the list
    And I should see a success message "Item added successfully!"
    Examples:
    | Item Name | Item Description    |
    | Item 1    | Item Description 1  | 
    | Item 2    | Item Description 2  |
    | Item 3    | Item Description 3  |
    | Item 4    | Item Description 4  |

  Scenario: Cypress - Scenario Outline Update
    Given I am on the item list page
    And I add an item with name "<Item Name>" and description "<Item Description>"
    When I click the Update button for item with name "<Item Name>"
    And I update the item with name "<Updated Item Name>" and description "<Updated Item Description>"
    # Then I should see the item "<Updated Item Name>" updated successfully
    Then I should see the item with name "<Updated Item Name>" and description "<Updated Item Description>" in the list
    Examples:
    | Item Name | Item Description      | Updated Item Name  | Updated Item Description  |
    | Item c1   | Item Description c1   | Updated Item c1    | Updated Description c1    |
    | Item c2   | Item Description c2   | Updated Item c2    | Updated Description c2    |
    | Item c3   | Item Description c3   | Updated Item c3    | Updated Description c3    |
    | Item c4   | Item Description c4   | Updated Item c4    | Updated Description c4    |
