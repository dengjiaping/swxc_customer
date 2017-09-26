package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;

import com.shiwaixiangcun.customer.presenter.IBloodPressurePresenter;
import com.shiwaixiangcun.customer.ui.IBloodPressureView;

/**
 * Created by Administrator on 2017/5/25.
 */

public class BloodPressureImpl implements IBloodPressurePresenter {
    private final static String BUG_TAG = "BloodPressureImpl";
    private IBloodPressureView iBloodPressureView;
    private int customId;

    public BloodPressureImpl(IBloodPressureView iBloodPressureView, int customId) {
        this.iBloodPressureView = iBloodPressureView;
        this.customId = customId;
    }

    @Override
    public void setBgaAdapterAndClick(Context context) {
        sendBloodPressureHttp(context);
    }


    //血压
    private void sendBloodPressureHttp(final Context context) {

    }


}
