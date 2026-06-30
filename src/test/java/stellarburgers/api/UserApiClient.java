package stellarburgers.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import stellarburgers.model.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru";
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String DELETE_ENDPOINT = "/api/auth/user";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(REGISTER_ENDPOINT);
    }
    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(DELETE_ENDPOINT);
    }
}