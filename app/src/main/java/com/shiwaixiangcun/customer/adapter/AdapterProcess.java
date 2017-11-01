package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RightDetailBean;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.widget.TimeLineMark;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/16.
 */

public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.RecordViewHolder> {

    private List<RightDetailBean.DataBean.ProcessBean> mRecordsList;

    private Context mContext;

    public AdapterProcess(Context context, List<RightDetailBean.DataBean.ProcessBean> recordsList) {
        this.mContext = context;
        this.mRecordsList = recordsList;
    }

    @Override
    public int getItemViewType(int position) {
        final int size = mRecordsList.size() - 1;
        if (size == 0)
            return AdapterProcess.ItemType.ATOM;
        else if (position == 0)
            return AdapterProcess.ItemType.START;
        else if (position == size)
            return AdapterProcess.ItemType.END;
        else return AdapterProcess.ItemType.NORMAL;
    }

    @Override
    public AdapterProcess.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new AdapterProcess.RecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_line, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(AdapterProcess.RecordViewHolder holder, int position) {
        RightDetailBean.DataBean.ProcessBean recordsBean = mRecordsList.get(position);
        holder.mTvDate.setText(DateUtil.getSecond(recordsBean.getTime()));
        holder.mTvRecord.setText(recordsBean.getMess());

    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }

    public void addData(List<RightDetailBean.DataBean.ProcessBean> recordsList) {
        mRecordsList.addAll(recordsList);
        notifyDataSetChanged();
    }

    public class ItemType {
        public final static int NORMAL = 0;

        public final static int HEADER = 1;
        public final static int FOOTER = 2;

        public final static int START = 4;
        public final static int END = 8;
        public final static int ATOM = 16;
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_time_line_mark)
        TimeLineMark mItemTimeLineMark;
        @BindView(R.id.tv_record)
        TextView mTvRecord;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        public RecordViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (type == AdapterProcess.ItemType.ATOM) {
                mItemTimeLineMark.setBeginLine(null);
                mItemTimeLineMark.setEndLine(null);
            } else if (type == AdapterProcess.ItemType.START) {
                mItemTimeLineMark.setBeginLine(null);
            } else if (type == AdapterProcess.ItemType.END) {
                mItemTimeLineMark.setEndLine(null);
            }

        }
    }

}

