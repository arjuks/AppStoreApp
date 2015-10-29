package com.example.arjun.midtermexample;

import java.io.Serializable;

/**
 * Created by arjun on 10/25/2015.
 */
public class Apps implements Serializable{

    String id,title,appurl,devname,date,price,simg;

    public Apps() {}

    public Apps(String id, String appurl, String title, String devname, String date, String price, String simg) {
        this.id = id;
        this.appurl = appurl;
        this.title = title;
        this.devname = devname;
        this.date = date;
        this.price = price;
        this.simg = simg;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }

    public String getAppurl() {
        return appurl;
    }

    public String setAppurl(String appurl) {
        this.appurl = appurl;
        return appurl;
    }

    public String getSimg() {
        return simg;
    }

    public String setSimg(String simg) {
        this.simg = simg;
        return simg;
    }
//
//    public String getLimg() {
//        return limg;
//    }
//
//    public void setLimg(String limg) {
//        this.limg = limg;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDevname() {
        return devname;
    }

    public String setDevname(String devname) {
        this.devname = devname;
        return devname;
    }

    public String getDate() {
        return date;
    }

    public String setDate(String date) {
        this.date = date;
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String setPrice(String price) {
        this.price = price;
        return price;
    }

    @Override
    public String toString() {
        return "Apps{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", appurl='" + appurl + '\'' +
                ", devname='" + devname + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                ", simg='" + simg + '\'' +
                //", limg='" + limg + '\'' +
                '}';
    }
}

