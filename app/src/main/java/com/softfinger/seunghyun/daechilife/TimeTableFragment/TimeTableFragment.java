package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.HomeFragment.DLClassRecommendAdapter;
import com.softfinger.seunghyun.daechilife.HomeFragment.HomeFragment;
import com.softfinger.seunghyun.daechilife.PageShow.MainPage;
import com.softfinger.seunghyun.daechilife.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

//개발 유의사항: TimeTableFragment에 ArrayList<LectureClass>에 해당하는 시간표에 저장된 함수는 TimeTable로 부터 가져와야하며 DB에서 꺼내오는 함수.
//추가 개발필요사항: 검색창, 새로운 시간표 창, 삭제 코드

public class TimeTableFragment extends android.support.v4.app.Fragment{

    /*액티비티, 콘텍스트*/
    static Context context;
    static View timetablepage;
    static RecyclerView daycellRV, timecellRV, timetablecellRV;
    static DayCellAdapter dayCellAdapter;
    TimeCellAdapter timeCellAdapter;
    TimeTableCellAdapter timeTableCellAdapter;
    ImageView addclassplus;

    /*시간표 구조*/
    //크기와 레이아웃과 관련된 변수
    static int timecellheight, timecellwidth, daycellheight, daycellwidth, timetablecellheight, timetablecellwidth;
    static int actionBarHeight;
    static ArrayList<String> day, time, timetable;
    static RelativeLayout firstcell;
    static RelativeLayout timetablelayout;

    //시간표 정보와 관련된 변수
    static ArrayList<LectureClass> maintablelecuturelist; //시간표에 보여지는 수업 리스트
    static ArrayList<ArrayList<Integer>> idlist; //textView의 idlist <위 ArrayList와 함께 움직여야 함>
    static ArrayList<LectureClass> mylecturelist; //관심저장한 수업 리스트
    static int textviewid= 0; //혹시나 중복 문제가 발생시 여기서 생길 가능성이 큼.
    static ArrayList<Integer> idtempsublist; //중복 저장이 안되기 위해 임시로 잠시 저장해두는함수.

    //임의로 시간 추가하는 함수와 관련된 변수
    static ArrayList<LectureClass> templecuturelist; //시간표에 보여지는 수업 리스트
    static ArrayList<Integer> tempidlist; //textView의 idlist <위 ArrayList와 함께 움직여야 함>
    static int temptextviewid= 0; //혹시나 중복 문제가 발생시 여기서 생길 가능성이 큼.
    static RelativeLayout topbanner;

    //시간표 색상
    static ArrayList<String> colorhexcodelist;
    static int currentlecturesize = 0; //지우기 떄 -하는 것을 주의, 시간표 변경시 무조건 초기화 요구 static이기 떄문

    //드래그시 시간 추가할때 관련된 변수
    static int tempstartweek;
    static int tempstarttime;
    static ArrayList<Integer> tempsublist;
    static int temptextid = 555;
    static int tempstart;
    static int templength;
    static int detbeg = 0; //드래그 작동중인지를 확인하는 코드
    static String temptimesaved;


    //드래그시 임시 저장
    static ArrayList<Integer> savedidtemp;
    static ArrayList<ArrayList<Integer>> timetemp;

    int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        timetablepage = myInflater.inflate(R.layout.timetablepage, null);
        new IAmABackgroundTask().execute();

        return timetablepage;
    }

    //빈 공간을 선택하면 작동하는 함수
    public static void initiatenewLecture(int position){

        //initiate variables
        tempstart = 0;
        templength = 0;
        detbeg = 0;
        tempstartweek = 0;
        tempstarttime = 0;

        Log.e("POS", Integer.toString(position));
        tempsublist = new ArrayList<>();
        tempstartweek = position%7;
        tempstarttime = (position/7); //time/2 + 8 is the lecture time

    }

    /*
     *  손가락 움직임을 추적하고 해석하는 함수
     *  1) 이 때 먼저, 있는 수업과 시간대가 겹칠때 대응하는 Case가 존재해야 함.
     *  2) 같은 요일을 선택한 경우 대체하시겠습니까 라는 Dialog 문의
     *  3) 요일을 탭한 경우 동일 시간대에 해당 강의가 생성되어야 함.
     *
     */

    //손가락이 움직일때 따라서 만드는 함수
    public static void followfinger(int position){

        tempstart = position%7;
        int tempstarttime2 = (position/7); //열만 찾아내면 됨;
        templength = 0;
        int beginparameter;

        if(detbeg == 0){

            detbeg = 1;
            beginparameter = tempstarttime;
            buildtemporarylecture(beginparameter, tempstart, 1); //initiate code

        }

        if(tempstarttime2 > tempstarttime){ //더 긴시간 방향으로 움직일 때

            detbeg = 1;
            templength = tempstarttime2 - tempstarttime;
            beginparameter = tempstarttime;
            for(int i = 0; i < tempsublist.size(); i++){
                TextView textView = timetablelayout.findViewById(tempsublist.get(i));
                timetablelayout.removeView(textView);
                Log.e("제거id", Integer.toString(tempsublist.get(i)));
            }

            tempsublist = new ArrayList<>();
            Log.e("초기화", Integer.toString(tempsublist.size()));
            buildtemporarylecture(beginparameter, tempstart, templength);

        }

        else if(tempstarttime2 < tempstarttime){  //이전것들을 다 지워야 해

            detbeg = 1;
            templength = tempstarttime - tempstarttime2;
            beginparameter = tempstarttime2;

            for(int i = 0; i < tempsublist.size(); i++){
                TextView textView = timetablelayout.findViewById(tempsublist.get(i));
                timetablelayout.removeView(textView);
            }
            tempsublist = new ArrayList<>();
            buildtemporarylecture(beginparameter, tempstart, templength);
            tempstarttime = tempstarttime2;

        }

        else if(tempstarttime2 == tempstarttime || templength == 0){

        }
    }

    public static String changeinttoday(int day1){

        String day="";

        switch (day1){
            case 0:
                day = "월";
                break;
            case 1:
                day = "화";
                break;
            case 2:
                day = "수";
                break;
            case 3:
                day = "목";
                break;
            case 4:
                day = "금";
                break;
            case 5:
                day = "토";
                break;
            case 6:
                day = "일";
                break;
        }
        return day;
    }

    //손가락을 땔때 그정보를 저장하는 함수
    public static void savefingerprint(){

        if(tempsublist == null || tempsublist.size() == 0){
            Log.e("흥2", "너가 문제니");
            return;
        }

        int textviewid = tempsublist.get(0); //그 뒤 초기화될 것임.
        savedidtemp.add(new Integer(textviewid));

        ArrayList<Integer> timeinfo = new ArrayList<>(); //요일, 시작시간,시작분, 끝시간, 끝분

        int dayI = tempstart; //요일 0: 월

        /* 요일 */
        String day = changeinttoday(dayI);
        timeinfo.add(dayI); //index 0: 요일

        /* 시작시간 */
        int starthour = (tempstarttime/2) + 8;
        int startmin = 0;
        if(Math.round(((float)tempstarttime)/2.0f) - (int)(tempstarttime/2) != 0){
            startmin = 30;
        };
        timeinfo.add(starthour); //index 1: 시작시간
        timeinfo.add(startmin); //index 2: 시작분

        String starttime, endingtime;
        if(starthour > 12){
            if(startmin == 0){
                starttime = Integer.toString(starthour-12) + " : " + Integer.toString(startmin) + "0";
            }
            else{
                starttime = Integer.toString(starthour-12) + " : " + Integer.toString(startmin);
            }

        }else{
            if(startmin == 0){
                starttime = Integer.toString(starthour) + " : " + Integer.toString(startmin) + "0";
            }
            else{
                starttime = Integer.toString(starthour) + " : " + Integer.toString(startmin);
            }
        }


        int endtime = tempstarttime + templength;
        int endhour = (endtime/2) + 8;
        int endmin = 0;
        if(Math.round(((float)endtime)/2.0f) - (int)(endtime/2) != 0){
            endmin = 30;
        };

        timeinfo.add(endhour); //index 3: 끝시간
        timeinfo.add(endmin); //index 4: 끝분

        if(endhour > 12){
            if(endmin == 0){
                endingtime = Integer.toString(endhour-12) + " : " + Integer.toString(endmin) + "0";
            }
            else{
                endingtime = Integer.toString(endhour-12) + " : " + Integer.toString(endmin);
            }
        }else{
            if(endmin == 0){
                endingtime = Integer.toString(endhour) + " : " + Integer.toString(endmin) + "0";
            }
            else{
                endingtime = Integer.toString(endhour) + " : " + Integer.toString(endmin);
            }
        }

        setTemptimesaved(day + " " + starttime + " ~ " + endingtime);


    }

    //Finger Processing Function. Synchronize. 손가락의 위치와 이미 수업이 있는지, 또 어디에 있는지를 세부적으로 조절해주는 함수
    public static void fingerprocessing(){

    }

    //모든 정보를 지우고 초기화하는 함수
    public static void vanishtemporarylecture(){

        for(int i = 0; i < tempsublist.size(); i++){
            TextView textView = timetablelayout.findViewById(tempsublist.get(i));
            timetablelayout.removeView(textView);
            Log.e("vanish", "제거id: " + Integer.toString(tempsublist.get(i)));
        }

        tempsublist = new ArrayList<>();
        Log.e("제거코드 종료", Integer.toString(tempsublist.size()));

        /* init ids */
        detbeg = 0;
        temptextid = 555;
        tempsublist = null;
        tempstarttime = 0;
        tempstartweek = 0;
        savedidtemp = new ArrayList<>();
        timetemp = new ArrayList<>();
    }


    //손가락에 따라 생성되는 임시 코드를 작성
    public static void buildtemporarylecture(int topparameter, int leftparameter, int heightsize){

        TextView addclassTV = new TextView(context);
        addclassTV.setId(temptextid);
        //addclassTV.setText(classname);
        Typeface face = Typeface.createFromAsset(FirstActivity.getActivity().getAssets(),
                "fonts/nanumgodic.ttf");
        addclassTV.setTypeface(face);
        addclassTV.setTextColor(Color.parseColor("#ffffff"));
        addclassTV.setBackgroundColor(Color.parseColor("#e0e0e0"));
        addclassTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); //textsize
        tempsublist.add(new Integer(temptextid));
        ++temptextid;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                timetablecellwidth,
                timetablecellheight*heightsize); //*4를 변경하면 됨

        int margintop, marginbottom, marginleft, marginright;

        margintop = daycellheight + timetablecellheight * topparameter; //24를 변경하면 됨
        marginleft = timecellwidth + timetablecellwidth * leftparameter; //0을 switch문으로 변경하면 됨.
        marginright = 0;
        marginbottom = 0;

        lp.setMargins(marginleft, margintop, marginright, marginbottom);
        addclassTV.setLayoutParams(lp);
        addclassTV.setGravity(Gravity.CENTER);
        timetablelayout.addView(addclassTV);
        Log.e("추가ID", Integer.toString(temptextid-1));
    }

    public int turnpxtoweek(float width){
        return (int)((width - timecellwidth)/timetablecellwidth);
    }

    public int turnpxtotime(float height){
        return (int)((height - actionBarHeight - daycellheight) / timetablecellheight);
    }

    /*수업 추가*/
    //플러스 버튼 누르면 새로운 레이아웃 생성
    public void setAddClass(){
        addclassplus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirstActivity.startaddclasstimetableactivity();
            }
        });
    }

    //원하는 위치에 textView를 생성하는 함수
    public static TextView createTextViewByTime(int heightsize, int topparameter, int leftparameter, String classname, RelativeLayout layout){

        TextView addclassTV = new TextView(context);
        addclassTV.setText(classname);
        Typeface face = Typeface.createFromAsset(FirstActivity.getActivity().getAssets(),
                "fonts/nanumgodic.ttf");
        addclassTV.setTypeface(face);
        addclassTV.setTextColor(Color.parseColor("#ffffff"));
        addclassTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); //textsize

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                timetablecellwidth,
                timetablecellheight*heightsize); //*4를 변경하면 됨

        int margintop, marginbottom, marginleft, marginright;

        margintop = daycellheight + timetablecellheight * topparameter; //24를 변경하면 됨
        marginleft = timecellwidth + timetablecellwidth * leftparameter; //0을 switch문으로 변경하면 됨.
        marginright = 0;
        marginbottom = 0;

        lp.setMargins(marginleft, margintop, marginright, marginbottom);
        addclassTV.setLayoutParams(lp);
        addclassTV.setGravity(Gravity.CENTER);
        layout.addView(addclassTV);

        return addclassTV;
    }

    //시간 문자열을 배치에 알맞게 변환해주는 함수
    public static ArrayList<ArrayList<Integer>> changeTimeStringtoArrayList(String time){

        ArrayList<ArrayList<Integer>> timelist = new ArrayList<>();

        String[] timepart = time.split("/");
        int size = timepart.length;

        for(int i = 0; i < size; i++){
            ArrayList<Integer> timelistelement = new ArrayList<>(); //구조 heightsize, marginTop, marginLeftparameter 크기 계산
            String day = timepart[i].replace(" ","").substring(0,1);

            /*시간 변환 함수*/
            String classtime = timepart[i].replace(" ","").substring(1);
            String[] timesplit = classtime.split("~");
            String starttime = timesplit[0]; String endtime = timesplit[1];

            int starttimehour, endtimehour, starttimemin, endtimemin;

            if(starttime.length() == 5){
                starttimehour = Integer.parseInt(starttime.substring(0, 2));
                starttimemin = Integer.parseInt(starttime.substring(3, 5));
            }else{
                starttimehour = Integer.parseInt(starttime.substring(0, 1));
                starttimemin = Integer.parseInt(starttime.substring(2, 4));
            }

            if(endtime.length() == 5){
                endtimehour = Integer.parseInt(endtime.substring(0, 2));
                endtimemin = Integer.parseInt(endtime.substring(3, 5));
            }else{
                endtimehour = Integer.parseInt(endtime.substring(0, 1));
                endtimemin = Integer.parseInt(endtime.substring(2, 4));
            }


            float starttimeFN = starttimehour;
            float endtimeFN = endtimehour;

            if(starttimemin == 30){
                starttimeFN += 0.5;
            }
            if(endtimemin == 30){
                endtimeFN += 0.5;
            }

            int height = (int)((endtimeFN - starttimeFN)*2);
            timelistelement.add(height); //textView 크기 결정

            int margintop = (int)((starttimeFN - 8)*2);
            timelistelement.add(margintop); //textView margrinTop 결정

            switch (day){ //textView marginLeft 결정
                case "월":
                    timelistelement.add(0);
                    break;
                case "화":
                    timelistelement.add(1);
                    break;
                case "수":
                    timelistelement.add(2);
                    break;
                case "목":
                    timelistelement.add(3);
                    break;
                case "금":
                    timelistelement.add(4);
                    break;
                case "토":
                    timelistelement.add(5);
                    break;
                case "일":
                    timelistelement.add(6);
                    break;

            }
            timelist.add(timelistelement);
        }

        return timelist;
    }

    //수업을 내 시간표에 대입하는 함수
    public static void insertLectureToTimeTable(LectureClass lecture, RelativeLayout layout){

        String time = lecture.getTime();
        ArrayList<ArrayList<Integer>> timelist = changeTimeStringtoArrayList(time); //이 사이즈가 textView의 개수
        ArrayList<Integer> idsublist = new ArrayList<>(); //이 List는 id에 관한 정보 list

        for(int i = 0; i < timelist.size(); i++){
            TextView createdTextView = createTextViewByTime(timelist.get(i).get(0), timelist.get(i).get(1), timelist.get(i).get(2),
                    lecture.getLecturename(), layout);
            createdTextView.setBackgroundColor(Color.parseColor(colorhexcodelist.get(currentlecturesize))); //동일 색상 지정
            createdTextView.setId(textviewid);
            idsublist.add(new Integer(textviewid)); //textview에 해당하는 id추가
            ++textviewid;
        }

        idtempsublist = new ArrayList<>();
        for(int i = 0; i < idsublist.size(); i ++){
            idtempsublist.add(idsublist.get(i));
        }

    }

    //
    public static void insertLectureToDB(LectureClass lecture){
        idlist.add(idtempsublist); //idlist에 추가
        ++currentlecturesize;
        maintablelecuturelist.add(lecture);
    }

    //수업을 임의로 넣어보는 함수
    public static void insertLectureTemp(LectureClass lecture, RelativeLayout layout){

        templecuturelist = new ArrayList<>();
        tempidlist = new ArrayList<>();

        String time = lecture.getTime();
        ArrayList<ArrayList<Integer>> timelist = changeTimeStringtoArrayList(time); //이 사이즈가 textView의 개수
        ArrayList<Integer> idsublist = new ArrayList<>(); //이 List는 id에 관한 정보 list

        for(int i = 0; i < timelist.size(); i++){
            TextView createdTextView = createTextViewByTime(timelist.get(i).get(0), timelist.get(i).get(1), timelist.get(i).get(2),
                    lecture.getLecturename(), layout);
            createdTextView.setBackgroundColor(Color.parseColor("#cccccc")); //동일 색상 지정
            createdTextView.setId(temptextviewid);
            idsublist.add(new Integer(temptextviewid)); //textview에 해당하는 id추가
            ++textviewid;
        }

        tempidlist = idsublist; //idlist에 추가
        templecuturelist.add(lecture);
    }

    public static void deleteLectureTemp(RelativeLayout layout){

        if(tempidlist != null) { //null point error
            for (int i = 0; i < tempidlist.size(); i++) {
                TextView textView = layout.findViewById(tempidlist.get(i));
                layout.removeView(textView);
            }

            templecuturelist = new ArrayList<>();
            tempidlist = new ArrayList<>();
        }

    }

    //수업 시간표 색상 설정하는 곳
    public void setColorList(){
        colorhexcodelist = new ArrayList<>();
        colorhexcodelist.add("#81d4fa");colorhexcodelist.add("#ffe082");colorhexcodelist.add("#ef9a9a");
        colorhexcodelist.add("#b0bec5");colorhexcodelist.add("#9575cd");colorhexcodelist.add("#c5e1a5");
        colorhexcodelist.add("#80cbc4");colorhexcodelist.add("#bcaaa4");colorhexcodelist.add("#81d4fa");

    }

    //TESTDB용 함수, 저장된 시간표, 관심저장된 시간표등을 설정함. 실제론 유저DB에서 가져와야 할 것임.
    public void setTestDB() {
        maintablelecuturelist = new ArrayList<>(); //처음에 내 시간표 수업 목록 객체화 백지상태.
        mylecturelist = new ArrayList<>();

        LectureClass lecture1 = new LectureClass("이승철", "개념상상");
        lecture1.setAge("고1");lecture1.setSubject("수학");lecture1.setLecturename("이승철 수1 심화");
        lecture1.setTime("월 18:30~22:30 / 수 18:30~22:30");

        LectureClass lecture2 = new LectureClass("이지정", "KNS어학원");
        lecture2.setAge("고3");lecture2.setSubject("영어");lecture2.setLecturename("이지정 SKY심화");
        lecture2.setTime("화 18:00~19:00 / 토 12:30~17:30");

        LectureClass lecture3 = new LectureClass("김선화", "대찬학원");
        lecture3.setAge("고2");lecture3.setSubject("국어");lecture3.setLecturename("김선화 수능대비");
        lecture3.setTime("일 9:00~13:00");

        LectureClass lecture4 = new LectureClass("이승철", "개념상상");
        lecture4.setAge("고1");lecture4.setSubject("수학");lecture4.setLecturename("이승철 수2 심화");
        lecture4.setTime("월 18:30~22:30 / 수 18:30~22:30");

        mylecturelist.add(lecture1);mylecturelist.add(lecture2);mylecturelist.add(lecture3);mylecturelist.add(lecture4);
    }

    //내 시간표를 처음으로 구축하는 함수, 혹시나 시간표를 변경할 경우 이 함수를 부르면 됨.
    public void setTimeTableFirst(){
        setTestDB();
        idlist = new ArrayList<>(); //처음에 시작할 idlist
        for(int i = 0; i < maintablelecuturelist.size(); i++){
            insertLectureToTimeTable(maintablelecuturelist.get(i), timetablelayout);
        }
    }

    //내 시간표에 있는 항목 Textview을 삭제하는 코드 삭제시 이 함수부터 불러야 함.
    public static void deleteTextView(LectureClass lecture, RelativeLayout layout){

        int position = 1000;

        for(int i = 0; i < maintablelecuturelist.size(); i++){
            if(maintablelecuturelist.get(i) == null){
                continue;
            }
            if(maintablelecuturelist.get(i).equals(lecture)){
                position = i;
                break;
            }
        }

        if(position == 1000){ //지우려는 강의를 못찾았을 때.
            Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //id로 textview delete
        ArrayList<Integer> id = idlist.get(position);
        for(int i = 0; i < id.size(); i++){
            TextView textView = layout.findViewById(id.get(i));
            layout.removeView(textView); //textview 삭제됨
        }

    }

    //내 DBlist_시간표에 있는 강좌를 삭제하는 코드
    public static void deleteMainLecture(LectureClass lecture){

        int position = 10000;

        for(int i = 0; i < maintablelecuturelist.size(); i++){
            if(maintablelecuturelist.get(i) == null){
                continue;
            }

            if(maintablelecuturelist.get(i).equals(lecture)){
                position = i;
                break;
            }
        }

        if(position == 10000){ //지우려는 강의를 못찾았을 때.
            Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //delete
        maintablelecuturelist.set(position, null);
        idlist.set(position, null);

        --currentlecturesize;

        return;
    }

    //삭제시 순서고려하지 않고 지우는 문제를 해결하는 코드. 마지막에 불리는 것은 필수
    public static void setMainLectureListAfterDelete(){
        for(int i = 0; i < maintablelecuturelist.size(); i++){
            if(maintablelecuturelist.get(i) == null){
                maintablelecuturelist.remove(i);
                --i;
            }
        }
        for(int i = 0; i < idlist.size(); i++){
            if(idlist.get(i) == null){
                idlist.remove(i);
                --i;
            }
        }
    }

    public static void deleteMyLecture(LectureClass lecture){

        int position = 10000;

        for(int i = 0; i < mylecturelist.size(); i++){
            if(mylecturelist.get(i) == null){
                continue;
            }

            if(mylecturelist.get(i).equals(lecture)){
                position = i;
                Log.e("LECTURE", lecture.getLecturename());
                break;
            }
        }

        if(position == 10000){ //지우려는 강의를 못찾았을 때.
            Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        //delete
        mylecturelist.set(position, null);

        return;

    }

    //강의 삭제 후 재조정 코드
    public static void setMyLectureAfterDelete(){
        for(int i = 0; i < mylecturelist.size(); i++){
            if(mylecturelist.get(i) == null){
                mylecturelist.remove(i);
                --i;
            }
        }
    }

    /*시간표 구조 짜는 함수*/
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    //셀 크기 결정하는 코드
    public void setTimeTableArchitectureSize(){

        int totalwidthPixel = FirstActivity.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int totalheightPixel = FirstActivity.getActivity().getWindowManager().getDefaultDisplay().getHeight();

        actionBarHeight = 40;

        TypedValue tv = new TypedValue();
        if (FirstActivity.getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        int heightPX = (int)((totalheightPixel-dpToPx(70)-actionBarHeight)/16);
        int widthDX = (int)totalwidthPixel/15;

        timetablecellheight = heightPX/2;
        heightPX = timetablecellheight*2;
        daycellheight = timetablecellheight-5;
        daycellwidth = (int)totalwidthPixel*2/15;
        timecellheight = heightPX;
        timecellwidth = widthDX;

        timetablecellwidth = daycellwidth;

        firstcell = timetablepage.findViewById(R.id.firstcell);
        firstcell.getLayoutParams().width = timecellwidth;
        firstcell.getLayoutParams().height = daycellheight;
    }

    //월화수목금, 시간세팅 if maximize controll하고 싶다면 parameter집어넣어서 이부분을 컨트롤하면 됨.
    public void setTimeTableArchitectureData(){
        day = new ArrayList<>();
        day.add("월");day.add("화");day.add("수");day.add("목");day.add("금");day.add("토");day.add("일");

        time = new ArrayList<>();
        for(int i = 8; i < 13; i++){
            time.add(Integer.toString(i));
        }
        for(int i = 1; i < 11; i++){
            time.add(Integer.toString(i));
        }

        timetable = new ArrayList<>();
        for(int i = 0; i < 210; i++){
            timetable.add("");
        }
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
        Log.e("흠>?", "뭐야");

        timetablecellRV.setOnTouchListener(new View.OnTouchListener() {

            //기존에 있는 수업을 선택시 관리하는 함수
            @Override
            public boolean onTouch(View view, MotionEvent e) {

                if(MainPage.getEnteringstatus() != null && MainPage.getEnteringstatus().size() != 0){
                    //우선은 이미 올라와 있는 상태에 대응하는 것을 유보하자고. 그다음으로 인식하는 상황이니 여기에 그 코딩을 진행하면 돼.
                    return false;
                }

                float x = e.getRawX();
                float y = e.getRawY();
                float max = 30*timetablecellheight + actionBarHeight + daycellheight;

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if(y > max){
                            return true;
                        }

                        int day = turnpxtoweek(x);
                        int time = turnpxtotime(y);
                        position = day+time*7;
                        Log.e("손가락 누름 상태", Integer.toString(position));
                        initiatenewLecture(position);

                        break;

                    case MotionEvent.ACTION_MOVE:

                        //이동 최대거리 설정
                        if(y > max + timetablecellheight){
                            if(detbeg == 0){
                                savefingerprint();
                                detbeg = 1;
                            }
                            position = 0;
                            break;
                        }

                        //Log.e("움직임 감지", Integer.toString(position));
                        int day1 = turnpxtoweek(x);
                        int time1 = turnpxtotime(y);

                        if(position != (day1 + time1 * 7) || detbeg == 0){
                            Log.e("움직임시작", Integer.toString(position));
                            position = day1 + time1*7;
                            followfinger(position);
                            Log.e("움직임완료", Integer.toString(position));
                        }

                        break;

                    case MotionEvent.ACTION_UP:
                        Log.e("손가락 뗌", Integer.toString(position));
                        //vanishtemporarylecture();
                        if(detbeg == 0){
                            //잠깐 손 댔다가 떼는 case
                            break;
                        }

                        savefingerprint();
                        MainPage.showTimeTableAddTab();

                        position = 0;
                        break;

                    default:
                        break;
                }
                return true;
            }

        });


    }

    //기본 타임테이블 세팅
    public void setTimeTableArchitecture(){

        setTimeTableArchitectureSize();

        setTimeTableArchitectureData();

        setTimeTableArchitectureRecyclerView();

    }

    //유저 정보에 있는 데이터 반영하는 함수
    public void setUserDataDefault(){

    }


    /* AsyncTask */
    class IAmABackgroundTask extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {

            //findViewById
            daycellRV = timetablepage.findViewById(R.id.daycellRV);
            timecellRV = timetablepage.findViewById(R.id.timecellRV);
            timetablecellRV = timetablepage.findViewById(R.id.timetablecellRV);
            addclassplus = timetablepage.findViewById(R.id.addclassplus);
            timetablelayout = timetablepage.findViewById(R.id.timetablerelativelayout);
            topbanner = timetablepage.findViewById(R.id.timetablebanner);

            //setTimetable
            setColorList();
            setTimeTableArchitecture();
            setTimeTableFirst();
            savedidtemp = new ArrayList<>();
            timetemp = new ArrayList<>();

            //setaddClass
            setAddClass();

        }

        @Override
        protected void onPostExecute(Boolean result) {

        }

        @Override
        protected Boolean doInBackground(String... params) {

            return true;
        }
    }

    public static void setDayCellAdapter(){

        daycellRV.setHasFixedSize(true);
        dayCellAdapter =  new DayCellAdapter(day, context);
        daycellRV.setAdapter(dayCellAdapter);

    }

    /* NotifyDataStateChanged for all adapter */
    public static void NotifyDataStateChanged(){
        AddClassTimeTableFragment.mydatastatechanged();
        AddClassMyFragment.mydatastatechanged();
    }

    /* Getter & Setter */
    public static int getTimecellheight(){
        return timecellheight;
    }

    public static int getTimecellwidth(){
        return  timecellwidth;
    }

    public static int getDaycellheight(){
        return daycellheight;
    }

    public static int getDaycellwidth(){
        return  daycellwidth;
    }

    public static int getTimetablecellheight(){
        return timetablecellheight;
    }

    public static int getTimetablecellwidth(){
        return timetablecellwidth;
    }

    public static ArrayList<String> getDay(){
        return day;
    }

    public static ArrayList<String> getTime(){
        return time;
    }

    public static ArrayList<String> getTimetable(){
        return timetable;
    }

    public static ArrayList<LectureClass> getMylecturelist() { return mylecturelist;}

    public static ArrayList<LectureClass> getMaintablelecuturelist(){return maintablelecuturelist; }

    public static RelativeLayout getTimetablelayout(){return timetablelayout;}

    public static View getTimeTableView(){
        return timetablepage;
    }

    public static RelativeLayout getTopbanner() {return topbanner;}

    public static void setDaycellheight(int height){daycellheight = height;}

    public static DayCellAdapter getDaycellAdapter(){ return dayCellAdapter; }

    public static RelativeLayout getFirstCell(){return firstcell;}

    public static String getTemptimesaved() {
        return temptimesaved;
    }

    public static void setTemptimesaved(String temptimesave) {
        temptimesaved = temptimesave;
    }
}