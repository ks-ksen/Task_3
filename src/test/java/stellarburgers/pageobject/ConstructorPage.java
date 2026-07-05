package stellarburgers.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    // Локаторы для табов с проверкой активного состояния
    @FindBy(xpath = "//span[text()='Булки']/parent::div[contains(@class, 'tab_tab__')]")
    private WebElement bunsTabWithClass;

    @FindBy(xpath = "//span[text()='Соусы']/parent::div[contains(@class, 'tab_tab__')]")
    private WebElement saucesTabWithClass;

    @FindBy(xpath = "//span[text()='Начинки']/parent::div[contains(@class, 'tab_tab__')]")
    private WebElement fillingsTabWithClass;

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
        } catch (Exception e) {
            throw new RuntimeException("Не удалось кликнуть по табу: " + e.getMessage(), e);
        }
    }

    /**
     * Получение активного таба на основе анализа классов
     */
    private String getActiveTabClass() {
        try {
            // Получаем все табы
            WebElement bunsTab = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[text()='Булки']/parent::div[contains(@class, 'tab_tab__')]")
            ));
            WebElement saucesTab = driver.findElement(
                    By.xpath("//span[text()='Соусы']/parent::div[contains(@class, 'tab_tab__')]")
            );
            WebElement fillingsTab = driver.findElement(
                    By.xpath("//span[text()='Начинки']/parent::div[contains(@class, 'tab_tab__')]")
            );

            // Получаем классы каждого таба
            String bunsClass = bunsTab.getAttribute("class");
            String saucesClass = saucesTab.getAttribute("class");
            String fillingsClass = fillingsTab.getAttribute("class");

            System.out.println("Class Buns: " + bunsClass);
            System.out.println("Class Sauces: " + saucesClass);
            System.out.println("Class Fillings: " + fillingsClass);

            // Анализируем классы - ищем признак активного таба
            // Обычно это может быть класс "current", "active", "tab_tab_type_current__" и т.д.
            Map<String, String> tabClasses = new HashMap<>();
            tabClasses.put("Булки", bunsClass);
            tabClasses.put("Соусы", saucesClass);
            tabClasses.put("Начинки", fillingsClass);

            String activeTabName = null;
            for (Map.Entry<String, String> entry : tabClasses.entrySet()) {
                if (entry.getValue().contains("current") ||
                        entry.getValue().contains("active") ||
                        entry.getValue().contains("type_current") ||
                        entry.getValue().contains("_active_")) {
                    activeTabName = entry.getKey();
                    break;
                }
            }

            return activeTabName;
        } catch (Exception e) {
            System.err.println("Ошибка при определении активного таба: " + e.getMessage());
            return null;
        }
    }

    /**
     * Получение элемента таба по названию
     */
    private WebElement getTabElementWithClass(String tabName) {
        return driver.findElement(
                By.xpath("//span[text()='" + tabName + "']/parent::div[contains(@class, 'tab_tab__')]")
        );
    }

    /**
     * Проверка, активен ли таб по классу
     */
    private boolean isTabActive(String tabClass) {
        return tabClass.contains("current") ||
                tabClass.contains("active") ||
                tabClass.contains("type_current") ||
                tabClass.contains("_active_");
    }

    /**
     * Ожидание активации таба
     */
    private void waitForTabActivation(String sectionName) {
        WebElement tabElement = getTabElementWithClass(sectionName);
        wait.until(driver -> isTabActive(Objects.requireNonNull(tabElement.getAttribute("class"))));
    }

    /**
     * Проверка видимости секции через анализ активного таба
     */
    private void checkSectionVisibleByTab(String sectionName) {
        // Кликаем на соответствующий таб для навигации к секции
        switch (sectionName) {
            case "Булки":
                clickBunsTab();
                break;
            case "Соусы":
                clickSaucesTab();
                break;
            case "Начинки":
                clickFillingsTab();
                break;
            default:
                throw new IllegalArgumentException("Неизвестная секция: " + sectionName);
        }

        waitForTabActivation(sectionName);

        // Получаем активный таб после клика
        String activeTab = getActiveTabClass();
        System.out.println("Активный таб после клика: " + activeTab);

        // Проверяем, что активный таб соответствует ожидаемой секции
        assertTrue(
                sectionName.equals(activeTab),
                "Ожидалась секция '" + sectionName + "', но активен таб '" + activeTab + "'"
        );

        // Дополнительно проверяем, что класс таба изменился
        WebElement currentTab = driver.findElement(
                By.xpath("//span[text()='" + sectionName + "']/parent::div[contains(@class, 'tab_tab__')]")
        );
        String currentClass = currentTab.getAttribute("class");

        // Проверяем, что у таба есть признак активности
        boolean isActive = currentClass.contains("current") ||
                currentClass.contains("active") ||
                currentClass.contains("type_current") ||
                currentClass.contains("_active_");

        assertTrue(isActive,
                "Таб '" + sectionName + "' не активен. Класс: " + currentClass
        );
    }

    /**
     * Проверка, что раздел отображается с использованием анализа классов табов
     */
    @Step("Проверить, что раздел 'Булки' отображается")
    public void checkBunsSectionVisible() {
        checkSectionVisibleByTab("Булки");
    }

    @Step("Проверить, что раздел 'Соусы' отображается")
    public void checkSaucesSectionVisible() {
        checkSectionVisibleByTab("Соусы");
    }

    @Step("Проверить, что раздел 'Начинки' отображается")
    public void checkFillingsSectionVisible() {
        checkSectionVisibleByTab("Начинки");
    }

}