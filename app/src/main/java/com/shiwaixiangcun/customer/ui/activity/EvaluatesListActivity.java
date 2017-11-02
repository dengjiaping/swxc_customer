package com.shiwaixiangcun.customer.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.shiwaixiangcun.customer.adapter.AdapterEvaluate;
import com.shiwaixiangcun.customer.model.EvaluatesListBean;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class EvaluatesListActivity extends BaseActivity implements View.OnClickListener {


    private static final int CODE_SUCCESS = 1001;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_evaluate)
    RecyclerView mRvEvaluate;
    @BindView(R.id.btn_all)
    Button mBtnAll;
    @BindView(R.id.btn_good)
    Button mBtnGood;
    @BindView(R.id.btn_mid)
    Button mBtnMid;
    @BindView(R.id.btn_bad)
    Button mBtnBad;
    @BindView(R.id.llayout_no_data)
    LinearLayout mLlayoutNoData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private List<GoodDetail.DataBean.EvaluatesBean> mListEvaluate = new ArrayList<>();
    private EvaluatesListBean mEvaluatesListBean;
    private AdapterEvaluate mAdapterEvaluate;

    private String strGrade;
    private int goodId;

    private int highTotal;
    private int midTotal;
    private int badTotal;
    private int allTotal;
    private int mCurrentPage = GlobalConfig.first_page;
    private int mPageSize = GlobalConfig.page_size;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluates_list);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            goodId = extras.getInt("goodID");
            highTotal = extras.getInt("highTotal");
            midTotal = extras.getInt("midTotal");
            badTotal = extras.getInt("badTotal");
            allTotal = extras.getInt("allTotal");
        }
        initViewAndEvent();
        initData(false);
    }

    /**
     * 获取数据
     *
     * @param isLoadMore 是否是上拉加载
     */
    private void initData(final boolean isLoadMore) {
        OkGo.<String>get(GlobalAPI.getEvaluateList)
                .params("goodsId", goodId)
                .params("grade", strGrade)
                .params("page.pn", mCurrentPage)
                .params("page.size", mPageSize)
                .execute(new StringCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Response<String> response) {
                        mEvaluatesListBean = JsonUtil.fromJson(response.body(), EvaluatesListBean.class);
                        if (mEvaluatesListBean == null) {
                            return;
                        }
                        switch (mEvaluatesListBean.getResponseCode()) {
                            case CODE_SUCCESS:

                                if (isLoadMore) {
                                    mLlayoutNoData.setVisibility(View.INVISIBLE);

                                    mRefreshLayout.finishLoadmore();
                                } else {
                                    if (mEvaluatesListBean.getData().getElements().size() == 0) {
                                        mLlayoutNoData.setVisibility(View.VISIBLE);
                                        mRefreshLayout.setEnableRefresh(false);
                                    } else {
                                        mRefreshLayout.setEnableRefresh(true);
                                        mLlayoutNoData.setVisibility(View.INVISIBLE);
                                    }
                                    mListEvaluate.clear();
                                    mRefreshLayout.finishRefresh();
                                }

                                EvaluatesListBean.DataBean data = mEvaluatesListBean.getData();
                                mBtnGood.setText("好评" + "(" + data.getHighTotal() + ")");
                                mBtnAll.setText("全部" + "(" + data.getTotalAmount() + ")");
                                mBtnMid.setText("中评" + "(" + data.getBadTotal() + data.getMidTotal() + data.getHighTotal() + ")");
                                mBtnBad.setText("差评" + "(" + data.getBadTotal() + ")");
                                mListEvaluate.addAll(mEvaluatesListBean.getData().getElements());
                                mAdapterEvaluate.notifyDataSetChanged();

                                break;
                            default:
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.finishLoadmore();
                                mLlayoutNoData.setVisibility(View.VISIBLE);
                                break;
                        }

                    }

                });

    }

    @SuppressLint("SetTextI18n")
    private void initViewAndEvent() {
        mTvPageName.setText("评价");
        mBackLeft.setOnClickListener(this);
        mBtnAll.setSelected(true);
        mBtnAll.setTextColor(getResources().getColor(R.color.ui_white));
        mBtnGood.setText("好评" + "(" + highTotal + ")");

        mBtnAll.setText("全部" + "(" + allTotal + ")");
        mBtnMid.setText("中评" + "(" + midTotal + ")");
        mBtnBad.setText("差评" + "(" + badTotal + ")");

        mBtnAll.setOnClickListener(this);
        mBtnBad.setOnClickListener(this);
        mBtnMid.setOnClickListener(this);
        mBtnGood.setOnClickListener(this);
        mAdapterEvaluate = new AdapterEvaluate(mListEvaluate);
        mRvEvaluate.setLayoutManager(new LinearLayoutManager(this));
        mRvEvaluate.setAdapter(mAdapterEvaluate);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.BOTH)
                .setMarginLeft(20)
                .setMarginRight(0)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvEvaluate.addItemDecoration(divider);


        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.finishRefresh();
                }
                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                initData(true);

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mRefreshLayout.isLoading()) {
                    mRefreshLayout.finishLoadmore();
                }
                mCurrentPage = 1;
                initData(false);

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_good:

                setSelected(mBtnGood, "HighOpinion");
                setUnSelected(mBtnMid);
                setUnSelected(mBtnAll);
                setUnSelected(mBtnBad);
                break;
            case R.id.btn_all:
                setSelected(mBtnAll, "");

                setUnSelected(mBtnGood);
                setUnSelected(mBtnMid);
                setUnSelected(mBtnBad);
                break;
            case R.id.btn_mid:
                setSelected(mBtnMid, "MiddleOpinion");
                setUnSelected(mBtnAll);
                setUnSelected(mBtnGood);
                setUnSelected(mBtnBad);
                break;
            case R.id.btn_bad:
                setSelected(mBtnBad, "BadOpinion");
                setUnSelected(mBtnAll);
                setUnSelected(mBtnGood);
                setUnSelected(mBtnMid);
                break;

            default:
                break;

        }

    }

    public void setSelected(Button button, String grade) {
        if (button.isSelected()) {
            return;
        } else {
            button.setSelected(true);
            button.setTextColor(getResources().getColor(R.color.ui_white));
            strGrade = grade;
            mListEvaluate.clear();
            mCurrentPage = 1;
            initData(false);

        }
    }

    public void setUnSelected(Button unSelected) {
        if (unSelected.isSelected()) {
            unSelected.setSelected(false);
            unSelected.setTextColor(getResources().getColor(R.color.black_text_100));
        } else {
            return;
        }
    }
}
