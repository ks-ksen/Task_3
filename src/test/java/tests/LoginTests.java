package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import stellarburgers.api.UserApiClient;
import stellarburgers.config.DriverConfig;
import stellarburgers.config.Urls;
import stellarburgers.model.User;
import stellarburgers.pageobject.ConstructorPage;
import stellarburgers.pageobject.ForgotPasswordPage;
import stellarburgers.pageobject.LoginPage;
import stellarburgers.pageobject.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для проверки входа в систему разными способами.
 */
public class LoginTests {
    private WebDriver driver;
    private UserApiClient apiClient;
    private User user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        driver = DriverConfig.getDriver();
        apiClient = new UserApiClient();
        // Создание пользователя через API для тестов входа
        String randomSuffix = String.valueOf(System.currentTimeMillis());
        user = new User("user" + randomSuffix + "@test.ru", "password123", "TestUser");
        accessToken = apiClient.createUser(user).jsonPath().getString("accessToken");
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
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    public void testLoginViaMainButton() {
        driver.get(Urls.MAIN_PAGE);
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickLoginButtonMain();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("qa-stellarburgers.education-services.ru/"), "Не выполнена авторизация");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccount() {
        driver.get(Urls.MAIN_PAGE);
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(driver.getCurrentUrl().contains("qa-stellarburgers.education-services.ru/"), "Не выполнена авторизация");
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testLoginViaRegisterForm() {
        driver.get(Urls.REGISTER_PAGE);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(driver.getCurrentUrl().contains("qa-stellarburgers.education-services.ru/"), "Не выполнена авторизация");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testLoginViaForgotPasswordForm() {
        driver.get(Urls.FORGOT_PASSWORD_PAGE);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue(driver.getCurrentUrl().contains("qa-stellarburgers.education-services.ru/"), "Не выполнена авторизация");
    }
}