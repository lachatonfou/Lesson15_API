package controllers;

import configurations.TestConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import models.User;

import static constants.CommonConstants.DEFAULT_USER;
import static io.restassured.RestAssured.given;

public class FluentUserController {
    TestConfig config = new TestConfig();
    RequestSpecification requestSpecification = given();

    public FluentUserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(config.getBaseUrl());
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add default user")
    public MyHttpResponse addDefaultUser() {
        this.requestSpecification.body(DEFAULT_USER);
        return new MyHttpResponse(given(this.requestSpecification).post("user").then());
    }

    @Step("Add user")
    public MyHttpResponse addUser(User user) {
        this.requestSpecification.body(user);
        return new MyHttpResponse(given(this.requestSpecification).post("user").then());
    }

    @Step("Get user by name")
    public MyHttpResponse getUserByName(String name) {
        return new MyHttpResponse(given(this.requestSpecification).get(String.format("user/" + name)).then());
    }

    @Step("Delete user by name")
    public MyHttpResponse deleteUserByName(String name) {
        return new MyHttpResponse(given(this.requestSpecification).delete(String.format("user/" + name)).then());
    }
}
