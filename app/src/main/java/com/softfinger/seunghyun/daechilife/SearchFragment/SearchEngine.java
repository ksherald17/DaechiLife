package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.softfinger.seunghyun.daechilife.DataModel.DynamoDB_TeacherClass;
import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.FirstActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SearchEngine {

    String query;
    List<DynamoDB_TeacherClass> teacherresultDBlist;

    TeacherElement teacherResult = null;

    /* Thread */
    Thread datathread;

    //Get TeacherResult
    public TeacherElement getTeacherResult() {
        return teacherResult;
    }


    public SearchEngine(String query){
        this.query = query;
        teacherresultDBlist = new ArrayList<>();
    }

    //결과를 가져오는 함수
    private void setResult(String query){

        /* 결과값 초기화 */
        teacherResult = null;

        int determineSituation = 0;
        int QUERY_MAYBE_TEACHER = 1;
        int QUERY_MAYBE_ACADEMY = 2;
        int QUERY_MAYBE_ACADEMYSHORT = 3;
        int QUERY_MAYBE_SCHOOL = 4;
        int QUERY_MAYBE_SUBJECT = 5; //subject는 자체적으로 판단이 가능해야해.
        String result = "";

        /* DETERMINE QUERY CHARACTERISTICS */
        if(query.length() == 3){ //세글자인 경우 고로 끝나면 학교를 뒤지고, 아니라면 선생님명부터 뒤지자.
            if(query.substring(2,3).equals("고")){
                determineSituation = QUERY_MAYBE_SCHOOL;
            }
            else{
                determineSituation = QUERY_MAYBE_TEACHER;
            }
        }

        else if(query.length() == 4 && query.substring(query.length()-2,query.length()-1).equals("고")){ //경기고1, 진선여고2처럼 입력된 상황

            boolean check;

            try {
                Integer.parseInt(query.substring(3,4));
                check = true;
            } catch(Exception e) {
                check = false;

            }

            if(check){
                determineSituation = QUERY_MAYBE_SCHOOL;
            }

        }

        /* get Item from TeacherDB */
        if(determineSituation == QUERY_MAYBE_TEACHER) {

            AmazonDynamoDBClient ddb = FirstActivity.clientManager
                    .ddb();

            final DynamoDBMapper mapper = new DynamoDBMapper(ddb);

            try {
                final String inputquery = query;
                datathread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DynamoDBQueryExpression<DynamoDB_TeacherClass> query2 =
                                new DynamoDBQueryExpression<DynamoDB_TeacherClass>();
                        DynamoDB_TeacherClass hashKeyValues = new DynamoDB_TeacherClass();
                        hashKeyValues.setTeacherName(inputquery);
                        query2.setHashKeyValues(hashKeyValues);
                        // getMapper() returns a DynamoDBMapper object with the appropriate
                        // AmazonDynamoDBClient object.
                        teacherresultDBlist = mapper.query(DynamoDB_TeacherClass.class, query2);
                        Log.e("선생님 데이터 가져오기", "성공적으로 학원 index를 받아왔습니다.");
                    }
                });
                datathread.start();

            } catch (AmazonServiceException ex) {
                Log.e("선생님 데이터 가져오기", "업로드 중 오류가 발생했습니다.");
                FirstActivity.clientManager
                        .wipeCredentialsOnAuthError(ex);
               determineSituation = 0;
            }

            try{
                datathread.join();
            }catch(InterruptedException ex){}

            //만약 선생님 검색에서 등장했다면 선생님 랙쳐클래스로 변환
            if(determineSituation != 0){

                DynamoDB_TeacherClass resultmap = teacherresultDBlist.get(0);

                ArrayList<String> subjectlist = new ArrayList<>();
                ArrayList<String> academylist = new ArrayList<>();
                ArrayList<String> timelist = new ArrayList<>();
                ArrayList<String> attendlist = new ArrayList<>();
                ArrayList<String> categorylist = new ArrayList<>();
                ArrayList<String> namelist = new ArrayList<>();
                ArrayList<String> descriptionlist = new ArrayList<>();

                Set<String> keyset = resultmap.getLectureInfo().keySet();

                for (String key : keyset) {
                    switch(key){
                        case "academynamelist":
                            academylist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                        case "subjectlist":
                            subjectlist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                        case "timelist":
                            timelist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                        case "attendlist":
                            attendlist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                        case "categorylist":
                            categorylist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                        case "namelist":
                            namelist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                        case "descriptionlist":
                            descriptionlist = (ArrayList<String>)resultmap.getLectureInfo().get(key);
                            break;
                    }
                }

                ArrayList<LectureClass> teacherlectures = new ArrayList<>();

                for(int i = 0; i < academylist.size(); i++){
                    LectureClass lectureClass = new LectureClass(resultmap.getTeacherName(), academylist.get(i));
                    lectureClass.setLecturename(namelist.get(i));
                    lectureClass.setSubject(subjectlist.get(i));
                    lectureClass.setTime(timelist.get(i));
                    lectureClass.setAge(attendlist.get(i));
                    lectureClass.setCategory(categorylist.get(i));
                    lectureClass.setDescription(descriptionlist.get(i));
                    teacherlectures.add(lectureClass);
                }

                teacherResult = new TeacherElement(resultmap.getTeacherName(), subjectlist.get(0));
                teacherResult.setLectureClassess(teacherlectures);
            }

            else{ //만약 3글자인데 선생님이 아니라면 다음을 시도

            }
        }



        /* 다0 이라면 공백제거한 상태로 재계산*/
    }


    /* Getter& Setter */

    public String getQuery() {

        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
