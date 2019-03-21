package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    GridLayout searchfilterlayout;

    static android.support.v4.app.FragmentManager manager;
    static android.support.v4.app.FragmentTransaction transaction;
    static FragmentManager searchfragmentManager;
    static FragmentTransaction searchfragmentTransaction;
    static SearchMainPage searchmainfragment;
    static SearchResultFragment searchResultFragment;

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
        title1 = searchpage.findViewById(R.id.gohomefromsearch);
        title2 = searchpage.findViewById(R.id.titlekumsek);
        searchimage = searchpage.findViewById(R.id.searchimgicon);
    }

    //메인검색화면으로 복귀
    public static void startHomeMain( ){

        searchfragmentTransaction = searchfragmentManager.beginTransaction();
        searchmainfragment = new SearchMainPage();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.searchcontainer, searchmainfragment);
        transaction.commit();

    }

    //검색 결과 페이지로 전환
    public static void startHomeResult(String searchtag){

        searchfragmentTransaction = searchfragmentManager.beginTransaction();
        searchResultFragment = new SearchResultFragment();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.searchcontainer, searchResultFragment);
        transaction.commit();

    }

    /* 페이지 전환과 관련된 모든 함수를 담당하는 부분 */
    public static void setSearchPageShifter(){

        searchtext.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.
                                startHomeResult(searchtext.getText().toString());
                                searchtext.setText("");
                                searchtext.setHint("학원명, 선생님명");
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );

        searchimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                startHomeResult(searchtext.getText().toString());
                searchtext.setText("");
                searchtext.setHint("학원명, 선생님명");

            }
        });

        //DL검색 누르면 다시 첫화면으로 복귀
        title1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                startHomeMain();

            }
        });

        title2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                startHomeMain();

            }
        });


    }

    /* 검색하는 화면 창 정교 컨트롤 : x표시로 삭제, 글씨 제한 기능 if possible 자동완성? */
    public static void setSearchEditTool(){
        searchtext.setHint("학원명, 선생님명");
    }

    //need 치면 x표시 및 삭제 그리고 글씨제한 기능 자동완성?
    public static void setSearch() {

        //검색창 관리
        setSearchEditTool();

        //페이지 전환 담당
        setSearchPageShifter();

    }
}