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

public class TimeTableCellAdapter extends RecyclerView.Adapter<TimeTableCellAdapter.TimeTableViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<String> timetable_real;
    private List<String> timetable_lists;
    Context context;

    TimeTableCellAdapter(List<String> timetable, Context context){
        this.timetable_real = timetable; //get DB
        this.context = context;
        this.timetable_lists = timetable;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView timetableTV;
        RelativeLayout timetablelayout;

        TimeTableViewHolder(View itemView){ //전개할 item 정의
            super(itemView);
            timetableTV = itemView.findViewById(R.id.timetablecellTV);
            timetablelayout = itemView.findViewById(R.id.timetablecelllayout);
        }

        public void bind(final String item, final TimeTableCellAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(timetable_lists == null){
            return 0;
        };
        return timetable_lists.size();
    }

    @Override
    public TimeTableCellAdapter.TimeTableViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celltimetable, viewGroup, false); //layoutInfalter
        v.getLayoutParams().height = TimeTableFragment.getTimetablecellheight();
        v.getLayoutParams().width = TimeTableFragment.getTimetablecellwidth();
        TimeTableCellAdapter.TimeTableViewHolder lvh = new TimeTableCellAdapter.TimeTableViewHolder(v);
        return lvh;
    }

    //need to be specified much more need index controll
    @Override
    public void onBindViewHolder(@NonNull TimeTableCellAdapter.TimeTableViewHolder timetableViewHolder, final int position) { //specify contents of each item

        final String cell = timetable_real.get(position);
        final RelativeLayout celllayout = timetableViewHolder.timetablelayout;
        final TextView cellTV = timetableViewHolder.timetableTV;

        final int pos = position;

        celllayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        cellTV.setText(cell);
    }

}