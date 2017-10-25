package com.shiwaixiangcun.customer.ui.activity;

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
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterToolDetail;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.GridUtils;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_tools_detail)
    RecyclerView mRvToolsDetail;
    AdapterToolDetail mAdapterToolDetail;
    ToolCategoryBean.ChildrenBeanX data;
    List<ToolCategoryBean.ChildrenBeanX.ChildrenBean> childrenList;
    boolean isShowRight;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    View bottomView = null;
    String isLogin = "";
    private TextView tvBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_detail);
        ButterKnife.bind(this);

        initData();
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        if (data == null) {
            return;
        }
        if (isShowRight) {
            mTvTopRight.setText("缴费记录");
            mTvTopRight.setVisibility(View.VISIBLE);
            mTvTopRight.setOnClickListener(this);

        }
        bottomView = LayoutInflater.from(mContext).inflate(R.layout.layout_footer_tools, null, false);
        tvBottom = (TextView) bottomView.findViewById(R.id.tv_bottom);
        tvBottom.setText(data.getRemark());
        mTvPageName.setText(data.getName());
        mBackLeft.setOnClickListener(this);
        mAdapterToolDetail = new AdapterToolDetail();
        mRvToolsDetail.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mRvToolsDetail.setAdapter(mAdapterToolDetail);
        mRvToolsDetail.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, DisplayUtil.dip2px(mContext, 10));
            }
        });
        mRvToolsDetail.removeAllViews();
        mAdapterToolDetail.addData(childrenList);

        mAdapterToolDetail.addFooterView(bottomView);
        mAdapterToolDetail.notifyDataSetChanged();

        mAdapterToolDetail.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
    }

    private void initData() {
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        Bundle extras = getIntent().getExtras();
        String json = extras.getString("item");
        isShowRight = extras.getBoolean("show");
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
