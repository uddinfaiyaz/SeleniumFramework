package steps.cucumber;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utility.Constant;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks extends AbstractSteps {


    @Before(order = 1)
    public void setUp(Scenario scenario) throws IOException {
        SoftAssertions softAssertions = new SoftAssertions();
        testContext().setSoftAssertions(softAssertions);
        testContext().setScenarioLogger(scenario);
        testContext().getScenarioLogger().log("SETUP SCENARIO: " + scenario.getName());
        Constant.setUpTestEnvData();
        testContext().getScenarioLogger().log("testEnv = " + Constant.testEnv);
        testContext().openBrowser();
    }



    @After()
    public void tearDown(Scenario scenario) throws IOException {
        testContext().getScenarioLogger().log("GENERIC TEARDOWN: " + scenario.getName());
      WebDriver driver=  testContext().getDriver();


        if (scenario.isFailed()) {
//            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
//                    .setPath(Paths.get("target/tmp", "screenshot.png")).setFullPage(true));
//            scenario.attach(screenshot, "image/png", "Screenshot");
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Save the screenshot to a file
            Path screenshotPath = Paths.get("target/tmp", "screenshot.png");
            Files.createDirectories(screenshotPath.getParent());
            Files.write(screenshotPath, screenshot);

            // Attach the screenshot to the Cucumber report
            scenario.attach(screenshot, "image/png", "Screenshot");
        }

        SoftAssertions softAssertions = testContext().getSoftAssertion();
        softAssertions.assertAll();
        StringBuilder assertionErrors = new StringBuilder();
        softAssertions.errorsCollected().forEach(error -> assertionErrors.append(error.toString()).append("\n"));

        // Log or report assertion errors
        if (!assertionErrors.isEmpty()) {
            testContext().getScenarioLogger().log("Soft assertion errors:\n" + assertionErrors);
            // You can also log assertion errors to a logger or any other reporting mechanism
        }
        driver.close();
        testContext().closeBrowser();
        testContext().reset();
    }

}
