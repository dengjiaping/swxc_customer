package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AdapterDoctor;
import com.shiwaixiangcun.customer.model.DoctorBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class DoctorActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_doctor_master)
    RecyclerView mRvDoctorMaster;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<DoctorBean.ElementsBean> mList;

    private AdapterDoctor mAdapterDoctor;
    private int currentPage = GlobalConfig.first_page;
    private int pageSize = GlobalConfig.page_size;
    private String strToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docor);
        ButterKnife.bind(this);
        initViewAndEvent();
        requestData(currentPage, pageSize, false);
    }

    /**
     *
     */
    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("全国专家库");
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");

        mList = new ArrayList<>();

        mAdapterDoctor = new AdapterDoctor(mList);
        mRvDoctorMaster.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvDoctorMaster.setAdapter(mAdapterDoctor);
        mRvDoctorMaster.addItemDecoration(divider);

        mAdapterDoctor.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DoctorBean.ElementsBean item = (DoctorBean.ElementsBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                readyGo(DoctorDetailActivity.class, bundle);

            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (refreshlayout.isRefreshing()) {
                    refreshlayout.finishRefresh();

                }
                requestData(currentPage, pageSize, true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                if (refreshlayout.isLoading()) {
                    refreshlayout.finishLoadmore();

                }
                currentPage = 1;
                requestData(currentPage, pageSize, false);


            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        requestData(currentPage, pageSize, false);
    }

    public void requestData(int start, int size, final boolean isLoadMore) {
        OkGo.<String>get(GlobalAPI.doctormaster)
                .params("access_token", strToken)
                .params("page.pn", start)
                .params("page.size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<DoctorBean>>() {
                        }.getType();
                        ResponseEntity<DoctorBean> data = JsonUtil.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {

                            case 1001:
                                if (data.getData().getElements().size() == 0) {
                                    mRefreshLayout.finishLoadmore(true);
                                    return;
                                }

                                if (isLoadMore) {

                                    currentPage++;
                                    mRefreshLayout.finishLoadmore(true);
                                } else {
                                    mList.clear();
                                    mRefreshLayout.finishRefresh(true);
                                }

                                mList.addAll(data.getData().getElements());
                                mAdapterDoctor.notifyDataSetChanged();
                                break;
                            default:
                                Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                                mRefreshLayout.finishLoadmore(false);
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
            default:
                break;
        }
    }
}
