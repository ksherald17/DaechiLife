package com.softfinger.seunghyun.daechilife.DataModel;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "LectureDB_Teacher")
public class DynamoDB_TeacherClass {

    String TeacherName;
    Map<String, List<String>> LectureInfo = new HashMap<String, List<String>>();

    @DynamoDBHashKey(attributeName = "TeacherName")
    public String getTeacherName() {
        return TeacherName;
    }
    public void setTeacherName(String teachername){this.TeacherName = teachername;}

    @DynamoDBAttribute(attributeName = "LectureInfo")
    public Map getLectureInfo() {
        return LectureInfo;
    }
    public void setLectureInfo(Map lectureInfo){this.LectureInfo = lectureInfo;}

}
