package stellarburgers.config;

/**
 * Класс с константами URL-адресов для тестирования.
 */
public class Urls {
    // Базовый URL
    public static final String BASE_URL = "https://qa-stellarburgers.education-services.ru";

    // Страницы
    public static final String MAIN_PAGE = BASE_URL + "/";
    public static final String LOGIN_PAGE = BASE_URL + "/login";
    public static final String REGISTER_PAGE = BASE_URL + "/register";
    public static final String FORGOT_PASSWORD_PAGE = BASE_URL + "/forgot-password";
    public static final String PROFILE_PAGE = BASE_URL + "/account";

    // API эндпоинты
    public static final String API_REGISTER = "/api/auth/register";
    public static final String API_USER = "/api/auth/user";
}