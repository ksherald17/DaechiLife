package com.softfinger.seunghyun.daechilife.HomeFragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softfinger.seunghyun.daechilife.DataModel.ClassRecommendElement;
import com.softfinger.seunghyun.daechilife.R;



import java.util.List;

public class DLClassRecommendAdapter extends RecyclerView.Adapter<DLClassRecommendAdapter.ClassViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<ClassRecommendElement> recommend_real;
    private List<ClassRecommendElement> recommend_list;
    Context context;
    static int pos1 = 0;

    DLClassRecommendAdapter(List<ClassRecommendElement> classes, Context context){
        this.recommend_real = classes; //get DB
        this.context = context;
        this.recommend_list = classes;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(ClassRecommendElement item);
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.
        TextView searchtext, description, r1, r2, r3, r4, r5;
        LinearLayout classcodilayout;

        ClassViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            searchtext = itemView.findViewById(R.id.codisecondtext);
            description = itemView.findViewById(R.id.codidescriptiontext);
            r1 = itemView.findViewById(R.id.codiresultfirst);
            r2 = itemView.findViewById(R.id.codiresultsecond);
            r3 = itemView.findViewById(R.id.codiresultthird);
            r4 = itemView.findViewById(R.id.codiresultfourth);
            r5 = itemView.findViewById(R.id.codiresultfifth);
            classcodilayout = itemView.findViewById(R.id.classcodilayout);
        }

        public void bind(final ClassRecommendElement item, final DLClassRecommendAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(recommend_list == null){
            return 0;
        };
        return recommend_list.size();
    }

    @Override
    public DLClassRecommendAdapter.ClassViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.classcodiitem, viewGroup, false); //layoutInfalter
        DLClassRecommendAdapter.ClassViewHolder lvh = new DLClassRecommendAdapter.ClassViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull DLClassRecommendAdapter.ClassViewHolder classViewHolder, final int position) { //specify contents of each item

        final ClassRecommendElement classRecommendElement = recommend_list.get(position);
        final TextView searchtext = classViewHolder.searchtext;
        final TextView description = classViewHolder.description;
        final TextView r1 = classViewHolder.r1;
        final TextView r2 = classViewHolder.r2;
        final TextView r3 = classViewHolder.r3;
        final TextView r4 = classViewHolder.r4;
        final TextView r5 = classViewHolder.r5;
        final int pos = position;
        final LinearLayout recommendlayout = classViewHolder.classcodilayout;

        recommendlayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //notifyDataSetChanged();
            }
        });

        searchtext.setText("'"+classRecommendElement.getSearchtext()+"'");
        description.setText(classRecommendElement.getDescriptiontext());
        //need to be modified
        r1.setText(classRecommendElement.getResult().get(0));
        r2.setText(classRecommendElement.getResult().get(1));
        r3.setText(classRecommendElement.getResult().get(2));
        r4.setText(classRecommendElement.getResult().get(3));
        r5.setText(classRecommendElement.getResult().get(4));
    }

}