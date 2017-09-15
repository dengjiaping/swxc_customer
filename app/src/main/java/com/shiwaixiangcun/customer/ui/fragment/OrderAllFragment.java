package com.shiwaixiangcun.customer.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterOrder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/15.
 * <p>
 * 全部订单Fragment
 */

public class OrderAllFragment extends BaseFragment {

    Unbinder unbinder;

    private AdapterOrder mAdapterOrder;

    @Override
    protected void initViewAndEvents(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.order_all;
    }

    @Override
    protected void destroyViewAndThing() {

    }

    @Override
    protected void onFirstUserVisible() {
        iniData();

    }

    /**
     * 请求网络数据
     */
    private void iniData() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
