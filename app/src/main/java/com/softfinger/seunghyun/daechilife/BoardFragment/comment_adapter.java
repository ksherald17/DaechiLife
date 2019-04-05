package com.softfinger.seunghyun.daechilife.BoardFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;
import java.util.List;

public class comment_adapter extends RecyclerView.Adapter<comment_ViewHolder> {
    private ArrayList<commentData> commentsList;
    private Context comment_context;
    public comment_adapter(ArrayList<commentData> clickedcomments){
        this.commentsList=clickedcomments;
    }
    @Override

    public comment_ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        //Context를 부모로 부터 받아와서
        comment_context = viewGroup.getContext() ;

        //받은 Context를 기반으로 LayoutInflater를 생성
        LayoutInflater layoutInflater = LayoutInflater.from(comment_context);

        //생성된 LayoutInflater로 어떤 Layout을 가져와서 어떻게 View를 그릴지 결정
        View commentView = layoutInflater.inflate(R.layout.comment_layout, viewGroup, false);

        //View 생성 후, 이 View를 관리하기위한 ViewHolder를 생성
        comment_ViewHolder viewHolder = new comment_ViewHolder(commentView);

        //생성된 ViewHolder를 OnBindViewHolder로 넘겨줌
        return viewHolder;
    }

    @Override
    //RecyclerView의 Row 하나하나를 구현하기위해 Bind(묶이다) 될 때
    public void onBindViewHolder(@NonNull comment_ViewHolder comment_viewHolder,final int position) {
        //RecyclerView에 들어갈 Data(Student로 이루어진 ArrayList 배열인 arrayListOfStudent)를 기반으로 Row를 생성할 때
        //해당 row의 위치에 해당하는 Student를 가져와서
        commentData comment = commentsList.get(position);
        //넘겨받은 ViewHolder의 Layout에 있는 View들을 어떻게 다룰지 설정
        //ex. TextView의 text를 어떻게 설정할지, Button을 어떻게 설정할지 등등...
        TextView commentAuthor = comment_viewHolder.commentAuthor;
        commentAuthor.setText(comment.getComment_author());

        TextView commentTime = comment_viewHolder.commentTime;
        commentTime.setText(comment.getComment_time());

        TextView commentContent = comment_viewHolder.commentContent;
        commentContent.setText(comment.getComment_content());

     /*   Button recommentButton=comment_viewHolder.recommentButton;
        recommentButton.setTypeface(MainActivity.maplestory_light);
        recommentButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick (View V){
                        //답글 쓰기 실행
                            }
                }
        );*/


    }

    @Override
    public int getItemCount(){
        return commentsList.size();
    }

}
class comment_ViewHolder extends  RecyclerView.ViewHolder {
    public TextView commentAuthor;
    public TextView commentTime;
    public TextView commentContent;
    public Button recommentButton;
    public comment_ViewHolder(View itemView) {
        super(itemView);
        commentAuthor = (TextView) itemView.findViewById(R.id.comment_author);
        commentTime = (TextView) itemView.findViewById(R.id.comment_time);
        commentContent = (TextView) itemView.findViewById(R.id.comment_content);
      //  recommentButton = (Button) itemView.findViewById(R.id.recommentbutton);
    }
}