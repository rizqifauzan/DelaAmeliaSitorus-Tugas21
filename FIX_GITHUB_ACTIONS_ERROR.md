# GitHub Actions Chrome Error Fix 🔧

## Problem
```
org.openqa.selenium.SessionNotCreatedException: Could not start a new session.
Message: session not created: Chrome instance exited.
```

## Root Cause
Chrome pada GitHub Actions Linux environment membutuhkan:
1. System dependencies (fonts, libraries)
2. Chrome binary yang sudah terinstall
3. ChromeDriver yang kompatibel
4. Chrome flags yang tepat untuk Linux/Docker environment

## Solutions Applied

### 1. **Hooks.java - Enhanced Chrome Detection & Configuration**
```java
✅ Auto-detect Chrome binary dari system paths
✅ Support untuk Linux, macOS, dan Windows paths
✅ Fallback ke WebDriverManager jika binary tidak ditemukan
✅ Tambah 20+ Chrome flags untuk stability
✅ Set implicit wait untuk element handling
✅ Disable automation detection
```

**Key Improvements:**
- `--headless=new` - Chrome 116+ headless mode
- `--single-process` - Better stability di CI/CD
- `--disable-blink-features=AutomationControlled` - Hide automation
- `--start-maximized` + `--window-size=1920,1080` - Viewport management
- `setAcceptInsecureCerts(true)` - Handle SSL issues

### 2. **GitHub Actions Workflow - System Dependencies**
```bash
✅ apt-get install chromium-browser
✅ apt-get install chromium-codecs-ffmpeg
✅ Install system libraries (libgtk-3-0, libnss3, dll)
✅ Set CHROME_BIN environment variable
```

### 3. **Environment Variables di CI/CD**
```yaml
HEADLESS: 'true'
CHROME_BIN: '/usr/bin/chromium-browser'
```

## Files Modified

### 1. `.github/workflows/main.yml`
- Tambah step: "Install system dependencies for Chrome"
- Set environment variables: `HEADLESS` dan `CHROME_BIN`

### 2. `src/test/java/hooks/Hooks.java`
- Auto-detect Chrome binary path
- Enhanced Chrome options untuk Linux
- Better error handling dan fallback

## Testing Lokal

### Linux/Mac
```bash
export CHROME_BIN=/usr/bin/chromium-browser
export HEADLESS=true
./gradlew clean test
```

### Windows PowerShell
```powershell
$env:HEADLESS='true'
.\gradlew.bat clean test
```

### Docker/Container
```bash
docker run -e HEADLESS=true -e CHROME_BIN=/usr/bin/chromium-browser -v $(pwd):/app ubuntu:latest bash -c "apt-get update && apt-get install -y chromium-browser openjdk-11-jdk && cd /app && ./gradlew clean test"
```

## Debugging Commands

Jika masih ada error, jalankan ini di GitHub Actions:

```bash
# Check Chrome installation
which chromium-browser
chromium-browser --version

# Check ChromeDriver
./gradlew --info test

# Check WebDriverManager cache
ls -la ~/.wdm/

# Test Chrome directly
chromium-browser --headless --no-sandbox --disable-gpu --disable-dev-shm-usage https://example.com
```

## Common Issues & Solutions

### Issue 1: "Chrome instance exited"
**Solution**: Pastikan semua system dependencies terinstall
```bash
sudo apt-get install -y chromium-browser chromium-codecs-ffmpeg libgtk-3-0 libxss1 libnss3 libgconf-2-4
```

### Issue 2: "Could not start a new session"
**Solution**: Pastikan `--no-sandbox` dan `--disable-dev-shm-usage` ada di Chrome options

### Issue 3: "Chrome timeout"
**Solution**: Tambah implicit wait time di Hooks:
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
```

### Issue 4: WebDriverManager slow
**Solution**: Gunakan system Chrome binary dengan auto-detection

## Verification Checklist

✅ System dependencies installed  
✅ Chrome binary detected correctly  
✅ ChromeDriver matches Chrome version  
✅ Headless mode enabled for CI/CD  
✅ All Chrome flags applied  
✅ Implicit wait configured  
✅ Sandbox mode disabled  
✅ GPU disabled for headless  

## Performance Tips

1. **Parallel Test Execution** - Gradle dapat run tests dalam parallel
2. **Caching** - WebDriverManager cache ChromeDriver locally
3. **Single Process** - Flag `--single-process` untuk resource efficiency
4. **Reduced Extensions** - `--disable-extensions` menghemat memory

## References

- [Selenium Chrome Options Documentation](https://chromedriver.chromium.org/capabilities)
- [WebDriverManager GitHub](https://github.com/bonigarcia/webdrivermanager)
- [GitHub Actions - Setup Chrome](https://github.com/browser-actions/setup-chrome)
- [Cucumber Java Documentation](https://cucumber.io/docs/cucumber/reference/)
