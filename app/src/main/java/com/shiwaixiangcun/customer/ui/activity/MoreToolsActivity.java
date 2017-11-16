package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterService;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.ui.activity.heath.IntelligentCareActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.GridUtils;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全部服务Activity
 *
 * @author Administrator
 */
public class MoreToolsActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;

    private List<AdapterService.MySection> mList;
    private AdapterService mAdapterService;
    private String isLogin = "";
    private int siteID;

    private List<String> mJsonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tools);
        ButterKnife.bind(this);
        initViewAndEvent();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
        OkGo.<String>get(GlobalAPI.getToolCategory)
                .params("siteId", siteID)
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
                                mList.clear();
                                for (ToolCategoryBean.ChildrenBeanX fatherItem : titleList) {

                                    String typeJson = JsonUtil.toJson(fatherItem);
                                    AdapterService.MySection title = new AdapterService.MySection(true, fatherItem.getName(), fatherItem.getSign(), typeJson);
                                    mList.add(title);
                                    List<ToolCategoryBean.ChildrenBeanX.ChildrenBean> childrenList = fatherItem.getChildren();
                                    for (ToolCategoryBean.ChildrenBeanX.ChildrenBean childrenItem : childrenList) {

                                        ToolCategoryBean.ChildrenBeanX.ChildrenBean treeBean = new ToolCategoryBean.ChildrenBeanX.ChildrenBean();
                                        treeBean.setName(childrenItem.getName());
                                        treeBean.setAppCategoryStatus(childrenItem.getAppCategoryStatus());
                                        treeBean.setLink(childrenItem.getLink());
                                        treeBean.setImageLink(childrenItem.getImageLink());
                                        treeBean.setSign(childrenItem.getSign());
                                        treeBean.setAuthorization(childrenItem.isAuthorization());
                                        AdapterService.MySection childItem = new AdapterService.MySection(treeBean, fatherItem.getSign(), typeJson);
                                        mList.add(childItem);
                                    }
                                }


                                mAdapterService.notifyDataSetChanged();
                                break;
                            default:
                                break;

                        }


                    }
                });
    }


    private void initViewAndEvent() {

        siteID = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        mTvPageName.setText("全部服务");
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        mList = new ArrayList<>();
        mJsonList = new ArrayList<>();
        mBackLeft.setOnClickListener(this);
        mAdapterService = new AdapterService(mList);
        mRvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        mRvCategory.setAdapter(mAdapterService);
        mAdapterService.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapterService.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(BUG_TAG, "click");
                AdapterService.MySection bean = (AdapterService.MySection) adapter.getData().get(position);
                if (bean.getStrType().equals("INTELLIGENT_CARE")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("item", bean.getTypeJson());
                    readyGo(IntelligentCareActivity.class, bundle);
                } else {
                    ToolCategoryBean.ChildrenBeanX.ChildrenBean treeBean = bean.getTreeBean();
                    if (bean.getTreeBean() == null) {
                        return;
                    } else {
                        if (treeBean.isAuthorization()) {
                            if (StringUtil.isEmpty(isLogin)) {
                                readyGo(LoginActivity.class);
                            } else {
                                GridUtils.readyGo(mContext, treeBean, true);
                            }
                        } else {

                            GridUtils.readyGo(mContext, treeBean, true);

                        }

                    }
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
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
