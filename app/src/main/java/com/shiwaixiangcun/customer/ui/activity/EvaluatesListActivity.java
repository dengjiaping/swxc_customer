package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterEvaluate;
import com.shiwaixiangcun.customer.model.EvaluatesListBean;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class EvaluatesListActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_evaluate)
    RecyclerView mRvEvaluate;


    private List<GoodDetail.DataBean.EvaluatesBean> mListEvaluate = new ArrayList<>();
    private EvaluatesListBean mEvaluatesListBean;
    private AdapterEvaluate mAdapterEvaluate;

    private String strGrade;
    private int goodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluates_list);
        ButterKnife.bind(this);
        initViewAndEvent();
        initData();
    }

    private void initData() {
        OkGo.<String>get(GlobalAPI.getEvaluateList)
                .params("goodsId", goodId)
                .params("grade", strGrade)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });

    }

    private void initViewAndEvent() {
        mTvPageName.setText("评价");
        mBackLeft.setOnClickListener(this);
        mAdapterEvaluate = new AdapterEvaluate(mListEvaluate);
        mRvEvaluate.setLayoutManager(new LinearLayoutManager(this));
        mRvEvaluate.setAdapter(mAdapterEvaluate);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;

        }

    }
}
