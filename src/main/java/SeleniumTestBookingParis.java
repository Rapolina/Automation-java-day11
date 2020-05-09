import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class SeleniumTestBookingParis {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver.exe", "chromedriver.exe");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.booking.com/");
        WebElement direction = driver.findElement(By.cssSelector("#ss"));
        direction.sendKeys("Paris");

        WebElement period = driver.findElement(By.xpath("//*[@data-mode='checkin']"));
        period.click();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,3);
        Date threeDays = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,7);
        Date tenDays = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePlusThreeDays = dateFormat.format(threeDays);
        String datePlusTenDays = dateFormat.format(tenDays);
        WebElement checkIn = driver.findElement(By.xpath(String.format("//*[@data-date='%s']",datePlusThreeDays )));
        checkIn.click();
        WebElement checkOut = driver.findElement(By.xpath(String.format("//*[@data-date='%s']", datePlusTenDays)));
        checkOut.click();

        WebElement guestInfo = driver.findElement(By.xpath("//*[@id=\"xp__guests__toggle\"]/span[2]/span[1]"));
        guestInfo.click();
        WebElement adultsNumber = driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[1]/div/div[2]/button[2]/span"));
        adultsNumber.click();
        adultsNumber.click();
        WebElement number0fRooms = driver.findElement(By.xpath("//*[@id=\"xp__guests__inputs-container\"]/div/div/div[3]/div/div[2]/button[2]/span"));
        number0fRooms.click();

        WebElement search = driver.findElement(By.xpath("//*[@id=\"frm\"]/div[1]/div[4]/div[2]/button/span[1]"));
        search.click();
        Thread.sleep(5000);

        WebElement budget = driver.findElement(By.xpath("//*[@id=\"filter_price\"]/div[2]/a[5]/label/div"));
        budget.click();

        String expensiveHotel = budget.getText().replaceAll("[^0-9]+", "");
        System.out.println("The most expensive hotel costs  per night:" +  expensiveHotel);
        int expensiveHotelPerNight = Integer.parseInt(expensiveHotel);
        Thread.sleep(5000);


        WebElement lowBudget = driver.findElement(By.xpath("//*[@id=\"sort_by\"]/ul/li[3]/a"));
        lowBudget.click();

        WebElement MinFromMax = driver.findElement(By.xpath("//*[@data-hotelid][1]//div[contains(@class,'bui-price-display__value prco-inline-block-maker-helper')]"));
        String minPriceFromMax = MinFromMax.getText().replaceAll("[^0-9]+", "");
        int hotelPerNight = Integer.parseInt(minPriceFromMax) / 7;
        System.out.println("Minimum price per night from " + hotelPerNight);



        Assert.assertTrue("Something wrong", hotelPerNight >= expensiveHotelPerNight);
        driver.quit();



    }
}



