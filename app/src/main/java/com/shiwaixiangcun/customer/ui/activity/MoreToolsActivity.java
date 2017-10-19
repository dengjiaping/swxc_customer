package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterService;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.utils.GridUtils;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreToolsActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;

    private List<AdapterService.MySection> mList;
    private AdapterService mAdapterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tools);
        ButterKnife.bind(this);
        initViewAndEvent();
        initData();
    }

    private void initData() {
        OkGo.<String>get(GlobalAPI.getToolCategory)
                .params("siteId", GlobalConfig.siteID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<ToolCategoryBean>>() {
                        }.getType();
                        ResponseEntity<ToolCategoryBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                List<ToolCategoryBean.ChildrenBeanX> titleList = responseEntity.getData().getChildren();
//                                ToolCategoryBean.ChildrenBeanX titleList = (ToolCategoryBean.ChildrenBeanX) responseEntity.getData().getChildren();
                                for (ToolCategoryBean.ChildrenBeanX headItem : titleList) {

                                    AdapterService.MySection title = new AdapterService.MySection(true, headItem.getName());
                                    mList.add(title);
                                    List<ToolCategoryBean.ChildrenBeanX.ChildrenBean> childrenList = headItem.getChildren();
                                    for (ToolCategoryBean.ChildrenBeanX.ChildrenBean childrenBean : childrenList) {

                                        ToolCategoryBean.ChildrenBeanX.ChildrenBean treeBean = new ToolCategoryBean.ChildrenBeanX.ChildrenBean();
                                        treeBean.setName(childrenBean.getName());
                                        treeBean.setAppCategoryStatus(childrenBean.getAppCategoryStatus());
                                        treeBean.setLink(childrenBean.getLink());
                                        treeBean.setImageLink(childrenBean.getImageLink());
                                        treeBean.setSign(childrenBean.getSign());
                                        AdapterService.MySection childItem = new AdapterService.MySection(treeBean);
                                        mList.add(childItem);
                                    }
                                }


                                mAdapterService.notifyDataSetChanged();
                                break;

                        }


                    }
                });
    }


    private void initViewAndEvent() {
        mTvPageName.setText("全部服务");
        mList = new ArrayList<>();
        mBackLeft.setOnClickListener(this);
        mAdapterService = new AdapterService(mList);
        mRvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        mRvCategory.setAdapter(mAdapterService);
        mAdapterService.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapterService.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AdapterService.MySection bean = (AdapterService.MySection) adapter.getData().get(position);
                showToastShort(position + "");
                if (bean.getTreeBean() == null) {
                    return;
                } else {
                    GridUtils.go(mContext, bean.getTreeBean());
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
