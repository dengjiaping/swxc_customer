package com.shiwaixiangcun.customer.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mList;

    protected Activity mContext;

    protected ListView mListView;

    protected LayoutInflater mInflater;

    protected View mCacheView;

    public BaseListAdapter(Activity context) {
        this.mContext = context;
        mInflater = mContext.getLayoutInflater();
        mList = new ArrayList<T>();
    }

    public BaseListAdapter(Activity context, List<T> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (mList != null && list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addItem(T item) {
        if (item != null) {
            mList.add(item);
            notifyDataSetChanged();
        }
    }

    public void addItem(int index, T item) {
        if (item != null) {
            mList.add(index, item);
            notifyDataSetChanged();
        }
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(T[] list) {
        List<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    public ListView getListView() {
        return mListView;
    }

    public void setListView(ListView listView) {
        mListView = listView;
    }

    public void remove(int position) {
        if (mList != null && mList.size() > 0) {
            mList.remove(position);
            notifyDataSetChanged();
        }
    }

    public abstract View createView(int position, View convertView, ViewGroup parent);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View contentView = null;
        try {
            contentView = createView(position, convertView, parent);
            if (contentView != null && mCacheView == null) {
                mCacheView = contentView;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentView != null ? contentView : (mCacheView != null ? mCacheView : new View(mContext)); //防止Crash
    }
}
