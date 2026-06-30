package stellarburgers.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Класс для настройки и получения WebDriver в зависимости от браузера.
 */
public class DriverConfig {

    private static final String BROWSER = System.getProperty("browser", "chrome").toLowerCase();

    public static WebDriver getDriver() {
        WebDriver driver;
        switch (BROWSER) {
            case "yandex":
                WebDriverManager.chromedriver().setup(); // пусть WDM сам подберёт драйвер
                ChromeOptions yandexOptions = new ChromeOptions();
                // Путь к исполняемому файлу Яндекс.Браузера (проверь актуальный на своей машине)
                yandexOptions.setBinary("C:/Program Files/Yandex/YandexBrowser/Application/browser.exe");
                driver = new ChromeDriver(yandexOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default: // chrome
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }
}