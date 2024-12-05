@always
Feature: Selenium - Item Management 2

  Scenario Outline: Add multiple items
    Given I navigate to the item list page
    When I add an item with name "<Item Name>" and description "<Item Description>"
    Then I should see the item "<Item Name>" in the item list
    And I should see the success message "Item added successfully!"
    Examples:
      | Item Name | Item Description   |
      | Item 1    | Item Description 1 |
      | Item 2    | Item Description 2 |
      | Item 3    | Item Description 3 |
      | Item 4    | Item Description 4 |

  Scenario Outline: Update previously added items
    Given I navigate to the item list page
    And I add an item with name "<Item Name>" and description "<Item Description>"
    And I click the Update button for item with name "<Item Name>"
    When I update the item to name "<Updated Item Name>" and description "<Updated Item Description>"
    Then I should see an item with the new item name "<Updated Item Name>"
    And I should see the success message "Item updated successfully!"
    Examples:
      | Item Name | Item Description   | Updated Item Name | Updated Item Description |
      | Item 1    | Item Description 1 | Updated Item 1    | Updated Description 1    |
      | Item 2    | Item Description 2 | Updated Item 2    | Updated Description 2    |
      | Item 3    | Item Description 3 | Updated Item 3    | Updated Description 3    |
      | Item 4    | Item Description 4 | Updated Item 4    | Updated Description 4    |
