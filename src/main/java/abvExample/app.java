package abvExample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class app {
    public static void main(String[] args) {
        ChromeOptions chromeOptions = new ChromeOptions().addArguments("Start-Maximized");
        WebDriver chromeDriver = new ChromeDriver(chromeOptions);
        AbvRegistrationPage abvRegistrationPage = new AbvRegistrationPage(chromeDriver);
        abvRegistrationPage.load();

        abvRegistrationPage.setDayOfBirth(2);
        abvRegistrationPage.setMontOfBirth(1);
        abvRegistrationPage.setYearOfBirth(2005);

        try {
            Thread.sleep(10000);
            chromeDriver.close();
            chromeDriver.quit();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
