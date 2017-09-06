package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * viewpage 和listview 相互冲突 将父view 传递到viewpage 里面
 *
 * 使用父类的方法 parent.requestDisallowInterceptTouchEvent(true);
 *
 * 当 requestDisallowInterceptTouchEvent 如果为true的时候 表示:父view 不拦截子view的touch 事件
 *
 * 这个方法只是改变flag
 *
 * @author baozi
 *
 */
public class DecoratorViewPager extends ViewPager {
    private ViewGroup parent;

    public DecoratorViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public DecoratorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNestedpParent(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("mmmmmmmmmm","----------1");
        if (parent != null) {
            Log.e("mmmmmmmmmm","dispatchTouchEvent");
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        Log.e("mmmmmmmmmm","----------2");
        if (parent != null) {
            Log.e("mmmmmmmmmm","onInterceptTouchEvent");
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        Log.e("mmmmmmmmmm","----------3");
        if (parent != null) {
            Log.e("mmmmmmmmmm","onTouchEvent");
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(arg0);
    }





}