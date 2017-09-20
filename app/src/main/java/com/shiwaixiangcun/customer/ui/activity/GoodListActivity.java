package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterGoodList;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.MallBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.RecyclerViewDivider;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoodListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_good_list)
    RecyclerView mRvGoodList;


    private int flag = 0;
    private String type;
    private List<ElementBean.ElementsBean> mGoodList = new ArrayList<>();
    AdapterGoodList mAdapterGoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }

    private void initView() {
        switch (flag) {
            case 4:
                mTvPageName.setText("每日精选");
                break;
            case 1:
                mTvPageName.setText("品质好货");
                break;
            case 2:
                mTvPageName.setText("热卖商品");
                break;
            case 3:
                mTvPageName.setText("新品热卖");
                break;
        }

        mAdapterGoodList = new AdapterGoodList(mGoodList);
        mBackLeft.setOnClickListener(this);
        mRvGoodList.setAdapter(mAdapterGoodList);
        mRvGoodList.setLayoutManager(new LinearLayoutManager(this));
        mAdapterGoodList.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mGoodList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });
//        mRvGoodList.addItemDecoration(new RecyclerViewDivider(this,LinearLayoutManager.VERTICAL,));


    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        flag = bundle.getInt("flag");
        requestData(type, flag, 1, 10);

    }

    /**
     * 请求数据
     *
     * @param type 请求的类型
     * @param flag 更新界面的标志
     */
    public void requestData(String type, final int flag, int start, int size) {

        HttpParams params = new HttpParams();
        params.put("goodsSubjectValue", type);
        params.put("page.pn", start);
        params.put("page.size", size);
        params.put("siteId", "1");
        OkGo.<String>get(GlobalConfig.getGuessLike)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<ElementBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<ElementBean> data = gson.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {
                            case 1001:
                                if (data.getData().getElements().size() == 0) {
                                    return;
                                }
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_GOOD_LIST, flag, data.getData()));
                                break;
                        }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_GOOD_LIST) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            //更新品质好货
            case 1:
                ElementBean data = (ElementBean) simpleEvent.getData();
                mAdapterGoodList.addData(data.getElements());
                break;

            //更新热卖商品
            case 2:
                ElementBean hotData = (ElementBean) simpleEvent.getData();
                mAdapterGoodList.addData(hotData.getElements());
                break;
            //更新 新品热卖
            case 3:
                ElementBean newData = (ElementBean) simpleEvent.getData();
                mAdapterGoodList.addData(newData.getElements());
                break;
            //更新每日精选
            case 4:
                ElementBean jingxuanData = (ElementBean) simpleEvent.getData();
                mAdapterGoodList.addData(jingxuanData.getElements());
                break;

        }
    }

}
