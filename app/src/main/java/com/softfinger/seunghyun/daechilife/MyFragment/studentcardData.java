package com.softfinger.seunghyun.daechilife.MyFragment;

public class studentcardData {
    private String student_name;
    private String student_school;
    private String student_grade;
    private int student_academynum;
    public studentcardData(String name,String school, String grade, int academynum){
        this.student_name=name;
        this.student_school=school;
        this.student_grade=grade;
        this.student_academynum=academynum;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_school() {
        return student_school;
    }

    public void setStudent_school(String student_school) {
        this.student_school = student_school;
    }

    public String getStudent_grade() {
        return student_grade;
    }

    public void setStudent_grade(String student_grade) {
        this.student_grade = student_grade;
    }

    public int getStudent_academynum() {
        return student_academynum;
    }

    public void setStudent_academynum(int student_academynum) {
        this.student_academynum = student_academynum;
    }
}
