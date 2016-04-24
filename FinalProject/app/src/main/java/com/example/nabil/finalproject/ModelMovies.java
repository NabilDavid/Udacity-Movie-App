package com.example.nabil.finalproject;

/**
 * Created by Nabil on 4/5/2016.
 */
public class ModelMovies {


    public String title = "title";
    public String desc = "Description";
    public Double rate = 0.5;
    public String photo = "photo";
    public String date = "date";

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }

    public String getTitle() {
        return title;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}