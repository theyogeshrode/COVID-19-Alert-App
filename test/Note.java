package com.example.test;

public class Note {
private String Name;
    private  String City;
    private  String Email;
    private  String PassWord;
    private  String Mobile;
    private int Uid;
    private Double lang;
    private Double lat;
    public Note(){

    }

    public Note(String name, String city, String email, String passWord, String mobile, int uid, Double lat, Double lang) {
        Name = name;
        City = city;
        Email = email;
        PassWord = passWord;
        Mobile = mobile;
        Uid = uid;
        this.lang = lang;
        this.lat = lat;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
