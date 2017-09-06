package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AllCityAdapter;
import com.shiwaixiangcun.customer.model.DefaultCityBean;
import com.shiwaixiangcun.customer.presenter.impl.CityDefaultImpl;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.ui.ICityDefaultView;

import java.util.ArrayList;
import java.util.List;

public class SwitchCityActivity extends AppCompatActivity implements View.OnClickListener,ICityDefaultView{

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private ListView lv_all_place;

    private List<String> list_city = new ArrayList<>();
    private List<DefaultCityBean.DataBean> list_default_city = new ArrayList<>();
    private AllCityAdapter allCityAdapter;
    private AllCityAdapter allCityAdapter_s;
    private EditText et_query_city;

    private ListView lv_search_result;

    private List<DefaultCityBean.DataBean> list_search_city = new ArrayList<>();
    private LinearLayout ll_search_before;
    private LinearLayout ll_search_after;
    private RelativeLayout rl_default_teb;
    private List<DefaultCityBean.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_city);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initLayout();
        initData();
    }

    private void initLayout() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        lv_all_place = (ListView) findViewById(R.id.lv_all_place);
        et_query_city = (EditText) findViewById(R.id.et_query_city);

        lv_search_result = (ListView) findViewById(R.id.lv_search_result);
        ll_search_before = (LinearLayout) findViewById(R.id.ll_search_before);
        ll_search_after = (LinearLayout) findViewById(R.id.ll_search_after);
        rl_default_teb = (RelativeLayout) findViewById(R.id.rl_default_teb);
    }

    private void initData() {
        final CityDefaultImpl CityDefaultImpl = new CityDefaultImpl(this);
        CityDefaultImpl.setBgaAdpaterAndClick(this);
        tv_page_name.setText("切换城市");
        for (int i = 0; i < 10; i++) {
            list_city.add("rrrrrraa"+i);
        }
//        allCityAdapter = new AllCityAdapter(list_default_city,this);
//        lv_all_place.setAdapter(allCityAdapter);


        allCityAdapter_s = new AllCityAdapter(list_search_city,this);
        lv_search_result.setAdapter(allCityAdapter_s);

        back_left.setOnClickListener(this);
        rl_default_teb.setOnClickListener(this);


        et_query_city.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("bbbbbbbbbbnnnnnaaonTextChanged", "-----------"+s);
                String trim = s.toString().trim();
                if (trim.length() > 0){
                    ll_search_before.setVisibility(View.GONE);
                    ll_search_after.setVisibility(View.VISIBLE);


                }else {
                    ll_search_before.setVisibility(View.VISIBLE);
                    ll_search_after.setVisibility(View.GONE);


                }

                CityDefaultImpl.setSearchCityClic(SwitchCityActivity.this,s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("bbbbbbbbbbnnnnnaaafterTextChanged", "-----------"+editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                Log.e("bbbbbbbbbbnnnnnaabeforeTextChanged", "-----------"+s);

            }


        });



        lv_all_place.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("bbbbbmmmbmbmn",i+"-----"+list_default_city.size());
                Intent intent = new Intent();
                intent.putExtra("cityRetureCode",data.get(i).getCityCode());
                intent.putExtra("cityRetureName",data.get(i).getCityName());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        lv_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("bbbbbmmmbmbmn",i+"");
                Intent intent = new Intent();
                intent.putExtra("cityRetureCode",list_search_city.get(i).getCityCode());
                intent.putExtra("cityRetureName",list_search_city.get(i).getCityName());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_default_teb:

                Intent intent = new Intent();
                intent.putExtra("cityRetureCode","101260208");
                intent.putExtra("cityRetureName","天鹅堡");
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(DefaultCityBean result) {
        list_default_city.clear();
        data = result.getData();

//        list_default_city.addAll(data);
//        allCityAdapter.notifyDataSetChanged();
        Log.e("aaaaaaaaaaaa", data.size()+"");
        allCityAdapter = new AllCityAdapter(data,this);
        lv_all_place.setAdapter(allCityAdapter);
        allCityAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSearchCityResult(DefaultCityBean result) {
        list_search_city.clear();
        List<DefaultCityBean.DataBean> data = result.getData();

        list_search_city.addAll(data);
        allCityAdapter_s.notifyDataSetChanged();
    }
}
