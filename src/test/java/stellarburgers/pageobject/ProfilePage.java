package stellarburgers.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы личного кабинета.
 */
public class ProfilePage extends BasePage {
    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    // Альтернативный локатор для кнопки "Выход"
    @FindBy(xpath = ".//button[text()='Выход']")
    private WebElement logoutButtonAlt;

    @FindBy(xpath = ".//p[text()='Конструктор']")
    private WebElement constructorLink;

    @FindBy(xpath = "//div[@class='AppHeader_header__logo__2D0X2']")
    private WebElement logo;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Клик на кнопку 'Выход'")
    public void clickLogoutButton() {
        try {
            logoutButton.click();
        } catch (Exception e) {
            logoutButtonAlt.click();
        }
    }

    @Step("Клик на ссылку 'Конструктор'")
    public void clickConstructorLink() {
        constructorLink.click();
    }

    @Step("Клик на логотип Stellar Burgers")
    public void clickLogo() {
        logo.click();
    }
}