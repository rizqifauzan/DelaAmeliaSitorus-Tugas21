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
