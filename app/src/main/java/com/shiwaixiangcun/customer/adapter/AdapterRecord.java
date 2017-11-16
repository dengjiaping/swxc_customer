package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AfterServiceDetail;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.widget.TimeLineMark;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * @date 2017/10/13
 */

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.RecordViewHolder> {

    private List<AfterServiceDetail.DataBean.RecordsBean> mRecordsList;

    private Context mContext;

    public AdapterRecord(Context context, List<AfterServiceDetail.DataBean.RecordsBean> recordsList) {
        this.mContext = context;
        this.mRecordsList = recordsList;
    }

    @Override
    public int getItemViewType(int position) {
        final int size = mRecordsList.size() - 1;
        if (size == 0) {
            return ItemType.ATOM;
        } else if (position == 0) {
            return ItemType.START;
        } else if (position == size) {
            return ItemType.END;
        } else {
            return ItemType.NORMAL;
        }
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_line, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        AfterServiceDetail.DataBean.RecordsBean recordsBean = mRecordsList.get(position);
        holder.mTvDate.setText(DateUtil.getSecond(recordsBean.getTime()));
        holder.mTvRecord.setText(recordsBean.getContent());

    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }

    public void addData(List<AfterServiceDetail.DataBean.RecordsBean> recordsList) {
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
            if (type == ItemType.ATOM) {
                mItemTimeLineMark.setBeginLine(null);
                mItemTimeLineMark.setEndLine(null);

            } else if (type == ItemType.START) {
                mItemTimeLineMark.setBeginLine(null);
                mItemTimeLineMark.setMarkerDrawable(mContext.getResources().getDrawable(R.drawable.shape_green_circle));
            } else if (type == ItemType.END) {
                mItemTimeLineMark.setEndLine(null);
            }

        }
    }

}
