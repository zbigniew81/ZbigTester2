import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BettingTest extends BaseTestCase {

    @Test
    public void bettingTest() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Zbyszek\\Downloads\\geckodriver-v0.18.0-win64\\geckodriver.exe");

        String betValue = "0.05";
        double betValueAsDouble = Double.parseDouble(betValue);
        String returnValue;
        double returnValueAsDouble;

        WebDriver driver = new FirefoxDriver();

        // Step1
        logger.info("Step 01: Open WilliamHill betting page.");
        driver.get("http://sports.williamhill.com/betting/en-gb");
        Thread.sleep(10000);

        // Step 2 - logger
        logger.info("Step 02: Open football category");
        driver.findElement(By.xpath("//*[@id='nav-football']/a")).click();
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//section[contains(@class,'btmarket__wrapper')]/div[@class='event'])[1]")));
        Thread.sleep(10000);

        // Step 3
        String dataOdds = driver.findElement(By.xpath("//section[contains(@class,'btmarket__wrapper')]/div[@class='event'][1]/div[2]/div[2]/div[1]/button")).getAttribute("data-odds").toString();
        driver.findElement(By.xpath("//section[contains(@class,'btmarket__wrapper')]/div[@class='event'][1]/div[2]/div[2]/div[1]/button")).click();
        Thread.sleep(10000);

        // Step 4

        driver.findElement(By.xpath("//input[contains(@id,'stake-input')]")).sendKeys(betValue);
        Thread.sleep(5000);

        String odds = driver.findElement(By.xpath("//selection-price")).getText().trim();
        double oddsAsDouble = parseRatio(odds);
        double bettingRate = oddsAsDouble + 1;
        returnValueAsDouble = bettingRate * betValueAsDouble;
        System.out.println(returnValueAsDouble);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        returnValue = df.format(returnValueAsDouble).replace(",", ".");
        System.out.println(returnValue);

        Assert.assertEquals(dataOdds, driver.findElement(By.xpath("//selection-price")).getText().trim());
        Assert.assertEquals(returnValue, driver.findElement(By.xpath("//*[@id='total-to-return-price']")).getText());

    }

    private double parseRatio(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        } else {
            return Double.parseDouble(ratio);
        }
    }
}
