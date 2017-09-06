package com.shiwaixiangcun.customer.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.RecordAccessListAdapter;
import com.shiwaixiangcun.customer.adapter.RecordfinishListAdapter;
import com.shiwaixiangcun.customer.model.RecordBean;
import com.shiwaixiangcun.customer.presenter.impl.HouseRecordImpl;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyListView;
import com.shiwaixiangcun.customer.ui.IRecordView;

import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity implements View.OnClickListener,IRecordView{

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private ListView lv_accept;
    private List<String> list_access = new ArrayList<>();
    private MyListView lv_finish;
    private TextView tv_tv;
    private RelativeLayout rl_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
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
        lv_accept = (ListView) findViewById(R.id.lv_accept);

        View footer_record = LayoutInflater.from(this).inflate(R.layout.footer_record, null);
        lv_finish = (MyListView) footer_record.findViewById(R.id.lv_finish);
        tv_tv = (TextView) footer_record.findViewById(R.id.tv_tv);

        View head_record = LayoutInflater.from(this).inflate(R.layout.head_record, null);
        rl_title = (RelativeLayout) head_record.findViewById(R.id.rl_title);

        lv_accept.addHeaderView(head_record);
        lv_accept.addFooterView(footer_record);
    }

    private void initData() {
        HouseRecordImpl houseRecord = new HouseRecordImpl(this);
        houseRecord.setBgaAdpaterAndClick(this);

        for (int i = 0; i < 10; i++) {
            list_access.add("111");
        }


        tv_page_name.setText("报修记录");


        back_left.setOnClickListener(this);

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
    private List<RecordBean.DataBean.ElementsBean> list_finish = new ArrayList<>();
    private List<RecordBean.DataBean.ElementsBean> list_accept = new ArrayList<>();
    @Override
    public void setBgaAdpaterAndClickResult(RecordBean result) {
        Log.i("aaaaaaa",result.getData().getElements().size()+"");
//        List<SubmitRecordsBean> elements = result.getData().getElements();
        List<RecordBean.DataBean.ElementsBean> elements = result.getData().getElements();
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getStatus().equals("FINISHED")){
                list_finish.add(elements.get(i));
            }else {
                list_accept.add(elements.get(i));
            }

        }
        if (list_finish.size() != 0 && list_accept.size() != 0 ){
            tv_tv.setVisibility(View.VISIBLE);
        }else {
            tv_tv.setVisibility(View.GONE);
        }

        if (list_accept.size() == 0){
            rl_title.setVisibility(View.GONE);
        }else {
            rl_title.setVisibility(View.VISIBLE);
        }

        Log.i("gggggoopa",list_finish.size()+"---------"+list_accept.size());

        RecordAccessListAdapter recordAccessListAdapter = new RecordAccessListAdapter(list_accept,this);
        lv_accept.setAdapter(recordAccessListAdapter);

        RecordfinishListAdapter recordfinishListAdapter = new RecordfinishListAdapter(list_finish,this);
        lv_finish.setAdapter(recordfinishListAdapter);

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
