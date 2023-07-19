package helpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class APIHelper {
    @Step
    public static String getUserToken(String email, String password) {
        String body = "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/tester/login")
                .then()
                .extract().response();
        String token = response.jsonPath().getString("accessToken");
        return token;
    }

    @Step
    public static Response authorization(String email, String password) {
        String body = "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/tester/login")
                .then()
                .extract().response();
        return response;
    }

    @Step
    public static Response createUser(String accessToken, String body){
        Response response = RestAssured.given()
                .accept("application/json")
                .auth ()
                .oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .post( "/api/automationTask/create")
                .then()
                .extract().response();
        return response;
    }

    @Step
    public static Response getUserByEmail(String accessToken, String email){
        String body = "{\n" +
                "  \"email\": \"" + email + "\"\n" +
                "}";
        Response response = RestAssured.given()
                .accept("application/json")
                .auth ()
                .oauth2(accessToken)
                .contentType("application/json")
                .body(body)
                .when()
                .post( "/api/automationTask/getOne")
                .then()
                .extract().response();
        return response;
    }

    @Step
    public static Response getAllUsers(String accessToken){
        Response response = RestAssured.given()
                .accept("application/json")
                .auth ()
                .oauth2(accessToken)
                .contentType("application/json")
                .when()
                .get( "/api/automationTask/getAll")
                .then()
                .extract().response();
        return response;
    }

    @Step
    public static Response deleteUserByID(String accessToken, String id){
        Response response = RestAssured.given()
                .accept("application/json")
                .auth ()
                .oauth2(accessToken)
                .contentType("application/json")
                .when()
                .delete( "/api/automationTask/deleteOne/" + id)
                .then()
                .extract().response();
        return response;
    }
}
