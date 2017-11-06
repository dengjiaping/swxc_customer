package com.shiwaixiangcun.customer.widget.pullableview;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class MyListener implements PullToRefreshLayout.OnRefreshListener {

    public onRefreshListener onRefreshListener;
    public onRefreshOkListener onRefreshOkListener;

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        Log.i("bbbbbbvvc", "shuaxinkaishi");
        if (onRefreshListener != null){
            onRefreshListener.refreshScence(true);
        }

        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i("bbbbbbvvc", "shuaxincchenggong");
                if (onRefreshOkListener != null){
                    onRefreshOkListener.refreshOkScence(true);
                }

                // 千万别忘了告诉控件刷新完毕了哦！
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i("bbbbbbvvc", "jiazaichenggong");
                // 千万别忘了告诉控件加载完毕了哦！
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    public void setRefreshListener(onRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setRefreshOkListener(onRefreshOkListener onRefreshOkListener) {
        this.onRefreshOkListener = onRefreshOkListener;
    }


    //开始刷新回调
    public interface onRefreshListener {
        void refreshScence(boolean isnot);
    }

	//开始刷新回调
	public interface onRefreshOkListener {
		void refreshOkScence(boolean isnot);
	}

}
