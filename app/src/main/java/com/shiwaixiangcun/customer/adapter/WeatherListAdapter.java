package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.WeatherDataBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;



/**
 * Created by Administrator on 2016/7/13.
 */
public class WeatherListAdapter extends BaseAdapter {
    private List<WeatherDataBean.WeatherBeanX> list;
    private Context context;

    public WeatherListAdapter(List<WeatherDataBean.WeatherBeanX> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_weather_list, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_week = convertView.findViewById(R.id.tv_week);
            mViewHolder.tv_Temperature_range = convertView.findViewById(R.id.tv_Temperature_range);
            mViewHolder.tv_weather_conditions = convertView.findViewById(R.id.tv_weather_conditions);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        String week = getWeek(list.get(position).getDate());

        mViewHolder.tv_week.setText(week);
        List<String> day_range = list.get(position).getInfo().getDay();
        List<String> night_range = list.get(position).getInfo().getNight();

        if (isNumeric(day_range.get(2)) && isNumeric(night_range.get(2))){
            if (Integer.parseInt(day_range.get(2)) > Integer.parseInt(night_range.get(2))) {
                mViewHolder.tv_Temperature_range.setText(night_range.get(2) + "°-" + day_range.get(2)+"°C");
            } else {
                mViewHolder.tv_Temperature_range.setText(day_range.get(2) + "°-" + night_range.get(2)+"°C");
            }
        }else {
            mViewHolder.tv_Temperature_range.setText("");
        }

        mViewHolder.tv_weather_conditions.setText(day_range.get(1));
        return convertView;
    }

    private String getWeek(String pTime) {


        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = c.get(Calendar.DAY_OF_WEEK);
        if (i == 1) {

            Week = "星期日";
        }
        if (i == 2) {
            Week = "星期一";
        }
        if (i == 3) {
            Week = "星期二";
        }
        if (i == 4) {
            Week = "星期三";
        }
        if (i == 5) {
            Week = "星期四";
        }
        if (i == 6) {
            Week = "星期五";
        }
        if (i == 7) {

            Week = "星期六";
        }



        return Week;
    }

    static class ViewHolder {
        TextView tv_week;
        TextView tv_Temperature_range;
        TextView tv_weather_conditions;
    }

}
