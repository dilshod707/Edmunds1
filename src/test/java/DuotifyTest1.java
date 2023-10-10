import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;


public class DuotifyTest1 {

    public static void main(String[] args) {

        //Step 1: Navigate to http://duotify.us-east-2.elasticbeanstalk.com/register.php
        WebDriver driver = new ChromeDriver();
        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        //Step 2: Verify the the title is "Welcome to Duotify!"
        Assert.assertEquals(driver.getTitle(), "Welcome to Duotify!");

        //Step 3:
        WebElement signUpLink = driver.findElement(By.id("hideLogin"));
        signUpLink.click();

        //Step 4
        Faker faker = new Faker();
        String userName = faker.name().username();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        //Step 5
        driver.findElement(By.name("username")).sendKeys(userName);
        driver.findElement(By.name("firstName")).sendKeys(firstName);
        driver.findElement(By.name("lastName")).sendKeys(lastName);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("email2")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("password2")).sendKeys(password);

        //Step 6: Click on SignUp
        driver.findElement(By.name("registerButton")).click();

        //Step 7: Verify URL
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?");

        //Step 8:
        // Assuming userLoggedInScript is the script element containing the logged-in username
        String fullName = (driver.findElement(By.id("nameFirstAndLast"))).getText();

        if (fullName.equals(firstName + " " + lastName)) {
            System.out.println("First and Last name match!");
        } else {
            System.out.println("First and Last name do not match.");
        }


        //Step 9

        WebElement usernameLink = (driver.findElement(By.id("nameFirstAndLast")));
        usernameLink.click();

        String actualUsername = (driver.findElement(By.id("nameFirstAndLast"))).getText();
        if (actualUsername.equals(fullName)) {
            System.out.println("Username is correct!");
        } else {
            System.out.println("Username is incorrect.");
        }
        driver.findElement(By.id("rafael")).click();

        //Step 10: Verify that you are logged out by verifying the URL is
        String currentUrl = driver.getCurrentUrl();
        //Assert.assertEquals(currentUrl, "http://duotify.us-east-2.elasticbeanstalk.com/register.php");
        if (currentUrl.equals("http://duotify.us-east-2.elasticbeanstalk.com/register.php")){
            System.out.println("URL matches!");
        } else{
            System.out.println("URL does not match!");
        }
        //Step 11: Login with same username and password
        driver.findElement(By.name("loginUsername")).sendKeys(userName);
        driver.findElement(By.name("loginPassword")).sendKeys(password);
        driver.findElement(By.name("loginButton")).click();

        //Step 12: Verify successful login
        String pageSource = driver.getPageSource();
        String textToVerify = "You Might Also Like";
        System.out.println(pageSource);

        if (pageSource.contains(textToVerify)) {
            System.out.println("Login successful. Found the text: " + textToVerify);
        } else {
            System.out.println("Login failed. Could not find the text: " + textToVerify);
        }

        //Step 13: Log out and Verify
        WebElement usernameLink2 = (driver.findElement(By.id("nameFirstAndLast")));
        usernameLink2.click();
        driver.findElement(By.id("rafael")).click();


       Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php");

       driver.quit();

       



    }
}
