package StepDefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverClass {
    public static WebDriver driver;
    public static WebDriver setup(){
        if(driver==null){
            System.setProperty("webdriver.chromedriver", "C:\\Users\\Shivam.Roy\\Downloads\\chromedriver_win32\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver=new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;

    }
}
