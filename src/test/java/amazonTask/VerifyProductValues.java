package amazonTask;

import Utilities.ConfigurationReader;
import Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class VerifyProductValues {

    WebDriver driver;

    @BeforeMethod
    public void setupMethod(){
        driver = Driver.getDriver();

        //1. go to https://www.amazon.com.tr/
        driver.get(ConfigurationReader.getProperty("url"));
    }


    @AfterMethod
    public void tearDownMethod(){
        Driver.closeDriver();
    }



    @Test
    public void verify_product_values_test(){

        //2. click accept cookies button
        WebElement acceptCookiesButton = driver.findElement(By.cssSelector("input#sp-cc-accept"));
        acceptCookiesButton.click();

        //3. send keys to search box and press ENTER to see results
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("iPhone13 512" + Keys.ENTER);

        //4. verify that the results are listed
        List<WebElement> allResults = driver.findElements(By.xpath("//img[@class='s-image']"));
        System.out.println("Amount of listed results: " + allResults.size());

        //5. click iPhone 13 at the top of the page
        WebElement item = driver.findElement(By.xpath("(//img[@class='s-image'])[1]"));
        item.click();

        //6. find product title
        WebElement productName = driver.findElement(By.cssSelector("span#productTitle"));
        String productNameText = productName.getText();

        //7. find size information
        WebElement size = driver.findElement(By.xpath("//span[.='512 GB']"));
        String sizeText = size.getText();

        //8. find price
        WebElement price = driver.findElement(By.xpath("(//div[@id='corePriceDisplay_desktop_feature_div']//span//following-sibling::*)"));
        String priceText = price.getText().replaceAll("\n",",");

        //9. find color
        WebElement color = driver.findElement(By.xpath("//span[.='Yıldız Işığı']"));
        String colorText = color.getText();

        //10. find stock
        WebElement stockStatus = driver.findElement(By.xpath("//span[.='    Stokta var.   ']"));
        String stockStatusText = stockStatus.getText();

        //11. verifying all values
        Assert.assertTrue(productNameText.contains(ConfigurationReader.getProperty("expectedName")));
        Assert.assertEquals(sizeText,ConfigurationReader.getProperty("expectedSize"));
        Assert.assertEquals(priceText,ConfigurationReader.getProperty("expectedPrice"));
        Assert.assertEquals(colorText,"Yıldız Işığı");
        Assert.assertEquals(stockStatusText,ConfigurationReader.getProperty("expectedStockStatus"));


    }










}
