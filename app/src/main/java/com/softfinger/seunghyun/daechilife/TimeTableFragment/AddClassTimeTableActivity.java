package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.PageShow.AddClassPageAdapter;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

/* 임시 시간표 출력 레이아웃 -> 뒤로가기 눌렀을때 같이 동반으로 삭제되는 코드가 요구됨 */

public class AddClassTimeTableActivity extends AppCompatActivity {

    static Activity activity;
    static Context context;

    RecyclerView daycellRV, timecellRV, timetablecellRV;
    DayCellAdapter dayCellAdapter;
    TimeCellAdapter timeCellAdapter;
    TimeTableCellAdapter timeTableCellAdapter;
    ArrayList<String> day, time, timetable;
    RelativeLayout firstcell;
    static RelativeLayout timetablelayout; //layout

    //시간표 DB
    ArrayList<LectureClass> mainlecturelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activity = this;
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclasstimetable);

        //findViewById
        daycellRV = findViewById(R.id.daycellRV2);
        timecellRV = findViewById(R.id.timecellRV2);
        timetablecellRV = findViewById(R.id.timetablecellRV2);
        timetablelayout = findViewById(R.id.timetablerelativelayout2);

        //setTimetable
        setTimeTableArchitecture();
        FirstActivity.startaddclassactivity();

    }
    //기본 테이블 세팅
    public void insertLectureinMainLectureList(){
        mainlecturelist = TimeTableFragment.getMaintablelecuturelist();
        for(int i = 0; i < mainlecturelist.size(); i++){
            TimeTableFragment.insertLectureToTimeTable(mainlecturelist.get(i), timetablelayout);
        }
    }

    //셀 크기 결정하는 코드
    public void setTimeTableArchitectureSize(){
        firstcell = findViewById(R.id.firstcell2);
        firstcell.getLayoutParams().width = TimeTableFragment.getTimecellwidth();
        firstcell.getLayoutParams().height = TimeTableFragment.getDaycellheight();
    }

    //월화수목금, 시간세팅 if maximize controll하고 싶다면 parameter집어넣어서 이부분을 컨트롤하면 됨.
    public void setTimeTableArchitectureData(){
        day = TimeTableFragment.getDay();
        time = TimeTableFragment.getTime();
        timetable = TimeTableFragment.getTimetable();
    }

    //리싸이클러뷰를 컨트롤. 총 day, time, timetablecell로 나눠져있음.
    public void setTimeTableArchitectureRecyclerView(){

        daycellRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        daycellRV.setLayoutManager(llm);
        dayCellAdapter =  new DayCellAdapter(day, context);
        daycellRV.setAdapter(dayCellAdapter);

        timecellRV.setHasFixedSize(true);
        LinearLayoutManager llm2 = new LinearLayoutManager(context);
        timecellRV.setLayoutManager(llm2);
        timeCellAdapter =  new TimeCellAdapter(time, context);
        timecellRV.setAdapter(timeCellAdapter);

        timetablecellRV.setHasFixedSize(true);
        timetablecellRV.setLayoutManager(new GridLayoutManager(context, 7));
        timeTableCellAdapter =  new TimeTableCellAdapter(timetable, context);
        timetablecellRV.setAdapter(timeTableCellAdapter);

    }

    //기본 타임테이블 세팅
    public void setTimeTableArchitecture(){

        setTimeTableArchitectureSize();

        setTimeTableArchitectureData();

        setTimeTableArchitectureRecyclerView();

        insertLectureinMainLectureList();

    }

    public static RelativeLayout getTimeTableLayout(){return timetablelayout;}

    public static void finishactivity(){
        activity.finish();
    }

}