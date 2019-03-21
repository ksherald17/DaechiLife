package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softfinger.seunghyun.daechilife.R;

public class SearchResultFragment extends android.support.v4.app.Fragment {

    Context context;
    View searchResultPage;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        searchResultPage = myInflater.inflate(R.layout.searchresultpage, null);

        return searchResultPage;
    }

}
