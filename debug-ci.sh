#!/bin/bash

# GitHub Actions Chrome Debug Script
# Run this to test Chrome setup locally before pushing to GitHub

set -e

echo "=========================================="
echo "🔍 GitHub Actions Chrome Setup Verification"
echo "=========================================="
echo ""

# Check Java
echo "[1] Checking Java..."
java -version
echo ""

# Check if on Linux
if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    echo "[2] Installing Chrome dependencies (Linux detected)..."
    sudo apt-get update -qq

    # Add Google Chrome repository
    echo "[2a] Adding Google Chrome repository..."
    sudo apt-get install -y wget gnupg
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
    sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
    sudo apt-get update -qq

    # Install Google Chrome (not snap)
    echo "[2b] Installing Google Chrome Stable..."
    sudo apt-get install -y google-chrome-stable

    # Install system libraries
    echo "[2c] Installing system libraries..."
    sudo apt-get install -y libgtk-3-0 libxss1 libnss3 libgconf-2-4 libappindicator1 libindicator7 xdg-utils fonts-liberation libappindicator3-1 lsb-release

    echo "[3] Checking Chrome installation..."
    which google-chrome-stable || echo "Google Chrome not found!"
    google-chrome-stable --version
    echo ""
fi

# Check Gradle
echo "[4] Checking Gradle..."
chmod +x gradlew
./gradlew --version
echo ""

# Build
echo "[5] Building project (skipping tests)..."
./gradlew build -DskipTests --no-daemon
echo ""

# Run tests with debug info
echo "[6] Running tests with Chrome debug..."
echo "    HEADLESS=true"
echo "    CHROME_BIN=$(which google-chrome-stable 2>/dev/null || echo 'not found')"
echo ""

HEADLESS=true CHROME_BIN=$(which google-chrome-stable 2>/dev/null || echo '') ./gradlew clean test --info

echo ""
echo "=========================================="
echo "✅ Verification Complete!"
echo "=========================================="
