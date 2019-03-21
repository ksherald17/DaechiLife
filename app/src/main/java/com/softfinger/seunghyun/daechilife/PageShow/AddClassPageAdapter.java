package com.softfinger.seunghyun.daechilife.PageShow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.TimeTableFragment.AddClassActivity;

public class AddClassPageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public AddClassPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AddClassActivity.getAddClassNewFragment();
            case 1:
                return AddClassActivity.getAddClassMyFragment();
            case 2:
                return AddClassActivity.getAddclasstimetablefragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}