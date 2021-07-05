package com.example.test;

public class Mobile {
   private   String date;
    private String mobile;
    public Mobile(){

    }
    public Mobile(String date, String mobile) {
        this.date = date;
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
