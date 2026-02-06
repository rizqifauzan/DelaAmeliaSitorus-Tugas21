package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Check for Chrome binary in environment or system paths
        String chromeBin = System.getenv("CHROME_BIN");
        if (chromeBin == null || chromeBin.isEmpty()) {
            // Try common Chrome paths in order of preference
            String[] possiblePaths = {
                "/usr/bin/google-chrome-stable",     // Google Chrome (GitHub Actions preferred)
                "/usr/bin/google-chrome",             // Google Chrome (alternative)
                "/usr/bin/chromium-browser",          // Chromium (fallback)
                "/usr/bin/chromium",                  // Chromium (alternative)
                "/snap/bin/chromium",                 // Snap package (last resort)
                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",  // macOS
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",     // Windows
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe" // Windows 32-bit
            };

            for (String path : possiblePaths) {
                java.nio.file.Path p = java.nio.file.Paths.get(path);
                if (java.nio.file.Files.exists(p)) {
                    chromeBin = path;
                    System.out.println("[Hooks] Found Chrome at: " + path);
                    break;
                }
            }
        }

        // Set Chrome binary if found
        if (chromeBin != null && !chromeBin.isEmpty()) {
            options.setBinary(chromeBin);
            System.out.println("[Hooks] Using Chrome binary: " + chromeBin);
        } else {
            // Fallback to WebDriverManager if no binary found
            System.out.println("[Hooks] Chrome binary not found, using WebDriverManager");
            WebDriverManager.chromedriver().setup();
        }

        // Enable headless mode for CI/CD environments
        String isHeadless = System.getenv("HEADLESS");
        if (isHeadless != null && isHeadless.equals("true")) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            System.out.println("[Hooks] Headless mode enabled");
        }

        // Linux/Docker specific arguments
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-web-resources");
        options.addArguments("--disable-extensions");

        // Disable browser features that require system resources
        options.addArguments("--disable-features=TranslateUI");
        options.addArguments("--disable-default-apps");

        // Performance and stability
        options.addArguments("--single-process");
        options.addArguments("--disable-background-networking");
        options.addArguments("--disable-client-side-phishing-detection");
        options.addArguments("--disable-component-extensions-with-background-pages");
        options.addArguments("--disable-component-update");
        options.addArguments("--disable-hang-monitor");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-prompt-on-repost");
        options.addArguments("--disable-sync");
        options.addArguments("--enable-automation");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Disable viewport constraints
        options.addArguments("--start-maximized");
        options.addArguments("--window-size=1920,1080");

        // Additional stability flags
        options.setAcceptInsecureCerts(true);
        options.addArguments("--disable-logging");
        options.addArguments("--disable-breakpad");

        // Create driver with retry logic
        try {
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
            System.out.println("[Hooks] ChromeDriver initialized successfully");
        } catch (Exception e) {
            System.err.println("[Hooks] Error creating ChromeDriver: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
