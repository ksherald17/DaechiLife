package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

public class AddClassTimeTableFragment extends android.support.v4.app.Fragment {

    /*액티비티, 콘텍스트*/
    static Context context;
    View timetablepage;
    RecyclerView timetablelectureRV;

    ArrayList<LectureClass> mytablelecturelist;
    static AddClassTimeTableAdapter addClassAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        timetablepage = myInflater.inflate(R.layout.addclasstimetablepage, null);

        //setRecylcerView
        timetablelectureRV = timetablepage.findViewById(R.id.addclasstimetableRV);
        setRecyclerView();

        return timetablepage;

    }

    public void setRecyclerView(){

        timetablelectureRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        timetablelectureRV.setLayoutManager(llm);
        addClassAdapter =  new AddClassTimeTableAdapter(TimeTableFragment.getMaintablelecuturelist(), context);
        timetablelectureRV.setAdapter(addClassAdapter);
        addClassAdapter.notifyDataSetChanged();

    }

    public static void mydatastatechanged(){
        addClassAdapter.notifyDataSetChanged();
    }

}