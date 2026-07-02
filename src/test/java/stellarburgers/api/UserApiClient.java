package stellarburgers.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import stellarburgers.config.Urls;
import stellarburgers.model.User;

import static io.restassured.RestAssured.given;

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

    public void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete(Urls.API_USER);
    }
}