package com.softfinger.seunghyun.daechilife.MyFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;

public class studentcard_adapter extends RecyclerView.Adapter<studentcard_ViewHolder> {
    private ArrayList<studentcardData> studentcards;
    private Context student_context;
    public studentcard_adapter(ArrayList<studentcardData> clickedstudentcard){
        this.studentcards=clickedstudentcard;
    }
    @Override

    public studentcard_ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //Context를 부모로 부터 받아와서
        student_context = viewGroup.getContext() ;

        //받은 Context를 기반으로 LayoutInflater를 생성
        LayoutInflater layoutInflater = LayoutInflater.from(student_context);

        //생성된 LayoutInflater로 어떤 Layout을 가져와서 어떻게 View를 그릴지 결정
        View studentcardView = layoutInflater.inflate(R.layout.mypage_studentitem, viewGroup, false);

        //View 생성 후, 이 View를 관리하기위한 ViewHolder를 생성
        studentcard_ViewHolder viewHolder = new studentcard_ViewHolder(studentcardView);

        //생성된 ViewHolder를 OnBindViewHolder로 넘겨줌
        return viewHolder;
    }

    @Override
    //RecyclerView의 Row 하나하나를 구현하기위해 Bind(묶이다) 될 때
    public void onBindViewHolder(@NonNull studentcard_ViewHolder studentcard_viewHolder,final int position) {
        studentcardData studentcard = studentcards.get(position);
        //RecyclerView에 들어갈 Data(Student로 이루어진 ArrayList 배열인 arrayListOfStudent)를 기반으로 Row를 생성할 때
        //해당 row의 위치에 해당하는 Student를 가져와서
        TextView studentname=studentcard_viewHolder.studentName;
        TextView studentSchool=studentcard_viewHolder.studentSchool;
        TextView studentGrade=studentcard_viewHolder.studentGrade;
        TextView studentAcademynum=studentcard_viewHolder.studentAcademynum;
        studentname.setText(studentcard.getStudent_name());
        studentSchool.setText(studentcard.getStudent_school());
        studentGrade.setText(studentcard.getStudent_grade());
        studentAcademynum.setText("다니는 학원 수: "+studentcard.getStudent_academynum()+"개");


    }

    @Override
    public int getItemCount(){
        return studentcards.size();
    }
}
class studentcard_ViewHolder extends  RecyclerView.ViewHolder {
    public TextView studentName;
    public TextView studentSchool;
    public TextView studentGrade;
    public TextView studentAcademynum;
    public studentcard_ViewHolder(View itemView) {
        super(itemView);
        studentName = (TextView) itemView.findViewById(R.id.studentcard_name);
        studentSchool = (TextView) itemView.findViewById(R.id.studentcard_school);
        studentGrade = (TextView) itemView.findViewById(R.id.studentcard_grade);
        studentAcademynum = (TextView) itemView.findViewById(R.id.studentcard_academynum);
    }
}