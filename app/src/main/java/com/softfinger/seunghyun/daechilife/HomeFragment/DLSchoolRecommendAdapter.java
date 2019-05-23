package com.softfinger.seunghyun.daechilife.HomeFragment;

import android.content.Context;
import android.graphics.Color;
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

import com.softfinger.seunghyun.daechilife.DataModel.HomeRecommendLecture;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.R;

import java.util.List;

public class DLSchoolRecommendAdapter extends RecyclerView.Adapter<DLSchoolRecommendAdapter.LectureViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<HomeRecommendLecture> lectures_real;
    private List<HomeRecommendLecture> lectures_list;
    Context context;
    static int pos1 = 0;

    DLSchoolRecommendAdapter(List<HomeRecommendLecture> teachers, Context context){
        this.lectures_real = teachers; //get DB
        this.context = context;
        this.lectures_list = teachers;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(HomeRecommendLecture item);
    }

    public class LectureViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView subject, teacher, time;
        RelativeLayout color;
        LinearLayout layout;

        LectureViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            layout = itemView.findViewById(R.id.schoolrecommendon);
            color = itemView.findViewById(R.id.srcolor);
            subject = itemView.findViewById(R.id.srsubject);
            teacher = itemView.findViewById(R.id.srdescript);
            time = itemView.findViewById(R.id.srtime);
        }

        public void bind(final HomeRecommendLecture item, final DLSchoolRecommendAdapter.OnItemClickListener listener) { ;
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
    public DLSchoolRecommendAdapter.LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schoolrecommend, viewGroup, false); //layoutInfalter
        DLSchoolRecommendAdapter.LectureViewHolder lvh = new DLSchoolRecommendAdapter.LectureViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull DLSchoolRecommendAdapter.LectureViewHolder teacherViewHolder, final int position) { //specify contents of each item

        final HomeRecommendLecture lecture = lectures_list.get(position);
        final TextView teacher = teacherViewHolder.teacher;
        final TextView time = teacherViewHolder.time;
        final RelativeLayout color = teacherViewHolder.color;
        final LinearLayout layout = teacherViewHolder.layout;
        final TextView subject = teacherViewHolder.subject;
        final int pos = position;


        teacher.setText(lecture.getTeacher());
        time.setText(lecture.getTime() );
        subject.setText(lecture.getCategory());

        switch(lecture.getCategory()){
            case "국어":
                color.setBackgroundColor(Color.parseColor("#F08080"));
                subject.setTextColor(Color.parseColor("#F08080"));
                break;
            case "영어":
                color.setBackgroundColor(Color.parseColor("#BA55D3"));
                subject.setTextColor(Color.parseColor("#BA55D3"));
                break;
            case "수학":
                color.setBackgroundColor(Color.parseColor("#000080"));
                subject.setTextColor(Color.parseColor("#000080"));
                break;
            case "과학":
                color.setBackgroundColor(Color.parseColor("#00BFFF"));
                subject.setTextColor(Color.parseColor("#00BFFF"));
                break;
        }

    }

}