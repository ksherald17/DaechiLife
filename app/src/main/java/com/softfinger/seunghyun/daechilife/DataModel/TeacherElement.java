package com.softfinger.seunghyun.daechilife.DataModel;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class TeacherElement {
    String teachername;
    List<String> academylist;
    String academyname;
    String subject;
    int expand = 1;

    /*constructor*/
    public TeacherElement(String teachername, String subject){
        this.teachername = teachername;
        academylist = new ArrayList<String>();
        this.subject = subject;
    }

    public TeacherElement(String teachername, String academyname, String subject){
        this.teachername = teachername;
        this.academyname = academyname;
        academylist = new ArrayList<String>();
        academylist.add(this.academyname);
        this.subject = subject;
    }

    public TeacherElement(String teachername, List<String> academylist, String subject){
        this.teachername = teachername;
        academylist = new ArrayList<String>();
        this.academylist = academylist;
        this.subject = subject;
    }


    /*getter, setter*/
    public void setTeachername(String teachername){
        this.teachername = teachername;
    }

    public String getTeachername(){
        return teachername;
    }


    public void setAcademylist(List<String> academylist){
        this.academylist = academylist;
    }

    public List<String> getAcademylist(){
        return academylist;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void setExpand(){
        expand = 1;
    }

    public void cancelExpand(){
        expand = 0;
    }

    public int getExpand(){
        return expand;
    }

}
