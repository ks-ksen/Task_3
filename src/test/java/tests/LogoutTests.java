package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellarburgers.api.UserApiClient;
import stellarburgers.config.DriverConfig;
import stellarburgers.config.Urls;
import stellarburgers.model.User;
import stellarburgers.pageobject.ConstructorPage;
import stellarburgers.pageobject.LoginPage;
import stellarburgers.pageobject.ProfilePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для проверки выхода из аккаунта.
 */
public class LogoutTests {
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
    @DisplayName("Выход по кнопке 'Выйти' в личном кабинете")
    public void testLogoutFromProfile() {
        // Вход в систему и переход в профиль
        driver.get(Urls.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickPersonalAccountButton();

        // Нажатие Выход
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.getCurrentUrl().contains("/login"));

        // Проверяем, что перебросило на страницу входа
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("/login"), "Не произошел выход");
        loginPage.checkLoginHeaderVisible();
    }
}