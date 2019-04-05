package com.softfinger.seunghyun.daechilife;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.softfinger.seunghyun.daechilife.BoardFragment.BoardFragment;
import com.softfinger.seunghyun.daechilife.DataLoad.AmazonClientManager;
import com.softfinger.seunghyun.daechilife.HomeFragment.HomeFragment;
import com.softfinger.seunghyun.daechilife.MyFragment.MyFragment;
import com.softfinger.seunghyun.daechilife.PageShow.MainPage;
import com.softfinger.seunghyun.daechilife.PageShow.PageAdapter;
import com.softfinger.seunghyun.daechilife.SearchFragment.SearchFragment;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.AddClassActivity;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.AddClassTimeTableActivity;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.TimeTableFragment;

import User.UserClass;

//변경된 개발 사항: 로그인 전과 로그인 후가 분류되어야만 함.
//해당 클래스는 처음 어플 시작되는 경우 계속 유지되는 Activity. Identity사진 보여주고 메인 어플화면으로 전환.
//최승현 바보
public class FirstActivity extends AppCompatActivity {

    public static AmazonClientManager clientManager = null;
    static Context context;
    static Activity activity;
    static AppCompatActivity compatActivity;

    static android.support.v4.app.FragmentManager manager;
    static android.support.v4.app.FragmentTransaction transaction;
    static FragmentManager myfragmentManager;
    static FragmentTransaction myfragmentTransaction;
    static MainPage myfragment;

    /*로그인 상태*/
    static UserClass user = null;
    static boolean loginstatus = false;

    /*첫 광고*/
    RelativeLayout firstshowlayout;
    int readytime = 0;

    /*페이지 클릭시 여기서 전환*/
    static int pagenumber;

    /*탭 구성요소 선언*/
    static android.support.v4.app.Fragment homefragment, searchfragment, timetablefragment, boardfragment, mypagefragment;
    static int fragmentready;

    //데이터 준비, Fragement들이다 Ready가 된다면, 그 때서야 창을 전환해서 어플을 실행. 그동안 광고화면 보여주기.
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activity = this;
        compatActivity = this;
        context = getApplicationContext();
        //clientManager = new AmazonClientManager(this);

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* 페이지 넘기는 역할 */
        pagenumber = 0;
        fragmentready = 0;

        //Fragment 전환하는 역할
        setContentView(R.layout.container);
        manager = getSupportFragmentManager();
        myfragmentManager = getFragmentManager();

        //광고페이지
        firstshowlayout = findViewById(R.id.firstshowlayout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(readytime == 1){
                    firstshowlayout.setVisibility(View.INVISIBLE);
                    startHomeContainer(); //준비가 됐지만 시간이 5초가 지나지 않았을 때 작동 시작.
                    readytime = 3; //실행됐으니 실행안해두 됨.
                }
                else{
                    readytime = 2; //5초가 지났지만 데이터들이 로딩이 안됐을때는 원상복귀
                }
            }
        }, 4000);

        new IAmABackgroundTask().execute();
    }


    public static void startHomeContainer(){

        myfragmentTransaction = myfragmentManager.beginTransaction();
        myfragment = new MainPage();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container, myfragment);
        transaction.commit();

    }

    class IAmABackgroundTask extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            //데이터가 준비되면 창 전환 시작
            if(readytime == 0){
                readytime = 1; //데이터가 미리 준비됐으므로 광고시간 증가
            }
            else if(readytime == 2){
                firstshowlayout.setVisibility(View.INVISIBLE);
                startHomeContainer(); //데이터가 3초보다 늦게 준비됐으므로 이때 실행
            }

        }

        @Override
        protected Boolean doInBackground(String... params) {

            homefragment = new HomeFragment();
            searchfragment = new SearchFragment();
            timetablefragment = new TimeTableFragment();
            boardfragment = new BoardFragment();
            mypagefragment = new MyFragment();

            return true;
        }
    }

    /*start activity*/
    public static void startaddclassactivity(){
        Intent intent = new Intent(context, AddClassActivity.class);
        activity.startActivity(intent);
    }

    public static void startaddclasstimetableactivity(){
        Intent intent = new Intent(context, AddClassTimeTableActivity.class);
        activity.startActivity(intent);
    }

    public static void finishAddClassActivity(){
        AddClassActivity.finishActivity();
        AddClassTimeTableActivity.finishactivity();
    }



    public static Fragment getHomefragment(){
        return homefragment;
    }

    public static Fragment getMypagefragment(){
        return mypagefragment;
    }

    public static Fragment getBoardfragment(){
        return boardfragment;
    }

    public static Fragment getSearchfragment(){
        return searchfragment;
    }

    public static Fragment getTimetablefragment(){
        return timetablefragment;
    }

    public static AppCompatActivity getActivity(){return compatActivity;}

}
