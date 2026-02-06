@echo off
REM GitHub Actions Ready - CI/CD Pipeline Test Script (Windows)
REM Run this locally to verify everything works before pushing to GitHub

echo.
echo ==========================================
echo 0x1 GitHub Actions Configuration Verification
echo ==========================================
echo.

echo Checking Java installation...
java -version

echo.
echo Checking Gradle wrapper...
if exist "gradlew.bat" (
    echo   OK: gradlew.bat found
) else (
    echo   ERROR: gradlew.bat NOT found
)

echo.
echo Checking test configuration files...
if exist "src\test\resources\junit-platform.properties" (
    echo   OK: junit-platform.properties found
)

if exist "build.gradle" (
    echo   OK: build.gradle found
)

echo.
echo Checking GitHub Actions workflow...
if exist ".github\workflows\main.yml" (
    echo   OK: main.yml found
)

echo.
echo ==========================================
echo 0x2 Running Tests with Headless Mode
echo ==========================================
echo.
echo Running: gradlew clean test with HEADLESS=true
echo.

setlocal enabledelayedexpansion
set HEADLESS=true
call gradlew.bat clean test

echo.
echo ==========================================
echo Verification Complete!
echo ==========================================
echo.
echo If all tests passed above, your project is ready for GitHub Actions!
echo.
pause
