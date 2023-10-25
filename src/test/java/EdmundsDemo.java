import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class EdmundsDemo {

    public static void main(String[] args) throws InterruptedException {

        //System.setProperty("webdriver.chrome.driver", "path_to_chromedriver.exe")
        //***STEP 1***
        WebDriver driver = new ChromeDriver();
        try {
        driver.get("https://www.edmunds.com/");

        driver.manage().window().maximize();

        //***STEP 2***
        driver.findElement(By.xpath("//a[@href=\"/inventory/srp.html?inventorytype=used%2Ccpo\"] ")).click();

        //***STEP 3***
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@data-tracking-id=\"inventory_view_filter_search_result\"]")).
                sendKeys(Keys.COMMAND + "a");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@data-tracking-id=\"inventory_view_filter_search_result\"]")).
                sendKeys(Keys.DELETE, "22031", Keys.ENTER);
        Thread.sleep(1000);

        //***STEP 4***
        WebElement checkbox = driver.findElement(By.xpath("(//span[@class='checkbox checkbox-icon size-18 icon-checkbox-unchecked3 text-gray-form-controls'])[2]"));
        checkbox.click();

        //***STEP 5***
        Thread.sleep(1000);
        Select make = new Select(driver.findElement(By.cssSelector("#usurp-make-select")));
        make.selectByVisibleText("Tesla");


        //***STEP 6***
        Thread.sleep(1000);
        String model = new Select(driver.findElement(By.cssSelector("#usurp-model-select"))).getFirstSelectedOption().getText();
        Assert.assertEquals(model, "Add Model");

        Thread.sleep(1000);
        //WebElement minYear = driver.findElement(By.xpath("//input[@id='min-value-input-Year']"));
        //Assert.assertEquals(minYear.getAttribute("2013"), "2013");

        WebElement minYear = driver.findElement(By.xpath("//input[@id='min-value-input-Year']"));
        minYear.sendKeys(Keys.COMMAND + "a", Keys.DELETE);
        Thread.sleep(1000);
        minYear.sendKeys("2012", Keys.ENTER);
        WebElement maxYear = driver.findElement(By.xpath("//input[@id='max-value-input-Year']"));
        maxYear.sendKeys(Keys.COMMAND + "a", Keys.DELETE);
        Thread.sleep(1000);
        maxYear.sendKeys("2023", Keys.ENTER);

        //***STEP 7***
        List<String> expectedModels = List.of("Any Model", "Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");

        Select modelDropdown = new Select(driver.findElement(By.cssSelector("#usurp-model-select")));
        List<WebElement> allModels = modelDropdown.getOptions();
        List<String> actualModels = new ArrayList<>();

        for (WebElement allModel : allModels) {
            actualModels.add(allModel.getText());
        }
        Thread.sleep(2000);
        //Assert.assertEquals(actualModels, expectedModels);

        //***STEP 8***
        modelDropdown.selectByVisibleText("Model 3");
        Thread.sleep(2000);

        //***STEP 9***
        WebElement minYearTsl = driver.findElement(By.cssSelector("#min-value-input-Year"));
        minYearTsl.sendKeys(Keys.COMMAND + "a");
        Thread.sleep(1000);
        minYearTsl.sendKeys(Keys.DELETE, "2020", Keys.ENTER);
        Thread.sleep(2000);

        //***STEP 10***
        List<WebElement> titles = driver.findElements(By.xpath("//div[@class='size-16 font-weight-bold mb-0_5 text-blue-30']")); // getting all title elements from the page and storing them
        for (WebElement title : titles) {
            System.out.println(title.getText());
        }
        /* Assert.assertEquals(titles.size(), "21"); */

        String result = "Tesla Model 3";
        List<Integer> years = new ArrayList<>();
        for (WebElement title : titles) {
            Assert.assertTrue(title.getText().toLowerCase().contains(result.toLowerCase()));
            years.add(Integer.parseInt(title.getText().substring(0, 4)));
        }
        //***STEP 11***
        int minRange = 2020;
        int maxRange = 2023;
        for (Integer year : years) {
            Assert.assertTrue(year >= minRange && year <= maxRange);
        }

            //***STEP 12***
            Select sortBy = new Select(driver.findElement(By.cssSelector("#sort-by")));
            sortBy.selectByVisibleText("Price: Low to High");
            Thread.sleep(2000);
            List<WebElement> priceElements = driver.findElements(By.xpath("//span[@class='heading-3']")); //storing all price elements

            List<Integer> prices = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                prices.add(Integer.parseInt(priceElement.getText().replaceAll("[$,]", "")));
            }

            final List<Integer> pricesSorted = new ArrayList<>(prices);
            Collections.sort(pricesSorted);

            Assert.assertTrue(prices.equals(pricesSorted));

            //***STEP 13***
            sortBy.selectByVisibleText("Price: High to Low");
            Thread.sleep(3000);

            List<WebElement> priceElements2 = driver.findElements(By.xpath("//span[@class='heading-3']"));

            List<Integer> pricesHighToLow = new ArrayList<>();
            // remove unnecessary chars from price ($ and ,) and store them into a separate arraylist
            for (WebElement priceElement : priceElements2) {
                pricesHighToLow.add(Integer.parseInt(priceElement.getText().replaceAll("[$,]", "")));
            }

            final List<Integer> pricesHighToLowSorted = new ArrayList<>(pricesHighToLow); // create a copy of the arraylist
            Collections.sort(pricesHighToLowSorted, Collections.reverseOrder()); // sort the copy

            Assert.assertTrue(pricesHighToLow.equals(pricesHighToLowSorted)); // verify if original is sorted as its copy

            //***STEP 14***
            sortBy.selectByVisibleText("Mileage: Low to High");
            Thread.sleep(3000);

            List<WebElement> mileages = driver.findElements(By.xpath("//span[@title=\"Car Mileage\"]/following-sibling::span[@class=\"text-cool-gray-30\"]"));
            List<Integer> mileageHighToLow = new ArrayList<>();
            for (WebElement mileage : mileages) {
                mileageHighToLow.add(Integer.parseInt(mileage.getText().replaceAll("[,miles]", "").trim()));
            }
            final List<Integer> mileagesSorted = new ArrayList<>(mileageHighToLow);
            Collections.sort(mileagesSorted);

            Assert.assertTrue(mileageHighToLow.equals(mileagesSorted));

            //***STEP 15***
            int lastIndex = mileages.size();
            String lastTitle = driver.findElement(By.xpath("(//div[@class='size-16 font-weight-bold mb-0_5 text-blue-30'])[" + lastIndex + "]")).getText();
            String lastPrice = driver.findElement(By.xpath("(//span[@class='heading-3'])[" + lastIndex + "]")).getText();
            String lastMileage = driver.findElement(By.xpath("(//span[@title=\"Car Mileage\"]/following-sibling::span[@class=\"text-cool-gray-30\"])[" + lastIndex + "]")).getText();

            driver.findElement(By.xpath("(//img[@data-test='image'])[" + lastIndex + "]")).click();

            //***STEP 16***
            Assert.assertEquals(driver.findElement(By.xpath("//span[@data-testid]")).getText(), lastPrice);
            Assert.assertEquals(driver.findElement(By.xpath("//h1[@class='d-inline-block mb-0 heading-2 mt-0_25']")).getText(), lastTitle);

            //***STEP 17***
            driver.navigate().back();

            WebElement divElement = driver.findElement(By.xpath("(//div[@class='usurp-inventory-card-photo pos-r desktop-photo srp-expanded'])[" + lastIndex + "]"));
            Assert.assertTrue(divElement.findElement(By.xpath("//div[@class='bg-white text-gray-darker']")).isDisplayed());

            //***STEP 18***
        }finally {
                Thread.sleep(5000); // wait 5 secs before quiting
                driver.quit(); //quit the browser


        }




        }
    }





