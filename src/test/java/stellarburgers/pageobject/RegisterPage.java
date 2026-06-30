package stellarburgers.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Object для страницы регистрации.
 */
public class RegisterPage extends BasePage {
    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameField;

    @FindBy(xpath = ".//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//a[@href='/login']")
    private WebElement loginLink;

    @FindBy(xpath = "//p[text()='Некорректный пароль']")
    private WebElement errorPasswordMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить имя: {name}")
    public void setName(String name) {
        nameField.sendKeys(name);
    }

    @Step("Заполнить email: {email}")
    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    @Step("Заполнить пароль: {password}")
    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        registerButton.click();
    }

    @Step("Клик на ссылку 'Войти'")
    public void clickLoginLink() {
        loginLink.click();
    }

    @Step("Проверить, что отображается сообщение об ошибке пароля")
    public void checkErrorPasswordMessageVisible() {
        assertTrue(errorPasswordMessage.isDisplayed(), "Сообщение об ошибке пароля не отображается");
    }

    @Step("Зарегистрировать пользователя с данными: имя={name}, email={email}, пароль={password}")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }
}