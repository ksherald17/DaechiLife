package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.HomeFragment.DLHomeChartAdapter;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

public class SearchMainPage extends android.support.v4.app.Fragment{

    Context context;
    View SearchMainPage;

    /*인기 검색어*/
    RecyclerView hotelementRV;
    SearchHotBoardAdapter searchHotBoardAdapter;
    static ArrayList<String> sampledb;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        SearchMainPage = myInflater.inflate(R.layout.searchmainpagef, null);

        /*Set 인기 검색어*/
        hotelementRV = SearchMainPage.findViewById(R.id.searchhotRV);
        setHotelementRV();

        SearchFragment.onScroll(SearchMainPage);

        return SearchMainPage;

    }

    public void setHotelementRV(){
        sampledb = new ArrayList<>();
        sampledb.add("김선화");sampledb.add("이승철");sampledb.add("현우진");sampledb.add("이지정");sampledb.add("최수준");
        sampledb.add("김동욱");sampledb.add("이명학");sampledb.add("강호길");sampledb.add("김종성");sampledb.add("남예진");

        hotelementRV.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context);
        hotelementRV.setLayoutManager(llm);
        searchHotBoardAdapter = new SearchHotBoardAdapter(sampledb, context);
        hotelementRV.setAdapter(searchHotBoardAdapter);
    }


}
