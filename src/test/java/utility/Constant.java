package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Constant {
    public static String defaultBrowser = "chrome";
    public static String defaultTestEnv = "ppe";
    public static String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : defaultBrowser;
    public static final String STATIC_OTP = "9999";
    public static String testEnv;
    public static String ADMIN_URL;
    public static String OTP_API_BASE_URL;
    public static Properties TestDataProperties;
    public static String DB_HOST_URL;
    public static String DB_USERNAME;
    public static String DB_PASSWORD;
    public static String DB_NAME;



    public static Path getImagePath(String imageName) {
        String osName = System.getProperty("os.name");
        if (osName.contains("windows")) {
            return Paths.get("src\\test\\resources\\images\\" + imageName);
        } else {
            return Paths.get("src//test//resources//images//" + imageName);
        }
    }

    private static String getTestEnv() {
        String testPropertyValue = System.getProperty("testEnv");
        String testEnvValue = System.getenv("testEnv");

        // First priority will be from the command line then environment value
        if (testPropertyValue == null && testEnvValue == null) {
            return defaultTestEnv;
        } else if (testPropertyValue != null) {
            return testPropertyValue;
        } else if (testEnvValue != null) {
            return testEnvValue;
        } else {
            return defaultTestEnv;
        }
    }

    public static void setUpTestEnvData() throws IOException {
        testEnv = getTestEnv();
        String folderPath = "src/test/resources/config/";
        TestDataProperties = new Properties();
        File envFile = new File(folderPath + testEnv + ".properties");
        FileInputStream fileInputStream = new FileInputStream(envFile);
        TestDataProperties.load(fileInputStream);
        fileInputStream.close();

        OTP_API_BASE_URL = TestDataProperties.getProperty("otp_api_url");
        ADMIN_URL = TestDataProperties.getProperty("admin_url");

        DB_HOST_URL = TestDataProperties.getProperty("db_host_url");
        DB_USERNAME = TestDataProperties.getProperty("db_username");
        DB_PASSWORD = TestDataProperties.getProperty("db_password");
        DB_NAME = TestDataProperties.getProperty("db_name");
    }
}
