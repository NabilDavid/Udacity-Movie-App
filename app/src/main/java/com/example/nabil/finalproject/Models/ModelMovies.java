package com.example.nabil.finalproject.Models;

import java.io.Serializable;

/**
 * Created by Nabil on 4/5/2016.
 */
public class ModelMovies implements Serializable {


    public String title = "title";
    public String desc = "Description";
    public String rate= "5";
    public String photo = "photo";
    public String date = "date";
    public  String ID=" ";

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public String getTitle() {
        return title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}