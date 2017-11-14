package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSearchResult;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.ElementBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.edit_search)
    EditText mEditSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;
    @BindView(R.id.rlayout_no_data)
    RelativeLayout mRlayoutNoData;
    private AdapterSearchResult mAdapter;
    private List<ElementBean.ElementsBean> mList = new ArrayList<>();
    private String searchKey;
    private int siteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        searchKey = bundle.getString("name");

        initView();
        requestData(searchKey, 1, 10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }

    /**
     * 从服务器获取数据以后更新
     *
     * @param simpleEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_SEARCH) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                ElementBean guessData = (ElementBean) simpleEvent.getData();
                mList.clear();
                mList.addAll(guessData.getElements());
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;


        }
    }

    /**
     * 请求网络数据
     *
     * @param page 页码
     * @param size 每页数目
     */
    private void requestData(String keyword, int page, int size) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("search", keyword);
        httpParams.put("page.pn", page);
        httpParams.put("page.size", size);
        httpParams.put("siteId", siteID);
        OkGo.<String>get(GlobalAPI.searchGood)
                .params(httpParams)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<ElementBean>>() {
                        }.getType();
                        ResponseEntity<ElementBean> data = JsonUtil.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {
                            case 1001:
                                if (data.getData().getElements().size() == 0) {
                                    mRlayoutNoData.setVisibility(View.VISIBLE);
                                    return;
                                }
                                mRlayoutNoData.setVisibility(View.GONE);
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_SEARCH, 1, data.getData()));
                                break;
                            default:
                                break;
                        }
                    }
                });


    }

    private void initView() {

        mEditSearch.setText(searchKey);
        siteID = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterSearchResult(mList);
        mRvSearchResult.setAdapter(mAdapter);
        mBackLeft.setOnClickListener(this);

        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.BOTH)
                .setMarginLeft(8)
                .setMarginRight(8)

                .setColorRes(R.color.color_divider_0_3)
                .build();
        mRvSearchResult.addItemDecoration(divider);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                searchKey = mEditSearch.getText().toString();
                Log.e(BUG_TAG, searchKey);
                requestData(searchKey, 1, 10);
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
        }

    }


}
