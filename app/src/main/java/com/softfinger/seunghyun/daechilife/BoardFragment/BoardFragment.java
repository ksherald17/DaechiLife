package com.softfinger.seunghyun.daechilife.BoardFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        return boardpage;
    }

}