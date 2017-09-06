package com.shiwaixiangcun.customer.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.presenter.impl.HouseGetBuyImpl;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.ui.IHouseGetBuyView;

public class IgetBuyActivity extends AppCompatActivity implements View.OnClickListener, IHouseGetBuyView {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private Button btn_get_buy_house;
    private RelativeLayout rl_success_submit;
    private Button btn_ok;
    private EditText et_buy_house;
    private EditText et_buy_house_size;
    private EditText post_content;
    private TextView tv_et_getbuy;

    private int MIN_MARK = 0;
    private int MAX_MARK = 10000;
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG", "[TextWatcher][onTextChanged]" + s);
            if (Utils.isNotEmpty(s.toString())) {
                tv_et_getbuy.setText(s.toString() + " (万元)");
            } else {
                tv_et_getbuy.setText("");
            }

            if (s != null && !s.equals("")) {
                if (MIN_MARK != -1 && MAX_MARK != -1) {
                    int markVal = 0;
                    try {
                        markVal = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e) {
                        markVal = 0;
                    }
                    if (markVal > MAX_MARK) {
                        Toast.makeText(getBaseContext(), "不能超过"+MAX_MARK+"万", Toast.LENGTH_SHORT).show();
                        tv_et_getbuy.setText(String.valueOf(MAX_MARK));
                        et_buy_house.setText(String.valueOf(MAX_MARK));
                    }
                    return;
                }
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iget_buy);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        btn_get_buy_house = (Button) findViewById(R.id.btn_get_buy_house);
        rl_success_submit = (RelativeLayout) findViewById(R.id.rl_success_submit);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        et_buy_house = (EditText) findViewById(R.id.et_buy_house);
        et_buy_house_size = (EditText) findViewById(R.id.et_buy_house_size);
        post_content = (EditText) findViewById(R.id.post_content);
        tv_et_getbuy = (TextView) findViewById(R.id.tv_et_getbuy);

        et_buy_house.addTextChangedListener(watcher);

    }

    private void initData() {
        tv_page_name.setText("我要买房");
        back_left.setOnClickListener(this);
        btn_get_buy_house.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_get_buy_house:
//                if (!Utils.isNotEmpty(et_buy_house.getText().toString().trim())){
//                    Toast.makeText(this,"请填写您的预期买房价格",Toast.LENGTH_LONG).show();
//                    return;
//                }else if (!Utils.isNotEmpty(et_buy_house_size.getText().toString().trim())){
//                    Toast.makeText(this,"请填写住房户型或商户面积",Toast.LENGTH_LONG).show();
//                    return;
//                }else if (!Utils.isNotEmpty(post_content.getText().toString().trim())){
//                    Toast.makeText(this,"请填写其他要求",Toast.LENGTH_LONG).show();
//                    return;
//                }
                String s_total_buy = "预期价格:" + et_buy_house.getText().toString().trim() + "\n" + "户型或面积:" + et_buy_house_size.getText().toString().trim() + "\n" + "其他要求:" + post_content.getText().toString().trim();
                HouseGetBuyImpl houseGetBuy = new HouseGetBuyImpl(this, s_total_buy);
                houseGetBuy.setBgaAdpaterAndClick(this);
                break;
            case R.id.btn_ok:
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001) {
            rl_success_submit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
