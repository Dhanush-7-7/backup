package capstone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Corporatecoverage {
    WebDriver driver;
    BufferedWriter reportWriter;

    @BeforeClass
    public void setup() throws IOException {
        driver = new ChromeDriver();
        driver.get("https://www.acko.com/");
        driver.manage().window().maximize();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = LocalDateTime.now().format(formatter);

        
        FileWriter fileWriter = new FileWriter("/home/zadmin/eclipse-workspace/capstone/Report/automation_report.html");
        reportWriter = new BufferedWriter(fileWriter);

        reportWriter.write("<html><head><title>Acko Automation Report</title></head><body>");
        reportWriter.write("<h1>Acko Website Automation Report</h1>");
        reportWriter.write("<p>Report generated on: " + currentDateTime + "</p>");
        reportWriter.write("<table border='1'><tr><th>Screenshot</th><th>Result</th></tr>");
    }

    @Test
    public void automateAckoWebsite() throws IOException, InterruptedException {
        WebElement productsNavLink = driver.findElement(By.xpath("//span[text()=\"Products\"][1]"));
        productsNavLink.click();
        captureStepReport("productsNavLink", "Clicked on Products Navigation");

        WebElement corporateCoverageOption = driver.findElement(By.xpath("//span[text()='Corporate coverage']"));
        corporateCoverageOption.click();
        captureStepReport("corporateCoverageOption", "Clicked on Corporate Coverage Option");

        WebElement groupHealthInsuranceLink = driver.findElement(By.xpath("//a[contains(text(), \"Group health insurance\")]"));
        groupHealthInsuranceLink.click();
        captureStepReport("groupHealthInsuranceLink", "Clicked on Group Health Insurance");

        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement scheduleDemoButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Schedule a Demo']")));
        scheduleDemoButton.click();
        captureStepReport("scheduleDemoButton", "Clicked on Schedule a Demo");

        takeScreenshot("/home/zadmin/eclipse-workspace/capstone/Report/schedule_demo_page.png");
        reportWriter.write("<tr><td><img src='/home/zadmin/eclipse-workspace/capstone/Report/schedule_demo_page.png' width='200'></td><td>Visited Schedule Demo Page</td></tr>");

        String demoPageTitle = driver.getTitle();
        System.out.println("Demo Page Title: " + demoPageTitle);
        Assert.assertTrue(demoPageTitle.contains("Get Quote for ACKO Group Health Insurance"));

        WebElement startHereButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[span[span[text()='Start']]]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", startHereButton);
        captureStepReport("startHereButton", "Start button is clicked");

        try {
            startHereButton.click();
        } catch (Exception e) {
            System.out.println("Regular click failed, using JavaScript click...");
            js.executeScript("arguments[0].click();", startHereButton);
        }

        takeScreenshot("/home/zadmin/eclipse-workspace/capstone/Report/start_here_page.png");
        reportWriter.write("<tr><td><img src='/home/zadmin/eclipse-workspace/capstone/Report/start_here_page.png' width='200'></td><td>Visited Start Here Page</td></tr>");

        fillForm();

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-qa=\"submit-button deep-purple-submit-button\"]")));
        submitButton.click();

        Thread.sleep(2000);

        takeScreenshot("/home/zadmin/eclipse-workspace/capstone/Report/thank_you_page.png");
        reportWriter.write("<tr><td><img src='/home/zadmin/eclipse-workspace/capstone/Report/thank_you_page.png' width='200'></td><td>Visited Thank You Page</td></tr>");

        driver.get("https://www.acko.com/");
        captureStepReport("homePageRedirect", "Redirected to Home Page using URL");

        reportWriter.write("</table></body></html>");
        reportWriter.close();
    }

    private void fillForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='Enter your first name']")));
        firstNameField.sendKeys("praveen");

        WebElement lastNameField = driver.findElement(By.xpath("//input[@name='family-name']"));
        lastNameField.sendKeys("r");

        WebElement phoneNumberField = driver.findElement(By.xpath("//input[@data-qa='phone-number-input']"));
        phoneNumberField.sendKeys("9876543210");

        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys("praveen@gmail.com");

        WebElement companyField = driver.findElement(By.xpath("//input[@name='organization']"));
        companyField.sendKeys("dk groups");

        WebElement okButton = driver.findElement(By.xpath("//button[@data-qa='ok-button-visible deep-purple-ok-button-visible']"));
        okButton.click();

        WebElement industryDropdownInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type or select an option']")));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", industryDropdownInput);

        WebElement industryOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Information Technology (IT)/ITES']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", industryOption);
        industryOption.click();

        WebElement cityDropdownInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@placeholder=\"Type or select an option\"])[2]")));
        jsExecutor.executeScript("arguments[0].click();", cityDropdownInput);

        WebElement cityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()=\"Bengaluru\"]")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", cityOption);
        cityOption.click();

        WebElement yesOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Yes']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", yesOption);
        yesOption.click();

        WebElement employeeStrengthOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='7 - 50']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", employeeStrengthOption);
        employeeStrengthOption.click();

        WebElement insuranceCoverageOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Employees + Spouse + Children']")));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", insuranceCoverageOption);
        insuranceCoverageOption.click();
    }

    private void captureStepReport(String screenshotName, String result) throws IOException {
        String screenshotPath = "/home/zadmin/eclipse-workspace/capstone/Report/" + screenshotName + ".png";
        takeScreenshot(screenshotPath);
        reportWriter.write("<tr><td><img src='" + screenshotPath + "' width='200'></td><td>" + result + "</td></tr>");
    }

    private void takeScreenshot(String filePath) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destinationPath = Path.of(filePath);
        Files.copy(screenshot.toPath(), destinationPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Screenshot saved at: " + filePath);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}
