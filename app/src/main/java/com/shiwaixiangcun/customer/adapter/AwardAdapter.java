package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AwardBean;
import com.shiwaixiangcun.customer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class AwardAdapter extends BaseAdapter {
    private List<AwardBean.DataBean.ElementsBean> list;
    private Context context;

    public AwardAdapter(List<AwardBean.DataBean.ElementsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_award, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_award = (ImageView) convertView.findViewById(R.id.iv_award);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (Utils.isNotEmpty(list.get(position).getCoverPath().getAccessUrl())) {


            Picasso.with(context).load(list.get(position).getCoverPath().getAccessUrl()).into(mViewHolder.iv_award);


//                Bitmap bitmap = null;
//                try {
//                    bitmap = Picasso.with(context).load(list.get(position).getCoverPath()).get();
//                    mViewHolder.iv_award.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

        }
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_award;
    }


    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
