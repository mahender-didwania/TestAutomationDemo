@cypress
Feature: Cypress - Update 1

  Scenario: Update an existing item
    Given I am on the item list page
    And I add an item with name "OldItem" and description "Original Description"
    When I update the item with name "NewItem" and description "Updated Description"
    Then I should see the item "NewItem" updated successfully

  @test
  Scenario: Cypress - Datatable Add
    Given I am on the item list page
    When I add the following items:
      | Name           | Description                 |
      | Item 10        | Description of Item 10      |
      | Item 11        | Description of Item 11      |
      | Item 12        | Description of Item 12      |
    Then I should see all the items in the list:
      | Name           | Description                 |
      | Item 10        | Description of Item 10      |
      | Item 11        | Description of Item 11      |
      | Item 12        | Description of Item 12      |
    And I should see a success message "Item added successfully!"

