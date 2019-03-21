package com.softfinger.seunghyun.daechilife.MyFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.softfinger.seunghyun.daechilife.R;

public class MyFragment extends android.support.v4.app.Fragment {

    /*액티비티, 콘텍스트*/
    static Context context;
    View homepage;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        homepage = myInflater.inflate(R.layout.mypage, null);

        return homepage;
    }
}
