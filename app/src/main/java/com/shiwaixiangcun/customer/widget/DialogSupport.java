package com.shiwaixiangcun.customer.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DialogSupport extends Dialog {

    RecyclerView mRvSupport;
    Button mBtnConfirm;
    List<GoodDetail.DataBean.ServicesBean> mList;
    private Context mContext;
    private OnCancelListener listener;
    private MyAdapter mAdapter;

    public DialogSupport(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public DialogSupport(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected DialogSupport(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.listener = cancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_support, null);
        setContentView(view);
        mRvSupport = (RecyclerView) view.findViewById(R.id.rv_support);
        mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int) (d.heightPixels * 0.7);
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        mList = new ArrayList<>();
        mAdapter = new MyAdapter();

    }

    public void setData(List<GoodDetail.DataBean.ServicesBean> list) {
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();


    }

    final class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public MyAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_support, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GoodDetail.DataBean.ServicesBean servicesBean = mList.get(position);
            holder.mTvDescSupport.setText(servicesBean.getRemark());
            holder.mTvTitleSupport.setText(servicesBean.getName());

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_title_support)
            TextView mTvTitleSupport;
            @BindView(R.id.tv_desc_support)
            TextView mTvDescSupport;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }


    }
}
