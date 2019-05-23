package com.softfinger.seunghyun.daechilife.PageShow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.R;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.DayCellAdapter;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.TimeTableFragment;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MainPage extends android.support.v4.app.Fragment {

    /*펼쳐지는 뷰 관리*/
    View mainp; //펼쳐지는 메인화면
    static ViewPagerAll viewPager; //페이지 전환된 결과 보여주는 것을 담당
    static PagerAdapter adapter; //페이지를 전환하는 역할을 담당

    /*상단 타이틀 탭*/
    static android.support.v7.widget.Toolbar toolbar;
    CardView loginbutton;

    /*액티비티, 콘텍스트*/
    static Context context;

    /*하단 탭 관리*/
    static LinearLayout hometap, searchtap, boardtap, timetabletap, mytap;
    static TextView hometaptext, searchtaptext, boardtaptext, timetabletext, mytaptext;
    static ImageView hometapimage, searchtapimage, boardtapimage, timetableimage, mytapimage;
    static int next, prev;

    /* 하단 탭 전환 및 시간표 탭 함수 */
    static EditText teacher, academy, category;
    static LinearLayout defaultbottom, timetablebottom;
    static TextView disabletimetableadd;
    static LinearLayout timetableadd;
    static DisplayMetrics metrics;

    static int academyinputstatus = 0;
    static int teacherinputstatus = 0;
    static int categoryinputstatus = 0;
    static int doneacademy, doneteacher, donecategory = 0; //need initialization
    static ImageView imA, imT, imR;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        mainp = myInflater.inflate(R.layout.mainpage, null);
        new MainPage.IAmABackgroundTask().execute();
        return mainp;
    }

    public void setTapViewID(){

        //탭 레이아웃
        hometap = mainp.findViewById(R.id.hometaplayout);
        searchtap = mainp.findViewById(R.id.searchtaplayout);
        boardtap = mainp.findViewById(R.id.boardtaplayout);
        timetabletap = mainp.findViewById(R.id.timetabletaplayout);
        mytap = mainp.findViewById(R.id.mypagetaplayout);

        //탭 텍스트
        hometaptext = mainp.findViewById(R.id.hometaptext);
        searchtaptext = mainp.findViewById(R.id.findtaptext);
        boardtaptext = mainp.findViewById(R.id.boardtaptext);
        timetabletext = mainp.findViewById(R.id.timetabletaptext);
        mytaptext = mainp.findViewById(R.id.mypagetaptext);

        //탭 이미지
        hometapimage = mainp.findViewById(R.id.hometapimage);
        searchtapimage = mainp.findViewById(R.id.findtapimage);
        boardtapimage = mainp.findViewById(R.id.boardtapimage);
        timetableimage = mainp.findViewById(R.id.timetabletapimage);
        mytapimage = mainp.findViewById(R.id.mypagetapimage);

        //탭 id
        defaultbottom = mainp.findViewById(R.id.defaulttoolbarbottom);
        timetablebottom = mainp.findViewById(R.id.timetableaddtoolbar);

        //추가 id
        disabletimetableadd = mainp.findViewById(R.id.disableaddtimetable);
        teacher = mainp.findViewById(R.id.addLectureTeacherET);
        academy = mainp.findViewById(R.id.addLectureAcademyET);
        category = mainp.findViewById(R.id.addLectureSubjectET);
        timetableadd = mainp.findViewById(R.id.addCLectureToTimeTable);
        imA = mainp.findViewById(R.id.imageA);
        imT = mainp.findViewById(R.id.imageT);
        imR = mainp.findViewById(R.id.imageR);
    }

    public void setTap(int prev, int next){
        setTapDefault(prev);
        setTapNext(next);
    }

    //시간표 임의 추가시 나타내는 하단 탭
    public static void showTimeTableAddTab(){
        TimeTableFragment.setDaycellheight(TimeTableFragment.getDaycellheight()/2);
        TimeTableFragment.setDayCellAdapter();
        TimeTableFragment.getDaycellAdapter().notifyDataSetChanged();
        TimeTableFragment.getFirstCell().getLayoutParams().height = TimeTableFragment.getDaycellheight();
        metrics = context.getResources().getDisplayMetrics();
        float dp = 180 * (metrics.densityDpi / 160f);
        TimeTableFragment.getTopbanner().setLayoutParams(new LinearLayout.LayoutParams(0,0));
        defaultbottom.setLayoutParams(new AppBarLayout.LayoutParams(0, 0));
        timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
    }


    //임의 추가에 관해 종합적으로 관리하는 코드
    public static void setTimeTableArbitraryAdd(){

        final InputMethodManager imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);

        //학원 입력에 관해 관리하는 코드
        academy.setOnTouchListener(new View.OnTouchListener() {

            //기존에 있는 수업을 선택시 관리하는 함수
            @Override
            public boolean onTouch(View view, MotionEvent e) {

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        academyinputstatus = 1;
                        float dp = 400 * (metrics.densityDpi / 160f);
                        timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        academy.requestFocus();
                        break;

                    default:
                        break;
                }
                return true;
            }

        });

        academy.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    imA.setImageResource(R.mipmap.checkred);
                    doneacademy = 1;
                    Log.e("검토중", teacher.getText().toString());
                    if(doneteacher == 0){
                        teacher.requestFocus();
                        academyinputstatus = 0;
                        teacherinputstatus = 1;
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                    else{
                        academy.clearFocus();
                        academyinputstatus = 0;
                        imm.hideSoftInputFromWindow(academy.getWindowToken(), 0);
                        float dp = 180 * (metrics.densityDpi / 160f);
                        timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
                    }
                    return true;
                }
                return false;
            }
        });

        //선생님 입력 관리하는 코드
        teacher.setOnTouchListener(new View.OnTouchListener() {

            //기존에 있는 수업을 선택시 관리하는 함수
            @Override
            public boolean onTouch(View view, MotionEvent e) {

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(doneacademy == 0 && donecategory == 0){
                            float dp = 400 * (metrics.densityDpi / 160f);
                            timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
                            teacherinputstatus = 1;
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                        else{
                            teacherinputstatus = 1;
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                        break;

                    default:
                        break;
                }
                return true;
            }

        });


        teacher.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    doneteacher = 1;
                    imT.setImageResource(R.mipmap.checkred);
                    if(doneacademy == 0){
                        academy.requestFocus();
                        academyinputstatus = 1;
                        teacherinputstatus = 0;
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    }

                    else if(donecategory == 0){
                        category.requestFocus();
                        categoryinputstatus = 1;
                        teacherinputstatus = 0;
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    else{
                        teacher.clearFocus();
                        teacherinputstatus = 0;
                        imm.hideSoftInputFromWindow(teacher.getWindowToken(), 0);
                        float dp = 180 * (metrics.densityDpi / 160f);
                        timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
                    }
                    return true;
                }
                return false;
            }
        });

        //과목/단원 입력에 관해 관리하는 코드
        category.setOnTouchListener(new View.OnTouchListener() {

            //기존에 있는 수업을 선택시 관리하는 함수
            @Override
            public boolean onTouch(View view, MotionEvent e) {

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        categoryinputstatus = 1;
                        if(doneacademy == 0 || doneteacher == 0){
                            float dp = 400 * (metrics.densityDpi / 160f);
                            timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
                        }
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        category.requestFocus();
                        break;

                    default:
                        break;
                }
                return true;
            }

        });

        category.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    imT.setImageResource(R.mipmap.checkred);
                    donecategory = 1;
                    Log.e("검토중", teacher.getText().toString());
                    if(doneteacher == 0){
                        teacher.requestFocus();
                        academyinputstatus = 0;
                        teacherinputstatus = 1;
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }
                    else{
                        academy.clearFocus();
                        academyinputstatus = 0;
                        imm.hideSoftInputFromWindow(academy.getWindowToken(), 0);
                        float dp = 180 * (metrics.densityDpi / 160f);
                        timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int)dp));
                    }
                    return true;
                }
                return false;
            }
        });


        /* 생성 취소와 관련된 항목 */

        //취소 버튼 눌렀을 때 동작하는 함수
        disabletimetableadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultTab();
                TimeTableFragment.vanishtemporarylecture();
                doneacademy = 0; doneteacher = 0; donecategory = 0; //initialize
            }
        });
        //시간표 추가 버튼을 눌렀을 때 동작하는 함수
        timetableadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultTab();
                TimeTableFragment.vanishtemporarylecture();
                doneacademy = 0; doneteacher = 0; donecategory = 0; //initialize
            }
        });
    }

    public static void showDefaultTab(){
        TimeTableFragment.setDaycellheight(TimeTableFragment.getDaycellheight()*2);
        TimeTableFragment.setDayCellAdapter();
        TimeTableFragment.getDaycellAdapter().notifyDataSetChanged();
        TimeTableFragment.getFirstCell().getLayoutParams().height = TimeTableFragment.getDaycellheight();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = 45 * (metrics.densityDpi / 160f);
        TimeTableFragment.getTopbanner().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int)dp));
        defaultbottom.setLayoutParams(new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT));
        timetablebottom.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
    }

    //눌렸던 탭을 원상복구 시키는 코드
    public void setTapDefault(int prev){
        switch(prev){
            case 0:
                hometaptext.setTypeface(null, Typeface.NORMAL);
                hometaptext.setTextColor(Color.parseColor("#000000"));
                hometapimage.setImageResource(R.drawable.home);
                break;
            case 1:
                searchtaptext.setTypeface(null, Typeface.NORMAL);
                searchtaptext.setTextColor(Color.parseColor("#000000"));
                searchtapimage.setImageResource(R.drawable.searchtap);
                break;
            case 2:
                timetabletext.setTypeface(null, Typeface.NORMAL);
                timetabletext.setTextColor(Color.parseColor("#000000"));
                timetableimage.setImageResource(R.drawable.schedule);
                break;
            case 3:
                boardtaptext.setTypeface(null, Typeface.NORMAL);
                boardtaptext.setTextColor(Color.parseColor("#000000"));
                boardtapimage.setImageResource(R.drawable.boardtap);
                break;
            case 4:
                mytaptext.setTypeface(null, Typeface.NORMAL);
                mytaptext.setTextColor(Color.parseColor("#000000"));
                mytapimage.setImageResource(R.drawable.mypagetap);
                break;
        }
    }

    //눌린 탭을 표시하는 코드
    public void setTapNext(int next){
        switch(next){
            case 0:
                hometaptext.setTypeface(null, Typeface.BOLD);
                hometaptext.setTextColor(Color.parseColor("#e26e6e"));
                hometapimage.setImageResource(R.drawable.homeorange);
                break;
            case 1:
                searchtaptext.setTypeface(null, Typeface.BOLD);
                searchtaptext.setTextColor(Color.parseColor("#e26e6e"));
                searchtapimage.setImageResource(R.drawable.searchorange);
                break;
            case 2:
                timetabletext.setTypeface(null, Typeface.BOLD);
                timetabletext.setTextColor(Color.parseColor("#e26e6e"));
                timetableimage.setImageResource(R.drawable.scheduleorange);
                break;
            case 3:
                boardtaptext.setTypeface(null, Typeface.BOLD);
                boardtaptext.setTextColor(Color.parseColor("#e26e6e"));
                boardtapimage.setImageResource(R.drawable.boardorange);
                break;
            case 4:
                mytaptext.setTypeface(null, Typeface.BOLD);
                mytaptext.setTextColor(Color.parseColor("#e26e6e"));
                mytapimage.setImageResource(R.drawable.myorange);
                break;
        }
    }

    public void setBelowTap(){

        setTapViewID();

        //탭 누를떄 변경 설정
        hometap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(prev == 0 && next == 0){
                        setTapNext(0);
                    }
                    else{
                        setTap(prev, next);
                    }
                    hometap.setBackgroundColor(Color.parseColor("#ffffff"));
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    prev = next;
                    next = 0;
                    viewPager.setCurrentItem(0,false);
                    hometap.setBackgroundColor(Color.parseColor("#e7e7e7"));
                    return true;
                }
                return false;
            }
        });

        searchtap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(prev == 1 && next == 1){
                        setTapNext(1);
                    }
                    else{
                        setTap(prev, next);
                    }
                    searchtap.setBackgroundColor(Color.parseColor("#ffffff"));
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    prev = next;
                    next = 1;
                    viewPager.setCurrentItem(1,false);
                    searchtap.setBackgroundColor(Color.parseColor("#e7e7e7"));
                    return true;
                }
                return false;
            }
        });
        boardtap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(prev == 3 && next == 3){
                        setTapNext(3);
                    }
                    else{
                        setTap(prev, next);
                    }
                    boardtap.setBackgroundColor(Color.parseColor("#ffffff"));
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    prev = next;
                    next = 3;
                    viewPager.setCurrentItem(3,false);
                    boardtap.setBackgroundColor(Color.parseColor("#e7e7e7"));
                    return true;
                }
                return false;
            }
        });

        timetabletap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(prev == 2 && next == 2){
                        setTapNext(2);
                    }
                    else{
                        setTap(prev, next);
                    }
                    timetabletap.setBackgroundColor(Color.parseColor("#ffffff"));
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    prev = next;
                    next = 2;
                    viewPager.setCurrentItem(2,false);
                    timetabletap.setBackgroundColor(Color.parseColor("#e7e7e7"));
                    return true;
                }
                return false;
            }
        });


        mytap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(prev == 4 && next == 4){
                        setTapNext(4);
                    }
                    else{
                        setTap(prev, next);
                    }
                    mytap.setBackgroundColor(Color.parseColor("#ffffff"));
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    prev = next;
                    next = 4;
                    viewPager.setCurrentItem(4,false);
                    mytap.setBackgroundColor(Color.parseColor("#e7e7e7"));
                    return true;
                }
                return false;
            }
        });


    }

    public static int getAcademyinputstatus(){
        return academyinputstatus;
    }

    public static int getTeacherinputstatus(){
        return teacherinputstatus;
    }

    public static void setAcademyinputstatus(int a){
        academyinputstatus = a;
    }

    public static void setTeacherinputstatus(int a){
        teacherinputstatus = a;
    }

    public static int getCategoryinputstatus(){
        return categoryinputstatus;
    }

    public static void setCategoryinputstatus(int a){
        categoryinputstatus = a;
    }

    class IAmABackgroundTask extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {

            //하단 탭 준비
            viewPager = mainp.findViewById(R.id.pager);
            viewPager.setSwipeable(false);
            setBelowTap();

            /*Define ViewPager*/
            adapter = new PageAdapter
                    (getChildFragmentManager(), 5); //tabLayout.getTabCount() = 5

            viewPager.setOffscreenPageLimit(5);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);

            prev = 0;
            next = 0;
            setTapNext(0);

        }

        @Override
        protected void onPostExecute(Boolean result) {

            setTimeTableArbitraryAdd();

        }

        @Override
        protected Boolean doInBackground(String... params) {


            return true;
        }
    }





}
