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
import com.softfinger.seunghyun.daechilife.R;

import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;

import java.util.List;

public class TimeCellAdapter extends RecyclerView.Adapter<TimeCellAdapter.TimeViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<String> time_real;
    private List<String> time_lists;
    Context context;
    static int pos1 = 0;

    TimeCellAdapter(List<String> time, Context context){
        this.time_real = time; //get DB
        this.context = context;
        this.time_lists = time;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView timeTV;
        RelativeLayout timelayout;

        TimeViewHolder(View itemView){ //전개할 item 정의
            super(itemView);
            timeTV = itemView.findViewById(R.id.timecellTV);
            timelayout = itemView.findViewById(R.id.timecelllayout);
        }

        public void bind(final String item, final TimeCellAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(time_real == null){
            return 0;
        };
        return time_real.size();
    }

    @Override
    public TimeCellAdapter.TimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celltime, viewGroup, false); //layoutInfalter
        v.getLayoutParams().height = TimeTableFragment.getTimecellheight();
        v.getLayoutParams().width = TimeTableFragment.getTimecellwidth();
        TimeCellAdapter.TimeViewHolder lvh = new TimeCellAdapter.TimeViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull TimeCellAdapter.TimeViewHolder timeViewHolder, final int position) { //specify contents of each item

        final String cell = time_real.get(position);
        final RelativeLayout celllayout = timeViewHolder.timelayout;
        final TextView cellTV = timeViewHolder.timeTV;

        final int pos = position;

        celllayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        cellTV.setText(cell);
    }

}