package com.softfinger.seunghyun.daechilife.PageShow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.softfinger.seunghyun.daechilife.FirstActivity;

import java.util.ArrayList;

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    int i = 1;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FirstActivity.getHomefragment();
            case 1:
                return FirstActivity.getSearchfragment();
            case 2:
                return FirstActivity.getTimetablefragment();
            case 3:
                return FirstActivity.getBoardfragment();
            case 4:
                return FirstActivity.getMypagefragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}

