package com.softfinger.seunghyun.daechilife.TimeTableFragment;


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

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.R;

import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;

import java.util.List;

public class DayCellAdapter extends RecyclerView.Adapter<DayCellAdapter.DayViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<String> day_real;
    private List<String> day_lists;
    Context context;
    static int pos1 = 0;

    DayCellAdapter(List<String> days, Context context){
        this.day_real = days; //get DB
        this.context = context;
        this.day_lists = days;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class DayViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView dayTV;
        RelativeLayout daylayout;

        DayViewHolder(View itemView){ //전개할 item 정의
            super(itemView);
            dayTV = itemView.findViewById(R.id.daycellTV);
            daylayout = itemView.findViewById(R.id.daycelllayout);
        }

        public void bind(final String item, final DayCellAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(day_lists == null){
            return 0;
        };
        return day_lists.size();
    }

    @Override
    public DayCellAdapter.DayViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cellday, viewGroup, false); //layoutInfalter
        v.getLayoutParams().height = TimeTableFragment.getDaycellheight();
        v.getLayoutParams().width = TimeTableFragment.getDaycellwidth();
        DayCellAdapter.DayViewHolder lvh = new DayCellAdapter.DayViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull DayCellAdapter.DayViewHolder dayViewHolder, final int position) { //specify contents of each item

        final String cell = day_lists.get(position);
        final RelativeLayout celllayout = dayViewHolder.daylayout;
        final TextView cellTV = dayViewHolder.dayTV;

        final int pos = position;

        celllayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        cellTV.setText(cell);
    }

}