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

import com.softfinger.seunghyun.daechilife.DataModel.BoardHotElement;
import com.softfinger.seunghyun.daechilife.R;

import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DLBoardHotElementAdapter extends RecyclerView.Adapter<DLBoardHotElementAdapter.BoardViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할

    static List<BoardHotElement> boards_real;
    private List<BoardHotElement> boards_list;
    Context context;
    static int pos1 = 0;

    DLBoardHotElementAdapter(List<BoardHotElement> boards, Context context){
        this.boards_real = boards; //get DB
        this.context = context;
        this.boards_list = boards;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(TeacherElement item);
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView title, writer, watch, comments, time;
        RelativeLayout imagelayout1, elementlayout;
        LinearLayout imagelayout2;
        ImageView image;

        BoardViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            title = itemView.findViewById(R.id.hotboardelementtitle);
            writer = itemView.findViewById(R.id.hotboardelementwriter);
            watch = itemView.findViewById(R.id.hotboardelementwatch);
            comments = itemView.findViewById(R.id.hotboardelementcomment);
            time = itemView.findViewById(R.id.hotboardelementtime);
            imagelayout1 = itemView.findViewById(R.id.hotboardelementimage1);
            imagelayout2 = itemView.findViewById(R.id.hotboardelementimage2);
            image = itemView.findViewById(R.id.hotboardelementimage);
            elementlayout = itemView.findViewById(R.id.hotboardelementlayout);

        }

        public void bind(final TeacherElement item, final DLHomeChartAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(boards_real == null){
            return 0;
        };
        return boards_real.size();
    }

    @Override
    public DLBoardHotElementAdapter.BoardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homehotboardelement, viewGroup, false); //layoutInfalter
        DLBoardHotElementAdapter.BoardViewHolder lvh = new DLBoardHotElementAdapter.BoardViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull DLBoardHotElementAdapter.BoardViewHolder boardViewHolder, final int position) { //specify contents of each item

        final BoardHotElement boardHotElement = boards_list.get(position);
        final TextView title = boardViewHolder.title;
        final TextView writer = boardViewHolder.writer;
        final TextView watch = boardViewHolder.watch;
        final TextView comment = boardViewHolder.comments;
        final TextView time = boardViewHolder.time;
        final ImageView image = boardViewHolder.image;
        final RelativeLayout imagelayout1 = boardViewHolder.imagelayout1;
        final LinearLayout imagelayout2 = boardViewHolder.imagelayout2;
        final RelativeLayout elementlayout = boardViewHolder.elementlayout;
        final int pos = position;

        elementlayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                notifyDataSetChanged();
            }
        });

        title.setText(boardHotElement.getTitle());
        writer.setText(boardHotElement.getWriterid());
        watch.setText(Integer.toString(boardHotElement.getWatch()));
        comment.setText(Integer.toString(boardHotElement.getComments()));

        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
        sdf.setTimeZone(TimeZone.getDefault());

        //날짜가 동일하면 시간을, 날짜가 동일하지 않다면 날짜를 기록
        String currentday = sdf.format(new Date());
        if(currentday.equals(boardHotElement.getDay())){
            time.setText(boardHotElement.getCurrenttime());
        }else{
            time.setText(boardHotElement.getDay());
        }

        //이미지
        if(boardHotElement.getImageexist() == 1){
            imagelayout1.setVisibility(View.VISIBLE);
            imagelayout2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }else{
            imagelayout1.setVisibility(View.INVISIBLE);
            imagelayout2.setVisibility(View.INVISIBLE);
            image.setVisibility(View.INVISIBLE);
        }

    }

}