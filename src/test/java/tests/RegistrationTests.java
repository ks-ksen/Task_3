package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellarburgers.api.UserApiClient;
import stellarburgers.config.DriverConfig;
import stellarburgers.config.Urls;
import stellarburgers.model.User;
import stellarburgers.pageobject.LoginPage;
import stellarburgers.pageobject.RegisterPage;

import java.time.Duration;

/**
 * Тесты для проверки функциональности регистрации.
 */
public class RegistrationTests {
    private WebDriver driver;
    private UserApiClient apiClient;
    private User user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        driver = DriverConfig.getDriver();
        apiClient = new UserApiClient();

        String randomSuffix = String.valueOf(System.currentTimeMillis());
        user = new User("user" + randomSuffix + "@test.ru", "password123", "TestUser");
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            apiClient.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void testSuccessfulRegistration() {
        driver.get(Urls.REGISTER_PAGE);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.getCurrentUrl().contains("/login"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.checkLoginButtonVisible();

        accessToken = apiClient.loginUser(user).jsonPath().getString("accessToken");

    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем (меньше 6 символов)")
    public void testRegistrationWithShortPasswordError() {
        driver.get(Urls.REGISTER_PAGE);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(user.getName(), user.getEmail(), "1234");

        registerPage.checkErrorPasswordMessageVisible();
    }
}