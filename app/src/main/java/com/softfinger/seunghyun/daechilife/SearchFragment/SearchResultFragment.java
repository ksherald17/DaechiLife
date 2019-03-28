package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

public class SearchResultFragment extends android.support.v4.app.Fragment {

    Context context;
    View searchResultPage;
    RecyclerView searchRV;
    SearchResultAdapter searchResultAdapter;

    /*Sample DB*/
    ArrayList<TeacherElement> teachers;
    int sampledbON = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        searchResultPage = myInflater.inflate(R.layout.searchresultpage, null);

        setSampleDB();
        setRecyclerView();

        return searchResultPage;
    }

    public void setSampleDB(){

        teachers = new ArrayList<>();

        /*이지정 선생님*/
        TeacherElement ijijung = new TeacherElement("이지정", "영어");
        ijijung.setDescription("#내신영어 #수능영어"); ijijung.setWatch(1021);
        LectureClass skyijijung = new LectureClass("이지정", "KNS어학원");
        skyijijung.setTime("수 6:30PM~8:00PM / 토 11:00AM~2:30PM");
        skyijijung.setLecturename("SKY 수능반");
        LectureClass whimoon = new LectureClass("이지정", "KNS어학원");
        whimoon.setTime("월 6:30PM~8:00PM / 일 11:00AM~2:30PM");
        whimoon.setLecturename("휘문고2 내신대비반");
        LectureClass kyunggi = new LectureClass("이지정", "KNS어학원");
        kyunggi.setTime("화 6:30PM~8:00PM / 목 6:30PM~8:00PM");
        kyunggi.setLecturename("경기고1 내신대비반");

        ijijung.addLecture(skyijijung);ijijung.addLecture(kyunggi);ijijung.addLecture(whimoon);

        /*아승철 선생님*/
        TeacherElement liseungcheoul = new TeacherElement("이승철", "수학");
        liseungcheoul.setDescription("#수능만점 #내신대비");liseungcheoul.setWatch(1321);
        LectureClass math2 = new LectureClass("이승철", "개념상상");
        math2.setTime("수 6:30PM~8:00PM / 토 11:00AM~2:30PM");
        math2.setLecturename("수2 심화반");
        LectureClass kyunggi2 = new LectureClass("이승철", "개념상상");
        kyunggi2.setTime("월 6:30PM~10:00PM / 목 6:30PM~10:00PM ");
        kyunggi2.setLecturename("경기고2 내신대비반");
        LectureClass mijuck = new LectureClass("이승철", "개념상상");
        mijuck.setTime("화 6:30PM~10:00PM / 금 6:30PM~10:00PM");
        mijuck.setLecturename("미적분 기본반");

        liseungcheoul.addLecture(math2);liseungcheoul.addLecture(kyunggi2);liseungcheoul.addLecture(mijuck);

        /*홍은영*/
        TeacherElement hongeunyoung = new TeacherElement("홍은영", "지학");
        hongeunyoung.setDescription("#지학내신 #수능지학 #지1 #지2 #논술");hongeunyoung.setWatch(521);
        LectureClass ji1 = new LectureClass("홍은영", "미래탐구");
        ji1.setTime("수 6:30PM~8:00PM / 토 11:00AM~2:30PM");
        ji1.setLecturename("지1 수능대비반");
        LectureClass kyunggi1 = new LectureClass("홍은영", "미래탐구");
        kyunggi1.setTime("월 6:30PM~10:00PM / 목 6:30PM~10:00PM ");
        kyunggi1.setLecturename("경기고1 지구과학 내신반");
        LectureClass jinsun2 = new LectureClass("홍은영", "미래탐구");
        jinsun2.setTime("화 6:30PM~10:00PM / 금 6:30PM~10:00PM");
        jinsun2.setLecturename("진선여고2 지구과학 내신반");

        hongeunyoung.addLecture(ji1);hongeunyoung.addLecture(kyunggi1);hongeunyoung.addLecture(jinsun2);

        teachers.add(ijijung); teachers.add(liseungcheoul); teachers.add(hongeunyoung);
    }

    public void setRecyclerView(){
        searchRV = searchResultPage.findViewById(R.id.searchresultRV);
        searchRV.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을 한다.
        searchRV.setLayoutManager(llm);
        searchResultAdapter = new SearchResultAdapter(teachers, context);
        searchRV.setAdapter(searchResultAdapter);
    }

    public void setResult(){

    }

}
