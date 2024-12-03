class ItemListPage {
    get addItemButton() {
        return cy.get('button[type="submit"]');
    }
    
    get itemList() {
        return cy.get('#items-list');
    }
    
    get message() {
        return cy.get('#message');
    }

    clickAddItem() {
        this.addItemButton.click();
    }

    clickUpdateForFirstMatchingItemName(itemName){
        cy.contains('td', itemName)
        .parent()
        .within(() => {
            cy.contains('button', 'Update').click();
        });
    }

    addItemWithNameAndDescription(itemName, ItemDescription){
        cy.get("#name").clear().type(itemName);
        cy.get("#description").clear().type(ItemDescription);
        cy.get('button[type="submit"]').click();
    }

    verifyItemListed(itemName) {
        this.itemList.contains('td', itemName);
    }

    verifyItemAndDescriptionListed(itemName, description) {
        this.itemList.contains('td', itemName).and.contains(description)
    }

    getMessage() {
        return this.message.should('be.visible');
    }

    getItemList() {
        return this.itemList.should('be.visible');
    }


}

export default new ItemListPage();
