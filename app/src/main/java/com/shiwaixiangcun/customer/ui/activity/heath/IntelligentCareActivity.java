package com.shiwaixiangcun.customer.ui.activity.heath;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterIntelligentCare;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.model.WatchInfoBean;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.NotOpenActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.GridUtils;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         <p>
 *         智能关爱页面
 */
public class IntelligentCareActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_tools_detail)
    RecyclerView mRvToolsDetail;

    AdapterIntelligentCare mAdapterIntelligentCare;
    ToolCategoryBean.ChildrenBeanX data;
    List<ToolCategoryBean.ChildrenBeanX.ChildrenBean> childrenList;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    View bottomView = null;
    String isLogin = "";
    private TextView tvBottom;

    private String strToken;
    private boolean isBindWatch = false;

    @Override
    protected void onRestart() {
        super.onRestart();
        requestData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligent_care);
        ButterKnife.bind(this);
        initBundle();
        initViewAndEvent();
        requestData();
    }


    /**
     * 请求设备信息
     */
    private void requestData() {

        OkGo.<String>get(GlobalAPI.getWatchInfo)
                .cacheMode(CacheMode.NO_CACHE)
                .params("access_token", strToken)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Type type = new TypeToken<ResponseEntity<WatchInfoBean>>() {
                        }.getType();
                        ResponseEntity<WatchInfoBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                WatchInfoBean.WatchDataBean watchData = responseEntity.getData().getWatchData();
                                if (watchData == null) {
                                    return;
                                }
                                if (watchData.getHardwareId() != 0) {

                                    AppSharePreferenceMgr.put(mContext, GlobalConfig.IS_BIND_WATCH, true);
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_setting).setVisibility(View.VISIBLE);
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_detail_content).setVisibility(View.GONE);
                                    TextView textView = (TextView) mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_status);
                                    TextView tvResidualPower = (TextView) mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_residual_power);
                                    textView.setText(responseEntity.getData().getWatchData().getModelType());
                                    tvResidualPower.setText("剩余电量 " + responseEntity.getData().getWatchData().getRemainingPower() + "%");
                                    isBindWatch = true;

                                } else {
                                    AppSharePreferenceMgr.put(mContext, GlobalConfig.IS_BIND_WATCH, false);
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_setting).setVisibility(View.GONE);

                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_residual_power).setVisibility(View.GONE);
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_detail_content).setVisibility(View.VISIBLE);
                                    TextView textView = (TextView) mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.tv_status);
                                    textView.setText("未绑定");
                                    isBindWatch = false;


                                }
                                if (watchData.getHardwareId() != 0 || watchData.isFamilyLocation()) {
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.view_divider).setVisibility(View.VISIBLE);
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.llayout_location).setVisibility(View.VISIBLE);

                                } else {
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.view_divider).setVisibility(View.GONE);
                                    mAdapterIntelligentCare.getViewByPosition(mRvToolsDetail, 0, R.id.llayout_location).setVisibility(View.GONE);


                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

    }

    private void initViewAndEvent() {
        if (data == null) {
            return;
        }

        bottomView = LayoutInflater.from(mContext).inflate(R.layout.layout_footer_tools, null, false);
        tvBottom = bottomView.findViewById(R.id.tv_bottom);
        tvBottom.setText(data.getRemark());
        mTvPageName.setText(data.getName());
        mBackLeft.setOnClickListener(this);
        mAdapterIntelligentCare = new AdapterIntelligentCare();
        mRvToolsDetail.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mRvToolsDetail.setAdapter(mAdapterIntelligentCare);
        mRvToolsDetail.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, DisplayUtil.dip2px(mContext, 10));
            }
        });
        mRvToolsDetail.removeAllViews();
        mAdapterIntelligentCare.addData(childrenList);
        mAdapterIntelligentCare.notifyDataSetChanged();

        mAdapterIntelligentCare.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToolCategoryBean.ChildrenBeanX.ChildrenBean bean = (ToolCategoryBean.ChildrenBeanX.ChildrenBean) adapter.getData().get(position);


                if (bean.isAuthorization()) {
                    if (StringUtil.isEmpty(isLogin)) {
                        readyGo(LoginActivity.class);
                    } else {
                        GridUtils.readyGo(mContext, bean);
                    }
                } else {
                    GridUtils.readyGo(mContext, bean);

                }
            }
        });

        mAdapterIntelligentCare.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                readyGo(LocationActivity.class);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
    }

    private void initBundle() {
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        Bundle extras = getIntent().getExtras();
        String json = extras.getString("item");
        data = JsonUtil.fromJson(json, ToolCategoryBean.ChildrenBeanX.class);

        if (data != null) {
            childrenList = data.getChildren();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                Bundle bundle = new Bundle();
                bundle.putString("name", "缴费记录");
                bundle.putInt("image", R.drawable.data_empty);
                bundle.putString("message", "缴费记录为空");
                readyGo(NotOpenActivity.class, bundle);
                break;

            default:
                break;

        }
    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }


}
