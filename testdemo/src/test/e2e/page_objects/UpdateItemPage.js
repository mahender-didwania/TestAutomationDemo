class UpdateItemPage {
    get itemNameInput() {
        return cy.get('#name');
    }

    get itemDescriptionInput() {
        return cy.get('#description');
    }

    get updateButton() {
        return cy.get('button[type="submit"]');
    }

    get message() {
        return cy.get('#message');
    }

    updateItem(name, description) {
        this.itemNameInput.clear().type(name);
        this.itemDescriptionInput.clear().type(description);
        this.updateButton.click();
    }

    verifyURI(){
        cy.url().should('include', '/update.html');
    }

    getMessage() {
        return this.message.should('be.visible');
    }
}

export default new UpdateItemPage();
