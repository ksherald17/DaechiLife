package com.softfinger.seunghyun.daechilife.BoardFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.R;

public class BoardFragment extends android.support.v4.app.Fragment {

    /*액티비티, 콘텍스트*/
    static Context context;
    View boardpage;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        boardpage = myInflater.inflate(R.layout.boardpage, null);
        RelativeLayout freeboardlayout = boardpage.findViewById(R.id.freeboard_layout);
        FrameLayout newarticlebtn = boardpage.findViewById(R.id.newarticlebtn);

        freeboardlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,board.class);
                intent.putExtra("boardname",1);
                startActivity(intent);
            }
        });
        newarticlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,board_newarticle.class);
                intent.putExtra("newarticle",1);
                startActivity(intent);
            }
        });
        return boardpage;
    }

}