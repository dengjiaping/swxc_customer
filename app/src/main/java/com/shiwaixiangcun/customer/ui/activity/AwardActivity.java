package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bboylin.awesomelistview.LoadMoreListView;
import com.bboylin.awesomelistview.RefreshLayout;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AwardAdapter;
import com.shiwaixiangcun.customer.model.AwardBean;
import com.shiwaixiangcun.customer.presenter.impl.AwardImpl;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.ui.IAwardView;

import java.util.ArrayList;
import java.util.List;

public class AwardActivity extends AppCompatActivity implements View.OnClickListener,IAwardView,ListView.OnItemClickListener{

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private List<String> list_award = new ArrayList<>();
    private LoadMoreListView lv_award;
    private List<AwardBean.DataBean.ElementsBean> list_award_image = new ArrayList<>();
    private AwardAdapter awardAdapter;
    private RefreshLayout refreshable_layout;
    private int is_not_click = 0;
    private AwardImpl award;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);
        initLayout();
        initData();
    }

    private void initLayout() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        lv_award = (LoadMoreListView) findViewById(R.id.lv_award);
        refreshable_layout = (RefreshLayout) findViewById(R.id.refreshable_layout);
    }

    private void initData() {
        award = new AwardImpl(this);
//        award.setBgaAdpaterAndClick(this,1);
        tv_page_name.setText("活动");

        for (int i = 0; i < 10; i++) {
            list_award.add("hahhaha"+i);
        }

        awardAdapter = new AwardAdapter(list_award_image,this);
        lv_award.setAdapter(awardAdapter);


        back_left.setOnClickListener(this);
        lv_award.setOnItemClickListener(this);
        listen();


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(AwardBean result) {
        if (is_not_click == 0){
            list_award_image.clear();
        }

        totalAmount = result.getData().getTotalAmount();


        List<AwardBean.DataBean.ElementsBean> elements = result.getData().getElements();
        list_award_image.addAll(elements);
        awardAdapter.notifyDataSetChanged();

        if (list_award_image.size() == 0){
            Toast.makeText(AwardActivity.this,"现在还没有活动呦",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("nnnnnnnnnnna",i+"");
        Intent intent = new Intent(this, SurroundDetailsActivity.class);
        intent.putExtra("articleId", list_award_image.get(i).getId() + "");
//        intent.putExtra("detailtitle", elements_headline.get(i - 1).getTitle() + "");
//        intent.putExtra("detailcontent", elements_headline.get(i - 1).getSummary());
        startActivity(intent);

    }

    private Handler mHandler;
    private int i = 0;

    private void listen() {
        lv_award.setOnPullUpLoadListener(new LoadMoreListView.OnPullUpLoadListener() {
            @Override
            public void onPullUpLoading() {
                mHandler.sendEmptyMessageDelayed(0x124, 2000);
            }
        });


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123) {
                    i = 1;
                    Log.e("vvvvvvvvvvvvvcc","刷新");
                    is_not_click = 0;
                    award.setBgaAdpaterAndClick(AwardActivity.this,1);
                    refreshable_layout.finishRefreshing();
                } else if (msg.what == 0x124) {
                    refreshable_layout.finishRefreshing();
                    Log.e("vvvvvvvvvvvvvcc","加载---"+i);
                    if (i < (Math.round(totalAmount/10)+1)*2) {
                        i++;
                        is_not_click = 1;
                        award.setBgaAdpaterAndClick(AwardActivity.this,i);
                        lv_award.onPullUpLoadFinished(true);
                    } else {
                        lv_award.onPullUpLoadFinished(false);
                    }

                }
            }
        };

        refreshable_layout.setOnRefreshListener(new RefreshLayout.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(0x123, 2000);
            }
        });

    }

}
