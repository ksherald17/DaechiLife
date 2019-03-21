package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.PageShow.AddClassPageAdapter;
import com.softfinger.seunghyun.daechilife.PageShow.PageAdapter;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

/* ViewPager로 변경되는 레이아웃 */

public class AddClassActivity extends AppCompatActivity {

    static Activity activity;
    TabLayout addclasstab;
    ImageView goback;
    static TextView deletesetting;

    //Viewpage
    static AddClassTimeTableFragment addclasstimetablefragment; //내 시간표
    static AddClassNewFragment addclassnewfragement; //새 강좌추가
    static AddClassMyFragment addclassmyfragment; //관심 강좌

    static int deletego = 0; //page상태가 편집 상태인지 아닌지 확인하는 코드; => 페이지 전환마다 초기화, Adapter은 이 변수로 확인.

    static int pagenumber;

    ViewPager viewPager;
    AddClassPageAdapter classPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclasstransparent);
        setDeleteSetting();
        setTabViewPager();
    }

    public void setTabViewPager(){
        //setViewByID
        addclasstab = findViewById(R.id.addclasstablayout);
        goback = findViewById(R.id.getoutaddclass);

        //goback
        goback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AddClassTimeTableActivity.finishactivity();
                activity.finish();
            }
        });

        //setFragment
        addclasstimetablefragment = new AddClassTimeTableFragment();
        addclassnewfragement = new AddClassNewFragment();
        addclassmyfragment = new AddClassMyFragment();

        //setTab
        addclasstab.addTab(addclasstab.newTab().setText("수업 검색"));
        addclasstab.addTab(addclasstab.newTab().setText("관심 강좌"));
        addclasstab.addTab(addclasstab.newTab().setText("내 시간표"));

        viewPager = findViewById(R.id.addclassviewpager);

        /*Define ViewPager*/
        classPageAdapter = new AddClassPageAdapter(getSupportFragmentManager(), 3);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(classPageAdapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(addclasstab));

        hideSetting();

        addclasstab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setReset();
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pagenumber = 0;
                    hideSetting();
                }
                else if(tab.getPosition() == 1){
                    pagenumber = 1;
                    showSetting();
                }
                else{
                    pagenumber = 2;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //페이지 전환시 초기화시키는 함수
    public void setReset(){
        ArrayList<LectureClass> mainlecturelist = TimeTableFragment.getMaintablelecuturelist();
        ArrayList<LectureClass> mylecturelist = TimeTableFragment.getMylecturelist();
        if(deletesetting.getText().toString().equals("삭제")){

            for(int i = 0; i < mainlecturelist.size(); i++){
                mainlecturelist.get(i).setADD_OnMAINTABLELIST_DELETE(0); //모든수 초기화
            }

            for(int j = 0; j < mylecturelist.size(); j++){
                mylecturelist.get(j).setADD_OnMYLIST_DELETE(0);
            }
            deletesetting.setText("편집");
            deletego = 0;
        }

        for(int i = 0; i < mylecturelist.size(); i++){
            mylecturelist.get(i).setADD_OnMYLIST_TOUCHED(0);
        }

        TimeTableFragment.deleteLectureTemp(AddClassTimeTableActivity.getTimeTableLayout());
        AddClassMyFragment.addClassAdapter.notifyDataSetChanged();
        AddClassTimeTableFragment.addClassAdapter.notifyDataSetChanged();
    }

    public void setDeleteSetting(){
        deletesetting = findViewById(R.id.addsetting);
        deletesetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(deletesetting.getText().toString().equals("편집")){
                    setDeleteElement(1);
                    deletego = 1;
                    deletesetting.setText("삭제");
                    AddClassTimeTableFragment.addClassAdapter.notifyDataSetChanged();
                    AddClassMyFragment.addClassAdapter.notifyDataSetChanged();
                }

                else if(deletesetting.getText().toString().equals("삭제")){
                    setDeleteElement(0);
                    deletego = 0;
                    deletesetting.setText("편집");
                    AddClassTimeTableFragment.addClassAdapter.notifyDataSetChanged();
                    AddClassMyFragment.addClassAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    public void setDeleteElement(int delete){

        int deleted = 0;

        if(delete == 0){

            int pagenum = getPagenumber();

            if(pagenum == 0) {//show filter
            }
            else if(pagenum == 1){

                ArrayList<LectureClass> mylecuturelist = TimeTableFragment.getMylecturelist();

                for(int i = 0; i < mylecuturelist.size(); i++){

                    if(mylecuturelist.get(i).getADD_OnMYLIST_DELETE() == 0){
                        continue;
                    }

                    else{
                        //정보초기화
                        mylecuturelist.get(i).setADD_OnMYLIST(0);
                        mylecuturelist.get(i).setADD_OnMYLIST_TOUCHED(0);
                        mylecuturelist.get(i).setADD_OnMYLIST_DELETE(0);
                        TimeTableFragment.deleteMyLecture(mylecuturelist.get(i));

                        //삭제 명시
                        deleted = 1;
                    }
                }

                //지운적이 있다면 정리코드
                if(deleted == 1){
                    TimeTableFragment.deleteLectureTemp(AddClassTimeTableActivity.getTimeTableLayout());
                    TimeTableFragment.setMyLectureAfterDelete();
                    deleted = 0;
                }

                AddClassMyFragment.addClassAdapter.notifyDataSetChanged();
            }

            else if(pagenum == 2){

                ArrayList<LectureClass> lecturelist = TimeTableFragment.getMaintablelecuturelist();
                for(int i = 0; i < lecturelist.size(); i++){

                    if(lecturelist.get(i).getADD_OnMAINTABLELIST_DELETE() == 0){
                        continue;
                    }
                    else if(lecturelist.get(i).getADD_OnMAINTABLELIST_DELETE() == 1){

                        //정보초기화
                        lecturelist.get(i).setADD_OnMAINTABLELIST(0);
                        lecturelist.get(i).setADD_OnMAINTABLELIST_DELETE(0);

                        //시간표에서 삭제
                        TimeTableFragment.deleteTextView(lecturelist.get(i), TimeTableFragment.getTimetablelayout());
                        TimeTableFragment.deleteTextView(lecturelist.get(i), AddClassTimeTableActivity.getTimeTableLayout());
                        TimeTableFragment.deleteMainLecture(lecturelist.get(i));
                        AddClassTimeTableFragment.addClassAdapter.notifyDataSetChanged();
                        AddClassMyFragment.addClassAdapter.notifyDataSetChanged();

                        //삭제 명시
                        deleted = 1;

                    }
                }

                //지운적이 있다면 정리코드
                if(deleted == 1){
                    TimeTableFragment.setMainLectureListAfterDelete();
                    deleted = 0;
                }

            }

        }

        else{

            int pagenum = getPagenumber();
            if(pagenum == 0) {//show filter
            }
            else if(pagenum == 1){
                AddClassMyFragment.addClassAdapter.notifyDataSetChanged();
            }
            else if(pagenum == 2){
                AddClassTimeTableFragment.addClassAdapter.notifyDataSetChanged();
            }

        }
    }

    public static void hideSetting(){
        deletesetting.setVisibility(View.INVISIBLE);
    }

    public static void showSetting(){
        deletesetting.setVisibility(View.VISIBLE);
    }

    public static void finishActivity(){

        //setUnClick
        if(TimeTableFragment.getMylecturelist() != null){
            for(int i = 0; i < TimeTableFragment.getMylecturelist().size(); i++){
                if(TimeTableFragment.getMylecturelist().get(i).getADD_OnMYLIST() == 1){
                    TimeTableFragment.getMylecturelist().get(i).setADD_OnMYLIST_TOUCHED(0);
                }
            }
        }

        if(TimeTableFragment.getMaintablelecuturelist() != null){
            for(int i = 0; i < TimeTableFragment.getMaintablelecuturelist().size(); i++){
                TimeTableFragment.getMaintablelecuturelist().get(i).setADD_OnMAINTABLELIST_TOUCHED(0);
            }
        }

        activity.setContentView(TimeTableFragment.getTimeTableView());
        activity.finish();
    }

    public static Activity getActivity(){
        return activity;
    }

    public static AddClassTimeTableFragment getAddclasstimetablefragment(){
        return addclasstimetablefragment;
    }

    public static AddClassMyFragment getAddClassMyFragment(){
        return addclassmyfragment;
    }

    public static int getDeletego(){return deletego;}

    public static AddClassNewFragment getAddClassNewFragment(){
        return addclassnewfragement;
    }

    public static int getPagenumber(){ return pagenumber; }
}
