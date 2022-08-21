package com.example.ecgmonitor;

public class User {
    public String rate;
    public String tanggal;

    public User() {
    }

    public User(String rate, String tanggal) {
        this.rate = rate;
        this.tanggal = tanggal;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
