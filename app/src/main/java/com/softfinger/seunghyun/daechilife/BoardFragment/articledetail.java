package com.softfinger.seunghyun.daechilife.BoardFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

import org.w3c.dom.Comment;

public class articledetail extends AppCompatActivity {
    //    private articleboard_adapter articleboard_Adapter;
    //    private RecyclerView recyclerArticle;
    private comment_adapter comment_adapter;
    private RecyclerView recyclerComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articledetail_layout);
        final articleData nowarticledata = articleboard_adapter.getClickedarticleData();
        TextView articledetail_Title=findViewById(R.id.articledetail_title);
        articledetail_Title.setText(nowarticledata.getArticle_title());
        TextView articledetail_Author=findViewById(R.id.articledetail_author);
        articledetail_Author.setText(nowarticledata.getArticle_author());
        TextView articledetail_Content=findViewById(R.id.articledetail_content);
        articledetail_Content.setText(nowarticledata.getArticle_content());
        TextView articledetail_Viewnum=findViewById(R.id.articledetail_viewtext);
        articledetail_Viewnum.setText("조회수 : "+nowarticledata.getArticle_viewnum()+"회");
        TextView articledetail_Commentnum=findViewById(R.id.articledetail_commenttext);
        articledetail_Commentnum.setText("댓글 : "+nowarticledata.getArticle_comments().size()+"개");
        TextView articledetail_Time=findViewById(R.id.articledetail_time);
        articledetail_Time.setText(nowarticledata.getArticle_time());


        comment_adapter= new comment_adapter(nowarticledata.getArticle_comments());
        recyclerComment = (RecyclerView) findViewById(R.id.articledetail_commentview);
        recyclerComment.setAdapter(comment_adapter);
        recyclerComment.setLayoutManager(new LinearLayoutManager(articledetail.this));
        EditText commentInput = (EditText) findViewById(R.id.comment_input);
        final String newComment = commentInput.getText().toString();
        //Button comment_add= (Button) findViewById(R.id.comment_add);
       /* comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nowarticledata.getArticle_comments().add(new commentData("사용자","지금",newComment));
                comment_adapter.notifyDataSetChanged();
            }
        });

*/
    }
}
