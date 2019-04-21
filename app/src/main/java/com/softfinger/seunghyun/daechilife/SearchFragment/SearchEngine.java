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

public class SearchEngine {

    String query;
    TeacherElement teacherResult = null;
    static List<TeacherElement> teacherresultlist;
    List<DynamoDB_TeacherClass> dbteachertempresult;

    /* TABLE */
    static String lecture_teacherTableName = "LectureDB_Teacher";

    /* Thread */
    Thread datathread;

    //Get TeacherResult
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
        dbteachertempresult = new ArrayList<>();

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

                        DynamoDBQueryExpression<DynamoDB_TeacherClass> query2 =
                                new DynamoDBQueryExpression<>();
                        DynamoDB_TeacherClass hashKeyValues = new DynamoDB_TeacherClass();
                        hashKeyValues.setTeacherName(inputquery);
                        Log.e("SearchEngine", inputquery);
                        query2.setHashKeyValues(hashKeyValues);
                        // getMapper() returns a DynamoDBMapper object with the appropriate
                        // AmazonDynamoDBClient object.
                        dbteachertempresult = mapper.query(DynamoDB_TeacherClass.class, query2);
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

            try {
                datathread.join();

                if(dbteachertempresult == null || dbteachertempresult.size() == 0){
                    determineSituation = 0;
                    Log.e("선생님 데이터 가져오기", "검색결과 없음");
                }else{
                    Log.e("선생님 데이터 가져오기", "검색 성공");
                }

                //만약 선생님 검색에서 등장했다면 선생님 랙쳐클래스로 변환
                if (determineSituation != 0) {

                    Log.e("선생님 데이터 이름", dbteachertempresult.get(0).getTeacherName());

                    List<String> subjectlist = dbteachertempresult.get(0).getSubjectlist();
                    List<String> academylist = dbteachertempresult.get(0).getAcademynamelist();
                    List<String> timelist = dbteachertempresult.get(0).getTimelist();
                    List<String> attendlist = dbteachertempresult.get(0).getAttendlist();
                    List<String> categorylist = dbteachertempresult.get(0).getCategorylist();
                    List<String> namelist = dbteachertempresult.get(0).getNamelist();
                    List<String> descriptionlist = dbteachertempresult.get(0).getDescriptionlist();
                    List<String> englishnamelist = dbteachertempresult.get(0).getAcademyenglishlist();

                    ArrayList<LectureClass> teacherlectures = new ArrayList<>();

                    for (int i = 0; i < academylist.size(); i++) {

                        LectureClass lectureClass = new LectureClass(dbteachertempresult.get(0).getTeacherName(),
                                academylist.get(i));
                        lectureClass.setLecturename(namelist.get(i));
                        lectureClass.setSubject(subjectlist.get(i));
                        lectureClass.setTime(timelist.get(i));
                        lectureClass.setAge(attendlist.get(i));
                        lectureClass.setCategory(categorylist.get(i));
                        lectureClass.setDescription(descriptionlist.get(i));
                        lectureClass.setAcademyenglishname(englishnamelist.get(i));
                        teacherlectures.add(lectureClass);
                    }

                    teacherResult = new TeacherElement(dbteachertempresult.get(0).getTeacherName(), subjectlist.get(0));
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

}
