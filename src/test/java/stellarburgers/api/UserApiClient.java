package stellarburgers.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import stellarburgers.config.Urls;
import stellarburgers.model.User;

import static io.restassured.RestAssured.given;
import static stellarburgers.config.Urls.API_LOGIN_USER;

public class UserApiClient {
    static {
        RestAssured.baseURI = Urls.BASE_URL;
    }

    public Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(Urls.API_REGISTER);
    }

    public Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(Urls.API_LOGIN_USER);
    }

    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(Urls.API_USER);
    }
}