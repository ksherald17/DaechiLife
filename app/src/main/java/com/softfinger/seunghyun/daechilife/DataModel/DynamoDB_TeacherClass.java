package com.softfinger.seunghyun.daechilife.DataModel;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "LectureDB_Teacher")
public class DynamoDB_TeacherClass {

    List<String> subjectlist;
    List<String> academynamelist;
    List<String> timelist;
    List<String> attendlist;
    List<String> categorylist;
    List<String> academyenglishlist;
    List<String> namelist;
    List<String> descriptionlist;
    String TeacherName;

    @DynamoDBHashKey(attributeName = "TeacherName")
    public String getTeacherName() {
        return TeacherName;
    }
    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    @DynamoDBAttribute(attributeName = "subjectlist")
    public List<String> getSubjectlist() {
        return subjectlist;
    }
    public void setSubjectlist(List<String> subjectlist) {
        this.subjectlist = subjectlist;
    }

    @DynamoDBAttribute(attributeName = "academynamelist")
    public List<String> getAcademynamelist() {
        return academynamelist;
    }
    public void setAcademynamelist(List<String> academynamelist) {
        this.academynamelist = academynamelist;
    }

    @DynamoDBAttribute(attributeName = "timelist")
    public List<String> getTimelist() {
        return timelist;
    }
    public void setTimelist(List<String> timelist) {
        this.timelist = timelist;
    }

    @DynamoDBAttribute(attributeName = "attendlist")
    public List<String> getAttendlist() {
        return attendlist;
    }
    public void setAttendlist(List<String> attendlist) {
        this.attendlist = attendlist;
    }

    @DynamoDBAttribute(attributeName = "categorylist")
    public List<String> getCategorylist() {
        return categorylist;
    }
    public void setCategorylist(List<String> categorylist) {
        this.categorylist = categorylist;
    }

    @DynamoDBAttribute(attributeName = "namelist")
    public List<String> getNamelist() {
        return namelist;
    }
    public void setNamelist(List<String> namelist) {
        this.namelist = namelist;
    }

    @DynamoDBAttribute(attributeName = "descriptionlist")
    public List<String> getDescriptionlist() {
        return descriptionlist;
    }
    public void setDescriptionlist(List<String> descriptionlist) {
        this.descriptionlist = descriptionlist;
    }

    @DynamoDBAttribute(attributeName = "academyenglishlist")
    public List<String> getAcademyenglishlist() {
        return academyenglishlist;
    }
    public void setAcademyenglishlist(List<String> academyenglishlist) {
        this.academyenglishlist = academyenglishlist;
    }
}
