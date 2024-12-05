Feature: Selenium - Item Management 1

  Scenario: Update an existing item
    Given I navigate to the item list page
    And I add an item with name "Item 100" and description "Another item description"
    And I click the Update button for item with name "Item 100"
    When I update the item to name "Item 200" and description "Updated Description"
    Then I should see an item with the new item name "Item 200"
    And I should see the success message "Item updated successfully!"

  @test
  Scenario: Add multiple items using a data table
    Given I navigate to the item list page
    When I add the following items:
      | Name    | Description            |
      | Item 10 | Description of Item 10 |
      | Item 11 | Description of Item 11 |
      | Item 12 | Description of Item 12 |
    Then I should see all the items in the list:
      | Name    | Description            |
      | Item 10 | Description of Item 10 |
      | Item 11 | Description of Item 11 |
      | Item 12 | Description of Item 12 |
    Given I should see the success message "Item added successfully!"
