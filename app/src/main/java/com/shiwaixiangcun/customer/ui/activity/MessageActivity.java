package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.MessageDetailActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterMessage;
import com.shiwaixiangcun.customer.model.MessageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.heath.HeartRateActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_message)
    RecyclerView mRvMessage;

    private AdapterMessage mAdapterMessage;
    private List<MessageBean.ElementsBean> mMessageList;
    private String tokenString;
    private String refreshToken;

    private int page = 1;
    private int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initViewAndEvent();
        initData();
    }

    private void initData() {

        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(GlobalAPI.getMessage)
                .params("access_token", tokenString)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "success");
                        Type type = new TypeToken<ResponseEntity<MessageBean>>() {
                        }.getType();

                        ResponseEntity<MessageBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                mMessageList.clear();
                                mMessageList.addAll(responseEntity.getData().getElements());
                                mAdapterMessage.notifyDataSetChanged();
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }

                    }
                });
    }

    private void initViewAndEvent() {

        mTvPageName.setText("消息中心");
        mMessageList = new ArrayList<>();
        mBackLeft.setOnClickListener(this);
        mAdapterMessage = new AdapterMessage(mMessageList);
        mRvMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mRvMessage.setAdapter(mAdapterMessage);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(0)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvMessage.addItemDecoration(divider);
        mAdapterMessage.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MessageBean.ElementsBean bean = (MessageBean.ElementsBean) adapter.getData().get(position);
                switch (bean.getMessageCoreType()) {
                    case "INTERROGATION":

                        Bundle bundle = new Bundle();
                        bundle.putString("partner", bean.getPartner());
                        bundle.putString("problemId", bean.getProblemId());
                        readyGo(MessageDetailActivity.class, bundle);
                        break;
                    case "HEALTHMESSAGE":

                        Bundle healthBundle = new Bundle();
                        healthBundle.putInt("customID", bean.getCustomerId());
                        readyGo(HeartRateActivity.class, healthBundle);
                        break;
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
        }

    }
}
