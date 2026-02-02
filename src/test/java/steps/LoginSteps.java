package steps;

import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    WebDriver driver = Hooks.driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("saya membuka halaman login")
    public void buka_halaman_login() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    @When("saya memasukkan username {string}")
    public void isi_username(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                .sendKeys(username);
    }

    @And("saya memasukkan password {string}")
    public void isi_password(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @And("saya menekan tombol login")
    public void klik_login() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("login berhasil ditampilkan")
    public void login_berhasil() {
        String flash = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        ).getText();

        assertTrue(flash.contains("secure area"));
    }

    @Then("muncul pesan login gagal")
    public void login_gagal() {
        String flash = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        ).getText();

        assertTrue(flash.toLowerCase().contains("invalid"));
    }
}
