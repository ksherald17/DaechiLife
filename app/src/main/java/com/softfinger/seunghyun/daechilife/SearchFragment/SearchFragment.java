package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.PageShow.MainPage;
import com.softfinger.seunghyun.daechilife.R;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.AddClassMyFragment;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.AddClassTimeTableFragment;

import org.w3c.dom.Text;

public class SearchFragment extends android.support.v4.app.Fragment {

    //SearchFragment가 큰 틀로 하위에 SearchMainPage와 SearchResultFragment가 존재.

    /*액티비티, 콘텍스트*/
    static Context context;
    View searchpage;
    static EditText searchtext;
    static TextView title1, title2;
    static ImageView searchimage;
    static RelativeLayout searchtoplayout, searchbelowlayout;
    static android.support.design.widget.CollapsingToolbarLayout searchtoolbar;
    static android.support.v7.widget.Toolbar toolbarbottom;
    static android.support.design.widget.AppBarLayout tab_layoutsearch;

    static android.support.v4.app.FragmentManager manager;
    static android.support.v4.app.FragmentTransaction transaction;
    static FragmentManager searchfragmentManager;
    static FragmentTransaction searchfragmentTransaction;
    static SearchMainPage searchmainfragment;
    static SearchResultFragment searchResultFragment;
    static FrameLayout searchcontainer;

    /*검색 결과와 관련된 코드*/
    static String searchquery;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        searchpage = myInflater.inflate(R.layout.searchpage, null);

        setId();

        //setTransActionReady
        manager = (FirstActivity.getActivity()).getSupportFragmentManager();
        searchfragmentManager = (FirstActivity.getActivity()).getFragmentManager();
        startHomeMain();

        //검색과 관련된 모든 함수 준비
        setSearch();

        return searchpage;
    }

    public void setId(){
        //setID
        searchtext = searchpage.findViewById(R.id.searchresulttext);
        //title1 = searchpage.findViewById(R.id.gohomefromsearch);
        //title2 = searchpage.findViewById(R.id.titlekumsek);
        searchimage = searchpage.findViewById(R.id.searchimgicon);
        //searchtoplayout = searchpage.findViewById(R.id.searchtaptoplayout);
        searchbelowlayout = searchpage.findViewById(R.id.searchtapbelow);
        searchtoolbar = searchpage.findViewById(R.id.searchtoolbarlayout);
        tab_layoutsearch = searchpage.findViewById(R.id.tab_layoutsearch);
        searchcontainer = searchpage.findViewById(R.id.searchcontainer);
    }

    //스크롤 반응
    public static void onScroll(View scrollView){
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            float y0 = 0;
            float y1 = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    y0 = motionEvent.getY();
                    /* 다른방식
                    if (y1 - y0 > 0) {
                        tab_layoutsearch.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(50)));
                        searchtoolbar.setLayoutParams(new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(50)));
                        toolbarbottom.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(50)));
                        searchbelowlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                0, 0.0f));
                        searchtoplayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                0, 1.0f));
                    } else if (y1 - y0 < 0) {
                        searchcontainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        tab_layoutsearch.setLayoutParams(new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(110)));
                        searchtoolbar.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(110)));
                        toolbarbottom.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(90)));
                        searchbelowlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                0, 0.6f));
                        searchtoplayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                0, 0.4f));
                    }
                    y1 = motionEvent.getY();*/
                }

                return false;
            }
        });
    }

    //메인검색화면으로 복귀
    public static void startHomeMain( ){
        setSearchquery("");
        searchtext.setText("");
        searchtext.setHint("선생님, 학원명, 학교명을 입력하세요.");
        searchfragmentTransaction = searchfragmentManager.beginTransaction();
        searchmainfragment = new SearchMainPage();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.searchcontainer, searchmainfragment);
        transaction.commit();

    }

    //검색 결과 페이지로 전환
    public static void startHomeResult(String searchtag){
        setSearchquery(searchtag);
        searchfragmentTransaction = searchfragmentManager.beginTransaction();
        searchResultFragment = new SearchResultFragment();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.searchcontainer, searchResultFragment);
        transaction.commit();
        hideKeyboard(FirstActivity.getActivity()); //keyboard가리는 효과
    }

    /* 페이지 전환과 관련된 모든 함수를 담당하는 부분 */
    public static void setSearchPageShifter(){

        searchtext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                searchtext.setText("");
                searchtext.setHint("선생님, 학원명, 학교명을 입력하세요.");

            }
        });

        searchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:
                        startHomeResult(searchtext.getText().toString());
                        //searchtext.setText("");
                        searchtext.setHint("선생님, 학원명, 학교명으로 수업을 검색하세요.");
                        return true;

                    default:
                        return false;
                }
            }
        });

        searchtext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    startHomeResult(searchtext.getText().toString());
                    //searchtext.setText("");
                    searchtext.setHint("선생님, 학원명, 학교명으로 수업을 검색하세요.");
                    return true;
                }
                return false;
            }
        });

        searchimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                startHomeResult(searchtext.getText().toString());
                //searchtext.setText("");
                //searchtext.setHint("선생님, 학원명, 학교명을 입력하세요.");

            }
        });

        //DL검색 누르면 다시 첫화면으로 복귀
        /*
        title1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                hideKeyboard(FirstActivity.getActivity());
                startHomeMain();

            }
        });

        title2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                hideKeyboard(FirstActivity.getActivity());
                startHomeMain();

            }
        });*/
    }

    /* 검색하는 화면 창 정교 컨트롤 : x표시로 삭제, 글씨 제한 기능 if possible 자동완성? */
    public static void setSearchEditTool(){
        searchtext.setHint("선생님, 학원명, 학교명으로 수업을 검색하세요.");
    }

    //need 치면 x표시 및 삭제 그리고 글씨제한 기능 자동완성?
    public static void setSearch() {

        //검색창 관리
        setSearchEditTool();

        //페이지 전환 담당
        setSearchPageShifter();

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*Getter Setter*/

    public static String getSearchquery() {
        return searchquery;
    }

    public static void setSearchquery(String searchquery) {
        SearchFragment.searchquery = searchquery;
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}