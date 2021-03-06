package com.softfinger.seunghyun.daechilife.HomeFragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.softfinger.seunghyun.daechilife.BoardFragment.BoardFragment;
import com.softfinger.seunghyun.daechilife.DataModel.BoardHotElement;
import com.softfinger.seunghyun.daechilife.DataModel.ClassRecommendElement;
import com.softfinger.seunghyun.daechilife.DataModel.HomeRecommendLecture;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.MyFragment.MyFragment;
import com.softfinger.seunghyun.daechilife.R;
import com.softfinger.seunghyun.daechilife.SearchFragment.SearchFragment;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.TimeTableFragment;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class HomeFragment extends android.support.v4.app.Fragment {

    /*액티비티, 콘텍스트*/
    static Context context;
    View homepage;

    /*탑 광고 슬라이더*/
    SliderLayout slider;

    /*홈 차트 화면*/
    RecyclerView homedlchart, homedlclassrecommend, homedlHotBoard, homedlIkmyungBoard, homeschoolrecommendrv;
    DLSchoolRecommendAdapter dlSchoolRecommendAdapter;
    DLHomeChartAdapter dlHomeChartAdapter;
    DLClassRecommendAdapter dlClassRecommendAdapter;
    DLBoardHotElementAdapter dlBoardHotElementAdapter;
    DLBoardIkMyungAdapter dlBoardIkMyungAdapter;
    DLEnterAdapter dlEnterAdapter;
    TabLayout maincharttab;
    ImageView mainbanner;

    List<TeacherElement> sampledb;
    List<ClassRecommendElement> samplecrdb;
    List<BoardHotElement> samplehotboarddb, sampleIkMyungBoardDB;
    List<HomeRecommendLecture> sampleHRdb;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        homepage = myInflater.inflate(R.layout.homepage, null);

        new IAmABackgroundTask().execute();

        return homepage;
    }

    //이미지 광고 배너 전환 설정 함수
    public void setSlider(){
        slider.setIndicatorAnimation(IndicatorAnimations.SCALE_DOWN); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        slider.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        slider.setScrollTimeInSec(5);

        for (int i = 0; i <= 2; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(FirstActivity.getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://daechilife.s3.ap-northeast-2.amazonaws.com/mainslide/top.png");
                    break;
                case 1:
                    sliderView.setImageUrl("https://daechilife.s3.ap-northeast-2.amazonaws.com/mainslide/example.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://daechilife.s3.ap-northeast-2.amazonaws.com/mainslide/example2.jpg");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER);

            slider.addSliderView(sliderView);
        }
    }

    //메인 홈 차트 출력 함수
    public void sethomeDLchart(){

        //mainbanner = homepage.findViewById(R.id.mainbanner);
        //Resources res = getResources();
        //Bitmap src = BitmapFactory.decodeResource(res, R.mipmap.mainbanner1);
        //RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
        //dr.setCornerRadius(15);
        //mainbanner.setImageDrawable(dr);

        //연습용 DB세팅
        sampledb = new ArrayList<>();
        sampledb.add(new TeacherElement("이지정", "KNS어학원", "영어"));
        sampledb.add(new TeacherElement("이승철", "시대인재", "수학"));
        sampledb.add(new TeacherElement("홍은영", "미래탐구", "과탐"));
        sampledb.add(new TeacherElement("강민찬", "러셀", "국어"));
        sampledb.add(new TeacherElement("김동욱", "홍문,세정", "국어"));

        homedlchart.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을
        // 한다.
        homedlchart.setLayoutManager(llm);
        dlHomeChartAdapter = new DLHomeChartAdapter(sampledb, context);
        homedlchart.setAdapter(dlHomeChartAdapter);

    }

    //메인 홈 경기고 학부모 추천 강의
    public void setHomeRecommendSchool(){
        sampleHRdb = new ArrayList<>();
        HomeRecommendLecture l1 = new HomeRecommendLecture();
        l1.setAcademy("대찬학원");l1.setCategory("국어");l1.setTeacher("김선화 시 총정리");l1.setTime("일요일 13:00~15:00");
        HomeRecommendLecture l2 = new HomeRecommendLecture();
        l2.setAcademy("영독학원");l2.setCategory("영어");l2.setTeacher("영독인 고3 내신 강의");l2.setTime("월요일 18:00~22:00");
        HomeRecommendLecture l3 = new HomeRecommendLecture();
        l3.setAcademy("개념상상");l3.setCategory("수학");l3.setTeacher("이승철 고2 내신 강의");l3.setTime("수 18:30~22:00, 토 18:30~22:00");
        HomeRecommendLecture l4 = new HomeRecommendLecture();
        l4.setAcademy("미래탐구");l4.setCategory("과학");l4.setTeacher("최수준 서바이벌 모의고사");l4.setTime("일요일 15:00~18:00");
        sampleHRdb.add(l1);sampleHRdb.add(l2);sampleHRdb.add(l3);sampleHRdb.add(l4);

        homeschoolrecommendrv.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을 한다.
        homeschoolrecommendrv.setLayoutManager(llm);
        dlSchoolRecommendAdapter = new DLSchoolRecommendAdapter(sampleHRdb, context);
        homeschoolrecommendrv.setAdapter(dlSchoolRecommendAdapter);


    }

    //메인 홈 유명게시판 추천 함수
    public void sethomeDLHotBoard(){

        samplehotboarddb = new ArrayList<>();
        BoardHotElement c1 = new BoardHotElement("경기고 수학 선생님에 대해 시험을...", "예진밍", 8, 850);
        c1.setDay("19.03.07");
        BoardHotElement c2 = new BoardHotElement("대찬학원 김선화 선생님", "용환이", 5, 750);
        c2.setDay("19.03.04");
        BoardHotElement c3 = new BoardHotElement("깊은생각 입학시험에 관하여", "킹선호", 4, 651);
        c3.setDay("19.03.01");
        BoardHotElement c4 = new BoardHotElement("여름방학 공과대학 프론티어 캠프", "공돌이", 5, 450);
        c4.setDay("19.03.06");
        BoardHotElement c5 = new BoardHotElement("대치동 맛집 포마토스푼!!", "승현맘", 2, 350);
        c5.setDay("19.03.05");

        samplehotboarddb.add(c1);samplehotboarddb.add(c2);samplehotboarddb.add(c3);
        samplehotboarddb.add(c4);samplehotboarddb.add(c5);

        homedlHotBoard.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을 한다.
        homedlHotBoard.setLayoutManager(llm);
        dlBoardHotElementAdapter = new DLBoardHotElementAdapter(samplehotboarddb, context);
        homedlHotBoard.setAdapter(dlBoardHotElementAdapter);

    }

    public void sethomeDLIkMyungBoard(){

        sampleIkMyungBoardDB = new ArrayList<>();
        BoardHotElement c1 = new BoardHotElement("김선화 수업 들을만 한가요?", "예진밍", 8, 850);
        c1.setDay("19.03.07");
        BoardHotElement c2 = new BoardHotElement("대찬학원 이승철 선생님!", "용환이", 5, 750);
        c2.setDay("19.03.04");
        BoardHotElement c3 = new BoardHotElement("경기고 고1 내신 질문드립니다.", "킹선호", 4, 651);
        c3.setDay("19.03.01");
        BoardHotElement c4 = new BoardHotElement("고2 수학 내신 문제집 뭐가 좋을까요", "공돌이", 5, 450);
        c4.setDay("19.03.06");
        BoardHotElement c6 = new BoardHotElement("배고프다..!", "정현이", 0, 250);
        c6.setDay("19.03.06");
        sampleIkMyungBoardDB.add(c1);sampleIkMyungBoardDB.add(c2);sampleIkMyungBoardDB.add(c3);
        sampleIkMyungBoardDB.add(c4);sampleIkMyungBoardDB.add(c6);


        homedlIkmyungBoard.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을 한다.
        homedlIkmyungBoard.setLayoutManager(llm);
        dlBoardIkMyungAdapter = new DLBoardIkMyungAdapter(sampleIkMyungBoardDB, context);
        homedlIkmyungBoard.setAdapter(dlBoardIkMyungAdapter);

    }

    //대학입학전형 차트
    public void sethomeEntranceRV(){

        List<String> schoolname = new ArrayList<>();
        List<Integer> schoolimage = new ArrayList<>();
        schoolname.add("서울대학교"); schoolname.add("연세대학교");schoolname.add("이화여대");schoolname.add("고려대학교");
        schoolname.add("카이스트");schoolname.add("성균관대학교");
        schoolimage.add(R.mipmap.seoul);schoolimage.add(R.mipmap.akaraka);schoolimage.add(R.mipmap.ewha);
        schoolimage.add(R.mipmap.godae);schoolimage.add(R.mipmap.kaist);
        schoolimage.add(R.mipmap.seoungyunkwan);
        RecyclerView entranceRV = homepage.findViewById(R.id.entranceRV);
        entranceRV.setHasFixedSize(true); //recyclerView has fixed size
        dlEnterAdapter = new DLEnterAdapter(schoolname, schoolimage, context);
        entranceRV.setAdapter(dlEnterAdapter);

    }

    class IAmABackgroundTask extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {

            //slider = homepage.findViewById(R.id.bannerSlider);
            homedlchart = homepage.findViewById(R.id.dlhomechart);
            homedlHotBoard = homepage.findViewById(R.id.homeboardhotRV);
            homedlIkmyungBoard = homepage.findViewById(R.id.hometalkRV);
            maincharttab = homepage.findViewById(R.id.maincharttab);
            homeschoolrecommendrv = homepage.findViewById(R.id.homerecommendschoolRV);

            sethomeDLchart();
            sethomeDLHotBoard();
            sethomeDLIkMyungBoard();
            sethomeEntranceRV();
            setHomeRecommendSchool();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //setSlider();
            wrapTabIndicatorToTitle(maincharttab, 1, 2);
        }

        @Override
        protected Boolean doInBackground(String... params) {


            return true;
        }
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }


}
