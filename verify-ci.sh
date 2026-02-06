#!/bin/bash

# GitHub Actions Ready - CI/CD Pipeline Test Script
# Run this locally to verify everything works before pushing to GitHub

echo "=========================================="
echo "🔍 GitHub Actions Configuration Verification"
echo "=========================================="
echo ""

echo "✓ Checking Java installation..."
java -version

echo ""
echo "✓ Checking Gradle wrapper..."
if [ -f "./gradlew" ]; then
    echo "  ✅ gradlew found"
else
    echo "  ❌ gradlew NOT found"
fi

echo ""
echo "✓ Checking test configuration files..."
if [ -f "src/test/resources/junit-platform.properties" ]; then
    echo "  ✅ junit-platform.properties found"
fi

if [ -f "build.gradle" ]; then
    echo "  ✅ build.gradle found"
fi

echo ""
echo "✓ Checking GitHub Actions workflow..."
if [ -f ".github/workflows/main.yml" ]; then
    echo "  ✅ main.yml found"
fi

echo ""
echo "=========================================="
echo "🧪 Running Tests with Headless Mode"
echo "=========================================="
echo ""
echo "Running: HEADLESS=true ./gradlew clean test"
echo ""

HEADLESS=true ./gradlew clean test

echo ""
echo "=========================================="
echo "✅ Verification Complete!"
echo "=========================================="
echo ""
echo "If all tests passed above, your project is ready for GitHub Actions!"
