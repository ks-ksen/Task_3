package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import stellarburgers.api.UserApiClient;
import stellarburgers.config.DriverConfig;
import stellarburgers.model.User;
import stellarburgers.pageobject.ConstructorPage;
import stellarburgers.pageobject.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для проверки перехода в личный кабинет.
 */
public class ProfileNavigationTests {
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
    @DisplayName("Переход в личный кабинет по клику на 'Личный кабинет'")
    public void testNavigateToProfile() {
        // Вход в систему
        driver.get("https://qa-stellarburgers.education-services.ru/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        // Клик на "Личный кабинет"
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickPersonalAccountButton();

        // Проверяем, что открылась страница профиля
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/account"), "Не произошел переход в личный кабинет");
    }
}