package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.WeatherListAdapter;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.presenter.impl.WeatherImpl;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyListView;
import com.shiwaixiangcun.customer.ui.IWeatherView;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener ,IWeatherView{

    private ChangeLightImageView back_left;
    private LinearLayout id_gallery;
    private List<String> list_weather_future = new ArrayList<>();
    private MyListView mlv_recently;
    private ScrollView sv_weather;
    private LinearLayout ll_switch_city;
    private  List<WeatherDataBean.WeatherBeanX> list_weather_weeks = new ArrayList<>();
    private WeatherListAdapter weatherListAdapter;
    private TextView tv_aqi_quality;
    private TextView tv_today_temperature;
    private TextView tv_today_temperature_range;
    private TextView tv_weather_conditions;
    private static final int WITCH_LOAD = 2010;
    private WeatherImpl weather;
    private TextView tv_city_switch_name;
    private TextView tv_du;
    private ImageView iv_switch_image;
    private LinearLayout ll_air_zw;
    private LinearLayout ll_future;
    private LinearLayout ll_recently;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initLayout();
        initData();
    }

    private void initLayout() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        id_gallery = (LinearLayout) findViewById(R.id.id_gallery);
        mlv_recently = (MyListView) findViewById(R.id.mlv_recently);
        sv_weather = (ScrollView) findViewById(R.id.sv_weather);
        ll_switch_city = (LinearLayout) findViewById(R.id.ll_switch_city);
        tv_aqi_quality = (TextView) findViewById(R.id.tv_aqi_quality);
        tv_today_temperature = (TextView) findViewById(R.id.tv_today_temperature);
        tv_today_temperature_range = (TextView) findViewById(R.id.tv_today_temperature_range);
        tv_weather_conditions = (TextView) findViewById(R.id.tv_weather_conditions);
        tv_city_switch_name = (TextView) findViewById(R.id.tv_city_switch_name);
        tv_du = (TextView) findViewById(R.id.tv_du);
        iv_switch_image = (ImageView) findViewById(R.id.iv_switch_image);
        ll_air_zw = (LinearLayout) findViewById(R.id.ll_air_zw);
        ll_future = (LinearLayout) findViewById(R.id.ll_future);
        ll_recently = (LinearLayout) findViewById(R.id.ll_recently);
    }

    private void initData() {
        weather = new WeatherImpl(this);
        weather.setBgaAdpaterAndClick(this,"101260209");

        sv_weather.smoothScrollTo(0, 20);
        sv_weather.setFocusable(true);
        back_left.setOnClickListener(this);
        ll_switch_city.setOnClickListener(this);
        for (int i = 0; i < 10; i++) {
            list_weather_future.add("ghhaa" + i);
        }


        weatherListAdapter = new WeatherListAdapter(list_weather_weeks,this);
        mlv_recently.setAdapter(weatherListAdapter);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.ll_switch_city:
                Intent intent = new Intent(WeatherActivity.this,SwitchCityActivity.class);
                startActivityForResult(intent,WITCH_LOAD);
                break;
        }
    }


    private void getlt(final List<WeatherDataBean.HourlyForecastBean> list_weather_day) {

        for (int i = 0; i < list_weather_day.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_weather_future, id_gallery, false);
            TextView tv_time_day = (TextView) view.findViewById(R.id.tv_time_day);
            TextView tv_weather_temperature = (TextView) view.findViewById(R.id.tv_weather_temperature);
            TextView tv_weahter_conditions_day = (TextView) view.findViewById(R.id.tv_weahter_conditions_day);
//            ImageView iv_certificates = (ImageView) view.findViewById(R.id.iv_certificates);
//            if (Utils.isNotEmpty(certificates.get(i).getThumbImageURL())) {
//                Picasso.with(this).load(certificates.get(i).getThumbImageURL()).into(iv_certificates);
//            }
//            iv_certificates.setBackgroundColor(Color.parseColor("#332D3230"));
            tv_time_day.setText(list_weather_day.get(i).getHour()+"时");
            tv_weather_temperature.setText(list_weather_day.get(i).getTemperature()+"°");
            tv_weahter_conditions_day.setText(list_weather_day.get(i).getInfo());
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String url_bit_image = certificates.get(finalI).getAccessUrl();
                    Log.i("vvvviiia", "----------aaa" + finalI);
//                    Intent intent = new Intent(SurroundDetailActivity.this,BigImageActivity.class);
//                    intent.putExtra("bigimagelist",(Serializable) certificates);
//                    intent.putExtra("serid",finalI);
//                    startActivity(intent);
                }
            });
            id_gallery.addView(view);
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(WeatherDataBean result) {
        tv_du.setVisibility(View.VISIBLE);
        iv_switch_image.setVisibility(View.VISIBLE);
        ll_air_zw.setVisibility(View.VISIBLE);
        ll_future.setVisibility(View.VISIBLE);
        ll_recently.setVisibility(View.VISIBLE);
        tv_city_switch_name.setVisibility(View.VISIBLE);
        list_weather_weeks.clear();
        List<WeatherDataBean.HourlyForecastBean> hourly_forecast = result.getHourly_forecast();

        getlt(hourly_forecast);

        List<WeatherDataBean.WeatherBeanX> weather = result.getWeather();

        list_weather_weeks.addAll(weather);
        weatherListAdapter.notifyDataSetChanged();

        tv_aqi_quality.setText(result.getPm25().getAqi()+"  "+result.getPm25().getQuality());
        tv_today_temperature.setText(result.getRealtime().getWeather().getTemperature());

        if (isNumeric(weather.get(0).getInfo().getDay().get(2)) && isNumeric(weather.get(0).getInfo().getNight().get(2))){
            if (Integer.parseInt(weather.get(0).getInfo().getDay().get(2)) > Integer.parseInt(weather.get(0).getInfo().getNight().get(2))) {
                tv_today_temperature_range.setText(weather.get(0).getInfo().getNight().get(2) + "°-" + weather.get(0).getInfo().getDay().get(2)+"°C");
            } else {
                tv_today_temperature_range.setText(weather.get(0).getInfo().getDay().get(2) + "°-" + weather.get(0).getInfo().getNight().get(2)+"°C");
            }
        }else {
            tv_today_temperature_range.setText("");
        }

        tv_weather_conditions.setText(result.getRealtime().getWeather().getInfo());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WITCH_LOAD){
            if (data != null){

                String cityRetureCode = data.getStringExtra("cityRetureCode");
                String cityRetureName = data.getStringExtra("cityRetureName");

                weather.setBgaAdpaterAndClick(this,cityRetureCode);
                Log.e("ffffffffffllll",cityRetureCode);
                tv_city_switch_name.setText(cityRetureName);
            }
        }
    }
}
