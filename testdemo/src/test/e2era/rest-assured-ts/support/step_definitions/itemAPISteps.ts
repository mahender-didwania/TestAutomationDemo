import {Given, Then, When} from '@cucumber/cucumber'
import {ApiHelper} from './apiHelper'
// import {expect} from "chai"
import chai from 'chai'

export class TestDataMemory {
    private memory: Map<string, any>
    private RESPONSE_KEY: string = "Response"
    private ITEM_ID_KEY: string = "ItemID"

    constructor() {
        this.memory = new Map()
    }

    saveResponse(response: any): string {
        sharedState.save(this.RESPONSE_KEY, response)
        return this.getResponse()
    }

    getResponse(): any {
        return this.load(this.RESPONSE_KEY)
    }

    saveItemID(itemID: any): string {
        sharedState.save(this.ITEM_ID_KEY, itemID)
        return sharedState.getItemID()
    }

    getItemID(): any {
        return this.load(this.ITEM_ID_KEY)
    }

    save<T>(key: string, value: T): void {
        this.memory.set(key, value)
    }

    load<T>(key: string): T {
        if (this.memory.has(key)) {
            return this.memory.get(key)
        }
        throw new Error(`Value does not exist for ${key}, can't be retrieved.`)
    }
}

const {expect} = chai
const apiHelper = new ApiHelper()
let sharedState = new TestDataMemory()

Given('I create an item with name {string} and description {string}', async (name: string, description: string) => {
    let response: any
    let savedItemId: string

    response = await apiHelper.createItem(name, description)
    console.log(`Steps: Post Response Code: ${(response.statusCode.toString())}`);
    expect(response.statusCode).to.eq(201)
    expect(response.body.trim()).to.eq("Item added successfully")

    // Fetch all items and find the ID of the newly created item
    response = await apiHelper.getItems()
    const items = JSON.parse(response.body)
    const newItem = items.find((item: any) => item.name === name && item.description === description)

    expect(newItem).to.not.be.null
    savedItemId = newItem.id
    sharedState.saveResponse(response)
    sharedState.saveItemID(savedItemId)
})

Then('I should receive a {int} status code', (statusCode: number) => {
    let response = sharedState.getResponse()
    // console.log(`Steps: Saved Response Body:  ${(response.body)}`)
    console.log(`Steps: Saved Response Code:  ${(response.statusCode)}`)
    expect(response.statusCode).to.eq(statusCode)
})

Given('I get the list of items', async () => {
    let response: any
    response = await apiHelper.getItems()
    sharedState.saveResponse(response)
})

Then('I should see an item with name {string} and description {string}', (name: string, description: string) => {
    let response = sharedState.getResponse()
    // console.log(`Steps: Saved Response Body:  ${(response.body)}`)
    console.log(`Steps: Saved Response Code:  ${(response.statusCode)}`)
    const responseBody = response.body
    expect(responseBody).to.contain(name)
    expect(responseBody).to.contain(description)
})

When('I update the item to have name {string} and description {string}', async (name: string, description: string) => {
    let response: any
    let savedItemId: string = sharedState.getItemID()
    // console.log(`Steps: Saved Response Body:  ${(response.body)}`)
    console.log(`Steps: Saved Item ID:  ${(savedItemId)}`)
    expect(savedItemId).to.not.be.null
    response = await apiHelper.updateItem(savedItemId, name, description)
    expect(response.statusCode).to.eq(200)
    expect(response.body.trim()).to.eq("Item updated successfully")
})

Then('the item should have name {string} and description {string}', async (name: string, description: string) => {
    let response: any
    let savedItemId: string = sharedState.getItemID()

    // console.log(`Steps: Saved Response Body:  ${(response.body)}`)
    // console.log(`Steps: Saved Response Code:  ${(response.statusCode)}`)
    console.log(`Steps: Saved Item ID:  ${(savedItemId)}`)

    response = await apiHelper.getItems()
    const item = JSON.parse(response.body).find((item: any) => item.id === savedItemId)
    expect(item).to.not.be.null
    expect(item.name).to.eq(name)
    expect(item.description).to.eq(description)
})

When('I delete the item', async () => {
    let response: any
    let savedItemId: string = sharedState.getItemID()

    response = await apiHelper.deleteItem(savedItemId)
    expect(response.statusCode).to.eq(200)
    expect(response.body.trim()).to.eq("Item deleted successfully")
})

Then('the item should no longer exist', async () => {
    let response: any
    let savedItemId: string = sharedState.getItemID()

    response = await apiHelper.getItems()
    const itemFound = JSON.parse(response.body).some((item: any) => item.id === savedItemId)
    expect(itemFound).to.be.false
})

Given('I try to create an item with invalid name {string} and description {string}', async (name: string, description: string) => {
    let response: any
    response = await apiHelper.createItem(name, description)
    sharedState.saveResponse(response)
})

When('I try to update a non-existent item with ID {string} and name {string} and description {string}', async (id: string, name: string, description: string) => {
    let response: any
    response = await apiHelper.updateItem(id, name, description)
    sharedState.saveResponse(response)
})

When('I try to delete a non-existent item with ID {string}', async (id: string) => {
    let response: any
    response = await apiHelper.deleteItem(id)
    sharedState.saveResponse(response)
})

Then('I should receive an error message {string}', (expectedMessage: string) => {
    let response = sharedState.getResponse()
    expect(response.body.trim()).to.eq(expectedMessage)
})
