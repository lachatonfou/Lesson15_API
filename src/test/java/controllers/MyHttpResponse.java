package controllers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;

public class MyHttpResponse {
    private final ValidatableResponse response;

    public MyHttpResponse(ValidatableResponse response) {
        this.response = response;
    }

    @Step("Check status code")
    public MyHttpResponse statusCodeIs(int status) {
        this.response.statusCode(status);
        return this;
    }


    @Step("Check json value by path '{path}' and expected value '{expectedValue}'")
    public MyHttpResponse jsonValueIs(String path, String expectedValue) {
        String body = """ 
                {
                  "id": 1,
                  "category": {
                    "id": 1,
                    "name": "Maggie"
                  },
                  "name": "doggie",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 2,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }""";
        response.extract().jsonPath().getLong("id");
        response.extract().jsonPath().get("category.name");
        response.extract().jsonPath().get("tags[0].id");
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue).as("Actual value '%s' is not equals to expected '%s' for the path '%s' and response: \n%s", actualValue, expectedValue, path, this.response.extract().response().andReturn().asPrettyString()).isEqualTo(expectedValue);
        return this;
    }

    @Step("Check json value is not null")
    public MyHttpResponse jsonValueIsNotNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue).isNotNull();
        return this;
    }

    @Step("Check json value is null")
    public MyHttpResponse jsonValueIsNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(actualValue).isNull();
        return this;
    }

    @Step("Get json value by path: {path}")
    public String getJsonValue(String path) {
        String value = this.response.extract().jsonPath().getString(path);
        Assertions.assertThat(value).isNotNull();
        return value;
    }

    @Override
    @Step("Return info about response")
    public String toString() {
        return String.format("Status code: %s and response: \n%s", response.extract().response().statusCode(), response.extract().response().asPrettyString());
    }
}
