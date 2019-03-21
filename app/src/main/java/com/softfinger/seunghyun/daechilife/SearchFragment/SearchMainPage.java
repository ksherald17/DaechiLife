package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.R;

public class SearchMainPage extends android.support.v4.app.Fragment{

    Context context;
    View SearchMainPage;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        SearchMainPage = myInflater.inflate(R.layout.searchmainpage, null);

        //인기검색어 누르면 해당 검색결과로 출력되는 코드가 존재헤야 함.

        return SearchMainPage;

    }

    //인기검색어 setting
    public void setHotKeyWords(){

    }
}
