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
import com.softfinger.seunghyun.daechilife.R;

import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;

import java.util.List;

public class DLHomeChartAdapter extends RecyclerView.Adapter<DLHomeChartAdapter.TeacherViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<TeacherElement> teachers_real;
    private List<TeacherElement> teachers_list;
    Context context;
    static int pos1 = 0;

    DLHomeChartAdapter(List<TeacherElement> teachers, Context context){
        this.teachers_real = teachers; //get DB
        this.context = context;
        this.teachers_list = teachers;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(TeacherElement item);
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.
        TextView teachername, number, subject, academy;
        CardView chartcard;
        ImageView updown, expand;
        RelativeLayout elementlayout;
        LinearLayout expandlayout;

        TeacherViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            chartcard = itemView.findViewById(R.id.dlchartcardview);
            //subjectbackground = itemView.findViewById(R.id.dlchartsubjecttext);
            elementlayout = itemView.findViewById(R.id.dlchartelementlayout);
            updown = itemView.findViewById(R.id.dlchartchangeimage);
            number = itemView.findViewById(R.id.dlchartnumbertext);
            subject = itemView.findViewById(R.id.dlchartsubjecttext);
            teachername = itemView.findViewById(R.id.dlchartteachername);
            academy = itemView.findViewById(R.id.dlchartacademyname);
            expand = itemView.findViewById(R.id.chartelementexpand);
            expandlayout = itemView.findViewById(R.id.expandlinearlayout);
            //songname = itemView.findViewById(R.id.cbtitle);
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
        if(teachers_list == null){
            return 0;
        };
        return teachers_list.size();
    }

    @Override
    public DLHomeChartAdapter.TeacherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dlchartelement, viewGroup, false); //layoutInfalter
        DLHomeChartAdapter.TeacherViewHolder lvh = new DLHomeChartAdapter.TeacherViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull DLHomeChartAdapter.TeacherViewHolder teacherViewHolder, final int position) { //specify contents of each item

        final TeacherElement teacherElement = teachers_list.get(position);
        final TextView teachername = teacherViewHolder.teachername;
        final TextView number = teacherViewHolder.number;
        final RelativeLayout elementlayout = teacherViewHolder.elementlayout;
        //final TextView subjectbackground = teacherViewHolder.subjectbackground;
        final TextView subject = teacherViewHolder.subject;
        final TextView academy = teacherViewHolder.academy;
        final ImageView updown = teacherViewHolder.updown;
        final ImageView expand = teacherViewHolder.expand;
        final int pos = position;
        final CardView chartcard = teacherViewHolder.chartcard;
        final LinearLayout expandlayout = teacherViewHolder.expandlayout;



        expandlayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(teacherElement.getExpand() == 0){
                    chartcard.setCardElevation(0f);
                    teacherElement.setExpand();
                    expand.setImageResource(R.drawable.expandgray);
                    final float scale = context.getResources().getDisplayMetrics().density;
                    int pixels = (int) (60 * scale + 0.5f);
                    elementlayout.getLayoutParams().height = pixels;
                }

                else if(teacherElement.getExpand() == 1){
                    chartcard.setCardElevation(4f);
                    teacherElement.cancelExpand();
                    expand.setImageResource(R.drawable.expandup);
                    final float scale = context.getResources().getDisplayMetrics().density;
                    int pixels = (int) (200 * scale + 0.5f);
                    elementlayout.getLayoutParams().height = pixels;
                }

                notifyDataSetChanged();
            }
        });

        number.setText(Integer.toString(pos + 1));
        teachername.setText(teacherElement.getTeachername() + "쌤");
        subject.setText(teacherElement.getSubject());

        //need function
        academy.setText(teacherElement.getAcademylist().get(0));




    }

}