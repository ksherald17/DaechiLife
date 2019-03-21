package com.softfinger.seunghyun.daechilife.DataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class BoardHotElement {

    String title;
    String writerid;
    String currenttime;
    String day;
    int comments;
    int watch;
    String imageplace;
    int imageexist;

    public BoardHotElement(String title, String writerid){
        this.title = title;
        this.writerid = writerid;
        comments = 0;
        watch = 0;
        imageexist = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
        sdf.setTimeZone(TimeZone.getDefault());
        day = sdf.format(new Date());

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        sdf2.setTimeZone(TimeZone.getDefault());
        currenttime = sdf2.format(new Date());
    }

    public BoardHotElement(String title, String writerid, int comment, int watch){
        this.title = title;
        this.writerid = writerid;
        this.comments = 0;
        this.watch = 0;
    }

    public int getImageexist() {
        return imageexist;
    }

    public void setImageexist(int imageexist) {
        this.imageexist = imageexist;
    }


    public String getTitle() {
        return title;
    }

    public String getImageplace() {
        return imageplace;
    }

    public void setImageplace(String imageplace) {
        this.imageplace = imageplace;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriterid() {
        return writerid;
    }

    public void setWriterid(String writerid) {
        this.writerid = writerid;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
