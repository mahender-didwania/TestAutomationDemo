package com.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiHelper {

    private static final String BASE_URL = "http://localhost:9090/items";

    public Response createItem(String name, String description) {
        String requestBody = String.format("{\"name\":\"%s\", \"description\":\"%s\"}", name, description);
        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASE_URL);
    }

    public Response getItems() {
        return RestAssured.given()
                .contentType("application/json")
                .get(BASE_URL);
    }

    public Response updateItem(String itemId, String name, String description) {
        String requestBody = String.format("{\"name\":\"%s\", \"description\":\"%s\"}", name, description);
        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .put(BASE_URL + "/" + itemId);
    }

    public Response deleteItem(String itemId) {
        return RestAssured.given()
                .contentType("application/json")
                .delete(BASE_URL + "/" + itemId);
    }
}
