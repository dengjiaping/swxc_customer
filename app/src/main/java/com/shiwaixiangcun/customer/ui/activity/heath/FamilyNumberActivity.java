package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterFamilyNumber;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.FamilyNumberBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.SwitchButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         亲情号码
 */
public class FamilyNumberActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_add_number)
    TextView mTvAddNumber;
    @BindView(R.id.rv_family_number)
    RecyclerView mRvFamilyNumber;
    @BindView(R.id.llayout_nodata)
    LinearLayout mLlayoutNodata;
    @BindView(R.id.switch_ban)
    SwitchButton mSwitchBan;


    private List<FamilyNumberBean.SosPhoneDtosBean> mList;
    private AdapterFamilyNumber mAdapterFamilyNumber;
    private TextView mTvAddFamily;
    private int intelligenceWatchId;
    private String strToken;
    private String strRefreshToken;
    private SwitchButton mSwitchButtonBan;
    private SwitchButton mSwitchButtonLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_number);
        ButterKnife.bind(this);
        EventCenter.getInstance().register(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intelligenceWatchId = extras.getInt("intelligenceWatchId", 0);
        }

        initViewAndEvent();
        initData();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {

        OkGo.<String>get(GlobalAPI.sosList)
                .params("access_token", strToken)
                .params("intelligenceWatchId", intelligenceWatchId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<FamilyNumberBean>>() {
                        }.getType();
                        ResponseEntity<FamilyNumberBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }

                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                if (responseEntity.getData().isIncomingRestriction() && !mSwitchBan.isChecked()) {
                                    mSwitchBan.toggleImmediatelyNoEvent();
                                }


                                if (responseEntity.getData().isIncomingRestriction() && !mSwitchButtonBan.isChecked()) {
                                    mSwitchButtonBan.toggleImmediatelyNoEvent();
                                }
                                if (responseEntity.getData().isSosDialCycleTimes() && !mSwitchButtonLoop.isChecked()) {
                                    mSwitchButtonLoop.toggleImmediatelyNoEvent();
                                }
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_FAMILY_NUMBER, 1, responseEntity.getData()));

                                break;
                            default:
                                mLlayoutNodata.setVisibility(View.VISIBLE);
                                mRvFamilyNumber.setVisibility(View.GONE);
                                break;

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mLlayoutNodata.setVisibility(View.VISIBLE);
                        mRvFamilyNumber.setVisibility(View.GONE);
                    }
                });

    }

    private void initViewAndEvent() {
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        strRefreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");


        mBackLeft.setOnClickListener(this);
        mTvPageName.setText(R.string.family_number);
        mTvAddNumber.setOnClickListener(this);

        mSwitchBan.setOnCheckedChangeListener(this);
        mList = new ArrayList<>();
        mAdapterFamilyNumber = new AdapterFamilyNumber(mList);
        mRvFamilyNumber.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setColorRes(R.color.color_divider_0_3)
                .setMarginLeft(20)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.BETWEEN)
                .build();


        mRvFamilyNumber.setAdapter(mAdapterFamilyNumber);


        View headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_header_family, mRvFamilyNumber, false);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.layout_footer_family, mRvFamilyNumber, false);

        mTvAddFamily = footerView.findViewById(R.id.tv_add_family);
        mSwitchButtonBan = footerView.findViewById(R.id.switch_ban_number);
        mSwitchButtonLoop = footerView.findViewById(R.id.switch_loop);
        mAdapterFamilyNumber.addHeaderView(headerView);
        mAdapterFamilyNumber.addFooterView(footerView);
        mRvFamilyNumber.addItemDecoration(divider);

        mSwitchButtonBan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modifyWatchInfo("incomingRestriction", isChecked);
            }
        });
        mSwitchButtonLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modifyWatchInfo("sosDialCycleTimes", isChecked);
            }
        });
        mTvAddFamily.setOnClickListener(this);
        mAdapterFamilyNumber.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FamilyNumberBean.SosPhoneDtosBean bean = (FamilyNumberBean.SosPhoneDtosBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("familyInfo", bean);
                readyGo(ManageNumberActivity.class, bundle);
            }
        });

        mAdapterFamilyNumber.setChangeListener(new AdapterFamilyNumber.CheckChangeListener() {
            @Override
            public void onCheckChanger(int position, boolean isChecked) {
                Log.e("tag", position + "\t" + isChecked);
                FamilyNumberBean.SosPhoneDtosBean data = mAdapterFamilyNumber.getData().get(position - 1);
                modifyNumberInfo(data, isChecked);
            }
        });

    }

    /**
     * 修改SOS号码
     *
     * @param item
     * @param isChecked
     */
    private void modifyNumberInfo(FamilyNumberBean.SosPhoneDtosBean item, boolean isChecked) {
        OkGo.<String>put(GlobalAPI.modifyFamilyNumber)
                .params("access_token", strToken)
                .params("name", item.getName())
                .params("id", item.getId())
                .params("phone", item.getPhone())
                .params("dialFlag", isChecked)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        } else {
                            switch (responseEntity.getResponseCode()) {
                                case 1001:

                                    initData();

                                    break;
                                default:
                                    showToastShort("设置SOS失败");
                                    break;
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        showToastShort("修改亲情号码失败");

                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_FAMILY_NUMBER) {
            return;
        }
        FamilyNumberBean familyNumberBean = (FamilyNumberBean) simpleEvent.getData();
        switch (simpleEvent.mEventValue) {
            case 1:
                List<FamilyNumberBean.SosPhoneDtosBean> sosPhoneDtos = familyNumberBean.getSosPhoneDtos();

                if (sosPhoneDtos == null) {
                    mLlayoutNodata.setVisibility(View.VISIBLE);
                    mRvFamilyNumber.setVisibility(View.GONE);
                    return;
                }
                if (sosPhoneDtos.size() == 0) {
                    mLlayoutNodata.setVisibility(View.VISIBLE);
                    mRvFamilyNumber.setVisibility(View.GONE);
                } else {
                    mLlayoutNodata.setVisibility(View.GONE);
                    mRvFamilyNumber.setVisibility(View.VISIBLE);
                    mList.clear();
                    mList.addAll(sosPhoneDtos);
                    mAdapterFamilyNumber.notifyDataSetChanged();
                }


                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {

            case R.id.back_left:
                finish();
                break;

            case R.id.tv_add_number:
                bundle.putBoolean("isKill", true);
                readyGo(AddFamilyNumberActivity.class);
                break;
            case R.id.tv_add_family:

                if (mAdapterFamilyNumber.getData().size() == 10) {
                    showToastShort("只能添加10个亲情号码");
                } else {


                    bundle.putBoolean("isKill", false);
                    readyGo(AddFamilyNumberActivity.class);
                }
                break;

            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        EventCenter.getInstance().unregister(this);
        super.onDestroy();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_ban:
                modifyWatchInfo("incomingRestriction", isChecked);
                break;
            default:
                break;
        }

    }

    /**
     * 修改设备信息
     *
     * @param type
     * @param isChecked
     */
    private void modifyWatchInfo(String type, boolean isChecked) {
        OkGo.<String>put(GlobalAPI.modifyWatchInfo)
                .params("access_token", strToken)
                .params(type, isChecked)
                .isSpliceUrl(true)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        } else {
                            switch (responseEntity.getResponseCode()) {
                                case 1001:
                                    showToastShort("修改信息成功");

                                    break;
                                default:
                                    showToastShort("修改信息失败");
                                    break;
                            }
                        }

                    }
                });

    }
}
