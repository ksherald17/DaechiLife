package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends android.support.v4.app.Fragment {

    Context context;
    View searchResultPage;
    RecyclerView searchRV;
    SearchResultAdapter searchResultAdapter;

    /*Sample DB*/
    List<TeacherElement> teachers;
    int sampledbON = 1;

    /*Variants with Search*/
    String searchquery;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchquery = SearchFragment.getSearchquery();
        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        searchResultPage = myInflater.inflate(R.layout.searchresultpage, null);

        setSearchresult();
        setRecyclerView();

        return searchResultPage;
    }

    private void setSearchresult(){
        SearchEngine searchEngine = new SearchEngine(searchquery);
        searchEngine.setResult();
        teachers = searchEngine.getTeacherresultlist();
    }

    public void setRecyclerView(){
        searchRV = searchResultPage.findViewById(R.id.searchresultRV);
        searchRV.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을 한다.
        searchRV.setLayoutManager(llm);
        searchResultAdapter = new SearchResultAdapter(teachers, context);
        searchRV.setAdapter(searchResultAdapter);
    }

}
