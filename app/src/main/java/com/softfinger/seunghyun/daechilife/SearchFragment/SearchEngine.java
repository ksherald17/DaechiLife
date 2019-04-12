package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.FirstActivity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SearchEngine {

    String query;
    TeacherElement teacherResult = null;
    static List<TeacherElement> teacherresultlist;

    /* TABLE */
    static String lecture_teacherTableName = "LectureDB_Teacher";

    /* Thread */
    Thread datathread;

    //Get TeacherResult
    GetItemResult getItemResult;
    public TeacherElement getTeacherResult() {
        return teacherResult;
    }



    public SearchEngine(String query){
        this.query = query;
        teacherresultlist = new ArrayList<>();
    }

    //결과를 가져오는 함수
    public void setResult() {
        /* 결과값 초기화 */
        teacherResult = null;

        /* query characteristic determination */
        int determineSituation = 0;
        int QUERY_MAYBE_TEACHER = 1;
        int QUERY_MAYBE_ACADEMY = 2;
        int QUERY_MAYBE_ACADEMYSHORT = 3;
        int QUERY_MAYBE_SCHOOL = 4;
        int QUERY_MAYBE_SUBJECT = 5; //subject는 자체적으로 판단이 가능해야해.
        String result = "";

        /* DETERMINE QUERY CHARACTERISTICS */
        //세글자인 경우 고로 끝나면 학교를 뒤지고, 아니라면 선생님명부터 뒤지자.
        if (query.length() == 3) {
            if (query.substring(2, 3).equals("고")) {
                determineSituation = QUERY_MAYBE_SCHOOL;
            } else {
                determineSituation = QUERY_MAYBE_TEACHER;
            }
        }

        //학교 + 학년 조합
        else if (query.length() == 4 && query.substring(query.length() - 2, query.length() - 1).equals("고")) { //경기고1, 진선여고2처럼 입력된 상황

            boolean check;

            try {
                Integer.parseInt(query.substring(3, 4));
                check = true;
            } catch (Exception e) {
                check = false;

            }

            if (check) {
                determineSituation = QUERY_MAYBE_SCHOOL;
            }
        }

        /* Get Item from TeacherDB */
        if (determineSituation == QUERY_MAYBE_TEACHER) {

            final AmazonDynamoDBClient ddb = FirstActivity.clientManager
                    .ddb();

            final DynamoDBMapper mapper = new DynamoDBMapper(ddb);

            try {
                final String inputquery = query;
                datathread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
                        key.put("TeacherName", new AttributeValue().withS(inputquery));

                        GetItemRequest getItemRequest = new GetItemRequest().withTableName(lecture_teacherTableName).withKey(key);
                        getItemResult = ddb.getItem(getItemRequest);

                    }
                });
                datathread.start();

            } catch (AmazonServiceException ex) {
                Log.e("선생님 데이터 가져오기", "업로드 중 오류가 발생했습니다.");
                FirstActivity.clientManager
                        .wipeCredentialsOnAuthError(ex);
                determineSituation = 0;
            }

            try {
                datathread.join();

                if(getItemResult == null || getItemResult.getItem().get("TeacherName") == null){
                    determineSituation = 0;
                    Log.e("선생님 데이터 가져오기", "검색결과 없음");
                }else{
                    Log.e("선생님 데이터 가져오기", "검색 성공");
                }

                //만약 선생님 검색에서 등장했다면 선생님 랙쳐클래스로 변환
                if (determineSituation != 0) {

                    Log.e("선생님 데이터 이름", getItemResult.getItem().get("TeacherName").getS());

                    List subjectlist = getItemResult.getItem().get("subjectlist").getL();
                    List academylist = getItemResult.getItem().get("academynamelist").getL();
                    List timelist = getItemResult.getItem().get("timelist").getL();
                    List attendlist = getItemResult.getItem().get("attendlist").getL();
                    List categorylist = getItemResult.getItem().get("categorylist").getL();
                    List namelist = getItemResult.getItem().get("namelist").getL();
                    List descriptionlist = getItemResult.getItem().get("descriptionlist").getL();

                    ArrayList<LectureClass> teacherlectures = new ArrayList<>();

                    for (int i = 0; i < academylist.size(); i++) {

                        LectureClass lectureClass = new LectureClass(getItemResult.getItem().get("TeacherName").toString().replace("{S:", "").replace(",}", ""),
                                academylist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        lectureClass.setLecturename(namelist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        lectureClass.setSubject(subjectlist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        lectureClass.setTime(timelist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        lectureClass.setAge(attendlist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        lectureClass.setCategory(categorylist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        lectureClass.setDescription(descriptionlist.get(i).toString().replace("{S:", "").replace(",}", ""));
                        teacherlectures.add(lectureClass);
                        }

                        teacherResult = new TeacherElement(getItemResult.getItem().get("TeacherName").toString().replace("{S:", "").replace(",}", ""),
                                subjectlist.get(0).toString().replace("{S:", "").replace(",}", ""));
                        teacherResult.setLectureClassess(teacherlectures);
                        teacherresultlist.add(teacherResult);
                    }

                } catch (InterruptedException ex) {
            }
        }
        /* 다0 이라면 공백제거한 상태로 재계산*/
    }



    /* Getter& Setter */
    //teacherresult
    public static List<TeacherElement> getTeacherresultlist() {
        return teacherresultlist;
    }
    public static void setTeacherresultlist(List<TeacherElement> teacherresultlist) {
        SearchEngine.teacherresultlist = teacherresultlist;
    }

    //query
    public String getQuery() {

        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }

    /*DynamoDBQueryExpression<DynamoDB_TeacherClass> query2 =
                                new DynamoDBQueryExpression<DynamoDB_TeacherClass>();
                        DynamoDB_TeacherClass hashKeyValues = new DynamoDB_TeacherClass();
                        hashKeyValues.setTeacherName(inputquery);
                        Log.e("SearchEngine", inputquery);
                        query2.setHashKeyValues(hashKeyValues);
                        // getMapper() returns a DynamoDBMapper object with the appropriate
                        // AmazonDynamoDBClient object.
                        teacherresultDBlist = mapper.query(DynamoDB_TeacherClass.class, query2);
                        Log.e("선생님 데이터 가져오기", "성공적으로 학원 index를 받아왔습니다.");*/
}
