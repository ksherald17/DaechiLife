package com.softfinger.seunghyun.daechilife.DataModel;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "LectureDB_Teacher")
public class DynamoDB_TeacherClass {

    @DynamoDBHashKey(attributeName = "TeacherName")
    private String TeacherName;

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    @DynamoDBAttribute(attributeName = "LecturInfo")
    private Map<String, List<String>> LecturInfo = new HashMap<String, List<String>>();

    public Map<String, List<String>> getLecturInfo() {
        return LecturInfo;
    }

    public void setLecturInfo(Map<String, List<String>> lectureInfo) {
        LecturInfo = lectureInfo;
    }
}
/*
public class BodyTypeConverter implements DynamoDBTypeConverter<String, Map<String, Object>> {

    @Override
    public String convert(Map<String, Object> object) {
        DimensionType itemDimensions = (Map<String, Object>) object;

        // Convert the object to a DynamoDB json string
        String json = "wibble";

        return json;
    }

    @Override
    public DimensionType unconvert(String s) {
        Map<String, Object> item = new Map<String, Object>();

        // Convert s to a Map<String, Object> here.

        return item;
    }*/
//}
