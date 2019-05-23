package com.softfinger.seunghyun.daechilife.DataModel;

public class HomeRecommendLecture {

    String category;
    String teacher;
    String academy;
    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    int imageresource;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public int getImageresource() {
        return imageresource;
    }

    public void setImageresource(int imageresource) {
        this.imageresource = imageresource;
    }
}
