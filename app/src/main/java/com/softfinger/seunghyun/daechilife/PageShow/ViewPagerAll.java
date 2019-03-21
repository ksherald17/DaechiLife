package com.softfinger.seunghyun.daechilife.PageShow;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerAll extends ViewPager {
    private boolean swipeable;

    public ViewPagerAll(Context context) {
        super(context);
        setSwipeable(false);
    }

    public ViewPagerAll(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.swipeable = false;
        setSwipeable(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }
}
