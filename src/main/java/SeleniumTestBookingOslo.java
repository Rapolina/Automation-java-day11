import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class SeleniumTestBookingOslo {

    public static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver.exe", "chromedriver.exe");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.booking.com/");
        WebElement direction = driver.findElement(By.cssSelector("#ss"));
        direction.sendKeys("Oslo");

        WebElement period = driver.findElement(By.xpath("//*[@data-mode='checkin']"));
        period.click();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date oneDay = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date twoDays = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePlusThreeDays = dateFormat.format(oneDay);
        String datePlusTenDays = dateFormat.format(twoDays);
        WebElement checkIn = driver.findElement(By.xpath(String.format("//*[@data-date='%s']", datePlusThreeDays)));
        checkIn.click();
        WebElement checkOut = driver.findElement(By.xpath(String.format("//*[@data-date='%s']", datePlusTenDays)));
        checkOut.click();

        WebElement guestInfo = driver.findElement(By.xpath("//*[@id=\"xp__guests__toggle\"]"));
        guestInfo.click();

        WebElement children = driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[2]/div/div[2]/button[2]"));
        children.click();

        WebElement search = driver.findElement(By.xpath("//*[@class='sb-searchbox__button ']"));
        search.click();
        Thread.sleep(5000);

        WebElement threeStars = driver.findElement(By.xpath("//*[@id=\"filter_class\"]/div[2]/a[1]/label/div"));
        threeStars.click();
        WebElement fourStars = driver.findElement(By.xpath("//*[@id=\"filter_class\"]/div[2]/a[2]/label/div"));
        fourStars.click();


        Actions builder = new Actions(driver);
        WebElement topTenHotel = driver.findElement(By.xpath("//*[@data-hotelid][10]"));
        builder.moveToElement(topTenHotel).perform();
        Thread.sleep(5000);

        WebElement topTenHotelName = driver.findElement(By.xpath("//*[@data-hotelid][10]//span[contains(@class,'sr-hotel__name')]"));
        builder.moveToElement(topTenHotelName).perform();
        Thread.sleep(5000);



        WebElement backgoundHotel = driver.findElement(By.xpath("//*[@data-hotelid][10]"));
        builder.moveToElement(backgoundHotel).perform();
        BackgroundIsGreen(backgoundHotel);
        Thread.sleep(3000);

        WebElement backgoundHotelName = driver.findElement(By.xpath("//*[@data-hotelid][10]//span[contains(@class,'sr-hotel__name')]"));
        builder.moveToElement(backgoundHotelName).perform();
        TextIsRed (backgoundHotelName);
        Thread.sleep(3000);

        Assert.assertEquals("Something wrong", "color: red;", backgoundHotelName.getAttribute("style"));

    }

    public static void BackgroundIsGreen(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style','background: green;');", element);
    }

    public static void TextIsRed(WebElement text) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style','color: red;');", text);
    }

}



