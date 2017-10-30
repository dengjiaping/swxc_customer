package com.shiwaixiangcun.customer.adapter;/**
 * Author:Administrator
 * Date:  2017/9/13
 * Desc： eg
 */

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.interfaces.onCheckboxClickListener;
import com.shiwaixiangcun.customer.model.AddressBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AdapterManageAddress extends BaseQuickAdapter<AddressBean, BaseViewHolder> {


    private onCheckboxClickListener mCheckboxClickListener;

    public AdapterManageAddress(@Nullable List<AddressBean> data) {
        super(R.layout.manage_address, data);
    }

    public void setCheckboxClickListener(onCheckboxClickListener checkboxClickListener) {
        this.mCheckboxClickListener = checkboxClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, AddressBean item) {
        CheckBox checkBox = helper.getView(R.id.cb_default);
        checkBox.setOnCheckedChangeListener(null);
        helper.setText(R.id.tv_user_name, item.getDeliveryName());
        helper.setText(R.id.tv_user_phone, item.getDeliveryPhone());
        helper.setText(R.id.tv_address, item.getDeliveryAddress());
        helper.setChecked(R.id.cb_default, item.isDefaulted());
        if (item.isDefaulted()) {
            helper.getView(R.id.cb_default).setClickable(false);
            helper.setText(R.id.tv_default, "默认地址");
        }
        helper.addOnClickListener(R.id.tv_delete);
        helper.addOnClickListener(R.id.tv_edit);
        helper.setOnCheckedChangeListener(R.id.cb_default, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckboxClickListener != null) {
                    mCheckboxClickListener.checkboxClick(helper.getAdapterPosition(), helper.getConvertView(), b);
                }
            }
        });
    }
}
