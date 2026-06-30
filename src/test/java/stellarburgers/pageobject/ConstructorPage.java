package stellarburgers.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Page Object для главной страницы (конструктора) с использованием Actions.
 */
public class ConstructorPage extends BasePage {
    // Локаторы
    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    private WebElement loginButtonMain;

    @FindBy(xpath = "//p[text()='Личный Кабинет']")
    private WebElement personalAccountButton; // кнопка "Личный кабинет" в шапке

    // Локаторы для табов
    @FindBy(xpath = "//span[text()='Булки']/parent::div")
    private WebElement bunsTab;

    @FindBy(xpath = "//span[text()='Соусы']/parent::div")
    private WebElement saucesTab;

    @FindBy(xpath = "//span[text()='Начинки']/parent::div")
    private WebElement fillingsTab;

    // Локаторы для секций
    @FindBy(xpath = "//h2[text()='Булки']")
    private WebElement bunsSection;

    @FindBy(xpath = "//h2[text()='Соусы']")
    private WebElement saucesSection;

    @FindBy(xpath = "//h2[text()='Начинки']")
    private WebElement fillingsSection;

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Клик на кнопку 'Войти в аккаунт' на главной")
    public void clickLoginButtonMain() {
        loginButtonMain.click();
    }

    @Step("Клик на кнопку 'Личный кабинет' в шапке")
    public void clickPersonalAccountButton() {
        personalAccountButton.click();
    }


    @Step("Клик на раздел 'Булки'")
    public void clickBunsTab() {
        clickTabWithActions(bunsTab);
    }

    @Step("Клик на раздел 'Соусы'")
    public void clickSaucesTab() {
        clickTabWithActions(saucesTab);
    }

    @Step("Клик на раздел 'Начинки'")
    public void clickFillingsTab() {
        clickTabWithActions(fillingsTab);
    }

    /**
     * Клик по табу с использованием Actions и снятием перекрытия
     */
    private void clickTabWithActions(WebElement tab) {
        try {

            wait.until(ExpectedConditions.visibilityOf(tab));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tab);

            Actions actions = new Actions(driver);

            try {
                actions.moveToElement(tab).click().perform();
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
                } catch (Exception ex) {

                    String tabText = tab.getText();
                    WebElement newTab = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[text()='" + tabText + "']/parent::div")
                    ));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newTab);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newTab);
                }
            }

            Thread.sleep(300);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по табу: " + e.getMessage(), e);
        }
    }

    @Step("Проверить, что раздел 'Булки' отображается")
    public void checkBunsSectionVisible() {
        wait.until(ExpectedConditions.visibilityOf(bunsSection));
        assertTrue(bunsSection.isDisplayed(), "Раздел 'Булки' не отображается");
    }

    @Step("Проверить, что раздел 'Соусы' отображается")
    public void checkSaucesSectionVisible() {
        wait.until(ExpectedConditions.visibilityOf(saucesSection));
        assertTrue(saucesSection.isDisplayed(), "Раздел 'Соусы' не отображается");
    }

    @Step("Проверить, что раздел 'Начинки' отображается")
    public void checkFillingsSectionVisible() {
        wait.until(ExpectedConditions.visibilityOf(fillingsSection));
        assertTrue(fillingsSection.isDisplayed(), "Раздел 'Начинки' не отображается");
    }
}