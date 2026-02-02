# UI Automation Testing Framework

Framework ini dibuat untuk melakukan pengujian otomatis tampilan dan fungsi website
menggunakan Selenium WebDriver dan Cucumber dengan bahasa pemrograman Java serta
dikelola menggunakan Gradle.

---

## 🧰 Teknologi yang Digunakan

- Java
- Gradle
- Selenium WebDriver
- Cucumber BDD
- JUnit
- WebDriverManager
- Google Chrome

---

## 🎯 Tujuan

Framework ini bertujuan untuk:

- Mengotomatisasi pengujian UI website
- Menguji fungsionalitas login
- Menerapkan konsep Behavior Driven Development (BDD)
- Menggunakan Page Object Model agar kode lebih rapi dan mudah dirawat

---

## 📁 Struktur Proyek

```
Project21
│
├── src
│   └── test
│       ├── java
│       │   ├── hooks
│       │   │   └── Hooks.java
│       │   │
│       │   ├── pages
│       │   │   │── LoginPage.java
│       │   │
│       │   ├── steps
│       │   │   │── LoginSteps.java
│       │   │
│       │   └── runners
│       │       └── RunCucumberTest.java
│       │
│       └── resources
│           └── features
│               └── login.feature
│
├── build.gradle
├── settings.gradle
├── gradlew
└── README.md
```

---

## 🧩 Page Object Model (POM)

Setiap halaman website dibuat dalam satu class terpisah.

Contoh:
- `LoginPage.java` digunakan untuk menangani seluruh element dan aksi pada halaman login.

Hal ini memudahkan pemeliharaan kode jika terjadi perubahan tampilan website.

---

## 🧪 Skenario Pengujian

### ✅ Positive Test
- Login menggunakan username dan password yang benar

### ❌ Negative Test
- Login menggunakan password salah

### ⚠ Boundary Test
- Username kosong
- Username sangat panjang

---

## 📄 Contoh Feature File

```gherkin
Feature: Login Functionality

  Scenario: Login berhasil
    Given saya membuka halaman login
    When saya memasukkan username "tomsmith"
    And saya memasukkan password "SuperSecretPassword!"
    And saya menekan tombol login
    Then login berhasil ditampilkan

  Scenario: Login gagal
    Given saya membuka halaman login
    When saya memasukkan username "tomsmith"
    And saya memasukkan password "salah"
    And saya menekan tombol login
    Then muncul pesan login gagal

  Scenario: Login dengan username kosong
    Given saya membuka halaman login
    When saya memasukkan username ""
    And saya memasukkan password "SuperSecretPassword!"
    And saya menekan tombol login
    Then muncul pesan login gagal
```

---

## ▶ Cara Menjalankan Test

### Melalui Terminal

```bash
gradle test
```

atau

```bash
./gradlew test
```

---

### Melalui IntelliJ IDEA

1. Buka file `RunCucumberTest.java`
2. Klik tombol **Run ▶**

---

## 🌐 Website yang Diuji

```
https://the-internet.herokuapp.com/login
```

---

## ✅ Kesimpulan

Framework ini telah berhasil menerapkan:

- Selenium WebDriver
- Cucumber BDD
- Page Object Model (POM)
- Pengujian positif, negatif, dan batas
- Struktur kode yang rapi dan mudah dikembangkan

Framework ini dapat digunakan kembali untuk pengujian halaman website lainnya.

---

## 🔗 Repository GitHub

https://github.com/DelaAmeliaSitorus/Project21

