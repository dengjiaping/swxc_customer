package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.FamilyNumberBean;
import com.shiwaixiangcun.customer.widget.SwitchButton;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/11/8
 */

public class AdapterFamilyNumber extends BaseQuickAdapter<FamilyNumberBean.SosPhoneDtosBean, BaseViewHolder> {


    private CheckChangeListener mChangeListener = null;

    public AdapterFamilyNumber(@Nullable List<FamilyNumberBean.SosPhoneDtosBean> data) {
        super(R.layout.item_family_number, data);
    }

    public void setChangeListener(CheckChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, FamilyNumberBean.SosPhoneDtosBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_number, item.getPhone());
        SwitchButton switchButton = helper.getView(R.id.switch_sos);
        if (item.isDialFlag() && !switchButton.isChecked()) {
            switchButton.toggleImmediatelyNoEvent();
        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mChangeListener != null) {
                    mChangeListener.onCheckChanger(helper.getAdapterPosition(), isChecked);
                }
            }
        });


    }

    public interface CheckChangeListener {
        /**
         * 状态改变回调
         *
         * @param position
         * @param isChecked
         */
        void onCheckChanger(int position, boolean isChecked);
    }
}
