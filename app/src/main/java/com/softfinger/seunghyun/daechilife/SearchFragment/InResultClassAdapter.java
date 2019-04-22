package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.HomeFragment.DLHomeChartAdapter;
import com.softfinger.seunghyun.daechilife.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InResultClassAdapter extends RecyclerView.Adapter<InResultClassAdapter.LectureViewHolder> {

    static List<LectureClass> lectures_real;
    private List<LectureClass> lectures_list;
    Context context;

    InResultClassAdapter(List<LectureClass> lectures, Context context){
        if(lectures.size() > 3){

            lectures_real = new ArrayList<>();
            lectures_list = new ArrayList<>();

            for(int i = 0; i < 3; i ++){
                lectures_real.add(lectures.get(i));
                lectures_list.add(lectures.get(i));
            }

        }
        else{
            this.lectures_list = lectures;
            this.lectures_real = lectures; //get DB
        }
        this.context = context;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(TeacherElement item);
    }

    public class LectureViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView lecturename, lecturetime, academy;
        ImageView academypicture;
        RelativeLayout resultlayout;

        LectureViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            academypicture = itemView.findViewById(R.id.academypicture);
            lecturename = itemView.findViewById(R.id.lecturename);
            lecturetime = itemView.findViewById(R.id.lecturetime);
            academy = itemView.findViewById(R.id.lectureacademy);
            resultlayout = itemView.findViewById(R.id.searchresultclasslayout);

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
        if(lectures_real == null){
            return 0;
        };
        return lectures_real.size();
    }

    @Override
    public InResultClassAdapter.LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchresult_class, viewGroup, false); //layoutInfalter
        InResultClassAdapter.LectureViewHolder lvh = new InResultClassAdapter.LectureViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull InResultClassAdapter.LectureViewHolder lectureViewHolder, final int position) { //specify contents of each item

        final LectureClass result = lectures_list.get(position);
        final TextView lecturename = lectureViewHolder.lecturename;
        final TextView lecturetime = lectureViewHolder.lecturetime;
        final TextView academy = lectureViewHolder.academy;
        final ImageView academyimage = lectureViewHolder.academypicture;
        final RelativeLayout layout = lectureViewHolder.resultlayout;
        final int pos = position;

        lecturename.setText(result.getLecturename());
        lecturetime.setText(result.getTime());
        academy.setText(result.getAcademyname());

        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


            }
        });

        String imageurl = "https://daechilife.s3.ap-northeast-2.amazonaws.com/academylogo/" + result.getAcademyenglishname() + ".PNG";
        Picasso.with(FirstActivity.getActivity()).load(imageurl).into(academyimage);

    }

}
