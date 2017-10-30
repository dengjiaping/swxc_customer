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

    private List<residentLastBean.DataBean> mHouseDataList;
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
package com.shiwaixiangcun.customer.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResidentBean;
import com.shiwaixiangcun.customer.model.residentLastBean;
import com.shiwaixiangcun.customer.presenter.impl.ResidentCertificationImpl;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppManager;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ResUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.WheelView;
import com.shiwaixiangcun.customer.ui.IResifdentView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ResidentCertificationActivity extends Activity implements View.OnClickListener, WheelView.OnValueChangeListener, IResifdentView {

    private ChangeLightImageView back_left;
    private Button btn_to_other;
    private WheelView wheelView;
    private WheelView wheelView_t;
    private WheelView wheelView_h;
    private WheelView wheelView_f;
    private List<ResidentBean.DataBean> data;
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
    private List<residentLastBean.DataBean> data_house;
    private int id_new;
    private String totle;

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

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        btn_to_other = (Button) findViewById(R.id.btn_to_other);

        initView();

    }

    private void initData() {
        back_left.setOnClickListener(this);
        btn_to_other.setOnClickListener(this);

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
                Log.i("bpppaaa", "========" + str_houseId);
                if (Utils.isNotEmpty(totle_totle)) {
                    Intent intent = new Intent(this, ToResidentCertificationActivity.class);
                    intent.putExtra("slectHouse", totle_totle);
                    intent.putExtra("houseId", str_houseId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "请选择房产", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void initView() {
        wheelView = (WheelView) findViewById(R.id.WheelView_dialog);
        wheelView_t = (WheelView) findViewById(R.id.WheelView_t);
        wheelView_h = (WheelView) findViewById(R.id.WheelView_h);
        wheelView_f = (WheelView) findViewById(R.id.WheelView_f);
        wheelView.setOnValueChangedListener(this);
        wheelView_t.setOnValueChangedListener(this);
        wheelView_h.setOnValueChangedListener(this);
        wheelView_f.setOnValueChangedListener(this);

        wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelView.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));

    }

    private void setSubView(List<ResidentBean.DataBean> data) {
        String[] str_area = new String[data.size()];
        if (data.size() == 0) {
            String[] stringsNew = {""};
            wheelView.refreshByNewDisplayedValues(stringsNew);
            //设置是否可以上下无限滑动
            wheelView.setWrapSelectorWheel(false);
            wheelView.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        } else {
            for (int i = 0; i < data.size(); i++) {
                str_area[i] = data.get(i).getName();
            }
//        String[] str_wheel = {"我的","他的","你的"};
            wheelView.refreshByNewDisplayedValues(str_area);
            //设置是否可以上下无限滑动
            wheelView.setWrapSelectorWheel(false);
            wheelView.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        }


        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(0).getChildren();
        Log.i("oooooooooooee", children_t.size() + "");
        String[] str_t = new String[children_t.size()];
        if (children_t.size() == 0) {
            String[] stringsNew = {""};
            wheelView_t.refreshByNewDisplayedValues(stringsNew);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        } else {
            for (int i = 0; i < children_t.size(); i++) {
                str_t[i] = children_t.get(i).getName();
            }


            wheelView_t.refreshByNewDisplayedValues(str_t);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        }

        if (children_t.size() != 0){
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = children_t.get(0).getChildren();
            Log.i("oooooooooooee2", children_h.size() + "");
            String[] str_h = new String[children_h.size()];
            if (children_h.size() == 0) {
                String[] stringsNew = {""};
                wheelView_h.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_h.size(); i++) {
                    str_h[i] = children_h.get(i).getName();
                }
                wheelView_h.refreshByNewDisplayedValues(str_h);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


//        int id_o = data.get(0).getId();
//        int id_t = children_t.get(0).getId();
//        int id = children_h.get(0).getId();
        if (data.get(0).getChildren().size() == 0){
            totle = data.get(0).getName();
            id_new = data.get(0).getId();
        }else {
            if (data.get(0).getChildren().get(0).getChildren().size() == 0){
                totle = data.get(0).getName() + data.get(0).getChildren().get(0).getName();
                id_new = data.get(0).getChildren().get(0).getId();
            }else {
                totle = data.get(0).getName() + data.get(0).getChildren().get(0).getName() + data.get(0).getChildren().get(0).getChildren().get(0).getName();
            }
        }

//        Log.i("hhhhpppop", id_o + "---" + id_t + "---" + id);

//        if (id)
        sendHouseHttp(this, id_new, totle, 100);


    }

    private void setSubOView(List<ResidentBean.DataBean> data, int position) {
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = null;
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> children_f = null;
        position_o = position;
        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(position).getChildren();
        Log.i("oooooooooooee", children_t.size() + "");
        String[] str_t = new String[children_t.size()];
        if (children_t.size() == 0) {
            String[] stringsNew = {""};
            wheelView_t.refreshByNewDisplayedValues(stringsNew);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        } else {
            for (int i = 0; i < children_t.size(); i++) {
                str_t[i] = children_t.get(i).getName();
            }


            wheelView_t.refreshByNewDisplayedValues(str_t);
            //设置是否可以上下无限滑动
            wheelView_t.setWrapSelectorWheel(false);
            wheelView_t.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
            wheelView_t.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
            wheelView_t.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        }


        if (children_t != null && children_t.size() != 0) {
            children_h = children_t.get(0).getChildren();
            Log.i("oooooooooooee2", children_h.size() + "");
            String[] str_h = new String[children_h.size()];
            if (children_h.size() == 0) {
                String[] stringsNew = {""};
                wheelView_h.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_h.size(); i++) {
                    str_h[i] = children_h.get(i).getName();
                }
                wheelView_h.refreshByNewDisplayedValues(str_h);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


        if (children_h != null && children_h.size() != 0) {
            children_f = children_h.get(0).getChildren();
            Log.i("oooooooooooee3", children_f.size() + "");
            String[] str_f = new String[children_f.size()];

            if (children_f.size() == 0) {
                String[] stringsNew = {""};
                wheelView_f.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_f.size(); i++) {
                    str_f[i] = children_f.get(i).getName();
                }
                wheelView_f.refreshByNewDisplayedValues(str_f);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


    }

    private void setSubTView(List<ResidentBean.DataBean> data, int position) {
        List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = null;
        position_t = position;
        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(position_o).getChildren();

        if (children_t != null && children_t.size() != 0) {
            children_h = children_t.get(0).getChildren();

            Log.i("oooooooooooee2", children_h.size() + "");
            String[] str_h = new String[children_h.size()];
            if (children_h.size() == 0) {
                String[] stringsNew = {""};
                wheelView_h.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_h.size(); i++) {
                    str_h[i] = children_h.get(i).getName();
                }
                wheelView_h.refreshByNewDisplayedValues(str_h);
                //设置是否可以上下无限滑动
                wheelView_h.setWrapSelectorWheel(false);
                wheelView_h.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_h.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_h.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


        if (children_h != null && children_h.size() != 0) {
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> children_f = children_h.get(0).getChildren();
            Log.i("oooooooooooee3", children_f.size() + "");
            String[] str_f = new String[children_f.size()];

            if (children_f.size() == 0) {
                String[] stringsNew = {""};
                wheelView_f.refreshByNewDisplayedValues(stringsNew);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            } else {
                for (int i = 0; i < children_f.size(); i++) {
                    str_f[i] = children_f.get(i).getName();
                }
                wheelView_f.refreshByNewDisplayedValues(str_f);
                //设置是否可以上下无限滑动
                wheelView_f.setWrapSelectorWheel(false);
                wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
            }
        }


    }

    private void setSubHView(List<ResidentBean.DataBean> data, int position) {
        position_h = position;
        List<ResidentBean.DataBean.ChildrenBeanXXX> children_t = data.get(position_o).getChildren();
        if (children_t != null && children_t.size() != 0) {
            List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX> children_h = children_t.get(position_t).getChildren();
            if (children_h != null && children_h.size() != 0) {
                List<ResidentBean.DataBean.ChildrenBeanXXX.ChildrenBeanXX.ChildrenBeanX> children_f = children_h.get(0).getChildren();
                Log.i("oooooooooooee3", children_f.size() + "");
                String[] str_f = new String[children_f.size()];

                if (children_f.size() == 0) {
                    String[] stringsNew = {""};
                    wheelView_f.refreshByNewDisplayedValues(stringsNew);
                    //设置是否可以上下无限滑动
                    wheelView_f.setWrapSelectorWheel(false);
                    wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                    wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                    wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                } else {
                    for (int i = 0; i < children_f.size(); i++) {
                        str_f[i] = children_f.get(i).getName();
                    }
                    wheelView_f.refreshByNewDisplayedValues(str_f);
                    //设置是否可以上下无限滑动
                    wheelView_f.setWrapSelectorWheel(false);
                    wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                    wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                    wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                }

            }
        }


    }

    @Override
    public void onValueChange(WheelView picker, int oldVal, int newVal) {
        Log.i("kkkkkkkkkkkkk", oldVal + "----" + newVal);
        String[] content = wheelView.getDisplayedValues();
        String[] content_t = wheelView_t.getDisplayedValues();
        String[] content_h = wheelView_h.getDisplayedValues();
        String[] content_f = wheelView_f.getDisplayedValues();

        int id = picker.getId();
        switch (id) {
            case R.id.WheelView_dialog:
                int id_o = 100000;
                Log.i("hhhhhhhhh", "滑动了1");
                setSubOView(data, newVal);


                if (content.length != 0) {
                    Log.i("bbbbbbnn", (newVal - wheelView.getMinValue()) + "---------" + content.length);
                    Log.i("jjjjjjjjjjj1", content[newVal - wheelView.getMinValue()]);
                    name_o = content[newVal - wheelView.getMinValue()];
                }
                if (data.get(newVal).getChildren().size() != 0) {
                    name_t = data.get(newVal).getChildren().get(0).getName();

                    Log.i("miiioooaaa", "---------mmmmkkaaa");
                    if (data.get(newVal).getChildren().get(0).getChildren().size() != 0) {
                        Log.i("ssssssssaaa", "aaaaaaaaaaaa");
                        name_h = data.get(newVal).getChildren().get(0).getChildren().get(0).getName();
                    } else {
                        Log.i("ssssssssaaa", "aaaaasdsadsa");

                    }

                } else {

                }


                total_o = name_o + name_t + name_h;

                Log.i("miiioooaaa", "---------mmmmkk");

                int id_o_o = data.get(newVal).getId();
                if (data.get(newVal).getChildren().size() == 0) {
                    Log.i("miiioooaaa", "第二格为空");
                    wheelView_h.refreshByNewDisplayedValues(str_all_not);
                    id_o = data.get(newVal).getId();
                } else {
                    Log.i("miiioooaaa", "第二格不为空");
                    if (data.get(newVal).getChildren().get(0).getChildren().size() == 0) {
                        Log.i("miiioooaaa", "第三格为空");
                        id_o = data.get(newVal).getChildren().get(0).getId();
                    } else {
                        Log.i("miiioooaaa", "第三格不为空");
                        id_o = data.get(newVal).getChildren().get(0).getChildren().get(0).getId();
                    }
                }

//                int id_o_t = data.get(newVal).getChildren().get(0).getId();
//                int id_o_h = data.get(newVal).getChildren().get(0).getChildren().get(0).getId();
//                Log.i("uuuuuiiiopaa","---------0000");
//                Log.i("uuuuuiiiopaa",id_o_o+"-----"+id_o_t+"--------"+id_o_h+"");

//                sendHouseHttp(this, data.get(newVal).getChildren().get(0).getChildren().get(0).getId(), total_o, 100);
                sendHouseHttp(this, id_o, total_o, 100);


                Log.i("mmmmmmmmmsssss", id_o + "-----" + total_o);


                break;
            case R.id.WheelView_t:
                Log.i("hhhhhhhhh", "滑动了2");
                setSubTView(data, newVal);


                if (content_t.length != 0) {
                    Log.i("bbbbbbnn", (newVal - wheelView_t.getMinValue()) + "---------" + content_t.length);
                    Log.i("jjjjjjjjjjj2", content_t[newVal - wheelView_t.getMinValue()]);
                    name_t_t = content_t[newVal - wheelView_t.getMinValue()];
                }
                if (data != null && data.size() != 0) {
                    name_t_o = data.get(position_o).getName();
                }
                if (data.get(position_o).getChildren().size() != 0) {
                    if (data.get(position_o).getChildren().get(newVal).getChildren().size() != 0) {
                        name_t_h = data.get(position_o).getChildren().get(newVal).getChildren().get(0).getName();
                    }else {
                        name_t_h = "";
                    }


                }
                totle_t = name_t_o + name_t_t + name_t_h;
                if (data.get(position_o).getChildren().size() != 0) {
                    if (data.get(position_o).getChildren().size() != 0) {
                        if (null != data.get(position_o).getChildren().get(0).getChildren()) {
                            if (data.get(position_o).getChildren().get(0).getChildren().size() != 0) {
                                sendHouseHttp(this, data.get(position_o).getChildren().get(newVal).getChildren().get(0).getId(), totle_t, 100);
                            }else {
                                sendHouseHttp(this, data.get(position_o).getChildren().get(newVal).getId(), totle_t, 100);
                            }
                        }
                    }
                }


                break;
            case R.id.WheelView_h:
                Log.i("hhhhhhhhh", "滑动了3");
                setSubHView(data, newVal);


                if (content_h.length != 0) {
                    Log.i("bbbbbbnn", (newVal - wheelView_h.getMinValue()) + "---------" + content_h.length);
                    Log.i("jjjjjjjjjjj3", content_h[newVal - wheelView_h.getMinValue()]);
                    name_h_h = content_h[newVal - wheelView_h.getMinValue()];
                }
                if (data != null && data.size() != 0) {
                    name_h_o = data.get(position_o).getName();
                }
                if (data.get(position_o).getChildren().get(position_t).getChildren() != null && data.get(position_o).getChildren().get(position_t).getChildren().size() != 0) {
                    name_h_t = data.get(position_o).getChildren().get(position_t).getName();
                }
                Log.i("tttttttttrrr", position_o + "+====" + position_t + "------" + newVal);
                totle_h = name_h_o + name_h_t + name_h_h;
                sendHouseHttp(this, data.get(position_o).getChildren().get(position_t).getChildren().get(newVal).getId(), totle_h, 100);

                break;
            case R.id.WheelView_f:
                Log.i("ssssaaaasdsadsd",newVal+"");
//                Log.i("hhhhhhhhh", "滑动了4" + position_h + position_t + position_o);
//                if (data != null && data.size() != 0) {
//                    name_f_o = data.get(position_o).getName();
//                }
//                if (data.get(position_o).getChildren() != null && data.get(position_o).getChildren().size() != 0) {
//                    name_f_t = data.get(position_o).getChildren().get(position_t).getName();
//                }
//                if (data.get(position_o).getChildren().get(position_t).getChildren() != null && data.get(position_o).getChildren().get(position_t).getChildren().size() != 0) {
//                    name_f_h = data.get(position_o).getChildren().get(position_t).getChildren().get(position_h).getName();
//                }
//                String totle_f = name_f_o + name_f_t + name_t_h;
//                sendHouseHttp(this, data.get(position_o).getChildren().get(position_t).getChildren().get(position_h).getId(), totle_f, newVal);

                str_houseId = data_house.get(newVal).getId()+"";

                break;
        }


    }

    @Override
    public void setBgaAdpaterAndClickResult(ResidentBean result) {
        data = result.getData();
        setSubView(data);
    }

    String totle_totle = "";
    private String str_houseId = "";

    //某单元下得房屋
    private void sendHouseHttp(Context context, int id, final String little_total, final int value) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("regionId", id);
        hashMap.put("fields", "id,number");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.houseUnit);
        HttpRequest.get(Common.houseUnit, hashMap, new HttpCallBack() {


            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---aaassaa", responseJson);
//                Type type = new TypeToken<ResponseEntity>() {
//                }.getType();
//                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                residentLastBean residentLastBean = new Gson().fromJson(responseJson, residentLastBean.class);
                data_house = residentLastBean.getData();
                if (data_house.size() != 0){
                    Log.i("oooooooooooee3", data_house.size() + "");
                    String[] str_f = new String[data_house.size()];


                    if (value == 100) {
                        str_houseId = data_house.get(0).getId() + "";
                        if (data_house.size() == 0) {
                            String[] stringsNew = {""};
                            wheelView_f.refreshByNewDisplayedValues(stringsNew);
                            //设置是否可以上下无限滑动
                            wheelView_f.setWrapSelectorWheel(false);
                            wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                            wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                            wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                        } else {

                            for (int i = 0; i < data_house.size(); i++) {
                                str_f[i] = data_house.get(i).getNumber();
                            }
                            wheelView_f.refreshByNewDisplayedValues(str_f);
                            //设置是否可以上下无限滑动
                            wheelView_f.setWrapSelectorWheel(false);
                            wheelView_f.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
                            wheelView_f.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
                            wheelView_f.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
                        }
                        totle_totle = little_total + data_house.get(0).getNumber();
                    } else {
                        Log.i("ggggggggtt", value + "");
                        str_houseId = data_house.get(value).getId() + "";
                        totle_totle = little_total + data_house.get(value).getNumber();
                    }

                    Log.i("bbbbbbttt", totle_totle);
                }else {
                    wheelView_f.refreshByNewDisplayedValues(str_all_not);
                }




            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
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
}
