package com.shiwaixiangcun.customer.model;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.CircleImageView;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/11/10
 */

public class AdapterDoctor extends BaseQuickAdapter<DoctorBean.ElementsBean, BaseViewHolder> {
    public AdapterDoctor(@Nullable List<DoctorBean.ElementsBean> data) {
        super(R.layout.item_doctor, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorBean.ElementsBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_title_of_doctor, item.getTitleOfDoctor());
        helper.setText(R.id.tv_department, item.getDepartment());
        helper.setText(R.id.tv_good_filed, item.getGoodField());
        helper.setText(R.id.tv_inaugural_ospital, "擅长:" + item.getInauguralHospital());
        CircleImageView civAvatar = helper.getView(R.id.civ_avatar);
        ImageDisplayUtil.showImageView(mContext, item.getAvatar(), civAvatar);

    }
}
