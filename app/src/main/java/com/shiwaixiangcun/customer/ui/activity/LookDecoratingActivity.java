package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.DecordatingListAdapter;
import com.shiwaixiangcun.customer.model.RecoratingListBean;
import com.shiwaixiangcun.customer.presenter.impl.HouseRecoratingImpl;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.ui.IHouseRecoratingView;

import java.util.ArrayList;
import java.util.List;

public class LookDecoratingActivity extends AppCompatActivity implements View.OnClickListener,ListView.OnItemClickListener,IHouseRecoratingView{

    private ListView lv_decording;
    private ChangeLightImageView back_left;
    private List<String> list_decording = new ArrayList<>();
    private TextView tv_page_name;
    private List<RecoratingListBean> elements_recorating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_decorating);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        lv_decording = (ListView) findViewById(R.id.lv_decording);

    }

    private void initData() {
        HouseRecoratingImpl houseRecorating = new HouseRecoratingImpl(this);
        houseRecorating.setBgaAdpaterAndClick(this);

        tv_page_name.setText("找装修");
        for (int i = 0; i < 10; i++) {
            list_decording.add("aa");
        }




        back_left.setOnClickListener(this);
        lv_decording.setOnItemClickListener(this);

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(LookDecoratingActivity.this,CompanyDetailActivity.class);
        intent.putExtra("recoratingId",elements_recorating.get((int)l).getId()+"");
        startActivity(intent);
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity<PageBean<RecoratingListBean>> result) {
        elements_recorating = result.getData().getElements();

        DecordatingListAdapter decordatingListAdapter = new DecordatingListAdapter(elements_recorating,this);
        lv_decording.setAdapter(decordatingListAdapter);
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
