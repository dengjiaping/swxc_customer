package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ResidentBean;
import com.shiwaixiangcun.customer.model.residentLastBean;
import com.shiwaixiangcun.customer.presenter.impl.ResidentCertificationImpl;
import com.shiwaixiangcun.customer.ui.IResifdentView;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.ResUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.WheelView;

import java.util.List;

public class ResidentCertificationActivity extends BaseActivity implements View.OnClickListener, WheelView.OnValueChangeListener, IResifdentView {
    private ChangeLightImageView back_left;
    private Button btn_to_other;
    private WheelView wheelViewFirst;
    private WheelView mWheelViewSecond;
    private WheelView mWheelViewThird;
    private WheelView mWheelViewFourth;
    private List<ResidentBean.DataBean> data;

    private ResidentBean.DataBean firstData;
    private ResidentBean.DataBean.ChildrenBeanXXX secondData;
    private ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX thirdData;
    private residentLastBean.DataBean fourthData;
    private int position_t = 0;
    private int position_o = 0;
    private int position_h = 0;
    private String name_o;
    private String name_t;
    private String name_h;
    private String name_t_o;
    private String name_t_t;
    private String name_t_h;
    private String name_h_h;
    private String name_h_o;
    private String name_h_t;
    private String totle_h;
    private String totle_t;
    private String total_o;
    private String name_f_t;
    private String name_f_h;
    private String name_f_o;
    private String[] str_all_not = {""};
    private List<residentLastBean.DataBean> mHouseDataList;
    private int id_new;
    private String totle;
    private List<residentLastBean.DataBean> mFourDataList;
    private String houseName;
    private int houseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_certification);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        AppManager.getAppManager().addActivity(this);
        ResidentCertificationImpl residentCertification = new ResidentCertificationImpl(this, "");
        residentCertification.setBgaAdpaterAndClick(this);
        initViewAndEvent();
        initData();
    }


    /**
     * 初始化视图
     */
    private void initViewAndEvent() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        btn_to_other = (Button) findViewById(R.id.btn_to_other);


        wheelViewFirst = (WheelView) findViewById(R.id.WheelView_first);
        mWheelViewSecond = (WheelView) findViewById(R.id.WheelView_second);
        mWheelViewThird = (WheelView) findViewById(R.id.WheelView_third);
        mWheelViewFourth = (WheelView) findViewById(R.id.WheelView_fourth);


        wheelViewFirst.setWrapSelectorWheel(false);
        wheelViewFirst.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelViewFirst.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
        wheelViewFirst.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));

        mWheelViewSecond.setWrapSelectorWheel(false);
        mWheelViewSecond.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        mWheelViewSecond.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
        mWheelViewSecond.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));

        mWheelViewThird.setWrapSelectorWheel(false);
        mWheelViewThird.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        mWheelViewThird.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
        mWheelViewThird.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));

        mWheelViewFourth.setWrapSelectorWheel(false);
        mWheelViewFourth.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        mWheelViewFourth.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
        mWheelViewFourth.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));

        wheelViewFirst.setOnValueChangedListener(this);
        mWheelViewSecond.setOnValueChangedListener(this);
        mWheelViewThird.setOnValueChangedListener(this);
        mWheelViewFourth.setOnValueChangedListener(this);
        back_left.setOnClickListener(this);
        btn_to_other.setOnClickListener(this);


    }

    private void initData() {

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_to_other:
                if (Utils.isNotEmpty(houseName)) {
                    Intent intent = new Intent(this, ToResidentCertificationActivity.class);
                    intent.putExtra("slectHouse", houseName);
                    intent.putExtra("houseId", houseID);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "请选择房产", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }


    /**
     * 设置数据
     *
     * @param dataFirst
     */
    private void setSubView(List<ResidentBean.DataBean> dataFirst) {
        String[] strFirst = new String[dataFirst.size()];


        //设置第一列数据
        if (dataFirst.size() == 0) {
            String[] stringsNew = {""};
            wheelViewFirst.refreshByNewDisplayedValues(stringsNew);

        } else {
            for (int i = 0; i < dataFirst.size(); i++) {
                strFirst[i] = dataFirst.get(i).getName();
            }
            wheelViewFirst.refreshByNewDisplayedValues(strFirst);


        }


        //设置第二列数据
        List<ResidentBean.DataBean.ChildrenBeanXXX> dataSecond = dataFirst.get(0).getChildren();
        if (dataSecond == null) {
            return;
        }
        String[] strSecond = new String[dataSecond.size()];
        if (dataSecond.size() == 0) {
            String[] stringsNew = {""};
            mWheelViewSecond.refreshByNewDisplayedValues(stringsNew);

        } else {
            for (int i = 0; i < dataSecond.size(); i++) {
                strSecond[i] = dataSecond.get(i).getName();
            }

            mWheelViewSecond.refreshByNewDisplayedValues(strSecond);

        }
        //设置第三列数据
        if (dataSecond.size() != 0) {
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> childrenThird = dataSecond.get(0).getChildren();
            if (null == childrenThird) {
                return;
            }
            String[] strThird = new String[childrenThird.size()];
            if (childrenThird.size() == 0) {
                String[] stringsNew = {""};
                mWheelViewThird.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动

            } else {
                for (int i = 0; i < childrenThird.size(); i++) {
                    strThird[i] = childrenThird.get(i).getName();
                }
                mWheelViewThird.refreshByNewDisplayedValues(strThird);
                //设置是否可以上下无限滑动
//                mWheelViewThird.setWrapSelectorWheel(false);

            }
        }

        if (dataFirst.get(0).getChildren().size() == 0) {
            totle = dataFirst.get(0).getName();
            id_new = dataFirst.get(0).getId();
        } else {
            if (dataFirst.get(0).getChildren().get(0).getChildren().size() == 0) {
                totle = dataFirst.get(0).getName() + dataFirst.get(0).getChildren().get(0).getName();
                id_new = dataFirst.get(0).getChildren().get(0).getId();
            } else {
                totle = dataFirst.get(0).getName() + dataFirst.get(0).getChildren().get(0).getName() + dataFirst.get(0).getChildren().get(0).getChildren().get(0).getName();
            }
        }

        firstData = data.get(0);
        if (firstData.getChildren().size() == 0) {
            return;
        }
        secondData = firstData.getChildren().get(0);
        if (secondData == null) {
            return;
        }
        if (secondData.getChildren().size() == 0) {
            return;
        }
        thirdData = secondData.getChildren().get(0);
        if (thirdData == null) {
            return;
        }

        requestHouseInfo(thirdData.getId());


    }


    @Override
    public void onValueChange(WheelView picker, int oldVal, int newVal) {

        switch (picker.getId()) {
            case R.id.WheelView_first:
                scrollWheelFirst(newVal);
                break;
            case R.id.WheelView_second:
                scrollWheelSecond(newVal);
                break;
            case R.id.WheelView_third:
                scrollWheelThird(newVal);
                break;
            case R.id.WheelView_fourth:
                scrollWheelFourth(newVal);
                break;
        }

    }

    /**
     * 滚动第4个
     *
     * @param newVal 位置
     */
    private void scrollWheelFourth(int newVal) {
        fourthData = mHouseDataList.get(newVal);
        if (fourthData == null) {
            return;
        }

        houseName = fourthData.getNumberDesc();
        houseID = firstData.getId();
        Log.e(BUG_TAG, fourthData.getNumberDesc());

    }

    /**
     * 滚动第3个
     *
     * @param newVal 位置
     */
    private void scrollWheelThird(int newVal) {
        if (secondData.getChildren().size() == 0) {
            return;
        }
        thirdData = secondData.getChildren().get(newVal);
        if (thirdData == null) {
            return;
        }
        Log.e(BUG_TAG, "滚动第3列：" + thirdData.getName());
        requestHouseInfo(thirdData.getId());
    }

    /**
     * 滚动第2个
     *
     * @param newVal 位置
     */
    private void scrollWheelSecond(int newVal) {
        if (firstData.getChildren().size() == 0) {
            return;
        }
        secondData = firstData.getChildren().get(newVal);
        if (null == secondData) {
            return;
        }
        Log.e(BUG_TAG, "第二列选择的数据：" + secondData.getName());
        setWheelEmpty(mWheelViewThird);
        setWheelEmpty(mWheelViewFourth);
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> childrenThird = secondData.getChildren();
        if (null == childrenThird) {
            return;
        }
        String[] strThird = new String[childrenThird.size()];
        if (childrenThird.size() == 0) {
            String[] stringsNew = {""};
            mWheelViewThird.refreshByNewDisplayedValues(stringsNew);
        } else {
            for (int i = 0; i < childrenThird.size(); i++) {
                strThird[i] = childrenThird.get(i).getName();
            }
            mWheelViewThird.refreshByNewDisplayedValues(strThird);
        }
        requestHouseInfo(thirdData.getId());

    }

    /**
     * 滚动第一个
     *
     * @param newVal
     */
    private void scrollWheelFirst(int newVal) {
        firstData = data.get(newVal);
        setWheelEmpty(mWheelViewSecond);
        setWheelEmpty(mWheelViewThird);
        setWheelEmpty(mWheelViewFourth);
        Log.e(BUG_TAG, "第一列选择的数据：" + firstData.getName());
        //设置第二列数据

        List<ResidentBean.DataBean.ChildrenBeanXXX> dataSecond = firstData.getChildren();
        if (dataSecond == null) {
            return;
        }

        secondData = dataSecond.get(0);
        String[] strSecond = new String[dataSecond.size()];
        if (dataSecond.size() == 0) {
            String[] stringsNew = {""};
            mWheelViewSecond.refreshByNewDisplayedValues(stringsNew);

        } else {
            for (int i = 0; i < dataSecond.size(); i++) {
                strSecond[i] = dataSecond.get(i).getName();
            }

            mWheelViewSecond.refreshByNewDisplayedValues(strSecond);

        }
        //设置第三列数据
        if (dataSecond.size() != 0) {
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> childrenThird = dataSecond.get(0).getChildren();
            if (null == childrenThird) {
                return;
            }
            thirdData = childrenThird.get(0);
            String[] strThird = new String[childrenThird.size()];
            if (childrenThird.size() == 0) {
                String[] stringsNew = {""};
                mWheelViewThird.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动

            } else {
                for (int i = 0; i < childrenThird.size(); i++) {
                    strThird[i] = childrenThird.get(i).getName();
                }
                mWheelViewThird.refreshByNewDisplayedValues(strThird);
                //设置是否可以上下无限滑动
//                mWheelViewThird.setWrapSelectorWheel(false);

            }
        }

        requestHouseInfo(thirdData.getId());

    }

    /**
     * 获取房屋信息
     */
    private void requestHouseInfo(int regionId) {
        final String refresh_token = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        String tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(Common.houseUnit)
                .params("access_token", tokenString)
                .params("regionId", regionId)
                .params("fields", "id,numberDesc")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        residentLastBean residentLastBean = new Gson().fromJson(response.body(), residentLastBean.class);
                        if (residentLastBean == null) {
                            return;
                        }
                        mHouseDataList = residentLastBean.getData();

                        switch (residentLastBean.getResponseCode()) {
                            case 1001:
                                setWheelDataFourth(mHouseDataList);
                                break;

                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refresh_token);
                                break;
                            default:
                                showToastShort("获取房屋失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    @Override
    public void setBgaAdpaterAndClickResult(ResidentBean result) {
        data = result.getData();
        setSubView(data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    /**
     * 设置为空
     *
     * @param wheelEmpty
     */
    public void setWheelEmpty(WheelView wheelEmpty) {
        String[] stringsNew = {""};
        wheelEmpty.refreshByNewDisplayedValues(stringsNew);

    }

    /**
     * 设置第4个滚动控件信息
     *
     * @param wheelDataFourth
     */
    public void setWheelDataFourth(List<residentLastBean.DataBean> wheelDataFourth) {
        if (wheelDataFourth == null) {
            return;
        }
        houseName = wheelDataFourth.get(0).getNumberDesc();
        houseID = wheelDataFourth.get(0).getId();
        String[] strFourth = new String[wheelDataFourth.size()];
        //设置第一列数据
        if (wheelDataFourth.size() == 0) {
            String[] stringsNew = {""};
            mWheelViewFourth.refreshByNewDisplayedValues(stringsNew);
        } else {
            for (int i = 0; i < wheelDataFourth.size(); i++) {
                String number = wheelDataFourth.get(i).getNumberDesc();
                String[] split = number.split("[-]");
                strFourth[i] = split[split.length - 1];

            }
            mWheelViewFourth.refreshByNewDisplayedValues(strFourth);
        }
    }
}
