package steps.cucumber;


import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utility.Constant;

import java.awt.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;

public enum CucumberTestContext {
    CONTEXT;
    final String currUser = System.getProperty("user.name");
    private static final String PAYLOAD = "PAYLOAD";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";
    private static final String SCENARIO = "SCENARIO";
    private static final String DIVISION_TYPE = "DIVISION_TYPE";
    private static final String DB_CONNECTION = "DB_CONNECTION";
    private static final String SOFT_ASSERTION = "SOFT_ASSERTION";
    private final ThreadLocal<Map<String, Object>> threadLocal = withInitial(HashMap::new);

    private Map<String, Object> testContextMap() {
        return threadLocal.get();
    }

    public void set(String key, Object value) {
        testContextMap().put(key, value);
    }

    public Object get(String key) {
        return testContextMap().get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(testContextMap().get(key));
    }

    public void setPayload(Object value) {
        set(PAYLOAD, value);
    }

    public Object getPayload() {
        return testContextMap().get(PAYLOAD);
    }
    private WebDriver driver;
    public void openBrowser() {
        String browserName = Constant.browserName; // Example: "chrome", "firefox", "msedge"
        boolean isHeadless = getIsHeadlessBrowser();

        // Initialize WebDriver based on the browser name
        driver = switch (browserName.toLowerCase()) {
            case "chrome" -> createChromeDriver(isHeadless);
            case "firefox" -> createFirefoxDriver(isHeadless);
            case "msedge" -> createEdgeDriver(isHeadless);
            default -> createChromeDriver(isHeadless); // Default to Chrome
        };

        // Set window size or maximize
        if (!isHeadless) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(screenSize.width, screenSize.height));
        } else {
            driver.manage().window().maximize();
        }

        // Set geolocation (using Chrome as an example; similar for other browsers)
        if (browserName.equalsIgnoreCase("chrome")) {
            setGeolocation();
        }

        // Navigate to the target URL
        driver.get(Constant.ADMIN_URL);

        log(browserName + " browser is opened.");
    }

    private WebDriver createChromeDriver(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless"); // Use the --headless argument instead
        }
        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless) {
            options.addArguments("--headless"); // Use --headless as an argument
        }
        return new FirefoxDriver(options);
    }

    private WebDriver createEdgeDriver(boolean isHeadless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(isHeadless ? "--headless" : "--start-maximized");
        return new EdgeDriver(options);
    }

    private void setGeolocation() {
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", 23.0244537);
        coordinates.put("longitude", 72.5587647);
        coordinates.put("accuracy", 100);

        ((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
    }

    private boolean getIsHeadlessBrowser() {
        String headLessBrowserFromCommandLine = System.getProperty("headLessBrowser");
        if (currUser.contains("jenkins") || (headLessBrowserFromCommandLine != null && headLessBrowserFromCommandLine.equals("true"))) {
            return true;
        } else {
            return false;
        }
    }

    private void log(String message) {
        // Replace with your logging mechanism
        System.out.println(message);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void setScenarioLogger(Scenario scenario) {
        set(SCENARIO, scenario);
    }

    public Scenario getScenarioLogger() {
        return get(SCENARIO, Scenario.class);
    }

    public WebDriver getBrowserPage() {
        return (WebDriver) testContextMap().get("DRIVER");
    }

    public void reset() {
        testContextMap().clear();
    }


    public void setResponse(Response response) {
        set(RESPONSE, response);
    }

    public Response getResponse() {
        return get(RESPONSE, Response.class);
    }

    public void setDivisionType(String divisionType) {
        set(DIVISION_TYPE, divisionType);
    }

    public String getDivisionType() {
        return (String) get(DIVISION_TYPE);
    }

    public void setDbConnection(Connection dbConnection) {
        set(DB_CONNECTION, dbConnection);
    }

    public Connection getDbConnection() {
        return (Connection) testContextMap().get(DB_CONNECTION);
    }

    public void setSoftAssertions(SoftAssertions softAssertions) {
        set(SOFT_ASSERTION, softAssertions);
    }

    public SoftAssertions getSoftAssertion() {
        return (SoftAssertions) testContextMap().get(SOFT_ASSERTION);
    }

    public boolean getIsHeadLessBrowser() {
        String headLessBrowserFromCommandLine = System.getProperty("headLessBrowser");
        if (currUser.contains("jenkins") || (headLessBrowserFromCommandLine != null && headLessBrowserFromCommandLine.equals("true"))) {
            return true;
        } else {
            return false;
        }
    }
}