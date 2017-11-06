package com.shiwaixiangcun.customer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/11/2.
 */

public class CustomScrollView extends ScrollView {


    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context) {
        super(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }


}
