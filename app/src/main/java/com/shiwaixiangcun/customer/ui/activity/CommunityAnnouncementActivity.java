package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.ComListAdapter;
import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.CommunityPresenterImpl;
import com.shiwaixiangcun.customer.ui.CommunityView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

public class CommunityAnnouncementActivity extends BaseActivity implements View.OnClickListener, CommunityView, ListView.OnItemClickListener {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private ListView lv_community;
    private List<String> list_community = new ArrayList<>();
    private List<AnnouncementBean> elements_anno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_announcement);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        CommunityPresenterImpl communityPresenter = new CommunityPresenterImpl(this);
        communityPresenter.setBgaAdpaterAndClick(this);
        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        lv_community = (ListView) findViewById(R.id.lv_community);

    }

    private void initData() {
        tv_page_name.setText("社区公告");
        list_community.add("1");
        list_community.add("2");
        list_community.add("3");
        list_community.add("1");
        list_community.add("1");

        back_left.setOnClickListener(this);
        lv_community.setOnItemClickListener(this);
//        ComListAdapter comListAdapter = new ComListAdapter(this,list_community);
//        lv_community.setAdapter(comListAdapter);
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
    public void setBgaAdpaterAndClickResult(ResponseEntity<PageBean<AnnouncementBean>> result) {
        if (result != null) {
            elements_anno = result.getData().getElements();

            ComListAdapter comListAdapter = new ComListAdapter(this, elements_anno);
            lv_community.setAdapter(comListAdapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("fffffffaaa", i + "---------" + l);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("articleId", elements_anno.get(i).getId() + "");
        intent.putExtra("detailTitle", elements_anno.get(i).getTitle());
        intent.putExtra("detailContent", elements_anno.get(i).getSummary());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
