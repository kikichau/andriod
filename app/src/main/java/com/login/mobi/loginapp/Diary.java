package com.login.mobi.loginapp;

public class Diary {
    private String uid;
    private int did;
    private String date;
    private  String title;
    private String address;
    private String text;

    public Diary(String uid, String date, String title, String address, String text) {
        this.uid = uid;
        this.date = date;
        this.title = title;
        this.address = address;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid + '\'' +
                "did=" + did +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
