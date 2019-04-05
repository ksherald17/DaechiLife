package com.softfinger.seunghyun.daechilife.BoardFragment;

import java.util.ArrayList;
import java.util.List;

public class articleData {

    private String article_title;
    private String article_author;
    private String article_time;
    private ArrayList<commentData> article_comments;
    private int article_viewnum;
    private String article_content;
    public articleData ( String article_title,String article_author,String article_time,ArrayList<commentData> article_comments,int article_viewnum,String article_content)
    {
        this.article_title=article_title;
        this.article_author=article_author;
        this.article_time=article_time;
        this.article_comments=article_comments;
        this.article_viewnum=article_viewnum;
        this.article_content=article_content;

    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_author() {
        return article_author;
    }

    public void setArticle_author(String article_author) {
        this.article_author = article_author;
    }

    public String getArticle_time() {
        return article_time;
    }

    public void setArticle_time(String article_time) {
        this.article_time = article_time;
    }

    public ArrayList<commentData> getArticle_comments() {
        return article_comments;
    }

    public void setArticle_comments(ArrayList<commentData> article_comments) {
        this.article_comments = article_comments;
    }

    public int getArticle_viewnum() {
        return article_viewnum;
    }

    public void setArticle_viewnum(int article_viewnum) {
        this.article_viewnum = article_viewnum;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }
}
