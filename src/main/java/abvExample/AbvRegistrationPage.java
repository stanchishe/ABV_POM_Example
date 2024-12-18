package abvExample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class AbvRegistrationPage extends LoadableComponent<AbvRegistrationPage> {
    private static final String regPageUrl = "https://passport.abv.bg/app/profiles/registration";
    private static final String pageTitleString = "Създаване на АБВ Профил";
    private WebDriver webDriver;
    @FindBy(id = "regformUsername")
    WebElement userName;
    @FindBy(id = "password")
    WebElement password;
    @FindBy(id = "password2")
    WebElement passwordConfirmation;
    @FindBy(id = "mobilePhone")
    WebElement phoneNumber;
    @FindBy(id = "fname")
    WebElement firstName;
    @FindBy(id = "lname")
    WebElement lastName;
    @FindBy(xpath = "//*[@id=\"regform\"]/div[9]/div/label[1]")
    WebElement maleRadioButton;
    @FindBy(xpath = "//*[@id=\"regform\"]/div[9]/div/label[2]")
    WebElement femaleRadioButton;
    @FindBy(xpath = "//*[@id=\"bDay\"]/span")
    WebElement dayOfBirth;
    @FindBy(xpath = "//*[@id=\"bMonth\"]/span")
    WebElement monthOfBirth;
    @FindBy(xpath = "//*[@id=\"bYear\"]/span")
    WebElement yearOfBirth;
    @FindBy(xpath = "//*[@id=\"regform\"]/div[11]/div/input")
    WebElement forwardButton;

    public AbvRegistrationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @Override
    protected void load() {
        System.out.println("Loading the Registration Page...");
        webDriver.get(regPageUrl);
    }

    @Override
    protected void isLoaded() throws Error {
        WebElement pageTitle = webDriver.findElement(By.xpath("/html/body/article/div/div[2]/h2"));
        if(!pageTitle.getText().equals(pageTitleString)) {
            throw new Error("The page was not loaded  correctly");
        }
    }

    public void setUserName(String userId) {
        userName.sendKeys(userId);
    }

    public void setPassword(String passwordString) {
        password.sendKeys(passwordString);
        // fill in both passwords to avoid mistakes.
        // passwordConfirmation.sendKeys(passwordString);
    }

    public void setPasswordConfirmation (String passwordString) {
        passwordConfirmation.sendKeys(passwordString);
    }

    public void setPhoneNumber (String phoneNumString) {
        phoneNumber.sendKeys(phoneNumString);
    }

    public void setFirstName (String firstNameString) {
        firstName.sendKeys(firstNameString);
    }

    public void setLastName (String surname) {
        lastName.sendKeys(surname);
    }

    public void selectGender (String userGender) {
        if (userGender.equalsIgnoreCase("Male")) {
            maleRadioButton.click();
        } else if (userGender.equalsIgnoreCase("Female")) {
            femaleRadioButton.click();
        } else {
            throw new RuntimeException("Invalid gender was entered!");
        }
    }

    public void setDayOfBirth (int dayOfTheMonth) {
        if (dayOfTheMonth > 31 || dayOfTheMonth < 1) {
            throw new RuntimeException("Invalid day of birth selected");
        }
        dayOfBirth.click();
        List<WebElement> options = webDriver.findElements(By.xpath("//*[@id=\"bDay\"]/ul/li"));

        for (WebElement option : options) {
            if (option.getText().equals(String.valueOf(dayOfTheMonth))) {
                option.click();
                break;
            }
        }
    }


    public void setMontOfBirth (int monthOfBirthInt) {
        if (monthOfBirthInt > 12 || monthOfBirthInt < 1) {
            throw new RuntimeException("Invalid month of birth selected");
        }
        monthOfBirth.click();
        List<WebElement> options = webDriver.findElements(By.xpath("//*[@id=\"bMonth\"]/ul/li"));

        options.get(monthOfBirthInt - 1).click();
    }

    public void setYearOfBirth (int yearOfBirthInt) {
        if (yearOfBirthInt > 2024 || yearOfBirthInt < 1801) {
            throw new RuntimeException("Invalid year of birth selected");
        }
        yearOfBirth.click();
        List<WebElement> options = webDriver.findElements(By.xpath("//*[@id=\"bYear\"]/ul/li"));

        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(String.valueOf(yearOfBirthInt))) {
                option.click();
                break;
            }
        }
    }

    public void registerUser() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(forwardButton));
        } catch (Exception e) {
            throw  new RuntimeException("The Registration button does not work!");
        }
        forwardButton.click();
    }
}
