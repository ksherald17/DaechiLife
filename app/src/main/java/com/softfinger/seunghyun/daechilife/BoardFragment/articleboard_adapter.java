package com.softfinger.seunghyun.daechilife.BoardFragment;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;
import java.util.List;

public class articleboard_adapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<articleData> articleDataList;
        private Context context;
        public static articleData clickedarticleData;

        public articleboard_adapter(ArrayList<articleData> articleitem){
            this.articleDataList=articleitem;
        }
        @Override // Viewholder 초기화시 실행
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            //Context를 부모로 부터 받아와서
            context = viewGroup.getContext() ;

            //받은 Context를 기반으로 LayoutInflater를 생성
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            //생성된 LayoutInflater로 어떤 Layout을 가져와서 어떻게 View를 그릴지 결정
            View articleView = layoutInflater.inflate(R.layout.article_layout, viewGroup, false);

            //View 생성 후, 이 View를 관리하기위한 ViewHolder를 생성
            ViewHolder viewHolder = new ViewHolder(articleView);

            //생성된 ViewHolder를 OnBindViewHolder로 넘겨줌
            return viewHolder;
        }

    @Override
    //RecyclerView의 Row 하나하나를 구현하기위해 Bind(묶이다) 될 때
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {
        //RecyclerView에 들어갈 Data(Student로 이루어진 ArrayList 배열인 arrayListOfStudent)를 기반으로 Row를 생성할 때
        //해당 row의 위치에 해당하는 Student를 가져와서
        articleData article = articleDataList.get(position);
        //넘겨받은 ViewHolder의 Layout에 있는 View들을 어떻게 다룰지 설정
        //ex. TextView의 text를 어떻게 설정할지, Button을 어떻게 설정할지 등등...
        TextView articleTitle = viewHolder.articleTitle;
        articleTitle.setText(article.getArticle_title());

        TextView articleTime = viewHolder.articleTime;
        articleTime.setText(article.getArticle_time());
        TextView articleAuthor = viewHolder.articleAuthor;
        articleAuthor.setText(article.getArticle_author());
        TextView articleCommentsnum = viewHolder.articleCommentsnum;
        articleCommentsnum.setText(String.valueOf(article.getArticle_comments().size()));
        TextView articleViewnum = viewHolder.articleViewnum;
        articleViewnum.setText(String.valueOf(article.getArticle_viewnum()));
        LinearLayout articleLayout=viewHolder.articleLayout;
        articleLayout.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick (View V){
                        Intent intent = new Intent(board.context, articledetail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        board.context.startActivity(intent);
                        clickedarticleData = articleDataList.get(position);
                    }
                }
        );


    }
    public static articleData getClickedarticleData() {return clickedarticleData;}

    public void setArticleDataList(ArrayList<articleData> articleDataList) {
        this.articleDataList = articleDataList;
    }

    @Override
        public int getItemCount(){
            return articleDataList.size();
        }


}
class ViewHolder extends  RecyclerView.ViewHolder {
    public TextView articleTitle;
    public TextView articleAuthor;
    public TextView articleTime;
    public TextView articleCommentsnum;
    public TextView articleViewnum;
    public LinearLayout articleLayout;

    public ViewHolder(View itemView) {
        super(itemView);
        articleTitle = (TextView) itemView.findViewById(R.id.article_title);
        articleAuthor = (TextView) itemView.findViewById(R.id.article_name);
        articleTime = (TextView) itemView.findViewById(R.id.article_time);
        articleCommentsnum = (TextView) itemView.findViewById(R.id.article_commentnum);
        articleViewnum = (TextView) itemView.findViewById(R.id.article_viewnum);
        articleLayout = (LinearLayout) itemView.findViewById(R.id.article_layout);
    }
}
