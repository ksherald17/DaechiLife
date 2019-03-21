package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.HomeFragment.DLClassRecommendAdapter;
import com.softfinger.seunghyun.daechilife.R;

public class AddClassMyFragment extends android.support.v4.app.Fragment {

    /*액티비티, 콘텍스트*/
    static Context context;
    View mypage;
    static RecyclerView addclassmyRV;
    static AddClassMyAdapter addClassAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        mypage = myInflater.inflate(R.layout.addclassmypage, null);
        addclassmyRV = mypage.findViewById(R.id.addclassmyRV);

        setRecyclerView();

        return mypage;
    }

    public static void setRecyclerView(){

        addclassmyRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        addclassmyRV.setLayoutManager(llm);
        addClassAdapter =  new AddClassMyAdapter(TimeTableFragment.getMylecturelist(), context);
        addclassmyRV.setAdapter(addClassAdapter);
        addClassAdapter.notifyDataSetChanged();

    }

    public static void mydatastatechanged(){
        addClassAdapter.notifyDataSetChanged();
    }
}
