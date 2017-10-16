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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterRight;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.RightsRecordBean;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RightsRecordActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_right_record)
    RecyclerView mRvRightRecord;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private List<RightsRecordBean.ElementsBean> mList;
    private AdapterRight mAdapterRight;
    private String refreshToken;
    private String tokenString;
    private int page = 1;
    private int pageSize = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rights_record);
        ButterKnife.bind(this);
        initViewAndEvent();
        requestData();
    }

    private void requestData() {
        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(GlobalAPI.rightRecord)
                .params("access_token", tokenString)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "success");
                        Type type = new TypeToken<ResponseEntity<RightsRecordBean>>() {
                        }.getType();

                        ResponseEntity<RightsRecordBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                mList.clear();
                                mList.addAll(responseEntity.getData().getElements());
                                mAdapterRight.notifyDataSetChanged();
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
        mTvPageName.setText("维权记录");
        mBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
        mAdapterRight = new AdapterRight(mList);
        mRvRightRecord.setLayoutManager(new LinearLayoutManager(mContext));
        mRvRightRecord.setAdapter(mAdapterRight);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(0)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvRightRecord.addItemDecoration(divider);
        mAdapterRight.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RightsRecordBean.ElementsBean bean = (RightsRecordBean.ElementsBean) adapter.getData().get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable("detail", bean);
                readyGo(RightDetailActivity.class, bundle);
            }
        });

    }
}
