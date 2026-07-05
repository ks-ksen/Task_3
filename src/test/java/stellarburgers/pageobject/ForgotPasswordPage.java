package stellarburgers.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы восстановления пароля.
 */
public class ForgotPasswordPage extends BasePage {
    @FindBy(xpath = "//a[@href='/login']")
    private WebElement loginLink;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Клик на ссылку 'Войти' на странице восстановления пароля")
    public void clickLoginLink() {
        loginLink.click();
    }
}