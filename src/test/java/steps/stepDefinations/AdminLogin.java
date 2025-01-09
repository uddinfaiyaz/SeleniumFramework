package steps.stepDefinations;


import io.cucumber.java.en.And;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import steps.cucumber.AbstractSteps;
import utility.Constant;

import javax.swing.*;


public class AdminLogin extends AbstractSteps {
    private final WebDriver driver = testContext().getDriver();

    private final static String usernameLocator = "username";
    private final static String otpLocator = "password";
    private final static String closePopLocator = "(//p[@title='Close'])";
    private final static String dataPopUpLocator = "//div[@role='document']/div/div/*[name()='svg']";

    @And("User enters username as {string}")
    public void userEntersUsernameAs(String username) {
        driver.findElement(By.id(usernameLocator)).sendKeys(username);
    }

    @And("User generates and enters OTP for")
    public void userGeneratesAndEntersOtpGor() throws InterruptedException {
        WebElement element= driver.findElement(By.xpath("//button[contains(text(),'Generate OTP')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.xpath("//button[contains(text(),'Generate OTP')]")).click();
        Thread.sleep(2000);
        Alert alert=driver.switchTo().alert();
        alert.accept();

        Thread.sleep(2000);
   driver.findElement(By.id(otpLocator)).sendKeys(Constant.STATIC_OTP);
    }

    @And("User clicks on sign in button")
    public void userClicksOnLoginButton() throws InterruptedException {
        driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();

        Thread.sleep(5000);




    }
}
