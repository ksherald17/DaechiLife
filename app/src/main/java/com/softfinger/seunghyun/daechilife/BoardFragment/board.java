package com.softfinger.seunghyun.daechilife.BoardFragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

import java.util.ArrayList;
import java.util.List;

public class board extends AppCompatActivity {
    public static Context context;
    private ArrayList<articleData> articledatalist;
    private articleboard_adapter articleboard_Adapter;
    private RecyclerView recyclerArticle;
    private ArrayList<commentData> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);
        comments = new ArrayList<commentData>();
        articledatalist= new ArrayList<articleData>();
       // Typeface maplestory_bold=Typeface.createFromAsset(getAssets(),"maplestory_bold.ttf");
        TextView boardname = findViewById(R.id.boardname);
        comments.add(new commentData("안재준","12:00","댓글 내용1"));
        comments.add(new commentData("최승현","13:00","댓글 내용2"));

        articledatalist.add(new articleData("제목","닉네임","11:00",comments,10,"내용]" +
                "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ" +
                "ㅇㅇ" +
                "ㅇㅇ" +
                "내요오오오옹"));

        articleboard_Adapter = new articleboard_adapter(articledatalist);
        recyclerArticle = (RecyclerView) findViewById(R.id.recyclearticle);
        recyclerArticle.setAdapter(articleboard_Adapter);
        recyclerArticle.setLayoutManager(new LinearLayoutManager(board.this));



    }
}
