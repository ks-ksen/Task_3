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
import stellarburgers.pageobject.LoginPage;
import stellarburgers.pageobject.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для проверки переходов в конструктор из личного кабинета.
 */
public class ConstructorNavigationTests {
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
    @DisplayName("Переход из профиля в конструктор по клику на 'Конструктор'")
    public void testGoToConstructorViaLink() {
        // Вход и переход в профиль
        driver.get(Urls.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickPersonalAccountButton();

        // В профиле клик на "Конструктор"
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorLink();

        // Проверяем, что на главной
        assertTrue(driver.getCurrentUrl().contains("qa-stellarburgers.education-services.ru/"), "Не вернулись на главную");
        constructorPage.checkFillingsSectionVisible();
        constructorPage.checkBunsSectionVisible();
        constructorPage.checkSaucesSectionVisible();
    }

    @Test
    @DisplayName("Переход из профиля в конструктор по клику на логотип")
    public void testGoToConstructorViaLogo() {
        // Вход и переход в профиль
        driver.get(Urls.LOGIN_PAGE);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickPersonalAccountButton();

        // В профиле клик на логотип
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogo();

        assertTrue(driver.getCurrentUrl().contains("qa-stellarburgers.education-services.ru/"), "Не вернулись на главную");
        constructorPage.checkFillingsSectionVisible();
        constructorPage.checkBunsSectionVisible();
        constructorPage.checkSaucesSectionVisible();
    }

    @Test
    @DisplayName("Переходы по разделам конструктора: Булки, Соусы, Начинки")
    public void testConstructorTabsSwitching() {
        driver.get(Urls.BASE_URL);

        ConstructorPage constructorPage = new ConstructorPage(driver);

        // Переходим по разделам
        constructorPage.clickBunsTab();

        constructorPage.clickSaucesTab();

        constructorPage.clickFillingsTab();
    }
}