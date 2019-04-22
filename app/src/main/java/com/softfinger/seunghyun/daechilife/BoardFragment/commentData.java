package com.softfinger.seunghyun.daechilife.BoardFragment;

public class commentData {
    private String comment_author;
    private String comment_time;
    private String comment_content;
    public commentData(String author,String time, String content ){
        this.comment_author=author;
        this.comment_content=content;
        this.comment_time=time;
    }


    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }
}
