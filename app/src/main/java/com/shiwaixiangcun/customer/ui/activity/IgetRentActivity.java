package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.HouseGetRentImpl;
import com.shiwaixiangcun.customer.ui.IHouseGetRentView;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

public class IgetRentActivity extends AppCompatActivity implements View.OnClickListener, IHouseGetRentView {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private Button btn_submit_get_rent;
    private RelativeLayout rl_success_submit;
    private Button btn_ok;
    private EditText et_get_rent_money;
    private EditText et_house_size;
    private EditText post_content;
    private int MIN_MARK = 0;
    private int MAX_MARK = 1000000;
    private TextView tv_et_getrent;
    private TextWatcher watcher = new TextWatcher(){

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("TAG","[TextWatcher][onTextChanged]"+s);
            if (Utils.isNotEmpty(s.toString())){
                tv_et_getrent.setText(s.toString()+" (元/月)");
            }else {
                tv_et_getrent.setText("");
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
                        Toast.makeText(getBaseContext(), "不能超过"+MAX_MARK, Toast.LENGTH_SHORT).show();
                        tv_et_getrent.setText(String.valueOf(MAX_MARK));
                        et_get_rent_money.setText(String.valueOf(MAX_MARK));
                    }
                    return;
                }
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iget_rent);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = findViewById(R.id.back_left);
        tv_page_name = findViewById(R.id.tv_page_name);
        btn_submit_get_rent = findViewById(R.id.btn_submit_get_rent);
        rl_success_submit = findViewById(R.id.rl_success_submit);
        btn_ok = findViewById(R.id.btn_ok);
        et_get_rent_money = findViewById(R.id.et_get_rent_money);
        et_house_size = findViewById(R.id.et_house_size);
        post_content = findViewById(R.id.post_content);
        tv_et_getrent = findViewById(R.id.tv_et_getrent);

        et_get_rent_money.addTextChangedListener(watcher);

    }

    private void initData() {
        tv_page_name.setText("我要租房");
        back_left.setOnClickListener(this);
        btn_submit_get_rent.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_submit_get_rent:
//                if (!Utils.isNotEmpty(et_get_rent_money.getText().toString().trim())){
//                    Toast.makeText(this,"请填写您的预期租房价格",Toast.LENGTH_LONG).show();
//                    return;
//                }else if (!Utils.isNotEmpty(et_house_size.getText().toString().trim())){
//                    Toast.makeText(this,"请填写住房户型或商铺面积",Toast.LENGTH_LONG).show();
//                    return;
//                }else if (!Utils.isNotEmpty(post_content.getText().toString().trim())){
//                    Toast.makeText(this,"请填写其他要求",Toast.LENGTH_LONG).show();
//                    return;
//                }
                String s_total_get = "预期价格:"+et_get_rent_money.getText().toString().trim() + "\n" +"户型或面积:"+ et_house_size.getText().toString().trim() + "\n" +"其他要求:" +post_content.getText().toString().trim();
                HouseGetRentImpl houseGetRent = new HouseGetRentImpl(this, s_total_get);
                houseGetRent.setBgaAdpaterAndClick(this);
                break;
            case R.id.btn_ok:
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        if (result.getResponseCode() == 1001){
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
