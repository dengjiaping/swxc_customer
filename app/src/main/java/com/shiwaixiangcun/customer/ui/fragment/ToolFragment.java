package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterTool;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.ToolBean;
import com.shiwaixiangcun.customer.ui.activity.AwardActivity;
import com.shiwaixiangcun.customer.ui.activity.HouseRentingActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.LookDecoratingActivity;
import com.shiwaixiangcun.customer.ui.activity.OnlineServiceActivity;
import com.shiwaixiangcun.customer.ui.activity.SurroundLifeActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.HealthEvaluationActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.PhysicalActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.WebActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ToolFragment;
 */

public class ToolFragment extends Fragment {

    private static String BUG_TAG = "ToolFragment";

    @BindView(R.id.rv_tools)
    RecyclerView mRvTools;
    AdapterTool mAdapterTool;
    Unbinder unbinder;
    private List<ToolBean> mToolList;
    private List<ToolBean> mHeathList;
    private List<ToolBean> mPropetyList;
    private List<ToolBean> mSelectiveList;
    private String mTitle;
    private Activity mActivity;
    private boolean hasLogin = false;

    public static Fragment getInstance(String title) {

        ToolFragment fragment = new ToolFragment();
        fragment.mTitle = title;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool, container, false);
        unbinder = ButterKnife.bind(this, view);
        mActivity = this.getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mToolList = new ArrayList<>();
        switch (mTitle) {
            case "健康服务":
                mToolList.addAll(mHeathList);
                break;
            case "物业服务":
                mToolList.addAll(mPropetyList);
                break;
            case "优选服务":
                mToolList.addAll(mSelectiveList);
                break;
        }

        mAdapterTool.addData(mToolList);
    }

    private void initView() {

        String isLogin = (String) AppSharePreferenceMgr.get(this.getActivity(), Common.USER_IS_LOGIN, "notLogin");
        if (isLogin.equals("notLogin")) {
            hasLogin = false;
        }
        if (isLogin.equals("isLogin")) {
            hasLogin = true;
        }
        mAdapterTool = new AdapterTool(R.layout.layout_tool);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 4);
        mRvTools.setLayoutManager(gridLayoutManager);
        mRvTools.setAdapter(mAdapterTool);
        mAdapterTool.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToolBean toolBean = mToolList.get(position);
                Bundle bundle = new Bundle();
                switch (toolBean.id) {
                    case 1:
                        //体征数据
                        if (hasLogin) {
                            readyGo(PhysicalActivity.class);
                        } else {
                            readyGo(LoginActivity.class);
                        }
                        break;
                    case 2:
                        //健康评测
                        readyGo(HealthEvaluationActivity.class);
                        break;
                    case 3:
                        //健康方案
                        bundle.putInt("type", 3);
                        readyGo(WebActivity.class, bundle);
                        break;
                    case 4:
                        //健康动态
                        bundle.putInt("type", 4);
                        readyGo(WebActivity.class, bundle);
                        break;
                    case 5:
                        //预约挂号
                        bundle.putInt("type", 5);
                        readyGo(WebActivity.class, bundle);
                        break;
                    case 6:
                        //预约挂号
                        readyGo(TeatActivity.class);
                        break;
                    case 7:
                        //在线问诊
                        break;
                    case 8:
                        //在线报修
                        readyGo(OnlineServiceActivity.class);
                        break;
                    case 9:
                        //找装修
                        readyGo(LookDecoratingActivity.class);
                        break;
                    case 10:
                        //房屋租售
                        readyGo(HouseRentingActivity.class);
                        break;
                    case 11:
                        //在线缴费
                        Toast.makeText(mActivity, "暂未开通此功能", Toast.LENGTH_SHORT).show();
                        break;
                    case 12:
                        //周边生活
                        readyGo(SurroundLifeActivity.class);
                        break;
                    case 13:
                        //活动
                        readyGo(AwardActivity.class);
                        break;
                    case 14:
                        //建康保险
                        bundle.putInt("type", 14);
                        readyGo(WebActivity.class, bundle);
                        break;

                    case 15:
                        //旅游度假
                        bundle.putInt("type", 15);
                        readyGo(WebActivity.class, bundle);
                        break;

                }
            }
        });
    }

    /**
     * 别问我为什么这么写代码，我tm也是被逼的，日了狗
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeathList = new ArrayList<>();
        mSelectiveList = new ArrayList<>();
        mPropetyList = new ArrayList<>();

        //添加健康服务的数据
        ToolBean tizheng = new ToolBean(1, "体征数据", R.drawable.home_sign);
        mHeathList.add(tizheng);
        ToolBean jiankangceping = new ToolBean(2, "健康评测", R.drawable.home_appraisal);
        mHeathList.add(jiankangceping);
        ToolBean jiankanfangan = new ToolBean(3, "健康方案", R.drawable.home_scheme);
        mHeathList.add(jiankanfangan);
        ToolBean jiankangdongtai = new ToolBean(4, "健康动态", R.drawable.home_dynamic);
        mHeathList.add(jiankangdongtai);
        ToolBean yuyueguahao = new ToolBean(5, "预约挂号", R.drawable.home_consultation);
        mHeathList.add(yuyueguahao);
        ToolBean jiankangshipu = new ToolBean(6, "健康食谱", R.drawable.home_recipe);
        mHeathList.add(jiankangshipu);
        ToolBean zaixianwenzhen = new ToolBean(7, "在线问诊", R.drawable.home_inquiry);
        mHeathList.add(zaixianwenzhen);

        //添加物业服务数据
        ToolBean zaixianbaoxiu = new ToolBean(8, "在线报修", R.drawable.home_repair);
        ToolBean zhaozhuangxiu = new ToolBean(9, "找装修", R.drawable.home_fitment);
        ToolBean fangwuzushou = new ToolBean(10, "房屋租售", R.drawable.home_rent);
        ToolBean zaixianjiaofei = new ToolBean(11, "在线缴费", R.drawable.home_payment);
        ToolBean zhoubianshenghuo = new ToolBean(12, "周边生活", R.drawable.home_surround);
        mPropetyList.add(zaixianbaoxiu);
        mPropetyList.add(zhaozhuangxiu);
        mPropetyList.add(fangwuzushou);
        mPropetyList.add(zaixianjiaofei);
        mPropetyList.add(zhoubianshenghuo);
        //添加优选服务
        ToolBean huodong = new ToolBean(13, "活动", R.drawable.home_activity);
        ToolBean jiankangbaoxian = new ToolBean(14, "健康保险", R.drawable.home_insurance);
        ToolBean lvyoudujia = new ToolBean(15, "旅游度假", R.drawable.home_holiday);
        mSelectiveList.add(huodong);
        mSelectiveList.add(jiankangbaoxian);
        mSelectiveList.add(lvyoudujia);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this.getActivity(), clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
