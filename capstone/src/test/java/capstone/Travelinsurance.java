package capstone;


import org.openqa.selenium.By;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class Travelinsurance {

    @Test
    public void testNavigation() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.acko.com/");
        driver.manage().window().maximize();

        WebElement productsElement = driver.findElement(By.xpath("//span[text()='Products']"));
        productsElement.click();  

        WebElement travelElement = driver.findElement(By.xpath("//span[text()='Travel']"));
        travelElement.click();  
        WebElement travelInsuranceElement = driver.findElement(By.xpath("//a[text()='Travel insurance']"));
        travelInsuranceElement.click(); 

        Set<String> windowHandles = driver.getWindowHandles();
        String currentWindowHandle = driver.getWindowHandle();

        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(currentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        WebElement whereInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search country(s)']")));
        whereInputElement.click(); 

        WebElement australiaElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Australia']")));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", australiaElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", australiaElement);

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -200);"); // Scroll up by 200 pixels

        WebElement doneElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Done']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", doneElement);
        doneElement.click();

        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement whenElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='sc-kbdmFF jJmzMt'])[1]")));
        whenElement.click(); 

        WebElement firstDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[text()='26'])[2]")));
        firstDateElement.click();

        WebElement secondDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[text()='26'])[4]")));
        secondDateElement.click();

        // driver.quit(); 
    }
}
