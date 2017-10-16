package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.OrderBean;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class AdapterOrder extends BaseQuickAdapter<OrderBean.ElementsBean, BaseViewHolder> {

    public AdapterOrder(@Nullable List<OrderBean.ElementsBean> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.ElementsBean item) {
        OrderBean.ElementsBean.OrderDetailDtoListBean orderDetailDtoListBean = item.getOrderDetailDtoList().get(0);
        helper.setText(R.id.tv_order_number, item.getOrderNumber());
        helper.setText(R.id.tv_description, "共" + item.getGoodsTotal() + "件商品 合计 ￥"
                + ArithmeticUtils.format(item.getRealyPay()) + " (含运费 ￥" + ArithmeticUtils.format(item.getTransportMoney()) + ")");
        Button mBtnWhite = helper.getView(R.id.btn_white);
        Button mBtnRed = helper.getView(R.id.btn_red);
        LinearLayout llayoutStature = helper.getView(R.id.llayout_stature);
        ImageView mIvCover = helper.getView(R.id.iv_cover);
        helper.setText(R.id.tv_title, orderDetailDtoListBean.getGoodsName());
        helper.setText(R.id.tv_desc, orderDetailDtoListBean.getGoodsAttrDescription());
        helper.setText(R.id.tv_price, "￥ " + ArithmeticUtils.format(orderDetailDtoListBean.getPrice()));
        helper.setText(R.id.tv_order_amount, "x " + orderDetailDtoListBean.getGoodsAmount());
        helper.addOnClickListener(R.id.btn_red);
        helper.addOnClickListener(R.id.btn_white);
        ImageDisplayUtil.showImageView(mContext, orderDetailDtoListBean.getThumbImageURL(), mIvCover);
        switch (item.getOrderStatus()) {
            case "WaitDeliver":
                helper.setText(R.id.tv_order_stature, "等待发货");
                mBtnWhite.setVisibility(View.GONE);
                mBtnRed.setVisibility(View.GONE);
                llayoutStature.setVisibility(View.GONE);
                break;
            case "Closed":
                helper.setText(R.id.tv_order_stature, "已关闭");
                llayoutStature.setVisibility(View.VISIBLE);
                mBtnWhite.setVisibility(View.VISIBLE);
                mBtnRed.setVisibility(View.GONE);
                mBtnWhite.setText("删除订单");
                break;
            case "WaitPay":
                helper.setText(R.id.tv_order_stature, "等待付款");
                llayoutStature.setVisibility(View.VISIBLE);
                mBtnRed.setVisibility(View.VISIBLE);
                mBtnWhite.setVisibility(View.VISIBLE);
                mBtnWhite.setText("取消订单");
                mBtnRed.setText("付款");


                break;
            case "Delivered":
                helper.setText(R.id.tv_order_stature, "已发货");
                llayoutStature.setVisibility(View.VISIBLE);
                mBtnWhite.setVisibility(View.GONE);
                mBtnRed.setVisibility(View.VISIBLE);
                mBtnRed.setText("确认收货");
                break;
            case "Finished":
                helper.setText(R.id.tv_order_stature, "已完成");
                llayoutStature.setVisibility(View.GONE);
                llayoutStature.setVisibility(View.VISIBLE);
                mBtnWhite.setVisibility(View.VISIBLE);
                mBtnRed.setVisibility(View.GONE);
                mBtnWhite.setText("评价");

        }

    }
}
