import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExampleTests {

    WebDriver driver;

    @BeforeEach
    public void driverSetup()
    {
        System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterEach
    public void driverQuit()
    {
        driver.quit();
    }

    //Sprawdzenie czy po załadowaniu strony wartość domyślna w polu "Chcę" wynosi 5 000,00
    @Test
    public void defaultAmountOutValue()
    {
        //Przejście na stronę
        driver.navigate().to("https://cinkciarz.pl/wymiana-walut/kalkulator-walutowy");

        //Zaczekanie 10 sekund, aż okno z cookies będzie widoczne
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div/button[1]")));

        //Akceptacja wymaganych cookies
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div/button[1]")).click();

        //Oczekiwana wartość pola "Chcę"
        String ExpectedDefaultAmountOut = "5 000,00";

        //Znalezienie pola "Chcę" i przypisanie wartości pola do zmiennej value
        WebElement ActualDefaultAmountOut = driver.findElement(By.name("amount-out"));
        String value = ActualDefaultAmountOut.getAttribute("value");

        //Porównanie wartości oczekiwanej z aktualną
        Assertions.assertEquals(ExpectedDefaultAmountOut,value, "Domyślna wartość nie jest poprawna");

    }

    //Przeliczenie walut
    @Test
    public void gbpTopln()
    {
        //Przejście na stronę
        driver.navigate().to("https://cinkciarz.pl/wymiana-walut/kalkulator-walutowy");

        //Zaczekanie 10 sekund, aż okno z cookies będzie widoczne
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div/button[1]")));

        //Akceptacja wymaganych cookies
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div/div[1]/div/button[1]")).click();

        //Refresh, ponieważ był problem z oknem cookies
        driver.navigate().refresh();

        //Wybranie waluty wejściowej
        WebElement currencyIn = driver.findElement(By.className("filter-option-inner-inner"));
        currencyIn.click();

        //Wyszukanie waluty GBP i wybranie jej
        WebElement searchCurrencyIn = driver.findElement(By.xpath("/html/body/main/div[2]/section[1]/div/div/div[2]/div/div/form/div[2]/div[1]/div[1]/div/div/div[2]/input"));
        String currencyInName = "GBP";
        searchCurrencyIn.sendKeys(currencyInName);
        driver.findElement(By.cssSelector("[id=\"bs-select-1-4\"]")).click();

        //Wpisanie wartości jaką się posiada
        WebElement amountIn = driver.findElement(By.name("amount-in"));
        amountIn.click();
        String amountInValue = "1000";
        amountIn.sendKeys(amountInValue);

        //Wybranie waluty wyjściowej
        WebElement currencyOut = driver.findElement(By.cssSelector("[data-id=\"currency-out\"]"));
        currencyOut.click();

        //Wyszukanie waluty PLN i wybranie jej
        WebElement searchCurrencyOut = driver.findElement(By.xpath("/html/body/main/div[2]/section[1]/div/div/div[2]/div/div/form/div[2]/div[1]/div[2]/div/div/div[2]/input"));
        String currencyOutName = "PLN";
        searchCurrencyOut.sendKeys(currencyOutName);
        driver.findElement(By.cssSelector("[id=\"bs-select-2-1\"]")).click();

        //Wyszukanie i wybranie przycisku "Sprawdź kurs"
        WebElement checkBtn = driver.findElement(By.cssSelector("[data-button=\"check\"]"));
        checkBtn.click();

    }
}
