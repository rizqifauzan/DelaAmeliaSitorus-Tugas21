# GitHub Actions Workflow Documentation

## CI/CD Pipeline Configuration

### Workflow: Run JUnit CI
- **File**: `main.yml`
- **Trigger**: Push to `main` branch and Pull Requests to `main` branch
- **Environment**: Ubuntu Latest (Linux)

### Workflow Steps:
1. **Checkout Code** - Clones the repository
2. **Setup JDK 11** - Installs Java 11 (Temurin distribution)
3. **Setup Chrome** - Installs the latest Chrome browser for Selenium tests
4. **Build with Gradle** - Compiles the project (skips tests during build)
5. **Run Tests** - Executes Cucumber BDD tests with headless Chrome
6. **Archive Test Results** - Saves test reports and results as artifacts
7. **Generate Summary** - Creates a summary of test results in GitHub Step Summary

### Environment Variables:
- `HEADLESS`: Set to `true` to run Chrome in headless mode (automatically set in CI)

### Test Reports:
- **Cucumber Reports**: `build/reports/tests/test/`
- **Test Results XML**: `build/test-results/test/`
- **Retention Period**: 30 days

### Local Testing:
To run tests locally with headless Chrome:
```bash
HEADLESS=true ./gradlew clean test
```

Or with GUI:
```bash
./gradlew clean test
```

### Requirements:
- Java 11+
- Chrome browser
- Gradle 7.0+
