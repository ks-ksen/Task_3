package stellarburgers.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Object для страницы входа.
 */
public class LoginPage extends BasePage {
    @FindBy(xpath = "//h2[text()='Вход']")
    private WebElement loginHeader;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[@href='/register']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[@href='/forgot-password']")
    private WebElement forgotPasswordLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, что отображается заголовок 'Вход'")
    public void checkLoginHeaderVisible() {
        wait.until(ExpectedConditions.visibilityOf(loginHeader));
        assertTrue(loginHeader.isDisplayed(), "Заголовок 'Вход' не отображается");
    }

    @Step("Заполнить email: {email}")
    public void setEmail(String email) {
        emailField.sendKeys(email);
    }

    @Step("Заполнить пароль: {password}")
    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Выполнить вход с логином {email} и паролем {password}")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Проверить, что отображается кнопка 'Войти'")
    public void checkLoginButtonVisible() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        assertTrue(loginButton.isDisplayed(), "Кнопка 'Войти' не отображается");
    }
}