package com.softfinger.seunghyun.daechilife.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ClassRecommendElement {

    String searchtext;
    String descriptiontext;
    ArrayList<String> result;

    public ClassRecommendElement(String text, String description, ArrayList<String> result){
        this.searchtext = text;
        this.descriptiontext = description;
        this.result = result;
    }

    public void setSearchtext(String text){
        this.searchtext = searchtext;
    }

    public void setDescriptiontext(String description){
        this.descriptiontext = description;
    }

    public void setResult(ArrayList<String> result){
        this.result = result;
    }

    public String getSearchtext(){
        return searchtext;
    }

    public String getDescriptiontext(){
        return descriptiontext;
    }

    public ArrayList<String> getResult() {
        return result;
    }
}
