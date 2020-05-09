import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SeleniumTestBookingMoscow {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver.exe", "chromedriver.exe");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.booking.com/");
        WebElement direction = driver.findElement(By.cssSelector("#ss"));
        direction.sendKeys("Moscow");

        WebElement period = driver.findElement(By.xpath("//*[@data-mode='checkin']"));
        period.click();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 10);
        Date tenDays = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        Date fifteenDays = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePlusTenDays = dateFormat.format(tenDays);
        String datePlusFifteenDays = dateFormat.format(fifteenDays);
        WebElement checkIn = driver.findElement(By.xpath(String.format("//*[@data-date='%s']", datePlusTenDays)));
        checkIn.click();
        WebElement checkOut = driver.findElement(By.xpath(String.format("//*[@data-date='%s']", datePlusFifteenDays)));
        checkOut.click();

        WebElement search = driver.findElement(By.xpath("//*[@class='sb-searchbox__button ']"));
        search.click();
        Thread.sleep(5000);

        Actions builder = new Actions(driver);
        WebElement adults = driver.findElement(By.cssSelector("#group_adults"));
        builder.click(adults).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        WebElement rooms = driver.findElement(By.cssSelector("#no_rooms"));
        builder.click(rooms).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        WebElement newSearch = driver.findElement(By.xpath("//*[@class='sb-searchbox__button ']"));
        newSearch.click();
        Thread.sleep(5000);

        WebElement minBudgetHotel = driver.findElement(By.xpath("//*[@data-id='pri-1']"));
        minBudgetHotel.click();
        int minBudget = Integer.parseInt(minBudgetHotel.getText().replaceAll("[^0-9]+", ""));
        System.out.println("Budget per night is " + minBudget );

        WebElement firstHotel = driver.findElement(By.xpath("//*[@data-hotelid][1]//div[contains(@class,'bui-price-display__value prco-inline-block-maker-helper')]"));
        String  firstHotelPrice = firstHotel.getText().replaceAll("[^0-9]+", "");
        int HotelPricePerNight = Integer.parseInt(firstHotelPrice)/5;
        System.out.println("Price at the hotel per night: " + HotelPricePerNight);

        Assert.assertTrue("Something wrong", HotelPricePerNight <= minBudget);
        driver.quit();

    }
}
