package com.softfinger.seunghyun.daechilife.MyFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

public class MyFragment extends android.support.v4.app.Fragment {
    /*액티비티, 콘텍스트*/
    static Context context;
    View homepage;
    int a;
    private studentcard_adapter studentcard_adapter;
    private RecyclerView studentcard_recycler;
    private ArrayList<studentcardData> Data1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        homepage = myInflater.inflate(R.layout.mypage, null);

        Data1=new ArrayList<studentcardData>();
        Data1.add(new studentcardData("자녀1","경기고등학교","고등학교 2학년",6));
        Data1.add(new studentcardData("자녀2","청담고등학교","고등학교 1학년",5));
        studentcard_adapter=new studentcard_adapter(Data1);
        studentcard_recycler=(RecyclerView) homepage.findViewById(R.id.mypage_recycler);
        studentcard_recycler.setAdapter(studentcard_adapter);
        studentcard_recycler.setLayoutManager(new LinearLayoutManager(context));
        ImageButton settingbutton;
        settingbutton=homepage.findViewById(R.id.mypage_settingbutton);
        settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.getActivity(),mypage_setting.class);
                intent.putExtra("setting_my",1);
                startActivity(intent);
            }
        });

        return homepage;
    }
}
