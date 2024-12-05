import {isValidJson, prettyPrintJSON, restRequest} from "rest-assured-ts";

const BASE_URL = "http://localhost:9090/items";

export class ApiHelper {
    public async createItem(name: string, description: string) {
        let response: any;
        /*
                const requestBody = JSON.stringify({ name, description });
                return this.restAssured
                    .post(BASE_URL, requestBody, { headers: { 'Content-Type': 'application/json' } });
        */
        console.log("POST URL " + BASE_URL);
        const requestBody = JSON.stringify({name, description});
        console.log(`REQUEST Body  ${prettyPrintJSON(JSON.parse(requestBody))}`);
        const headerOptions = JSON.stringify({'Content-Type': 'application/json'});
        response = await restRequest(BASE_URL, {
            headerOptions: JSON.parse(headerOptions),
            httpMethod: "POST", inputBody: requestBody, timeOut: 20000
        });
        const responseBody = response.body;
        console.log(`Post Response Body: ${(responseBody)}`);
        console.log(`Post Response Code: ${(response.statusCode)}`);
        return response;
    }

    public async getItems() {
        let response: any;
        const headerOptions = JSON.stringify({"Content-Type": "application/json"});
        response = await restRequest(BASE_URL, {
            headerOptions: JSON.parse(headerOptions),
            httpMethod: "GET", timeOut: 20000
        });
        // console.log("GET User response " + prettyPrintJSON(JSON.parse(response.body)));
        return response;

    }

    public async updateItem(itemId: string, name: string, description: string) {
        let response: any;
        /*
                const requestBody = JSON.stringify({ name, description });
                return this.restAssured
                    .put(`${BASE_URL}/${itemId}`, requestBody, { headers: { 'Content-Type': 'application/json' } });
        */
        const requestBody = JSON.stringify({name, description});
        console.log("Update RequestBody as string" + requestBody);
        const headerOptions = JSON.stringify({'Content-Type': 'application/json'});
        response = await restRequest(`${BASE_URL}/${itemId}`, {
            headerOptions: JSON.parse(headerOptions),
            httpMethod: "PUT", inputBody: requestBody, timeOut: 10000
        });

        if (await isValidJson(response.body)) {
            const responseBody = response.body;
            console.log(`PUT Response: ${(responseBody)}`);
        } else {
            console.log(`PUT Response: ${response.body}`);
        }
        console.log(response.statusCode.toString());
        return response;

    }

    public async deleteItem(itemId: string) {
        let response: any;
        /*
                return this.restAssured
                    .delete(`${BASE_URL}/${itemId}`, { headers: { 'Content-Type': 'application/json' } });
        */
        const headerOptions = JSON.stringify({'Content-Type': 'application/json'});
        response = await restRequest(`${BASE_URL}/${itemId}`, {
            headerOptions: JSON.parse(headerOptions),
            httpMethod: "DELETE", timeOut: 10000
        });

        if (await isValidJson(response.body)) {
            const responseBody = response.body;
            console.log(`DELETE Response: ${(responseBody)}`);
        } else {
            console.log(`DELETE Response: ${response.body}`);
        }
        return response;
    }
}
