package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.SurroundMerchAdapter;
import com.shiwaixiangcun.customer.model.MerchantListBean;
import com.shiwaixiangcun.customer.presenter.impl.SurroundTopImpl;
import com.shiwaixiangcun.customer.pullableview.MyListener;
import com.shiwaixiangcun.customer.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.pullableview.PullableListView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.ui.ISurroundTopView;

import java.util.ArrayList;
import java.util.List;

public class SurroundTopActivity extends AppCompatActivity implements View.OnClickListener, ISurroundTopView, ListView.OnItemClickListener {

    private ChangeLightImageView back_left;
    private List<String> list_surround_top = new ArrayList<>();
    private PullableListView lv_surround_top;
    private List<MerchantListBean.DataBean.ElementsBean> elements_merch = new ArrayList<>();
    private SurroundMerchAdapter homeSurroundAdapter;
    private SurroundTopImpl surroundTop;
    private String merchant = "";
    private String nameType;
    private TextView tv_page_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround_top);
        Intent intent = getIntent();
        merchant = intent.getStringExtra("merchant");
        nameType = intent.getStringExtra("nameType");
        initLayout();
        initData();
    }

    private void initLayout() {

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        lv_surround_top = (PullableListView) findViewById(R.id.lv_surround_top);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);

        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                surroundTop.setBgaAdpaterAndClick(SurroundTopActivity.this, merchant);
            }
        });

    }

    private void initData() {
        if (Utils.isNotEmpty(nameType)) {
            tv_page_name.setText(nameType);
        } else {
            tv_page_name.setText("");
        }

        surroundTop = new SurroundTopImpl(this);
        surroundTop.setBgaAdpaterAndClick(this, merchant);

        for (int i = 0; i < 10; i++) {
            list_surround_top.add("sss" + i);
        }
        homeSurroundAdapter = new SurroundMerchAdapter(elements_merch, this);
        lv_surround_top.setAdapter(homeSurroundAdapter);


        back_left.setOnClickListener(this);
        lv_surround_top.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
        }
    }


    @Override
    public void setBgaAdpaterAndClickResult(MerchantListBean result) {
        elements_merch.clear();
        List<MerchantListBean.DataBean.ElementsBean> elements = result.getData().getElements();
        if (elements.size() != 0) {
            elements_merch.addAll(elements);
        }
        homeSurroundAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("bbbbbbmmmbmmb", i + "");
        Intent intent = new Intent(SurroundTopActivity.this, SurroundDetailActivity.class);
        intent.putExtra("merchId", elements_merch.get(i).getId() + "");
        startActivity(intent);
    }
}
